package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	private static final double DELTA = 0.1;
	
	public RayTracerBasic(Scene Sc) {
		super(Sc);
	}
	
	
	private boolean unshaded(LightSource light,GeoPoint gp, Vector l, Vector n)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay,light.getDistance(gp.point));
		return intersections == null;
		/*Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point po = gp.point.add(epsVector);
		Ray lightRay = new Ray(po,lightDirection,n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) 
			return true;
		return false;*/
	}
	@Override
	public Color traceRay(Ray ray) {
		var intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}

	
	/**
	 * Calculates the color of a point giving
	 * @param point - point on image
	 * @return the color in this point
	 */
	private Color calcColor(GeoPoint geo, Ray ray) {
		return scene.ambientLight.getIntensity()
				.add(geo.geometry.getEmission())
				.add(calcLocalEffects(geo, ray));
	}

	/**
	 * help to calculate "calcColor" - calculated light contribution from all light sources
	 * @param intersection - point with light
	 * @param ray - ray from the camera
	 * @return calculated light contribution from all light sources
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point).normalize();
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		Double3 kd = intersection.geometry.getMaterial().kD;
		Double3 ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point).normalize();
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				if (unshaded(lightSource,intersection , l, n)) 
				{
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(calcDiffusive(kd, l, n, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}
	
	/**
	 * calculate the diffusive light according to Phong's model
	 * @param kd - Coefficient for diffusive
	 * @param l - vector from light source
	 * @param n - normal to the point
	 * @param lightIntensity - Light intensity
	 * @return the diffusive light
	 */
	private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale((kd.scale(Math.abs(l.dotProduct(n)))));
	}
	
	/**
	 * calculate the specular light according to Phong's model
	 * @param ks - Coefficient for specular
	 * @param l - vector from light source
	 * @param n - normal to the point
	 * @param v - camera vector
	 * @param nShininess - exponent
	 * @param lightIntensity - Light intensity
	 * @return the specular light
	 */
	private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.add(n.scale(-2*n.dotProduct(l))).normalize();
		double result = -Util.alignZero(v.dotProduct(r));
		if (result <= 0)
			return Color.BLACK;
		return lightIntensity.scale(ks.scale(Math.pow(result, nShininess)));

	}

	

}

package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;


	
	public RayTracerBasic(Scene Sc) {
		super(Sc);
	}
	
	
	@Override
	public Color traceRay(Ray ray) {
		var closestPoint = findClosestIntersection(ray);
		if (closestPoint == null)
			return scene.background;
		return calcColor(closestPoint, ray);
	}

	/**
	 * Calculates the color of a given point from camera ray
	 * 
	 * @param ray - ray from the camera
	 * @param geo - point on geometry body
	 * @return the color in this point
	 */
	private Color calcColor(GeoPoint geo, Ray ray) {
		return calcColor(geo, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Recursive function to calculates the color of a given point from camera ray
	 * 
	 * @param closestPoint - point on geometry body
	 * @param ray          - ray from the camera
	 * @param level        - level of Recursion.
	 * @param k            - the current attenuation level
	 * @return the color in this point
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray, int level, double k) {
		if (closestPoint == null)
			return scene.background;
		Color color = closestPoint.geometry.getEmission().add(calcLocalEffects(closestPoint, ray, k));
		return 1 == level ? color : color.add(calcGlobalEffects(closestPoint, ray, level, k));
	}

	/**
	 * calculates light contribution with consideration for transparency and
	 * reflection
	 * 
	 * @param closestPoint - point on geometry body
	 * @param ray          - ray from the camera
	 * @param level        - level of Recursion.
	 * @param k            - the current attenuation level
	 * @return with consideration for transparency and reflection
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		Double3 kr = material.kR.scale(k);
		double kkr = ;
		Vector v = ray.getDir();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = clacRayReflection(n, v, geopoint.point, nv);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		Double3 kt = material.kT.scale(k);
		double kkt = kt * k;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = clacRayRefraction(n, v, geopoint.point, nv);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
		}
		return color;
	}

	/**
	 * help to calculate "calcColor" - calculated light contribution from all light
	 * sources
	 * 
	 * @param intersection - point on geometry body
	 * @param ray          - ray from the camera
	 * @param k            - the current attenuation level
	 * @return calculated light contribution from all light sources
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		var material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		Double3 kd = material.kD;
		Double3 ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, nl, lightIntensity),
							calcSpecular(ks, n, l, nl, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
	 * calculate the reflected ray
	 * 
	 * @param n  - normal to the point on geometry
	 * @param v  - camera vector
	 * @param p  - point on geometry body
	 * @param nv - equal to n.dotProduct(v)
	 * @return reflected ray
	 */
	private Ray clacRayReflection(Vector n, Vector v, Point p, double nv) {
		Vector r = v.add(n.scale(-2 * nv));
		return new Ray(p, r, n);
	}

	/**
	 * calculate the refracted ray
	 * 
	 * @param n  - normal to the point on geometry
	 * @param v  - camera vector
	 * @param p  - point on geometry body
	 * @param nv - equal to n.dotProduct(v)
	 * @return refracted ray
	 */
	private Ray clacRayRefraction(Vector n, Vector v, Point p, double nv) {
		return new Ray(p, v, n);
	}

	/**
	 * calculate the diffusive light according to Phong's model
	 * @param kd - Coefficient for diffusive
	 * @param l - vector from light source
	 * @param n - normal to the point
	 * @param lightIntensity - Light intensity
	 * @return the diffusive light
	 */
	private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
		return lightIntensity.scale((kd.scale(nl >= 0 ? nl : -nl)));
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
	private Color calcSpecular(Double3 ks, Vector l, Vector n,double nl, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.add(n.scale(-2*n.dotProduct(l))).normalize();
		double result = Util.alignZero(v.dotProduct(r));
		if (result >= 0)
			return Color.BLACK;
		return lightIntensity.scale(ks.scale(Math.pow(result, nShininess)));

	}
	


	/**
	 * For shading test between point and light source
	 * 
	 * @param light - light source
	 * @param l     - vector from light
	 * @param n     - normal of body
	 * @param gp    - point in geometry body
	 * @return
	 *         <li>true - if unshaded
	 *         <li>false - if shaded
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay);

		if (intersections == null)
			return true;
		double lightDistance = light.getDistance(gp.point);
		for (GeoPoint geopoint : intersections) {
			if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0
					&& geopoint.geometry.getMaterial().kT == 0)
				return false;
		}
		return true;
	}

	/**
	 * calculates the amount of shadow in the point sometimes we need light shadow
	 * and sometimes not
	 * 
	 * @param light - light source
	 * @param l     - vector from light
	 * @param n     - normal of body
	 * @param gp    - point in geometry body
	 * @return amount of shadow
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		var intersections = scene.geometries.findGeoIntersections(lightRay);
		double lightDistance = light.getDistance(gp.point);
		if (intersections == null)
			return 1.0;
		double ktr = 1.0;
		for (GeoPoint geopoint : intersections) {
			if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0)
				ktr *= geopoint.geometry.getMaterial().kT;
			if (ktr < MIN_CALC_COLOR_K)
				return 0.0;
		}
		return ktr;
	}

	/**
	 * Return the closest intersection point with the ray. if there is no
	 * intersection it returns null
	 * 
	 * @param ray Ray that intersect
	 * @return geoPoint of the closest point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null) {
			return null;
		}
		return ray.findClosestGeoPoint(intersections);
	}
	

}

package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	
	public RayTracerBasic(Scene Sc) {
		super(Sc);
	}

	public Color traceRay(Ray ray) {
		List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
		if(points!=null)
			  return calcColor(ray.findClosestGeoPoint(points));
		return scene.background;
	}
	public Color calcColor(GeoPoint p) {
		return this.scene.ambientLight.getIntensity();
	}
	

}

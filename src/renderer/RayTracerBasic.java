package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

	
	public RayTracerBasic(Scene Sc) {
		super(Sc);
	}

	public Color traceRay(Ray ray) {
		List<Point> points = scene.geometries.findIntsersections(ray);
		Point p;
		if(points!=null)
			  return calcColor(ray.findClosestPoint(points));
		return scene.background;
	}
	public Color calcColor (Point p) {
		return this.scene.ambientLight.getIntensity();
	}
	

}

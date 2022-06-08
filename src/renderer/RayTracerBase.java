package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {

	protected Scene scene;
	public RayTracerBase(Scene Sc)
	{
		this.scene=Sc;
	}
	public abstract Color traceRay (Ray ray);
	/**
	 * Calculating the color of the starting-point by the given main-ray constructed from it, a beam of rays
	 * sorrunding this ray and the beam's intersections with the scene
	 * @param ray The ray that was constructed - it's starting-point is the point we want to color
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @return The color of the starting-point of the given main-ray
	 */
	public abstract Color traceRay(Ray ray, int numOfRays);

	
	/**
	 * Calculating the color of the starting-point by the given main-ray constructed from it, a beam of rays
	 * sorrunding this ray and the beam's intersections with the scene.
	 * Includes adaptive-supersampling for performance improvement, if required.
	 * @param ray The ray that was constructed - it's starting-point is the point we want to color
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The color of the starting-point of the given main-ray
	 */
	public abstract Color traceRay(Ray ray, int numOfRays, boolean adaptiveSupersampling);

}




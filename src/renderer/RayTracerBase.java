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
}

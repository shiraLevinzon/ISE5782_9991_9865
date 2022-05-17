package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

public class Scene {
		public String name;
		public Color background;
		public AmbientLight ambientLight ;
		public Geometries geometries;
	    public List<LightSource> lights=new LinkedList<>();

		
		public Scene setLights(List<LightSource> lights) {
			this.lights = lights;
			return this;
		}

		public Scene(String nam)
		{
			this.name=nam;
			background=Color.BLACK;
			ambientLight=new AmbientLight() ;
			geometries =new Geometries();
		}

		public Scene setBackground(Color background) {
			this.background = background;
			return this;
		}

		public Scene setAmbientLight(AmbientLight ambientLight) {
			this.ambientLight = ambientLight;
			return this;
		}

		public Scene setGeometries(Geometries geometries) {
			this.geometries = geometries;
			return this;
		}

}

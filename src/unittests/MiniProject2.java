

package unittests;

import java.util.List;

import org.junit.Test;

import lighting.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.*;
import scene.Scene;



/**
 * Testing Mini Project 2 - FINAL PICTURE WITH SOFT SHADOWS AND ADAPTIVE SUPER-SAMPLING
 */
public class MiniProject2 {
	
	private Scene scene = new Scene("Test scene").setBackground(new Color(12,156,139));
	
	
	
	@Test
	public void MiniProject2ThePictureFromMiniProject1AdaptiveSupersampling() {
		
		Scene scene1 = new Scene("Test scene").setBackground(new Color(193,243,255)).setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),new Double3(0.15)));
		Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);
		
		scene1.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
				new Sphere(new Point(0, 0, -115), 25) //
						.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
				new Sphere(new Point(50, -50, -70), 20) //
						.setEmission(new Color(java.awt.Color.GRAY)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setkT(0.15).setnShininess(30)),
				new Sphere(new Point(-80, -80, -115), 50) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setkT(0.8).setnShininess(30)),
				new Sphere(new Point(-80, -80, -115), 25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)));

		scene1.lights.addAll(List.of(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), 10, new Vector(0, 0, -1)) //
												.setkL(4E-5).setkQ(2E-7),
									new SpotLight(new Color(java.awt.Color.RED), new Point(15, -70, -70), 10, new Vector(1, 0.4, 0))));

		ImageWriter imageWriter = new ImageWriter("MiniProject2PictureFromMiniProject1AdaptiveSupersampling", 600, 600);

				camera1.setImageWriter(imageWriter) //
				.setRayTracerBase(new RayTracerBasic(scene1))
				.setMultithreading(3)
				.setDebugPrint().renderImage(121, true).writeToImage();		
	}
	
	
	@Test
	public void MiniProject2ThePictureFromMiniProject1NoAdaptiveSupersampling() {
		
		Scene scene1 = new Scene("Test scene").setBackground(new Color(193,243,255)).setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),new Double3(0.15)));
		Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);
		
		scene1.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
				new Sphere(new Point(0, 0, -115), 25) //
						.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
				new Sphere(new Point(50, -50, -70), 20) //
						.setEmission(new Color(java.awt.Color.GRAY)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setkT(0.15).setnShininess(30)),
				new Sphere(new Point(-80, -80, -115), 50) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setkT(0.8).setnShininess(30)),
				new Sphere(new Point(-80, -80, -115), 25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)));

		scene1.lights.addAll(List.of(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), 10, new Vector(0, 0, -1)) //
												.setkL(4E-5).setkQ(2E-7),
									new SpotLight(new Color(java.awt.Color.RED), new Point(15, -70, -70), 10, new Vector(1, 0.4, 0))));

		ImageWriter imageWriter = new ImageWriter("MiniProject2PictureFromMiniProject1NoAdaptiveSupersampling", 600, 600);
		 
				camera1.setImageWriter(imageWriter) //
				.setRayTracerBase(new RayTracerBasic(scene1))
				.setMultithreading(3)
				.setDebugPrint().renderImage(121).writeToImage();		
	}
	
	


}




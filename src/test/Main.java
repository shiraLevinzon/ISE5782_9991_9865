package test;
import primitives.*;
import static java.lang.System.out;
import static primitives.Util.*;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

	/**
	 * Main program to tests initial functionality of the 1st stage
	 * 
	 * @param args irrelevant here
	 */
	public static void main(String[] args) {

		try { // test zero vector
			new Vector(0, 0, 0);
			out.println("ERROR: zero vector does not throw an exception");
		} catch (Exception e) {
		}

		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);

		// test length..
		if (!isZero(v1.lengthSquared() - 14))
			out.println("ERROR: lengthSquared() wrong value");
		if (!isZero(new Vector(0, 3, 4).length() - 5))
			out.println("ERROR: length() wrong value");

		// test Dot-Product
		if (!isZero(v1.dotProduct(v3)))
			out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
		if (!isZero(v1.dotProduct(v2) + 28))
			out.println("ERROR: dotProduct() wrong value");

		// test Cross-Product
		try { // test zero vector
			v1.crossProduct(v2);
			out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
		} catch (Exception e) {
		}
		Vector vr = v1.crossProduct(v3);
		if (!isZero(vr.length() - v1.length() * v3.length()))
			out.println("ERROR: crossProduct() wrong result length");
		if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
			out.println("ERROR: crossProduct() result is not orthogonal to its operands");

		// test vector normalization vs vector length and cross-product
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalize();
		if (!isZero(u.length() - 1))
			out.println("ERROR: the normalized vector is not a unit vector");
		try { // test that the vectors are co-lined
			v.crossProduct(u);
			out.println("ERROR: the normalized vector is not parallel to the original one");
		} catch (Exception e) {
		}
		if (v.dotProduct(u) < 0)
			out.println("ERROR: the normalized vector is opposite to the original one");

		// Test operations with points and vectors
		Point p1 = new Point(1, 2, 3);
		if (!(p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0))))
			out.println("ERROR: Point + Vector does not work correctly");
		if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)))
			out.println("ERROR: Point - Point does not work correctly");

		out.println("If there were no any other outputs - all tests succeeded!");
	}
}























/*package unittests;

import org.junit.Test;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
/*public class ShadowTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
/*	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 60) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3), 0, 0, 0) //
						.setkL(1E-5).setkQ(1.5E-7));

		camera. setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400));
		camera.renderImage();
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade move up
	 * and right
	 */
/*	@Test
	public void sphereTriangleMove1() {
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 60) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point3D(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3), 0, 0, 0) //
						.setkL(1E-5).setkQ(1.5E-7));

	
		camera.setImageWriter(new ImageWriter("sphereTriangleMove1", 400, 400)); 
		camera.renderImage();
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade move
	 * upper and righter
	 */
/*	@Test
	public void sphereTriangleMove2() {
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 60) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point(-50, -20, 0), new Point(-20, -50, 0), new Point(-48, -48, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add(
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3), 0, 0, 0) //
						.setkL(1E-5).setkQ(1.5E-7));

		
		camera.setImageWriter(new ImageWriter("sphereTriangleMove2", 400, 400)); 
		camera.renderImage();
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
/*	@Test
	public void sphereTriangleSpot1() {
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 60) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point(-30, -70, 0), new Point(-70, -30, 0), new Point(-70, -70, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-90, -90, 175), new Vector(1, 1, -3), 0, 0, 0) //
						.setkL(1E-5).setkQ(1.5E-7));

		
				camera.setImageWriter(new ImageWriter("sphereTriangleSpot1", 400, 400)); 
				camera.renderImage();
				camera.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
/*	@Test
	public void sphereTriangleSpot2() {
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 60) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
				new Triangle(new Point(-30, -70, 0), new Point(-70, -30, 0), new Point(-70, -70, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-80, -80, 115), new Vector(1, 1, -3), 0, 0, 0) //
						.setkL(1E-5).setkQ(1.5E-7));

	
			camera.setImageWriter(new ImageWriter("sphereTriangleSpot2", 400, 400));
			camera.renderImage();
			camera.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
/*	@Test
	public void trianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)), //
				new Sphere(new Point(0, 0, -115), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4), 0, 0, 0) //
						.setkL(4E-4).setkQ(2E-5));

		
		camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)); //
		camera.renderImage();
		camera.writeToImage();
	}

}*/
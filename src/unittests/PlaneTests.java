package unittests;

import java.util.List;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaneTests {

	@Test
	
	void testPlanePointPointPoint() {
		try {
			new Plane(new Point(1,2,3),new Point(2,4,6),new Point(4,8,12));//a case that all the points are on the same vector- cannot create the plane
			fail("GetNormal() should throw an exception, but it failed");
		} catch (Exception e) {}
	}	

	@Test
	void testPlanePointVector() {
		try {
			new Plane(new Point(1,2,3),new Vector(1,2,3));//a case that all the points are on the same vector- cannot create the plane
			fail("GetNormal() should throw an exception, but it failed");
		} catch (Exception e) {}	}

	@Test
	void testGetNormal() {
		Point p1= new Point(1,2,0);
		Point p2= new Point(4,-9,0);
		Point p3= new Point(1,0,8);
		Plane p= new Plane(p1,p2, p3);
		Vector v1=p1.subtract(p2);
		Vector v2=p2.subtract(p3);
		Vector v3=p3.subtract(p1);
		Vector n=p.getNormal(p1);
		assertTrue(isZero(v1.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
		assertTrue(isZero(v2.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
		assertTrue(isZero(v3.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
		
	}

	@Test
	void testGetNormalPoint() {
		Point p1= new Point(1,2,0);
		Point p2= new Point(4,-9,0);
		Point p3= new Point(1,0,8);
		Plane p= new Plane(p1,p2, p3);
		Vector v1=p1.subtract(p2);
		Vector v2=p2.subtract(p3);
		Vector v3=p3.subtract(p1);
		Vector n=p.getNormal();
		assertTrue(isZero(v1.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
		assertTrue(isZero(v2.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
		assertTrue(isZero(v3.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
			}
}


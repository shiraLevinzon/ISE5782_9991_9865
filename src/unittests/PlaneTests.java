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

	@Test
	void testFindIntsersections() {
		try {
			Plane pl = new Plane(new Point(0, 0, -3), new Vector(0, 0, -1));
			Ray r;

			// ============ Equivalence Partitions Tests ==============
			// The Ray is neither orthogonal nor parallel to the plane
			// TC01: the ray intersects the plane
			r = new Ray(new Vector(2, 1, -1),new Point(1, 1, 0));
			List<Point> result = pl.findIntsersections(r);
			assertEquals(List.of(new Point(7, 4, -3)), result,"wrong intersection! the ray intersects the plane");

			// TC02: the ray does not intersect the plane
			r = new Ray( new Vector(2, 1, 1),new Point(1, 1, 0));
			result=pl.findIntsersections(r);
			assertEquals("wrong intersection! the ray does not intersect the plane", null, result);

			// =============== Boundary Values Tests ==================
			// Ray is parallel to the plane
			// TC03: the ray is included in the plane
			r = new Ray(new Vector(2, 1, 0),new Point(1, 2, -3));
			assertEquals( null, pl.findIntsersections(r),"wrong intersection! the ray is parallel and included in the plane");
			// TC04: the ray is not included in the plane
			r = new Ray(new Vector(2, 1, 0),new Point(1, 2, -2));
			assertEquals("wrong intersection! the ray is parallel and not included in the plane", null, pl.findIntsersections(r));

			// Ray is orthogonal to the plane
			// TC05: Ray starts before the plane
			r = new Ray(new Vector(0, 0, -1),new Point(1, 1, 0));
			assertEquals("wrong intersection! the ray is orthogonal and starts before the plane",List.of(new Point(1, 1, -3)), pl.findIntsersections(r));
			// TC06: Ray starts in the plane
			r = new Ray( new Vector(0, 0, -1),new Point(1, 1, -3));
			assertEquals("wrong intersection! the ray is orthogonal and starts in the plane", null, pl.findIntsersections(r));
			// TC07: Ray starts after the plane
			r = new Ray(new Vector(0, 0, -1),new Point(1, 1, -4));
			assertEquals("wrong intersection! the ray is orthogonal and starts after the plane", null, pl.findIntsersections(r));

			// starting point is in the plane
			// TC08: Starting point of the ray is on the plane, but the vector is not included in the plane
			r = new Ray(new Vector(2, 1, -1),new Point(1, 1, -3));
			assertEquals("wrong intersection! Starting point of the ray is on the plane, but the vector is not included in the plane", null, pl.findIntsersections(r));
			// TC09: Starting point of the ray is equal to the point represents the plane- q0
			r = new Ray(new Vector(2, 1, -1),new Point(0, 0, -3));
			assertEquals("wrong intersection! Starting point of the ray is equal to the point represents the plane- q0", null, pl.findIntsersections(r));
	
		}
		catch(IllegalArgumentException e) {}	}

}

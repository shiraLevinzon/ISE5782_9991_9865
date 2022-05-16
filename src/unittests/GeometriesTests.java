/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * @author avigail and shira
 *
 */
class GeometriesTests {

	/**
	 * Test method for {@link geometries.Geometries#findIntsersections(primitives.Ray)}.
	 */
	@Test
	public void TestFindIntsersections(){
	       Sphere sph = new Sphere(new Point(1, 1, 1), 1);
	       Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 2));
	       Triangle tr = new Triangle(new Point(1,0,0), new Point(0, 1, 0), new Point(0, 0, 1));
	       Geometries collection = new Geometries(sph, plane, tr);

	       // ============ Equivalence Partitions Tests ==============

	       // TC01: Some of the Geometries are intersected
	       Ray ray = new Ray( new Vector(1, 1, 1),new Point(-1, 0, 0));
	       assertEquals(3, collection.findIntersections(ray).size()
	               ,"Wrong number of intersection points"); // Intersects only plane and sphere

	       // =============== Boundary Values Tests ==================

	       // TC11: All the Geometries are intersected
	       ray = new Ray(new Vector(-1, -1, -1),new Point(2, 2, 2.5));
	       assertEquals(4, collection.findIntersections(ray).size(),"Wrong number of intersection points");

	       // TC12: No Geometries are intersected
	       ray = new Ray(new Vector(-1, -1, -1),new Point(-1, 0, 0));
	       assertNull(collection.findIntersections(ray),"No intersection points");

	       // TC13: Only one Geometry shape is intersected
	       ray = new Ray(new Vector(-1, -1, -1),new Point(2, 0, 2));
	       assertEquals(1, collection.findIntersections(ray).size()
	              ,"Wrong number of intersection points");  // Intersects only plane

	       // TC14: Empty Geometries collection
	       collection = new Geometries();
	       assertNull(collection.findIntersections(new Ray(new Vector(1, 1, 0),new Point(-1, 0, 0))) ,"No geometry shapes in the collection");
	   }

}

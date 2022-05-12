/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


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
	public void testFindIntersections() {

		// ============ Equivalence Partitions Tests ==============
		var geometries = new Geometries(new Plane(new Point(-4, 0, 0), new Point(0, 0, 2), new Point(0, 0, 0)),
				new Sphere(new Point(0, 3, 0), 2),
				new Triangle(new Point(0, -2, 4), new Point(0, -2, 0), new Point(4, -2, 0)));

		// TC01: Some (but not all) shapes are cut
		assertEquals("Only one shape is cut", 3,
				geometries.findIntsersections(new Ray( new Vector(0, 1, 0),new Point(0.5, -1, 0.5))).size());

		// =============== Boundary Values Tests ==================

		Geometries geometriesEmpty = new Geometries();

		// TC02: Empty body collection
		assertNull("is Empty!",
				geometriesEmpty.findIntsersections(new Ray(new Vector(-1, 0, 1),new Point(2, 4, -3))));

		// TC03: No shape is cut
		assertNull("without crossing!",
				geometries.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(-1, 0.5, 0))));

		// TC04: Only one shape is cut
		assertEquals("Only one shape is cut", 2,geometries.findIntsersections(new Ray(new Vector(0, 1, 0),new Point(0, 0.5, 0))));/*,size()*/

		// TC05: All shapes are cut
		assertEquals("Only one shape is cut", 4,geometries.findIntsersections(new Ray(new Vector(0, -1, 0),new Point(0.5, 6, 0.5))));//.size());
	}

}

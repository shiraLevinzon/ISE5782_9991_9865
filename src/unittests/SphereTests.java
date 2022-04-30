/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author user
 *
 */
class SphereTests {
		/**
		 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
		 */
		@Test
		public void testGetNormal() {
			// ============ Equivalence Partitions Tests ==============
			var ball = new Sphere(new Point(0, 0, 0), 1);

			// Check that the normal is correct
			assertEquals("getNormal(Point3D) -The normal to the Sphere is not correct ", new Vector(1, 0, 0),
					ball.getNormal(new Point(1, 0, 0)));
		}

		/**
		 * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
		 */
		@Test
		public void testFindIntersections() {
			// ============ Equivalence Partitions Tests ==============
			var sphere = new Sphere(new Point(1, 0, 0), 1);

			// TC01: Ray's line is outside the sphere (0 points)
			assertNull("Ray's line out of sphere",
					sphere.findIntsersections(new Ray(new Vector(-1, 0, 0), new Point(1, 1, 0))));

			// TC02: Ray starts before and crosses the sphere (2 points)
			Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
			Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
			List<Point> result = sphere.findIntsersections(new Ray(new Vector(-1, 0, 0), new Point(3, 1, 0)));

			assertEquals("Wrong number of points", 2, result.size());
			if (result.get(0).getX() > result.get(1).getX())
				result = List.of(result.get(1), result.get(0));

			assertEquals("Ray crosses sphere", List.of(p1, p2), result);

			// TC03: Ray starts inside the sphere (1 point)
			result = sphere.findIntsersections(new Ray(new Vector(1, 0.5, 0), new Point(1, 0, 0)));
			assertEquals("Wrong number of points", 1, result.size());
			assertEquals("Ray starts inside the sphere", new Point(1.8660254037844386, 0.5, 0), result.get(0));

			// TC04: Ray starts after the sphere (0 points)
			assertNull("Ray starts after the sphere and cross sphere ????",
					sphere.findIntsersections(new Ray(new Vector(2, 0.5, 0), new Point(1, 0, 0))));

			// =============== Boundary Values Tests ==================

			// **** Group: Ray's line crosses the sphere (but not the center)

			// TC11: Ray starts at sphere and goes inside (1 points)
			result = sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(0.1339745962155614, 0.5, 0)));
			assertEquals("Wrong number of points", 1, result.size());
			assertEquals("Ray starts at sphere and goes inside and not cross sphere",
					new Point(1.8660254037844386, 0.5, 0), result.get(0));

			// TC12: Ray starts at sphere and goes outside (0 points)
			assertNull("Ray starts at sphere and goes outside and cross sphere ????",
					sphere.findIntsersections(new Ray(new Vector(-1, 0, 0),new Point(0.1339745962155614, 0.5, 0))));

			// **** Group: Ray's line goes through the center
			// TC13: Ray starts before the sphere (2 points)
			p1 = new Point(0, 0, 0);
			p2 = new Point(2, 0, 0);
			result = sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(-1, 0, 0)));

			assertEquals("Wrong number of points", 2, result.size());
			if (result.get(0).getX() > result.get(1).getX())
				result = List.of(result.get(1), result.get(0));
			assertEquals("Ray crosses sphere", List.of(p1, p2), result);

			// TC14: Ray starts at sphere and goes inside (1 points)
			result = sphere.findIntsersections(new Ray(new Vector(-1, 0, 0),new Point(2, 0, 0)));
			assertEquals("Wrong number of points", 1, result.size());
			assertEquals("Ray starts at sphere and goes inside and cross in other place",Double3.ZERO, result.get(0));

			// TC15: Ray starts inside (1 points)
			result = sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(1.5, 0, 0)));
			assertEquals("Wrong number of points", 1, result.size());
			assertEquals("The Ray does not cross in the right place", new Point(2, 0, 0), result.get(0));

			// TC16: Ray starts at the center (1 points)
			result = sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(1, 0, 0)));
			assertEquals("Wrong number of points", 1, result.size());
			assertEquals("The Ray does not cross in the right place", new Point(2, 0, 0), result.get(0));

			// TC17: Ray starts at sphere and goes outside (0 points)
			assertNull("Ray starts at sphere and goes outside and cross ???",
					sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(2, 0, 0))));

			// TC18: Ray starts after sphere (0 points)
			assertNull("Ray's line need to be after of sphere",
					sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(3, 0, 0))));

			// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
			// TC19: Ray starts before the tangent point
			assertNull("Ray starts before the tangent point and not cross the sphere",
					sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(0, 1, 0))));

			// TC20: Ray starts at the tangent point
			assertNull("Ray starts at the tangent point and not cross the sphere",
					sphere.findIntsersections(new Ray(new Vector(1, 0, 0),new Point(1, 1, 0))));

			// TC21: Ray starts after the tangent point
			assertNull("Ray starts after the tangent point and not cross the sphere",
					sphere.findIntsersections(new Ray( new Vector(1, 0, 0),new Point(2, 1, 0))));

			// **** Group: Special cases
			// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
			// center line
			assertNull("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",
					sphere.findIntsersections(new Ray( new Vector(0, 1, 0),new Point(-0.5, 0, 0))));
		}
	}


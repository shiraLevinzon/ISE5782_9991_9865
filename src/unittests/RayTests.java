package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class RayTests {

	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Vector(1, 0, 0),new Point(Double3.ZERO));
		// ============ Equivalence Partitions Tests ==============

		// TC01: A point in the middle of the list is closest to the beginning of the
		// Ray
		assertEquals("The middle point in the list is closest", new Point(1.5, 0, 0),
				ray.findClosestPoint(List.of(new Point(2, 0, 0), new Point(1.5, 0, 0), new Point(2, -1, 0))));

		// =============== Boundary Values Tests ==================

		// TC02: empty list
		assertNull("empty list: expect to return null", ray.findClosestPoint(List.of()));

		// TC03: The first point is closest to the beginning of the Ray
		assertEquals("The first point is closest", new Point(2, 0, 0),
				ray.findClosestPoint(List.of(new Point(2, 0, 0), new Point(2, 1, 0), new Point(2, -1, 0))));

		// TC04: The last point is closest to the beginning of the Ray
		assertEquals("The last point is closest", new Point(2, 0, 0),
				ray.findClosestPoint(List.of(new Point(2, -1, 0), new Point(2, 1, 0), new Point(2, 0, 0))));
	}

}

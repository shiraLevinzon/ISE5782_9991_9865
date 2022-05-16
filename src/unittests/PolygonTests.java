package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author avigail and shira
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}
		// TC02: Wrong vertices order
		assertThrows("Constructed a polygon with wrong order of vertices", IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0),
						new Point(-1, 1, 1)));
		// TC03: Not in the same plane
		assertThrows("Constructed a polygon with vertices that are not in the same plane",
				IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
						new Point(0, 1, 0), new Point(0, 2, 2)));
		// TC04: Concave quadrangular
		assertThrows("Constructed a concave polygon", IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)));

		// =============== Boundary Values Tests ==================
		// TC10: Vertex on a side of a quadrangular
		assertThrows("Constructed a polygon with vertix on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0, 0.5, 0.5)));
		// TC11: Last point = first point
		assertThrows("Constructed a polygon with vertice on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0, 0, 1)));
		// TC12: Colocated points
		assertThrows("Constructed a polygon with vertice on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0, 1, 0)));
	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
				new Point(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
/*	@Test
	public void testFindIntsersectionsRay() {

		Polygon pl = new Polygon(new Point(2, 0, 0), new Point(4, 0, 0), new Point(4, 0, 4),
				new Point(2, 0, 2));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Inside polygon
		assertEquals("the Ray not cross inside polygon", new Point(3, 0, 1),
				pl.findGeoIntersectionsHelper(new Ray( new Vector(0, 1, 0),new Point(3, -1, 1))).get(0));

		// TC02: Outside against edge
		assertNull("the Ray not cross outside against edge",
				pl.findGeoIntersections(new Ray(new Vector(0, 1, 0),new Point(1, -1, 1))));

		// TC03: Outside against vertex
		assertNull("the Ray not cross outside against vertex",
				pl.findGeoIntersections(new Ray( new Vector(0, 1, 0),new Point(1, -1, -1))));

		// =============== Boundary Values Tests ==================

		// TC04: the ray begins "before" the plane (On edge)
		assertNull("the ray begins before the plane and not cross on edge",
				pl.findGeoIntersections(new Ray(new Vector(0, 1, 0),new Point(2, -1, 1))));

		// TC05: the ray begins "before" the plane (In vertex)
		assertNull("the ray begins before the plane and not cross in vertex",
				pl.findGeoIntersections(new Ray(new Vector(0, 1, 0),new Point(2, -1, 2))));

		// TC06: the ray begins "before" the plane (On edge's continuation)
		assertNull("the ray begins before the plane and not cross on edge's continuation",
				pl.findGeoIntersections(new Ray(new Vector(0, 1, 0),new Point(2, -1, 3))));
	}*/
}
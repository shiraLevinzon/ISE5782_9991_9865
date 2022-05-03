/**
 * 
 */
package unittests;

import java.util.List;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author shira and avigail
 *
 */
class PolygonTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point[])}.
	 */
	@Test
	void testPolygon() {
		 // ============ Equivalence Partitions Tests ==============
        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point(0, 0, 1), new Point(0, 1, 0),
                    new Point(1, 0, 0), new Point(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================
        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		 // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                new Point(-1, 1, 1));
        double s = Math.sqrt(1d / 3);	
        assertEquals(new Vector(s, s, s), pl.getNormal(new Point(0, 0, 1)),"Bad normal to trinagle");

        }

	/**
	 * Test method for {@link geometries.Polygon#findIntsersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntsersections() {
		fail("Not yet implemented");
	}

}

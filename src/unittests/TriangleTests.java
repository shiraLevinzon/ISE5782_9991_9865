package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

mport java.util.List;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

class TriangleTests {

	@Test
	void testTriangle() {
		 Triangle tri = new Triangle(new Point(4,0,0),new Point(0,4,0),new Point(0,0,0));
	        assertEquals(new Vector(0,0,1),tri.getNormal(null));	}

	@Test
	void testGetNormal() {
		fail("Not yet implemented");
	}

	@Test
	void testFindIntsersections() {
		fail("Not yet implemented");
	}

}

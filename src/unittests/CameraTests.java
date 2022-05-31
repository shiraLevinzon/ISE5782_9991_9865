package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import renderer.Camera;
import primitives.*;

/**
 * Testing Camera Class
 * 
 * @author shira and avigail
 *
 */
class CameraTest {
	static final Point ZERO_POINT = new Point(0, 0, 0);

	/**
	 * Test method for
	 * {@link elements.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testConstructRay() {
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setViewPlaneDistance(10);
		String badRay = "Bad ray";

		// ============ Equivalence Partitions Tests ==============
		// EP01: 4X4 Inside (1,1)
		assertEquals(new Ray(new Vector(1, -1, -10),ZERO_POINT),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1), badRay);

		// =============== Boundary Values Tests ==================
		// BV01: 3X3 Center (1,1)
		assertEquals(new Ray(new Vector(0, 0, -10),ZERO_POINT),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1), badRay);

		// BV02: 3X3 Center of Upper Side (0,1)
		assertEquals(new Ray(new Vector(0, -2, -10),ZERO_POINT),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0), badRay);

		// BV03: 3X3 Center of Left Side (1,0)
		assertEquals(new Ray( new Vector(2, 0, -10),ZERO_POINT),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1), badRay);

		// BV04: 3X3 Corner (0,0)
		assertEquals(new Ray(new Vector(2, -2, -10),ZERO_POINT),
				camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0), badRay);

		// BV05: 4X4 Corner (0,0)
		assertEquals(new Ray(new Vector(3, -3, -10),ZERO_POINT),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0), badRay);

		// BV06: 4X4 Side (0,1)
		assertEquals(new Ray(new Vector(1, -3, -10),ZERO_POINT),
				camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0), badRay);

}

}
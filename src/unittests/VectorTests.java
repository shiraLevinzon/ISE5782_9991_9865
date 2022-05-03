/**
 * 
 */
package unittests;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * @author shira and avigail
 *
 */

class VectorTests {
	Vector v1 = new Vector(1, 2, 3);
	Vector v2 = new Vector(-2, -4, -6);
	Vector v3 = new Vector(0, 3, -2);
	
	// test vector normalization vs vector length and cross-product
	Vector v = new Vector(1, 2, 3);
	
	


	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector b = new Vector(-1, -2, -3);
		// ============ Equivalence Partitions Tests ==============
        assertEquals(b , v1.add(v2),"Add() wrong result length");//check if the add works well (not the zero vector)
	  // =============== Boundary Values Tests ==================
		 try {
		     v1.add(b);//zero vector
		     fail("Add() should throw an exception, but it failed");
		      } catch (Exception e) {}
		
	}

	/**
	 * Test method for {@link primitives.Vector#scale(int)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============
	    assertEquals(v2, v1.scale(-2));//regular case
	    // =============== Boundary Values Tests ==================
        // 
        try {
            v1.scale(0);//multiple by 0- cannot get the zero vector
            fail("Scale() should throw an exception, but it failed");
        } catch (Exception e) {}	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		
		// ============ Equivalence Partitions Tests ==============
		assertTrue(isZero(v1.dotProduct(v3)),"dotProduct() for orthogonal vectors is not zero");
		assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");
		// =============== Boundary Values Tests ==================
		try {
			assertTrue(isZero(new Vector(0,0,0).dotProduct(v1)),"dotProduct() wrong result length");//in that case, the ctor of vector will throw an exception- the zero vector
			fail("dotProduct() should throw an exception, but it failed");
		    } catch (Exception e) {}
	}
		
		

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {		
		
        // ============ Equivalence Partitions Tests ===============
        Vector vr = v1.crossProduct(v3);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                     "crossProduct() for parallel vectors does not throw an exception");
    

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		assertEquals(v3.lengthSquared(),13,"ERROR: lengthSquared() wrong value");//regular case
		 // =============== Boundary Values Tests ================== 
	    try {
		assertTrue(isZero(new Vector(0,0,0).lengthSquared()),"lengthSquared() wrong result lengthSquared");//in that case, the ctor of vector will throw an exception- the zero vector
		fail("Length() should throw an exception, but it failed");
	  } catch (Exception e) {}	
	  }	

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		
		// ============ Equivalence Partitions Tests ==============
		assertEquals(v3.length(),Math.sqrt(13),0.0001,"ERROR: length() wrong value");//regular case
		 // =============== Boundary Values Tests ================== 
	    try {
		assertTrue(isZero(new Vector(0,0,0).length()),"Length() wrong result length");//in that case, the ctor of vector will throw an exception- the zero vector
		fail("Length() should throw an exception, but it failed");
	    } catch (Exception e) {}	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
	    assertEquals(v1.normalize().length(), 1,0.0001,"ERROR: the normalized vector is not a unit vector");//regular case
	    // =============== Boundary Values Tests ==================
        // 
	    try {
		assertTrue(isZero(new Vector(0,0,0).normalize().length()),"Normalized() wrong result length");//in that case, the ctor of vector will throw an exception- the zero vector
		fail("Normalized() should throw an exception, but it failed");
	    } catch (Exception e) {}
		
	}

}

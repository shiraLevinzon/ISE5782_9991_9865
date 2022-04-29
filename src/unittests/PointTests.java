/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * @author user
 *
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		//positive with positive
		Point p=new Point(1,2,3);
		Vector p1=new Vector(1,1,1);
		Point p2=p.add(p1);
		Point p3=new Point(2,3,4);
		assertEquals(p3,p2);
		//negative with negative
		p=new Point(-1,-2,-3);
		 p1=new Vector(-1,-1,-1);
		 p2=p.add(p1);
		 p3=new Point(-2,-3,-4);
		assertEquals(p3,p2);
		//negative with positive 
		 p=new Point(-1,-1,-1);
		 p1=new Vector(1,2,3);
		 p2=p.add(p1);
		 p3=new Point(0,1,2);
		assertEquals(p3,p2);
		//positive with negative
		p=new Point(1,2,3);
		 p1=new Vector(-1,-2,-3);
		 p2=p.add(p1);
		 p3=new Point(0,0,0);
		assertEquals(p3,p2);
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		//positive with positive
				Point p=new Point(1,2,3);
				Vector p1=new Vector(1,1,1);
				Point p2=p.subtract(p1);
				Point p3=new Point(0,1,2);
				assertEquals(p3,p2);
				//positive with negative
				p=new Point(-1,-2,-3);
				 p1=new Vector(-1,-1,-1);
				 p2=p.subtract(p1);
				 p3=new Point(0,-1,-2);
				assertEquals(p3,p2);
				//negative with positive 
				 p=new Point(1,2,3);
				 p1=new Vector(-1,-1,-1);
				 p2=p.subtract(p1);
				 p3=new Point(2,3,4);
				assertEquals(p3,p2);
				//negative with negative
				p=new Point(1,2,3);
				 p1=new Vector(-1,-2,-3);
				 p2=p.subtract(p1);
				 p3=new Point(2,4,6);
				assertEquals(p3,p2);
	}

	/**
	 * Test method for {@link primitives.Point#DistanceSquared(primitives.Double3)}.
	 */
	@Test
	void testDistanceSquared() {
		//positive with positive
		Point p=new Point (1,1,1);
		Point p2= new Point(2,2,2);
		double d=p.DistanceSquared(p2);
		assertEquals(3,d);
		//positive with negative
		 p=new Point (1,1,1);
		 p2= new Point(-2,-2,-2);
		 d=p.DistanceSquared(p2);
		assertEquals(27,d);
		//negative with positive 	
		 p=new Point (-1,-1,-1);
		 p2= new Point(2,2,2);
		 d=p.DistanceSquared(p2);
		 assertEquals(27,d);
		//negative with negative
		 p=new Point (-1,-1,-1);
		 p2= new Point(-2,-2,-2);
		 d=p.DistanceSquared(p2);
		 assertEquals(3,d);
	}

	/**
	 * Test method for {@link primitives.Point#Distance(primitives.Double3)}.
	 */
	@Test
	void testDistance() {
		//positive with positive
				Point p=new Point (1,1,1);
				Point p2= new Point(2,2,2);
				double d=p.Distance(p2);
				assertEquals(Math.sqrt(3),d);
				//positive with negative
				 p=new Point (1,1,1);
				 p2= new Point(-2,-2,-2);
				 d=p.Distance(p2);
				assertEquals(Math.sqrt(27),d);
				//negative with positive 	
				 p=new Point (-1,-1,-1);
				 p2= new Point(2,2,2);
				 d=p.Distance(p2);
				 assertEquals(Math.sqrt(27),d);
				//negative with negative
				 p=new Point (-1,-1,-1);
				 p2= new Point(-2,-2,-2);
				 d=p.Distance(p2);
				 assertEquals(Math.sqrt(3),d);
		
	}
	//Point (0,0,0)!! exception must !
}

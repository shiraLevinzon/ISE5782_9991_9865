package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
	
	Point q0;
	Vector normal;
	
/**
 *constructor who gets 3 points and creates a plain
 * @param p1
 * @param p2
 * @param p3
 */
	public Plane(Point p1,Point p2,Point p3) {
		super();
		this.normal=null;
		this.q0 = p1;
	}

/**
 * constructor who gets one point and  the normalize vector and creates a plain
 * @param q0
 * @param normal
 */
	public Plane(Point q0, Vector normal) {
		super();
		
		this.q0 = q0;
		this.normal =normal.normalize();
	}
/**
 * 
 * @return the point 
 */

	public Point getQ0() {
		return q0;
	}

/**
 * 
 * @return the normalize vector 
 */
	public Vector getNormal() {
		return normal;
	}


	@Override
	public String toString() {
		return "Plane [q0=" + q0.toString() + ", normal=" + normal.toString() + "]";
	}
	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}	
}

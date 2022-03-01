package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
	
	Point q0;
	Vector normal;
	

	public Plane(Point p1,Point p2,Point p3) {
		super();
		this.normal=null;
		this.q0 = p1;
	}


	public Plane(Point q0, Vector normal) {
		super();
		
		this.q0 = q0;
		this.normal =normal.normalize();
	}


	public Point getQ0() {
		return q0;
	}


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

package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Sphere implements Geometry {

	private Point center;
	private double radius;

	/**
	 * getter center of sphere
	 * 
	 * @return a center of sphere
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * getter radius of sphere
	 * 
	 * @return a radius of sphere
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sphere constructor receiving a Point3d value to center and radius of Sphere
	 * 
	 * @param center - a center of sphere
	 * @param radius - a radius of sphere
	 */
	public Sphere(Point center, double radius) {
		this.center = center;
		this.radius = radius;
		//setMaxBoundary();
		//setMinBoundary();
	}

	@Override
	public Vector getNormal(Point point) {
		return point.subtract(center).normalize();
	}

	@Override
	public String toString() {
		return "center=" + center + ", radius=" + radius;
	}

	@Override
	public List<Point> findIntsersections(Ray ray)  {
		double tm;
		double d;
		var p0 = ray.getP0();
		try {
			var u = center.subtract(p0);
			tm = ray.getDir().dotProduct(u);
			d = Math.sqrt(u.lengthSquared() - (tm * tm));
			if (d >= radius)
				return null;
		} catch (Exception e) {
			d = 0;
			tm = 0;
		}
		double th = Math.sqrt(radius * radius - (d * d));
		double t1 = Util.alignZero(tm + th);
		double t2 = Util.alignZero(tm - th);
		double dis1 = Util.alignZero(t1 - max);
		double dis2 = Util.alignZero(t2 - max);
		Point p1, p2;
		if (t1 > 0 && dis1 <= 0 || t2 > 0 && dis2 <= 0) {
			List<Point> myList = new LinkedList<Point>();
			if (t1 > 0 && dis1 <= 0) {
				p1 = ray.getPoint(t1);
				if (!p1.equals(p0))
					myList.add(new Point(this.center, p1));
			}
			if (t2 > 0 && dis2 <= 0) {
				p2 = ray.getPoint(t2);
				if (!p2.equals(p0))
					myList.add(new Point(this,p2));
			}
			return myList;
		}
		return null;
	}
	
}




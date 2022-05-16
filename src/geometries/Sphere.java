package geometries;

import java.util.LinkedList;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Sphere extends Geometry {

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
	  public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Sphere)) return false;
	      Sphere other = (Sphere)obj;
	      return this.center.equals(other.center)&&this.radius==other.radius;
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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        double r = this.radius;

        // Special case: if point p0 == center, that mean that all we need to calculate
        // is the radios mult scalar with the direction, and add p0
        if (center.equals(ray.getP0())) {
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            result.add(new GeoPoint(this, ray.getPoint(r)));
            return result;
        }

        Vector u = center.subtract(ray.getP0());
        double tm = u.dotProduct(ray.getDir());
        double d = Math.sqrt(Util.alignZero(u.lengthSquared() - tm * tm));

        if (d > r) //also In case the cut is tangent to the object still return null - d = r
            return null;

        double th = Math.sqrt(r * r - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        if(Util.alignZero(t1) > 0 || Util.alignZero(t2) > 0){
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            if(Util.alignZero(t1) > 0){
                Point p1 = ray.getPoint(t1);
                result.add(new GeoPoint(this, p1));
            }
            if(Util.alignZero(t2) > 0){
                Point p2 = ray.getPoint(t2);
                result.add(new GeoPoint(this, p2));
            }
            return result;
        }
        else { //In case there are no intersections points
            return null;
        }
    }

	/*@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
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
		double dis1 = Util.alignZero(t1 );
		double dis2 = Util.alignZero(t2  );
		Point p1, p2;
		if (t1 > 0 && dis1 <= 0 || t2 > 0 && dis2 <= 0) {
			List<GeoPoint> myList = new LinkedList<GeoPoint>();
			if (t1 > 0 && dis1 <= 0) {
				p1 = ray.getPoint(t1);
				if (!p1.equals(p0))
					myList.add(new GeoPoint(this, p1));
			}
			if (t2 > 0 && dis2 <= 0) {
				p2 = ray.getPoint(t2);
				if (!p2.equals(p0))
					myList.add(new GeoPoint(this, p2));
			}
			return myList;
		}
		return null;
	}*/
	
}




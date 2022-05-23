package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;


public class Plane extends Geometry{
	
	private Point q0;
	private Vector normal;
	
/**
 *constructor who gets 3 points and creates a plain
 * @param p1
 * @param p2
 * @param p3
 */
	public Plane(Point p1,Point p2,Point p3) {
		super();
		Vector v1 = (p1.subtract(p2));//get one vector on plane
		Vector v2 = (p1.subtract(p3));//get second vector on plane
		this.normal = v1.crossProduct(v2).normalize();//if v1 and v2 are on the same direction of vector- the cross product and the normal will be zero vector.		
		this.q0 = p1;
	}

/**
 * constructor who gets one point and  the normalize vector and creates a plain
 * @param q0
 * @param normal
 * @throws Exception 
 */
	public Plane(Point q0, Vector normal)  {
		super();
		if(q0.equals(normal))
		{throw new IllegalArgumentException();}
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
		return getNormal();
	}

	@Override
	  public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Plane)) return false;
	      Plane other = (Plane)obj;
	      return this.normal.equals(other.normal)&&this.q0.equals(other.q0);
	   }
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max) {
		double nv = getNormal().dotProduct(ray.getDir());
		if (isZero(nv))
			return null;
		try {
			double numer = getNormal().dotProduct(getQ0().subtract(ray.getP0()));
			double t = alignZero(numer / nv);
			if (t > 0 && alignZero(t - max) <= 0) {
				var p1 = ray.getPoint(t);
				return List.of(new GeoPoint(this, p1));
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/*@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if (ray.getP0().equals(q0) || isZero(this.normal.dotProduct(ray.getDir()))
                || isZero(this.normal.dotProduct(q0.subtract(ray.getP0()))))
            return null;

        double t = (this.normal.dotProduct(q0.subtract(ray.getP0()))) / (this.normal.dotProduct(ray.getDir()));
        if (t < 0) 
            return null;

        //In case there is intersection with the plane return the point
        GeoPoint p = new GeoPoint(this, ray.getPoint(t));
        LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
        result.add(p);
        return result;
    }*/

	
}

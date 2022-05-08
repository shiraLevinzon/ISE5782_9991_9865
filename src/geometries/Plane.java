package geometries;

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
		return getNormal();
	}

	@Override
	public List<Point> findIntsersections(Ray ray) {
		try {
			Vector vec=q0.subtract(ray.getP0());//creating a new vector according to the point q0 and the starting point of the ray (P0)

			double t=normal.dotProduct(vec);//dot product between the vector we created and the normal of the plane

			if(isZero(normal.dotProduct(ray.getDir()))) //if the dot product equals 0 it means that the ray is parallel (makbil) to the plane
				return null;
			t=t/(normal.dotProduct(ray.getDir()));

			if(t==0) //if the distance between the p0-the start point of the ray and the plane is 0, its not counted in the intersections list
				return null;//no intersections
			else if(t > 0) // the ray crosses the plane
			{
				Point p=ray.getPoint(t);//get the new point on the ray, multiplied by the scalar t. p is the intersection point.
				return List.of(new Point(p.getXyz()));//if so, return the point- the intersection
			}
			else // the ray doesn't cross the plane
				return null;	
		}

		catch(Exception ex) //catch exceptions in the vector creation
		{
			return null;
		}
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import static primitives.Util.*;

public class Triangle extends Polygon {

	/**
	 * constructor that receive three points and activate the super constructor to create the triangle 
	 * @param vertices
	 */
	public Triangle(Point... vertices){
		super(vertices);
	}
	
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		// find intersections
		// if the ray inside the plane - return the cross point
		// if the ray not inside the plane (not cross) - return null
		var myList = super.plane.findGeoIntersections(ray, maxDistance);
		if (myList == null)
			return null;

		var P0 = ray.getP0();
		var dir = ray.getDir();

		// the formula
		var v1 = vertices.get(0).subtract(P0);
		var v2 = vertices.get(1).subtract(P0);
		var v3 = vertices.get(2).subtract(P0);

		var n1 = v1.crossProduct(v2).normalize();
		var n2 = v2.crossProduct(v3).normalize();
		var n3 = v3.crossProduct(v1).normalize();

		// check if n1,n2,n3 have the same sign(+\-)
		// -- all of them or bigger the zero or smallest then zero --
		if ((Util.alignZero(n1.dotProduct(dir)) > 0 && Util.alignZero(n2.dotProduct(dir)) > 0
				&& Util.alignZero(n3.dotProduct(dir)) > 0) == true
				|| (Util.alignZero(n1.dotProduct(dir)) < 0 && Util.alignZero(n2.dotProduct(dir)) < 0
						&& Util.alignZero(n3.dotProduct(dir)) < 0) == true)
			return List.of(new GeoPoint(this, myList.get(0).point));
		return null;
	}
	/*@Override
	public List<Point> findIntsersections(Ray ray) {
		List<Point> intersections = plane.findIntsersections(ray);//find intersections with the plane- triangle extends polygon, and polygon contains a plane.
		if (intersections == null) return null;//no intersections with the plane

		Point p0 = ray.getP0();
		Vector v = ray.getDir();

		//v1 v2 v3 are the vectors between p0 to each one of the triangle vertices, to create a kind of a pyramid
		Vector v1 = vertices.get(0).subtract(p0).normalize();
		Vector v2 = vertices.get(1).subtract(p0).normalize();
		Vector v3 = vertices.get(2).subtract(p0).normalize();

		//Vector n1=v1.crossProduct(v2)
		//Vector n2=v2.crossProduct(v3)
		//Vector n3=v3.crossProduct(v1)
		
		double s1 = v.dotProduct(v1.crossProduct(v2));//s1=v.dotProduct(n1)
		if (isZero(s1)) return null;//the ray is on the peah of the plane v1-v2, it means its not inside the triangle but on its edge (or on the vertex, or on the edge continue)
		double s2 = v.dotProduct(v2.crossProduct(v3));//s2=v.dotProduct(n2)
		if (isZero(s2)) return null;//the ray is on the peah of the plane v2-v3, it means its not inside the triangle but on its edge (or on the vertex, or on the edge continue)
		double s3 = v.dotProduct(v3.crossProduct(v1));//s3=v.dotProduct(n3)
		if (isZero(s3)) return null;//the ray is on the peah of the plane v3-v1, it means its not inside the triangle but on its edge (or on the vertex, or on the edge continue)

		if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) //if all the s1,s2,s3 are all positive or negative- the ray intersects the triangle.
		{
			return intersections;
		
		}
		else
			return null;//the ray is on the plane but outside the triangle
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// find intersections
				// if the ray inside the plane - return the cross point
				// if the ray not inside the plane (not cross) - return null
				var myList = super.plane.findGeoIntersections(ray);
				if (myList == null)
					return null;

				var P0 = ray.getP0();
				var dir = ray.getDir();

				// the formula
				var v1 = vertices.get(0).subtract(P0);
				var v2 = vertices.get(1).subtract(P0);
				var v3 = vertices.get(2).subtract(P0);

				var n1 = v1.crossProduct(v2).normalize();
				var n2 = v2.crossProduct(v3).normalize();
				var n3 = v3.crossProduct(v1).normalize();

				// check if n1,n2,n3 have the same sign(+\-)
				// -- all of them or bigger the zero or smallest then zero --
				if ((Util.alignZero(n1.dotProduct(dir)) > 0 && Util.alignZero(n2.dotProduct(dir)) > 0
						&& Util.alignZero(n3.dotProduct(dir)) > 0) == true
						|| (Util.alignZero(n1.dotProduct(dir)) < 0 && Util.alignZero(n2.dotProduct(dir)) < 0
								&& Util.alignZero(n3.dotProduct(dir)) < 0) == true)
					return List.of(new GeoPoint(this, myList.get(0).point));
				return null;
	}*/

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices.toString() + ", plane=" + plane.toString() + "]";
	}




	

}

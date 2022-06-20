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

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices.toString() + ", plane=" + plane.toString() + "]";
	}




	

}

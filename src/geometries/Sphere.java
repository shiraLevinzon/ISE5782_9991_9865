package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere implements Geometry  {
	private Double radius;
	private Point center;

	@Override
	public List<Point> findIntsersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Point> findGeoIntersections(Ray ray) 
	{
		//point and vector of ray
		Point3D p0 = ray.getP0();		//ray point
		Vector v = ray.getDir();		//ray vector

		if(p0.equals(center))       	//if the starting point of the ray is the center
			return List.of(new Point(this,ray.getPoint(radius)));//return the intersection point
		
		Vector u=center.subtract(p0);	//the vector between center and ray
		double tm=v.dotProduct(u); 		//the scale for the ray in order to get parallel to the sphere center
		double d=Math.sqrt(u.lengthSquared()-tm*tm);//get the distance between the ray and the sphere center
		//check if d is bigger then radius-the ray doesn't cross the sphere
		if (d>radius)
			return null;
		double th=Math.sqrt(radius*radius-d*d);//according Pythagoras
		double t1=tm+th;
		double t2=tm-th;
		if(t1>0&&t2>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if orthogonal -> no intersection
			return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
		else if(t1>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) //if only t1 is not orthogonal and positive
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		else if(t2>0&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if only t2 is not orthogonal and positive
			return List.of(new GeoPoint(this,ray.getPoint(t2)));
		else
			return null;//no intersections
	}



}


}

package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/***
 * 
 * interface for finding the Intersectable points
 *
 */
public abstract class Intersectable {
	
	public abstract List<Point> findIntsersections(Ray ray);
	public List<GeoPoint> findGeoIntersections(Ray ray){
		return findGeoIntersectionsHelper(ray);	}
	protected abstract List<GeoPoint> findGeoIntersectionsHelper (Ray ray);
	public static class GeoPoint {
		    public  Geometry geometry;
		    public  Point point;
		   
		    GeoPoint(Geometry geo,Point p){
		    	this.geometry=geo;
		    	this.point=p;
		    }
		    @Override
			public String toString(){
		    	return "Color-"+this.geometry.emission+", "+point.toString();
		    }
		    @Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (!(obj instanceof GeoPoint))
					return false;
				GeoPoint other = (GeoPoint) obj;
				return this.geometry.equals(other.geometry) && this.point.equals(other.point);
			}
		}
	}



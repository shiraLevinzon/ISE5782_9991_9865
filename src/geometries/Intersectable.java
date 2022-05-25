package geometries;

import java.util.List;

import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/***
 * 
 * interface for finding the Intersectable points
 *
 */
public abstract class Intersectable {
	
	public List<Point> findIntersections(Ray ray) {
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).toList();
	}
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
    	return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
    	return findGeoIntersectionsHelper(ray, maxDistance);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

	
	public static class GeoPoint {
		    public  Geometry geometry;
		    public  Point point;
		   
		    public GeoPoint(Geometry geo,Point p){
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
			public double getX() {
				// TODO Auto-generated method stub
				return point.getX();
			}
		}

	}



package primitives;

import java.util.List;
import java.util.stream.Collectors;

import geometries.Intersectable.GeoPoint;

/**
 * Class Ray is the basic class representing a Ray of Euclidean primitives in Cartesian
 * Object is founded in geometry
 * @author Shira Levinzon and Avigail Uzan
*/
public class Ray {
		Vector dir;
		Point p0;
		/**
		 * constructor that receives a vector and a point and creates a ray
		 * @param dir
		 * @param p0
		 */
		public Ray(Vector dir, Point p0) {
			this.dir =dir.normalize();
			this.p0 = p0;
		}
		public Point getPoint(double t) {
			try {
				return p0.add(dir.scale(t));
			} catch (Exception e) {
				return p0;
			}
		}
		
		public Point getP0() {
			return p0;
		}
		public Vector getDir() {
			return dir;
		}
		
		@Override
		  public boolean equals(Object obj) {
		      if (this == obj) return true;
		      if (obj == null) return false;
		      if (!(obj instanceof Ray)) return false;
		      Ray other = (Ray)obj;
		      return this.dir.equals(other.dir)&&this.p0.equals(other.p0);
		   }
		/***
		 * 
		 * @param list of points
		 * @return the closest point to the specific ray
		 */
		public Point findClosestPoint(List<Point> points) {
		    return points == null || points.isEmpty() ? null
		           : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
		}


		/**
		 * search from list of points what is the closest point to the ray and return is
		 * back
		 * 
		 * @param intersections - list of points we want to scan
		 * @return the closest point to the ray
		 */
		public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
			if (intersections == null)
				return null;
			GeoPoint minPoint = null;
			double minDistance = Double.POSITIVE_INFINITY;
			for (var item : intersections) {
				double d = item.point.Distance(p0);
				if (d < minDistance) {
					minPoint = item;
					minDistance = d;
				}
			}
			return minPoint;
		}
}


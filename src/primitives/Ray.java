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
		/***
		 * 
		 * @param list of points
		 * @return the closest point to the specific ray
		 */
		public Point findClosestPoint(List<Point> intersections) {
			var gp = findClosestGeoPoint(intersections == null ? null
					: intersections.stream().map(p -> new GeoPoint(null, p)).collect(Collectors.toList()));
			return gp == null ? null : gp.point;
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


package primitives;
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
		public Point getPoint(double t1) {
			return this.p0;
		}
		
//������ �� �� DIR
}

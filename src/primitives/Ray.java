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
<<<<<<< HEAD
		public Point getPoint(\) {
			return this.p0;
		}
		public Vector getDir() {
			// TODO Auto-generated method stub
			return null;
=======
		public Point getPoint(double t) {
			return  p0.add(dir.scale(t));
		}
		public Point getP0() {
			// TODO Auto-generated method stub
			return p0;
		}
		public Vector getDir() {
			// TODO Auto-generated method stub
			return dir;
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		}
		
//להוסיף גט של DIR
}

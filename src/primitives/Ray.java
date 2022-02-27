package primitives;
/**
 * Class Ray is the basic class representing a Ray of Euclidean primitives in Cartesian
 * Object is founded in geometry
 * @author Shira Levinzon and Avigaul Uzan
*/
public class Ray {
		Vector dir;
		Point p0;
		public Ray(Vector dir, Point p0) {
			this.dir =normalize(dir);
			this.p0 = p0;
		}
		

}

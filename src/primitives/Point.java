package primitives;
/**
 * Class Point is the basic class representing a … of Euclidean primitives in Cartesian
 * 3-Dimensional coordinate system.
 * @author shira levinzon and avigail uzan
*/

public class Point {
	 Double3 xyz;
	 /**
	  * constructor that receiving Object from Double3 type 
	  * @param xyz
	  */
		 
	public Point(Double3 xyz)
	{
		this.xyz=xyz;
	}
	/**
	 * constructor that receiving the values of the three coordinates from Double
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Point(double d1,double d2,double d3)
	{
		this.xyz=new Double3(d1,d2,d3);
	}
	
	
	@Override
	  public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Point)) return false;
	      Point other = (Point)obj;
	      return this.xyz.equals(other.xyz);
	   }
	
	
	@Override
	public String toString() {
		return "Point [xyz=" + xyz.toString() + "]";
	}

	
	/**
	 * Sum two floating point triads into a new triad where each couple of numbers
	 * is summarized
	 * 
	 * @param rhs right handle side operand for addition
	 * @return result of add
	 */
	public Point add(Vector v) {
		return new Point(v.xyz.add(this.xyz));
	}
	/**
	 * Subtract two floating point triads into a new triad where each couple of
	 * numbers is subtracted
	 * 
	 * @param rhs right handle side operand for addition
	 * @return result of add
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}
	/**
	 * calculate squared distance between two points
	 * @param p- point
	 * @return squared distance between two points 
	 */
	double DistanceSquared(Double3 d) 
	{
		//Point p=new Point(d);
		Point po=new Point(d.subtract(this.xyz));
		return po.xyz.d1*po.xyz.d1 + po.xyz.d2*po.xyz.d2 + po.xyz.d3*po.xyz.d3;
	}
	/**
	 * calculate the distance between two points 
	 * @param p- point
	 * @return distance between two points 
	 */
	 double Distance(Double3 d) 
	 {
		//Point p=new Point(d);
		return Math.sqrt(DistanceSquared(d));
	 }
}

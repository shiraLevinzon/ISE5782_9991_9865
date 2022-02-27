package primitives;

public class Point {
	 Double3 xyz;
	 /**constructors
	  * constructor that receiving the values of the three coordinates from Double
	  * constructors that receiving Object from Double3 type
	  * @param xyz
	  * 
	  */
		 
	public Point(Double3 xyz) {
		this.xyz=new Double3(xyz.d1,xyz.d2,xyz.d3);
	}

	public Point(double d1,double d2,double d3) {
		this.xyz=new Double3(d1,d2,d3);
	}
	//Override equals func that compare between two points and return true if they equal,otherwise כשךדק
	
	@Override
	  public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Point)) return false;
	      Point other = (Point)obj;
	      return this.xyz.equals(other.xyz);
	   }
	 // Override to string func
	@Override
	public String toString() {
		return "Point [xyz=" + xyz.toString() + "]";
	}
	//Get Func
	public Double3 getXyz() {
		return xyz;
	}
	
	Point add(Vector v) {
		return new Point(v.getXyz().add(this.xyz));
	}
	
	Vector subtract(Point rhs) {
		return new Vector();
	}
	
	

}

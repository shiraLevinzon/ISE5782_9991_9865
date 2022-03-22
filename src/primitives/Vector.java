package primitives;
/**
 * This class extends Point class and will handle all functions related to the Vector
 * @author shira levinzon and avigail uzan
 *
 */
public class Vector extends Point
{
	/**
	 * constructor that receiving the values of the three coordinates from Double
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3){
		super(d1,d2,d3);
		if(this.xyz.equals( Double3.ZERO))
			throw new IllegalArgumentException("the argument isn't lagal");
	}
	
	/**
	 * constructors that receiving Object from Double3 type 
	 * @param xyz
	 */
	public Vector(Double3 xyz) {
		super(xyz);
		if(this.xyz.equals( Double3.ZERO))
			throw new IllegalArgumentException("the argument isn't lagal");
	}
	
	@Override
	public String toString(){
		return "Vector- " + super.toString();
	}
	
	/**
	 * performs addition between two vectors.
	 * @param num
	 * @return a new vector as the sum of two vectors       
	 */
	public Vector add(Vector other){
		return new Vector((super.add(other)).xyz);
	}
	
	/**
	 * multiplies a vector by a number
	 * @param num
	 * @return a new vector multiplied by num       
	 */
	public Vector scale(int num){
		return new Vector((super.xyz.scale(num)));
	}
	
	/**
	 * Scalar product
	 * @param other
	 * @return Scalar product between this to other
	 */
	public double dotProduct (Vector other){
		return this.xyz.d1*other.xyz.d1 + this.xyz.d2*other.xyz.d2 + this.xyz.d3*other.xyz.d3;
	}
	
	/**
	 * Vector product
	 * @param other
	 * @return Vector product between this to other
	 */
	public Vector crossProduct (Vector other){
		return new Vector(this.xyz.d2*other.xyz.d3-this.xyz.d3*other.xyz.d2,this.xyz.d3*other.xyz.d1-this.xyz.d1*other.xyz.d3,this.xyz.d1*other.xyz.d2-this.xyz.d2*other.xyz.d1);
	}
	
	
	/**
	 * Calculate the length of the vector squared
	 * @return length of the vector squared
	 */
	public double lengthSquared (){
		Point p=new Point (Double3.ZERO);
		return super.DistanceSquared(p);
	}
	/**
	 * Calculate the length of the vector
	 * @return length of the vector
	 */
	public double length(){
		return Math.sqrt(this.lengthSquared());
	}
	/**
	 * normalize the vector
	 * @return a new vector which is the original vector, normalized
	 */
	public Vector normalize(){
		return new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length());
	}
	
}

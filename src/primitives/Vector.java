package primitives;
/**
 * This class extends Point class and will handle all functions related to the Vector
 * @author shira levinzon and avigail uzan
 *
 */
public class Vector extends Point
{
<<<<<<< HEAD
	public Vector(double d1, double d2, double d3){
=======
	/**
	 * constructor that receiving the values of the three coordinates from Double
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3)
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		super(d1,d2,d3);
		if(this.xyz.equals( Double3.ZERO))
			throw new IllegalArgumentException("the argument isn't lagal");
	}
<<<<<<< HEAD
	
	public Vector(Double3 xyz) {
=======
	/**
	 * constructors that receiving Object from Double3 type 
	 * @param xyz
	 */
	public Vector(Double3 xyz) 
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		super(xyz);
		if(this.xyz.equals( Double3.ZERO))
			throw new IllegalArgumentException("the argument isn't lagal");
	}
	
	@Override
	public String toString(){
		return "Vector- " + super.toString();
	}
<<<<<<< HEAD
	public Vector add(Vector other){
=======
	
	
	/**
	 * performs addition between two vectors.
	 * @param num
	 * @return a new vector as the sum of two vectors       
	 */
	public Vector add(Vector other)
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		return new Vector((super.add(other)).xyz);
	}
	
<<<<<<< HEAD
	public Vector scale(int num){
=======
	
	/**
	 * multiplies a vector by a number
	 * @param num
	 * @return a new vector multiplied by num       
	 */
	public Vector scale(int num)
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		return new Vector((super.xyz.scale(num)));
	}
<<<<<<< HEAD
	public double dotProduct (Vector other){
=======
	
	/**
	 * Scalar product
	 * @param other
	 * @return Scalar product between this to other
	 */
	public double dotProduct (Vector other)
	{
		/* יש כאן מלא בדיקות לעשות על הוקטורים*/
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		Vector tmp= new Vector(this.xyz.product(other.xyz));
		return tmp.xyz.d1+tmp.xyz.d2+tmp.xyz.d3;
	}
<<<<<<< HEAD
	
	public Vector crossProduct (Vector other){
=======
	/**
	 * Vector product
	 * @param other
	 * @return Vector product between this to other
	 */
	public Vector crossProduct (Vector other)
	{
		/* יש כאן מלא בדיקות לעשות על הוקטורים*/
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		return new Vector(this.xyz.d2*other.xyz.d3-this.xyz.d3*other.xyz.d2,this.xyz.d3*other.xyz.d1-this.xyz.d1*other.xyz.d3,this.xyz.d1*other.xyz.d2-this.xyz.d2*other.xyz.d1);
	}
<<<<<<< HEAD
	
	public double lengthSquared (){
=======
	/**
	 * Calculate the length of the vector squared
	 * @return length of the vector squared
	 */
	public double lengthSquared ()
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		return super.DistanceSquared(Double3.ZERO);
	}
<<<<<<< HEAD
	
	public double length(){
=======
	/**
	 * Calculate the length of the vector
	 * @return length of the vector
	 */
	public double length()
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		return Math.sqrt(this.lengthSquared());
	}
<<<<<<< HEAD
	
	public Vector normalize(){
=======
	/**
	 * normalize this vector
	 * @return a new vector which is the original vector, normalized
	 */
	public Vector normalize()
	{
>>>>>>> branch 'master' of https://github.com/shiraLevinzon/ISE5782_9991_9865
		//double t=this.length();
		return new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

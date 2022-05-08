package geometries;
import primitives.*;
/**
 *return the normal of all the geometric shapes realized from it 
 * @author user
 *
 */
public abstract class Geometry extends Intersectable {

	protected Color emission=Color.BLACK;
	public abstract Vector getNormal(Point p);
	/***
	 * return emission
	 */
	public Color getEmission() {
		return this.emission;
	}
	/***
	 * 
	 */
	public Intersectable setEmission(Color other) {
		 this.emission=other;
		 return this;
	}	
}

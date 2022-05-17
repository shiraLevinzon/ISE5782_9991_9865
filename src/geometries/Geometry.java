package geometries;
import primitives.*;
/**
 *return the normal of all the geometric shapes realized from it 
 * @author user
 *
 */
public abstract class Geometry extends Intersectable {

	protected Color emission=Color.BLACK;
	private Material material=new Material();
	
	
	/***
	 * getters and setters
	 * @return
	 */
	public Material getMaterial() {
		return material;
	}
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
	/***
	 * return emission
	 */
	public Color getEmission() {
		return this.emission;
	}
	
	
	
	public abstract Vector getNormal(Point p);
	
	public Geometry setEmission(Color other) {
		 this.emission=other;
		 return this;
	}	
}

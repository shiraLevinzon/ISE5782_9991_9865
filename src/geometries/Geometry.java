package geometries;
import primitives.*;
/**
 *return the normal of all the geometric shapes realized from it 
 * @author user
 *
 */
public interface Geometry extends Intersectable {

	Vector getNormal(Point p); 
}

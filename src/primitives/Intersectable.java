package primitives;

import java.util.List;
/***
 * 
 * interface for finding the Intersectable points
 *
 */
public interface Intersectable {
	
	public List<Point> findIntsersections(Ray ray);
}

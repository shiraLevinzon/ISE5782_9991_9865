package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
/***
 * 
 * interface for finding the Intersectable points
 *
 */
public interface Intersectable {
	
	public List<Point> findIntsersections(Ray ray);
}

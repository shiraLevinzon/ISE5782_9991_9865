package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries extends Intersectable {
	
	private List<Intersectable> bodies;

	/**
	 * Default Ctor build empty list of bodies
	 */
	public Geometries() {
		this.bodies = new LinkedList<Intersectable>();
		/*minBoundary = new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		maxBoundary = new Point3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);*/
	}

	/**
	 * Ctor with arguments
	 * 
	 * @param geometries list of bodies
	 */
	public Geometries(Intersectable... geometries) {
		this(); // first we initialize the list
	//	add(geometries);
	}

	/**
	 * get all the bodies
	 */
	public List<Intersectable> getBudies() {
		return bodies;
	}

	/**
	 * add bodies to the list of bodies
	 * 
	 * @param geometries list of bodies to add
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable intersectable : geometries) {
			bodies.add(intersectable);
			//lastAdded = intersectable;
			//setMinBoundary();
			//setMaxBoundary();
		}
	}

	/**
	 * This function returns only the relevant point of the intersection using the
	 * help of regular grid structure if the box is null that means we call the
	 * regular find intersection (without acceleration)
	 * 
	 * @param ray            - Ray that intersect
	 * @param box            - box of the scene
	 * @param shadowRaysCase - if its shadow ray we traverse always all the way .
	 * @param dis            - distance for find intersection
	 * @return the relevant point
	 */
	/*public List<GeoPoint> findRelevantIntersections(Ray ray, Box box, boolean shadowRaysCase, double dis) {
		if (box == null)
			return this.findGeoIntersections(ray, dis);
		return box.findIntersectionsInTheBox(ray, shadowRaysCase, dis);
	}*/

	/*@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
		List<GeoPoint> points = null;
		if (bodies != null) {
			for (var body : bodies) {
				var result = body.findGeoIntersections(ray, max);
				if (result != null)
					if (points == null)
						points = new LinkedList<GeoPoint>(result);
					else
						points.addAll(result);
			}
		}
		return points;
	}*/

	/*@Override
	public void setMaxBoundary() {
		double x, y, z;
		x = lastAdded.maxBoundary.getX();
		y = lastAdded.maxBoundary.getY();
		z = lastAdded.maxBoundary.getZ();
		double maxX = maxBoundary.getX();
		double maxY = maxBoundary.getY();
		double maxZ = maxBoundary.getZ();
		if (x > maxX)
			maxX = x;
		if (y > maxY)
			maxY = y;
		if (z > maxZ)
			maxZ = z;
		maxBoundary = new Point3D(maxX, maxY, maxZ);
	}

	@Override
	public void setMinBoundary() {
		double x, y, z;
		x = lastAdded.minBoundary.getX();
		y = lastAdded.minBoundary.getY();
		z = lastAdded.minBoundary.getZ();
		double minX = minBoundary.getX();
		double minY = minBoundary.getY();
		double minZ = minBoundary.getZ();
		if (x < minX)
			minX = x;
		if (y < minY)
			minY = y;
		if (z < minZ)
			minZ = z;
		minBoundary = new Point3D(minX, minY, minZ);
	}*/
	@Override
	public List<Point> findIntsersections(Ray ray) {
		List<Point> points = null;
		if (bodies != null) {
			for (var body : bodies) {
				var result = body.findIntsersections(ray);
				if (result != null)
					if (points == null)
						points = new LinkedList<Point>(result);
					else
						points.addAll(result);
			}
		}
		return points;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
	

	



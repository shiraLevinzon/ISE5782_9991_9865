package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder  extends Tube {
	
	public Cylinder(Ray axisRay, double radius) {
		super(axisRay, radius);
		// TODO Auto-generated constructor stub
	}

	private double height;
	
	

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}

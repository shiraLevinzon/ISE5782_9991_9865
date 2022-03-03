package geometries;

import primitives.Point;

public class Triangle extends Polygon implements Geometry {

	/**
	 * constructor that receive three points and activate the super constructor to create the triangle 
	 * @param vertices
	 */
	public Triangle(Point... vertices){
		super(vertices);
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices.toString() + ", plane=" + plane.toString() + "]";
	}

}

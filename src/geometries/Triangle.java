package geometries;

import primitives.Point;

public class Triangle extends Polygon implements Geometry {

	public Triangle(Point... vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices.toString() + ", plane=" + plane.toString() + "]";
	}

}

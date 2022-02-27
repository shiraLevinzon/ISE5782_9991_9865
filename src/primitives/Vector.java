package primitives;

public class Vector extends Point {

	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		// TODO Auto-generated constructor stub
	}

	public Vector(Double3 xyz) {
		super(xyz);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Vector- " + super.toString();
	}

	
	

}

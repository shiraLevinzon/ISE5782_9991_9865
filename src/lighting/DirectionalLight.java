package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight  extends Light implements LightSource  {
	private Vector direction;
	public DirectionalLight(Color Ia,Vector d) {
		super(Ia);
		this.direction=d;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity();
	}
	
	@Override
	public Vector getL(Point p) {
		return direction.normalize();
	}

	


}

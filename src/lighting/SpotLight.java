package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
	
	private Vector direction;
	
	public SpotLight(Color Ia, Point po,Vector dir, double kC, double kL, double kQ) {
		super(Ia, po, kC, kL, kQ);
		this.direction=dir.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		var result=direction.dotProduct(getL(p));
		if(result<=0)
			return Color.BLACK;
		return super.getIntensity(p).scale(result);
	}

	@Override
	public Vector getL(Point p) {
		return super.getL(p);
	}
	

}

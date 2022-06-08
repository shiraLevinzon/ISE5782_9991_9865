package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight {
	
	private Vector direction;
	
	public SpotLight(Color Ia, Point po,Vector dir, double kC, double kL, double kQ) {
		super(Ia, po, kC, kL, kQ);
		this.direction=dir.normalize();
	}
	public SpotLight(Color Ia, Point po,Vector dir)
	{
		super(Ia, po, 1, 0, 0);
		this.direction=dir.normalize();
	}
	/**
	 * SpotLight's Constructor
	 * @param intensity the light's intensity
	 * @param position the light's position-point
	 * @param gridSize the light's grid's size
	 * @param direction the light's direction
	 */
	public SpotLight(Color intensity, Point position, int gridSize, Vector direction) {
		super(intensity, position, gridSize,1,0,0);
		this.direction = direction;	
	}
	@Override
    public Color getIntensity(Point p) {
        Vector l = getL(p);
        if (l == null)
            l = direction;
        double cos = Util.alignZero(l.dotProduct(direction));
        if (cos <= 0) return Color.BLACK;
        return super.getIntensity(p).scale(cos);
    }

	/*public Color getIntensity(Point p) {
		var result=direction.dotProduct(getL(p));
		if(result<=0)
			return Color.BLACK;
		return super.getIntensity(p).scale(result);
	}*/

	@Override
	public Vector getL(Point p) {
		return super.getL(p);
	}
	
	
	

}

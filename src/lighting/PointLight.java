package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
	
	private Point position;
	private double kC=1,kL=0,kQ=0;
	private double gridSize;
	
	
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;

	}

	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;

	}

	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	public PointLight(Color Ia, Point po, double kC,double kL,double kQ) 
	{
		super(Ia);
		this.position=po;
		this.kC=kC;
		this.kL=kL;
		this.kQ=kQ;
		
	}
	/**
	 * PointLight's Constructor
	 * @param intensity the light's intensity
	 * @param position the light's position-point
	 * @param gridSize the light's grid's size
	 */
	public PointLight(Color intensity, Point position, double gridSize,double kC,double kL,double kQ) {
		super(intensity);
		this.position = position;
		this.gridSize = gridSize;
		this.kC=kC;
		this.kL=kL;
		this.kQ=kQ;
	}
	@Override
	public Color getIntensity(Point p) {
		var distance=p.Distance(position);
		return super.getIntensity().reduce(kC+kL*distance+kQ*distance*distance);
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize();
	}
	
	@Override
	public double getDistance(Point point) {
		return position.Distance(point);
	}
	
	@Override
	public double getGridSize() {
		return gridSize;
	}

}

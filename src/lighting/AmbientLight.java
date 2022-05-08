package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
	Color intensity ;
	public AmbientLight (Color Ia,Double3 Ka)
	{
		this.intensity=Ia.scale(Ka);
	}
	public AmbientLight()
	{
		this.intensity=Color.BLACK;
	}
	
	public Color getIntensity() {
		return this.intensity;
	}
		
}

package lighting;

import primitives.Color;

abstract class Light {
	
	private Color intensity;
	
	protected Light(Color Ia)
	{
		this.intensity=Ia;
	}
	public Color getIntensity() {
		return this.intensity;
	}
	

}

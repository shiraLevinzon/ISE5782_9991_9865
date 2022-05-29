package primitives;

public class Material {
	
	public Double3 kD=new Double3(0,0,0),kS=new Double3(0,0,0),kT=new Double3(0,0,0),kR=new Double3(0,0,0);
	public int nShininess=0;
	
	/**
	 * @param kT the kT to set
	 */
	public Material setkT(Double3 kT) {
		this.kT = kT;
		return this;
	}
	/**
	 * @param kR the kR to set
	 */
	public Material setkR(Double3 kR) {
		this.kR = kR;
		return this;
	}
	/**
	 * @param kD the kD to set
	 */
	public Material setkD(Double3 kD) {
		this.kD =kD;
		return this;
	}
	/**
	 * @param kS the kS to set
	 */
	public Material setkS(Double3 kS) {
		this.kS = kS;
		return this;

	}
	/**
	 * @param kD the kD to set
	 */
	public Material setkD(double kD) {
		this.kD = new Double3(kD);
		return this;

	}
	/**
	 * @param kS the kS to set
	 */
	public Material setkR(double kR) {
		this.kR = new Double3(kR);
		return this;

	}
	/**
	 * @param kD the kD to set
	 */
	public Material setkT(double kT) {
		this.kT = new Double3(kT);
		return this;

	}
	/**
	 * @param kS the kS to set
	 */
	public Material setkS(double kS) {
		this.kS = new Double3(kS);
		return this;

	}
	/**
	 * @param nShininess the nShininess to set
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;

	}

}

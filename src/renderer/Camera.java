/**
 * 
 */
package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * @author shira
 *
 */
public class Camera {
	private Point p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;
	public Point getP0() {
		return p0;
	}
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}
	public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;		
		return this;
	}
	public Vector getvUp() {
		return vUp;
	}
	public Vector getvTo() {
		return vTo;
	}
	public Vector getvRight() {
		return vRight;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getDistance() {
		return distance;
	}
	/**
	 * A camera constructor that receives two vectors in the direction of the
	 * camera(up,to) and point3d for the camera lens
	 * 
	 * @param p0  - location of the camera lens
	 * @param vTo - starting at P0 and pointing forward
	 * @param vUp -starting at P0 and pointing upwards
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!Util.isZero(vUp.dotProduct(vTo))) // check if vTo not plumb to vUp
			throw new IllegalArgumentException("Vectors are not vertical");
		this.p0 = p0;
		this.vTo = vTo.normalize();
		this.vUp = vUp.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
	}
	/**
	 * setter for size of view plane
	 * 
	 * @param width  - a width of plane view
	 * @param height - a height of plane view
	 * @return the camera himself
	 */
	public Camera setViewPlaneSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * setter for distance from camera to view plane
	 * 
	 * @param distance - a distance from camera to view plane
	 * @return the camera himself
	 */
	public Camera setViewPlaneDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * The function builds a ray through a given pixel (j,i) within the grid of nX
	 * and nY
	 * 
	 * @param nX - the size of width
	 * @param nY - the size of height
	 * @param j  - the index in the column
	 * @param i  - the index in the row
	 * @return ray that passes in given pixel in the grid
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

		// image center
		Point pc = p0.add(vTo.scale(distance));
		// ratio
		var ry = height / nY;
		var rx = width / nX;
		// pixel(i,j) center
		var yi = (i - (nY - 1) / 2.0) * ry;
		var xj = (j - (nX - 1) / 2.0) * rx;

		Point pij = pc;
		if (xj != 0)
			pij = pij.add(vRight.scale(xj));
		if (yi != 0)
			pij = pij.add(vUp.scale(-yi));

		Vector vij = pij.subtract(p0);

		return new Ray(vij,p0);
	}
	public void renderImage()
	{
		if(this.p0==null || this.vTo==null|| this.vUp==null || this.rayTracerBase==null || this.vRight==null||this.imageWriter==null)
			throw new MissingResourceException("one of the properties contains empty value", null, null);
		throw new UnsupportedOperationException();
	}
	public void printGrid(int interval, Color color) 
	{
		if(this.imageWriter==null)
			throw new MissingResourceException("image writer failed", null, null);
		var writer = new ImageWriter("firstImage", 800, 500);
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 800; j++) {
				if (i % 50 == 0 || j % 50 == 0 || i == 799 || j == 499)
					writer.writePixel(j, i, color);
				else
					writer.writePixel(j, i, new primitives.Color(0,0,255));
			}
		}
		writer.writeToImage();
	}
	public void writeToImage()
	{
		if(this.imageWriter==null)
			throw new MissingResourceException("image writer failed", null, null);
		imageWriter.writeToImage();
	}
}

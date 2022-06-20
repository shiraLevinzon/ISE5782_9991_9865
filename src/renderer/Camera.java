/**
 * 
 */
package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * @author shira and avigail
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
	private RayTracerBase rayTracer;
	private int threadsCount = 0;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage
	public Point getP0() {
		return p0;
	}
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}
	public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
		this.rayTracer = rayTracerBase;		
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
	/**
	 * Set multi-threading <br>
	 * - if the parameter is 0 - number of cores less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Camera setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
		if (threads != 0)
			this.threadsCount = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			this.threadsCount = cores <= 2 ? 1 : cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Camera setDebugPrint() {
		print = true;
		return this;
	}

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multithreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long maxRows = 0;
		private long maxCols = 0;
		private long pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long counter = 0;
		private int percents = 0;
		private long nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			this.maxRows = maxRows;
			this.maxCols = maxCols;
			this.pixels = (long) maxRows * maxCols;
			this.nextCounter = this.pixels / 100;
			if (Camera.this.print)
				System.out.printf("\r %02d%%", this.percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++this.counter;
			if (col < this.maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (Camera.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			++row;
			if (row < this.maxRows) {
				col = 0;
				target.row = this.row;
				target.col = this.col;
				if (Camera.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percent = nextP(target);
			if (Camera.this.print && percent > 0)
				synchronized (this) {
					notifyAll();
				}
			if (percent >= 0)
				return true;
			if (Camera.this.print)
				synchronized (this) {
					notifyAll();
				}
			return false;
		}

		/**
		 * Debug print of progress percentage - must be run from the main thread
		 */
		public void print() {
			if (Camera.this.print)
				while (this.percents < 100)
					try {
						synchronized (this) {
							wait();
						}
						System.out.printf("\r %02d%%", this.percents);
						System.out.flush();
					} catch (Exception e) {
					}
		}
	}

	
	/**
	 * This function renders image's pixel color map from the scene included with the Renderer object.
	 * This is a wrapping function to the function renderImageThreaded (when using only one ray and adaptive-supersampling isn't required).
	 */
	public Camera renderImage() throws MissingResourceException {
		renderImage(1, false);
		return this;
	}
	
	/**
	 * This function renders image's pixel color map from the scene included with the Renderer object,
	 * including the use in beam of rays.
	 * This is a wrapping function to the function renderImageThreaded (when adaptive-supersampling isn't required).
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 */
	public Camera renderImage(int numOfRays) throws MissingResourceException {
		renderImage(numOfRays ,false);
		return this;
	}

	/**
	 * This function renders image's pixel color map from the scene included with the Renderer object,
	 * including the use in beam of rays and adaptive-supersampling if required.
	 * This is a wrapping function to the function renderImageThreaded.
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 */
	public Camera renderImage(int numOfRays, boolean adaptiveSupersampling) {
		if(this.p0==null || this.vTo==null|| this.vUp==null || this.rayTracer==null || this.vRight==null||this.imageWriter==null)
			throw new MissingResourceException("one of the properties contains empty value", null, null);

		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		if (threadsCount == 0)
			for (int i = 0; i < nY; ++i)
				for (int j = 0; j < nX; ++j)
					castRay(nX, nY, j, i, numOfRays, adaptiveSupersampling);
		else
			renderImageThreaded(numOfRays, adaptiveSupersampling);
		return this;
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
	/**
	 * Cast ray from camera in order to color a pixel
	 * @param nX resolution on X axis (number of pixels in row)
	 * @param nY resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 */
	private void castRay(int nX, int nY, int col, int row, int numOfRays, boolean adaptiveSupersampling) {
		Ray ray = constructRayThroughPixel(nX, nY, col, row);
		Color color = rayTracer.traceRay(ray, numOfRays, adaptiveSupersampling);
		imageWriter.writePixel(col, row, color);
	}
	
	/**
	 * This function renders image's pixel color map from the scene included with the Renderer object - with multi-threading
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 */
	private void renderImageThreaded(int numOfRays, boolean adaptiveSupersampling) {
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		final Pixel thePixel = new Pixel(nY, nX);
		// Generate threads
		Thread[] threads = new Thread[threadsCount];
		for (int i = threadsCount - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel))
					castRay(nX, nY, pixel.col, pixel.row, numOfRays, adaptiveSupersampling);
			});
		}
		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Print percents on the console
		thePixel.print();

		// Ensure all threads have finished
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}

		if (print)
			System.out.print("\r100%");
	}
}

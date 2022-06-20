/**
 * @author 
 * @author 
 */


package renderer;

import java.util.LinkedList;
import java.util.List;
import lighting.*;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.*;



/**
 * RayTracerBasic's class. Extends the abstract-class RayTracerBase.
 * Represents the using of the rays that was traced from the camera, integrating with the scene - when each pixel traces a single ray.
 */
public class RayTracerBasic extends RayTracerBase {
	
	private static final double INITIAL_K = 1.0;
	private static final int MAX_CALC_COLOR_LEVEL = 100;
	private static final double MIN_CALC_COLOR_K = 0.001;

	
	
	
	/**
	 * RayTracerBasic's Constructor
	 * @param scene The scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	
	
	@Override
	public Color traceRay(Ray ray) {
		return traceRay(ray, 1, false);
	}

	@Override	
	public Color traceRay(Ray ray, int numOfRays) {
		return traceRay(ray, numOfRays, false);
	}
	
	@Override	
	public Color traceRay(Ray ray, int numOfRays, boolean adaptiveSupersampling) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray, numOfRays, adaptiveSupersampling);
	}
	
	/**
	 * Finds the closest intersection-geoPoint to the starting-point of the given ray.
	 * @param ray the given constructed-ray
	 * @return The closest intersection-geoPoint to the ray's starting-point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
		return intersections == null ? null : ray.findClosestGeoPoint(intersections);
	}
	
	
	/**
	 * Calculates the color of the given geoPoint that's on the given ray.
	 * @param geoPoint the geoPoint
	 * @param ray the constructed-ray
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The calculated color
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray, int numOfRays, boolean adaptiveSupersampling) {
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K, numOfRays, adaptiveSupersampling)
		.add(scene.ambientLight.getIntensity());
		}
	
	/**
	 * Calculates the color of the given geoPoint that's on the given ray, includes the claculations of the local and global effects.
	 * @param geoPoint the geoPoint
	 * @param ray the constructed-ray
	 * @param level the max-level of recursion
	 * @param k initial k for the discount-factors
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The calculated color of the point's emission, local and global effects
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k, int numOfRays, boolean adaptiveSupersampling) {
		Color color = geoPoint.geometry.getEmission();
		color = color.add(calcLocalEffects(geoPoint, ray, k,numOfRays, adaptiveSupersampling));
		return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k, numOfRays, adaptiveSupersampling));
	}
	
	
	/**
	 * Calculates the local-effects of the scene's light-sources on the intersection-geoPoint's color.
	 * @param intersection the intersection-geoPoint
	 * @param ray the constructed-ray
	 * @param k initial k for the discount-factors
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The color of the intersection-geoPoint determined by the local-effects of the scene's light-sources
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k, int numOfRays, boolean adaptiveSupersampling) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (isZero(nv))
			return Color.BLACK;
		
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		Double3 kd = material.kD;
		Double3 ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				// PICTURE IMPROVEMENT SOFT SHADOWS - use beam of rays in the function transparency:
				double ktr = transparency(lightSource, l, n, intersection, numOfRays,adaptiveSupersampling);
				//if (unshaded(lightSource,l,n,intersection)) {
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(lightIntensity.scale(calcDiffusive(kd, nl)+calcSpecular(ks, l, n, nl, v, nShininess)));
					}
			}
		}
		return color;
	}
	
	/**
	 * Calculates the difusion-addition to the geoPoint's color, determined by one of the light-sources.
	 * @param kd the defusion-factor of the geometry's material 
	 * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source
	 * @return The difusion-addition to the geoPoint's color
	 */
	private double calcDiffusive(Double3 kd, double nl) {
		//double nl = alignZero(n.dotProduct(l));
		if(nl < 0)
			nl = (-1)*nl;
		return kd.scale(nl).getD1();		
	}
	
	/**
	 * Calculates the specularization-addition to the geoPoint's color, determined by one of the light-sources.
	 * @param ks the specularization-factor of the geometry's material 
	 * @param l the light-vector of the light-source
	 * @param n the intersected-geometry's normal
	 * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source (SAVES RECALCULATING)
	 * @param v the direction-vector of the constructed-ray
	 * @param nShininess the specularization's-exponential-factor of the geometry's material 
	 * @return The specularization-addition to the geoPoint's color, determined by one of the light-sources
	 */
	private double calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess) {
		//double twiceNL = alignZero(n.dotProduct(l)*2);
		//Vector r = l.subtract(n.scale(twiceNL)).normalized();
		Vector r = l.subtract(n.scale(2*nl)).normalize();
		double vr = alignZero(v.dotProduct(r));
		if(vr>0)
			return 0;
		
		double vrPowed = Math.pow((-1)*vr,nShininess);
		return ks.scale(vrPowed).getD1();
	}

	/**
	 * Checks wether the intersection-geoPoint is unshaded regarding one of the light-sources.
	 * @param light the light-source
	 * @param l the light-vector of the light-source
	 * @param n the intersected-geometry's normal
	 * @param geoPoint the intersection-geoPoint
	 * @return If the intersection-geoPoint is unshaded - true, otherwise - false
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) 
			return true;
		double lightDistance = light.getDistance(geoPoint.point);
		for (GeoPoint gp : intersections) {
			if(alignZero(gp.point.Distance(geoPoint.point)-lightDistance)<=0 && Util.isZero(gp.geometry.getMaterial().kT.getD1()))
				return false;
		}
		return true;
	}
	
	/**
	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources.
	 * @param light the light-source
	 * @param l the light-vector of the light-source
	 * @param n the intersected-geometry's normal
	 * @param geoPoint the intersection-geoPoint
	 * @return The discount-factor of the shading on this intersection-geoPoint
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection,n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return 1;
		double ktr = 1;
		double lightDistance = light.getDistance(geoPoint.point);
		for (GeoPoint gp : intersections) {
			if(alignZero(gp.point.Distance(geoPoint.point)-lightDistance)<=0) {
				ktr= gp.geometry.getMaterial().kT.scale(ktr).getD1();
				if (ktr < MIN_CALC_COLOR_K)
					return 0;
				}
			}
		return ktr;
	}
	
	/**
	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources.
	 * @param ray the light-ray from the light-source (or from a spot on it's grid)
	 * @param n the intersected-geometry's normal
	 * @param geoPoint the intersection-geoPoint
	 * @return The discount-factor of the shading on this intersection-geoPoint
	 */
	private double transparency(Ray ray, Vector n, GeoPoint geoPoint) {
		Vector thisRayToLightDir = ray.getDir().scale(-1); // from point to light source ( - in the fitting position in the light-source's grid)
		Ray thisRayToLight = new Ray(geoPoint.point, thisRayToLightDir,n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(thisRayToLight);
		if (intersections == null)
			return 1;
		double ktr = 1; // this ray's shading discount-factor
		double thisRayToLightDistance = ray.getP0().Distance(geoPoint.point); // distance of this ray - from the intersection-geoPoint to the current spot in the light-source's grid which is "ray"'s starting point...
		for (GeoPoint gp : intersections) {
			if(alignZero(gp.point.Distance(geoPoint.point)-thisRayToLightDistance)<=0) {
				ktr = gp.geometry.getMaterial().kT.scale(ktr).getD1();
				if (ktr < MIN_CALC_COLOR_K)
					return 0;
			}
		}
		return ktr;
	}
	
	

	/**
	 * MINI PROJECT 1 - PICTURE IMPROVEMENT SOFT SHADOWS
	 * THE UPDATED FUNCTION TRANSPARENCY:
	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
	 * using a beam of rays to create soft shadows for picture improvement ( - getting the average discount-factor from the rays in the beam).
	 * @param light the light-source
	 * @param l the light-vector of the light-source
	 * @param n the intersected-geometry's normal
	 * @param geoPoint the intersection-geoPoint
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @return The discount-factor of the shading on this intersection-geoPoint
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint, int numOfRays) {
		double lightDistance = light.getDistance(geoPoint.point);
		// if the distance between the intersection-geoPoint and the light-source is 0 then there's no shading at all - return discount-factor=1:
		if(isZero(lightDistance))
			return 1;
		
		// construct a beam of rays from the light-source towards the intersection-geoPoint:
		List<Ray> rays = constructBeamOfRaysUsingGrid(geoPoint.point, l, lightDistance, light.getGridSize(), numOfRays);
		double ktrSum = 0; // sum of the rays' - from the beam of rays - shading discount-factors
		for (Ray ray : rays)
			// calculate this ray's discount-factors and update the sum of shading discount-factors:
			ktrSum+=transparency(ray, n, geoPoint);
			
		// divide ktrSum by the overall number of rays in the beam to get an average of all discount-factors, and return it:
		return ktrSum / rays.size();
	}
	
	/**
	 * MINI PROJECT 1 - PICTURE IMPROVEMENT SOFT SHADOWS
	 * THE NEW FUNCTION - CONSTRUCTING THE BEAM OF RAYS:
	 * Constructs and returns a beam of rays from the grid towards the given point.
	 * @param point the point - where the beam of ray "points" to
	 * @param dirToPoint the direction-vector from the grid's center point towards the point
	 * @param distance the distance between the grid and the point
	 * @param gridSize the grid's size ( - both width and height)
	 * @param numOfRays the number of constructed rays wanted in the beam of rays
	 * @return a beam of rays from the grid towards the given point
	 */
	private List<Ray> constructBeamOfRaysUsingGrid(Point point, Vector dirToPoint, double distance, double gridSize, int numOfRays) {
		Point gridCenter=point.add(dirToPoint.scale(-distance));
		List<Ray> beamOfRays = new LinkedList<Ray>();
		// add the main-ray to the beam of rays:
		beamOfRays.add(new Ray(dirToPoint,gridCenter));
		// if the grid's size is 0 then only one ray can be constructed - the main-ray:
		if(isZero(gridSize))
    		return beamOfRays;
		
		Vector gridVright=dirToPoint.findOrthogonal().normalize();
    	Vector gridVup=gridVright.crossProduct(dirToPoint).normalize();
    	int numRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
    	double cubeSize = gridSize/numRaysInRowCol;
    	
    	Point tempCenter = gridCenter;
    	for(int i = 0 ; i < numRaysInRowCol ; i++) {
    		double PX= alignZero((i - 0.5*(numRaysInRowCol-1))*cubeSize);
    		for(int j = 0 ; j < numRaysInRowCol ; j++){
    			if(i!=0 || j!=0){
    				double PY= alignZero((j - 0.5*(numRaysInRowCol-1))*cubeSize);
                    if(PX != 0)
                    	tempCenter = tempCenter.add(gridVright.scale(PX));
                    if(PY != 0)
                    	tempCenter = tempCenter.add(gridVup.scale(PY));
                    // construct this ray and add it to the beam of rays:
                    beamOfRays.add(new Ray(point.subtract(tempCenter),tempCenter));
                    // reset tempCenter - the center of the current cube - to be the grid's center, to allow making the next iterations correctly:
                    tempCenter = gridCenter;
    			}
    		}    		 
    	}
    	
    	return beamOfRays;
	}
	
	
	
	/**
	 * MINI PROJECT 2 - PICTURE IMPROVEMENT SOFT SHADOWS AND PERFORMANCE IMPROVEMENT ADAPTIVE-SUPERSAMPLING
	 * THE NEW "WRAPPING" FUNCTION TRANSPARENCY:
	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
	 * using a beam of rays to create soft shadows for picture improvement and adaptive-supersampling (if required) for performance improvement.
	 * @param light the light-source
	 * @param l the light-vector of the light-source
	 * @param n the intersected-geometry's normal
	 * @param geoPoint the intersection-geoPoint
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The discount-factor of the shading on this intersection-geoPoint
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint, int numOfRays, boolean adaptiveSupersampling) {
		// if adaptive-supersampling isn't required or there is only 1 ray required or the grid's size is 0 -
		// then use transparency from mini project 1 (without adaptive-supersampling):
		if(!adaptiveSupersampling || numOfRays==1 || isZero(light.getGridSize()))
			return transparency(light, l, n, geoPoint, numOfRays);
		
		// else - use the function that improve performance by adaptive-supersampling:
		double lightDistance = light.getDistance(geoPoint.point);
		Point gridCenter=geoPoint.point.add(l.scale(-lightDistance));
		Vector gridVright=l.findOrthogonal().normalize();
    	Vector gridVup=gridVright.crossProduct(l).normalize();
    	int numRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
		return adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter, gridVright, gridVup, light.getGridSize(), numRaysInRowCol);
	}
	
	/**
	 * MINI PROJECT 2 - PICTURE IMPROVEMENT SOFT SHADOWS AND PERFORMANCE IMPROVEMENT ADAPTIVE SUPERSAMPLING
	 * THE NEW FUNCTION ADAPTIVESUPERSAMPLING:
	 * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources,
	 * using a beam of rays to create soft shadows for picture improvement and adaptive-supersampling for performance improvement.
	 * @param geoPoint the intersection-geoPoint
	 * @param n the intersected-geometry's normal
	 * @param gridCenter the grid's center
	 * @param gridVright the grid's "right"-direction
	 * @param gridVup the grid's "up"-direction
	 * @param gridSize the grid's size ( - both width and height)
	 * @param numOfRaysLeftInRowCol how many rays should yet be traced until we get a grid with x cubes when x=the original number of rays wanted in the beam
	 * @return The discount-factor of the shading on this intersection-geoPoint
	 */
	  private double adaptiveSupersamplingForTransparency(GeoPoint geoPoint, Vector n, Point gridCenter, Vector gridVright, Vector gridVup, double gridSize, int numOfRaysLeftInRowCol) {

		double halfGridSize=alignZero(gridSize/2);
		// 4 corners of the grid:
		Point corner1 = (gridCenter.add(gridVright.scale(halfGridSize))).add(gridVup.scale(halfGridSize));
		Point corner2 = (gridCenter.add(gridVright.scale(-halfGridSize))).add(gridVup.scale(halfGridSize));
		Point corner3 = (gridCenter.add(gridVright.scale(halfGridSize))).add(gridVup.scale(-halfGridSize));
		Point corner4 = (gridCenter.add(gridVright.scale(-halfGridSize))).add(gridVup.scale(-halfGridSize));
		// 4 rays from each of the corners towards the intersection-geoPoint: 
		Ray ray1 = new Ray(geoPoint.point.subtract(corner1),corner1);
		Ray ray2 = new Ray(geoPoint.point.subtract(corner2),corner2);
		Ray ray3 = new Ray(geoPoint.point.subtract(corner3),corner3);
		Ray ray4 = new Ray(geoPoint.point.subtract(corner4),corner4);
		// 4 discount-factors of the intersection-geoPoint's color gotten from each of the rays:
		double ktr1 = alignZero(transparency(ray1, n, geoPoint));
		double ktr2 = alignZero(transparency(ray2, n, geoPoint));
		double ktr3 = alignZero(transparency(ray3, n, geoPoint));
		double ktr4 = alignZero(transparency(ray4, n, geoPoint));
		
		// if all 4 ktr are equal - no need for further recursion, return this ktr:
		if(ktr1==ktr2 && ktr2==ktr3 && ktr3==ktr4)
			return ktr1;
		
		// the numOfRaysLeftInRowCol for the grid's quarters in the next recursion level - for each grid's quarter 
		// there'll be needed half rays in the row/column than the rays in the row/column that were needed in the original grid:
		int nextNumOfRaysLeftInRowCol=(numOfRaysLeftInRowCol/2);
		// if all wanted rays were traced or saved from unnecessary tracing - stop further recursions, return the average of ktrs from this level:
		if(nextNumOfRaysLeftInRowCol<1)
			return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
		
		// the grid's size for the grid's quarters in the next recursion level:
		double quarterGridSize=alignZero(gridSize/4);
		// if the grid's size of next recursion level is too small - stop further recursions, return the average of ktrs from this level:
		if(isZero(halfGridSize))
			return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
		
		Point gridCenter1 = (gridCenter.add(gridVright.scale(quarterGridSize))).add(gridVup.scale(quarterGridSize));
		Point gridCenter2 = (gridCenter.add(gridVright.scale(-quarterGridSize))).add(gridVup.scale(quarterGridSize));
		Point gridCenter3 = (gridCenter.add(gridVright.scale(quarterGridSize))).add(gridVup.scale(-quarterGridSize));
		Point gridCenter4 = (gridCenter.add(gridVright.scale(-quarterGridSize))).add(gridVup.scale(-quarterGridSize));
		
		ktr1 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter1, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
		ktr2 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter2, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
		ktr3 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter3, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
		ktr4 = adaptiveSupersamplingForTransparency(geoPoint, n, gridCenter4, gridVright, gridVup, halfGridSize, nextNumOfRaysLeftInRowCol);
		
		
		// return the average of the 4 ktrs got from the recursion steps:
		return alignZero((ktr1+ktr2+ktr3+ktr4)/4);
	}
	
	
	
	/**
	 * Calculates the global-effects of the scene's light-sources on the intersection-geoPoint's color.
	 * @param intersection the intersection-geoPoint
	 * @param ray the constructed-ray
	 * @param level the max-level of recursion
	 * @param k initial k for the discount-factors
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The color of the intersection-geoPoint determined by the global-effects of the scene's light-sources
	 */
	private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, double k, int numOfRays, boolean adaptiveSupersampling) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		Material material = intersection.geometry.getMaterial();
		double kkr = material.kR.scale(k).getD1();
		double kkt = material.kT.scale(k).getD1();
		Color color = Color.BLACK;
		
		if (kkr > MIN_CALC_COLOR_K)
			color = calcGlobalEffect(constructReflectedRay(intersection.point, v, n), level, material.kR, kkr, numOfRays, adaptiveSupersampling);
		if (kkt > MIN_CALC_COLOR_K) 
			color = color.add(calcGlobalEffect(constructRefractedRay(intersection.point, v, n), level, material.kT, kkt,numOfRays, adaptiveSupersampling));
		
		return  color;
	}
	
	/**
	 * The function calcGlobalEffects that allows the recursioning
	 * @param ray the ray constructed in this recursion-stage, which starts at some geometry
	 * @param level the max-level of recursion
	 * @param kR the initial k for the discount-factor regarding the value of the geometry's material's feature - on which the ray starts
	 * @param kkx the discount-factor of the color so far
	 * @param numOfRays the number of rays wanted in the beam when using a beam of rays for picture improvement
	 * @param adaptiveSupersampling whether the adaptive-supersampling picture improvement is required
	 * @return The color of the intersection-geoPoint determined by this recursion-stage's global-effect
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 kR, double kkx,int numOfRays, boolean adaptiveSupersampling) {
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx, numOfRays, adaptiveSupersampling)).scale(kR);
	}
	
	/**
	 * Constructs a reflected ray to a former-ray which ends on the given point and it's direction is the given direction
	 * @param point the former-ray's end point - and the reflected-ray's starting point
	 * @param v the former-ray's direction
	 * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
	 * @return the constructed reflected-ray
	 */
	private Ray constructReflectedRay(Point point, Vector v, Vector n) {
		double vn = alignZero(v.dotProduct(n));
		// if v.dotProduct(n)==0 then the ray we construct is tangent to the geometry:
		if (isZero(vn))
			return null;
		Vector dir = v.subtract(n.scale(2*vn));
		return new Ray(point, dir,n);
	}
	
	/**
	 * Constructs a refracted-ray to a former-ray which ends on the given point and it's direction is the given direction
	 * @param point the former-ray's end point - and the refracted-ray's starting point
	 * @param v the former-ray's direction - and the refracted-ray's direction
	 * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
	 * @return the constructed refracted-ray
	 */
	private Ray constructRefractedRay(Point point, Vector v, Vector n) {
		return new Ray(point, v, n);
	}
	
	
	
	
	
}

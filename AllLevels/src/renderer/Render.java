package renderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import elements.Camera;
import elements.LightSource;
import elements.PointLight;
import geometries.Box;
import geometries.Boxable;
import geometries.FlatGeometry;
import geometries.Geometry;
import primitives.MyColor;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

public class Render {
	
	private Scene _scene;
	private ImageWriter _imagewriter;
	private final int RECURSION_LEVEL = 3;
	public boolean multipleRefractionRaysOn = true;
	public boolean multipleReflectionRaysOn = true;
	
	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting name to "default", width=height=nx=ny=500
	 */
	public Render() {
		super();
		this._scene = new Scene();
		this._imagewriter = new ImageWriter("Default",500,500,500,500);
	}
//Constructor gets Scene and ImageWriter
	public Render(ImageWriter imageWriter, Scene scene) {
		super();
		this._scene = scene;
		this._imagewriter = imageWriter;
	}
	
	@Override
	public String toString() {
		return "Render [_scene=" + _scene + ", _imagewriter=" + _imagewriter + "]";
	}


	public ImageWriter getImageWriter() {
		return this._imagewriter;
	}
	
	public void writeToImage() {
		this._imagewriter.writeToimage();
	}
	

	public Scene getScene() {
		return _scene;	
	}
	public void setImageWriter(ImageWriter imageWriter) {
		this._imagewriter = imageWriter;
		
	}
	public void setImageWriterName(String string) {
		this._imagewriter.setName(string);
	}
	
	// ***************** Operations ******************** //
	
	/*************************************************
	 * FUNCTION
	 * render image
	 * 
	 * PARAMETERS
	 * 
	 * RETURN VALUE
	 * 
	 * MEANING
	 * This functions renders the image by running in loop all over the height*width of
	 * the image, constructing the rays from the camera, finding the intersections with 
	 * the view plane, and calculating the color in each pixel
	 * 
	 * SEE ALSO
	 * 
	 **************************************************/
	public void renderImage() throws Exception
	{
		for (int i=0;i<_imagewriter.getHeight();i++){
			for (int j=0;j<_imagewriter.getWidth();j++)
			{
				Ray ray = _scene.getCamera().constructRayThroughPixel(_imagewriter.getNx(),
																	  _imagewriter.getNy(),i,j,
																	  _scene.getScreenDistance(), 
																	  _imagewriter.getWidth(),
																	  _imagewriter.getHeight());
				Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
				if (intersectionPoints.isEmpty())
					_imagewriter.writePixel(i, j, _scene.getBackground());
				else
				{
					Entry<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints, ray);
					if (closestPoint == null)
						_imagewriter.writePixel(i, j, _scene.getBackground());
					else
						_imagewriter.writePixel(i, j, calcColor(closestPoint.getKey(),closestPoint.getValue(),ray));
				}
			}
		}	
	}
	
	/*************************************************
	 * FUNCTION
	 * get scene ray intersections
	 * 
	 * PARAMETERS
	 * Ray 
	 * RETURN VALUE
	 * Map<Geometry,List<Point3D>>
	 * MEANING
	 * This functions iterating all the geometries in the scene, looking for intersections
	 * with given ray (by calling each geometry's 'findIntersections' func. , and returning a map of all the geometries intersecting the
	 * ray, with the points of intersections.
	 * @throws
	 * Exception
	 * SEE ALSO
	 * Geometry.findIntersection
	 **************************************************/
	private Map<Geometry, List<Point3D>> getSceneRayIntersections(Ray ray) throws Exception
	{
		Iterator<Geometry> geometries = _scene.getGeometriesIterator();
		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		
		while (geometries.hasNext())
		{
			Geometry geometry = geometries.next();
			if(_scene.boxing && geometry instanceof Box) 
			{
				if(!((Box)geometry).findIntersections(ray).isEmpty()) {
					Map<Geometry, List<Point3D>> boxIntersectionPoints = ((Box)geometry).getInBoxRayIntersections(ray);
					intersectionPoints.putAll(boxIntersectionPoints);
				}
			}
			else {
				ArrayList<Point3D> geometryIntersectionPoints = (ArrayList<Point3D>) geometry.findIntersections(ray);
				if(!(geometryIntersectionPoints.isEmpty()))
					intersectionPoints.put(geometry, geometryIntersectionPoints);
			}
		}
		return intersectionPoints;
	}

	/*************************************************
	 * FUNCTION
	 * get Closest Point
	 * 
	 * PARAMETERS
	 * Map<Geometry,List<Point3D>>
	 * 
	 * RETURN VALUE
	 * An Entry<Geometry,Point3D> representing the closest point
	 * 
	 * MEANING
	 * while looking for a point to paint (in the render image function), we want to 
	 * find the nearest point to the required, so this function iterates all the map
	 * given as parameter, and finding the nearest point of intersection to P0 of the
	 * camera. 
	 * 
	 * SEE ALSO
	 * 
	 **************************************************/
	private Entry<Geometry, Point3D> getClosestPoint(Map<Geometry,List<Point3D>> intersectionPoints, Ray ray) throws Exception
	{
		double distance = Double.MAX_VALUE;
		Point3D P0 = ray.getPOO(); 
		Map<Geometry, Point3D> minDistancePoint = new HashMap<Geometry, Point3D>();
		for (Entry<Geometry, List<Point3D>> entry:intersectionPoints.entrySet())
			for (Point3D point: entry.getValue())
				if(P0.distance(point) < distance)
				{
					minDistancePoint.clear();
					minDistancePoint.put(entry.getKey(), new Point3D(point));
					distance = P0.distance(point);
				}
		
		Entry<Geometry, Point3D> closestEntry = minDistancePoint.entrySet().iterator().next();
		
		// checking if the ray intersect a box. if it does- recursively calling this function
		// with the box intersections to find inner intersections
		if (closestEntry.getKey() instanceof Box)
			{
				Map<Geometry,List<Point3D>> boxIntersections = ((Box)closestEntry.getKey()).getInBoxRayIntersections(ray);
				if (!boxIntersections.isEmpty())
				{
					return getClosestPoint(boxIntersections, ray);
				}
				else
					return null;
			}
		return closestEntry;
	}

	/*************************************************
	 * FUNCTION
	 * findClosestIntersection
	 * 
	 * PARAMETERS
	 * Ray
	 * 
	 * RETURN VALUE
	 * Entry<Geometry,Point3D> representing the closest intersection point
	 * 
	 * MEANING
	 * looking for the closest intersection between the ray and the scene, if there aren't
	 * any- returning null, if exist- finding the closest point by calling getClosestPoint
	 * function 
	 * 
	 * SEE ALSO
	 * Render.getClosestPoint
	 **************************************************/
    private Entry<Geometry, Point3D> findClosestIntersection(Ray ray) throws Exception {

        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);

        if (intersectionPoints.size() == 0)
            return null;
        return getClosestPoint(intersectionPoints,ray);

    }

    /**
     * Inner function, only for calling the recursive calcColor function and start the
     * recursion level with '0', and the "attenuation" factor with 1.0
     * @param geometry
     * @param point
     * @param ray
     * @return
     * @throws Exception
     */
    private Color calcColor(Geometry geometry, Point3D point, Ray ray) throws Exception {
        return calcColor(geometry, point, ray, 0, 1.0);
    }

	/*************************************************
	 * FUNCTION
	 * calcColor
	 * 
	 * PARAMETERS
	 * Geometry, Point3D, Ray, int
	 * 
	 * RETURN VALUE
	 * Color
	 * 
	 * MEANING
	 * The function calculates the exact color of a specified pixel, according to phong
	 * model- ambient+diffuse+specular 
	 * 
	 * SEE ALSO
	 * Render.getClosestPoint
	 **************************************************/
    private Color calcColor(Geometry geometry, Point3D point, Ray inRay, int level, double k) throws Exception {

        if (level == RECURSION_LEVEL) {
            return Color.BLACK;
        }

        Color ambientLight = _scene.getAmbientLight().getIntensity(null);
        Color emissionLight = geometry.getEmmission();

        Color inherentColors = MyColor.addColors(ambientLight, emissionLight);
       
        Iterator<LightSource> lights = _scene.getLightsIterator();

        Color lightReflected = new Color(0, 0, 0);
        Vector normal = geometry.getNormal(point).normalize();
        Vector v = new Vector(point,_scene.getCamera().getP0());
        
        while (lights.hasNext()) {
            LightSource light = lights.next();
            Vector L = light.getL(point).normalize();

            if(normal.dotProduct(L)*normal.dotProduct(v) > 0)
            {
            	double ktr = transparency(light, normal, point,geometry);
            	
            	if (!Util.isZero(ktr*k)) {

            		Color lightIntensity = MyColor.scaleColor(light.getIntensity(point),ktr);

            		// calculating the attenuation factor of the diffuse and the specular (double)

            		double diff = calcDiffusiveComp(geometry.getMaterial().getKd(),
            				normal, L);

            		double spec = calcSpecularComp(geometry.getMaterial().getKs(), v,
            				normal,L,geometry.getShininess());
            		// summing them
            		double difSpec = diff + spec;
            		lightReflected = MyColor.scaleColor(lightIntensity, difSpec);
            	}
            }
        }
        
        Color I0 = MyColor.addColors(inherentColors, lightReflected);

        // Recursive calls
        // Recursive call for a reflected ray
        double kr = geometry.getMaterial().getKr();
        Color reflected = new Color(0, 0, 0);
        if(multipleReflectionRaysOn)
        {
        	List<Ray> reflectedRays = constructReflectedRayList(normal, point, inRay);
        	for(Ray ray: reflectedRays)
        	{
        		Entry<Geometry,Point3D> reflectedEntry = findClosestIntersection(ray);
        		if(reflectedEntry!=null)
        		{
        			Color temp = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), ray, level + 1, k*kr);
        			temp = MyColor.scaleColor(temp,kr);
        			double numOfRaysFraction = (1.0/reflectedRays.size());
        			temp = MyColor.scaleColor(temp, numOfRaysFraction );
        			reflected=MyColor.addColors(reflected,temp);
        		}
        	}
        }

        else {
        	Ray reflectedRay = constructReflectedRay(normal, point, inRay);
        	Entry<Geometry, Point3D> reflectedEntry = findClosestIntersection(reflectedRay);

        	if (reflectedEntry != null) {
        		reflected = MyColor.scaleColor(calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay, level + 1, k*kr),kr);
        	}

        }
        // Recursive call for refracted ray/rays
        Color refractedColor = new Color(0, 0, 0);
        double kt = geometry.getMaterial().getKt();
        
        if(multipleRefractionRaysOn)
        {
        	// Getting a List of Rays created by constructRefractedRayList function
        	List<Ray> refractedRays = constructRefractedRayList(geometry, point, inRay);
        	// iterating the list, adding the color of each ray's intersections points
        	for(Ray refractedRay: refractedRays) 
        	{
        		Entry<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);

        		if (refractedEntry != null) {
        			Color temp = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1, k*kt);
        			temp = MyColor.scaleColor(temp,kt);
        			double numOfRaysFraction = (1.0/refractedRays.size());
        			temp = MyColor.scaleColor(temp, numOfRaysFraction );
        			refractedColor=MyColor.addColors(refractedColor,temp);
        		}
        	}
        }
        else
        {
        	Ray refractedRay = constructRefractedRay(geometry, point, inRay);
        	Entry<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);
    		if (refractedEntry != null) {
//    			double kt = geometry.getMaterial().getKt();
    			refractedColor= MyColor.scaleColor(calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1, k*kt),kt);
    		}
        }
        
        // end of recursive calls

        Color envColors = MyColor.addColors(reflected, refractedColor);
        Color finalColor = MyColor.addColors(envColors, I0);

        return finalColor;
    }

	/*************************************************
	 * FUNCTION
	 * constructRefractedRay
	 * 
	 * PARAMETERS
	 * Geometry, Point3D, Ray
	 * 
	 * RETURN VALUE
	 * Ray
	 * 
	 * MEANING
	 * The function calculates the ray created from the refracted light ray sended from 
	 * light source. (normal to the geometry * Ray direction), than adding epsilon vector.
	 * 
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/
    private Ray constructRefractedRay(Geometry geometry, Point3D point, Ray inRay) throws Exception {

    	Vector eps = geometry.getNormal(point);
    	double angle = eps.dotProduct(inRay.getDirection());
    	eps = angle<0? eps.scale(-2) : eps.scale(2);
        Point3D p = point.add(eps);

        //if (geometry instanceof FlatGeometry) 
            return new Ray(p, inRay.getDirection());
        //}
    }
    
	/*************************************************
	 * FUNCTION
	 * constructRefractedRayList
	 * 
	 * PARAMETERS
	 * Geometry, Point3D, Ray
	 * 
	 * RETURN VALUE
	 * List<Ray>
	 * 
	 * MEANING
	 * The function calculates the refracted ray from the light ray sended from 
	 * light source. (normal to the geometry * Ray direction), than adding epsilon vector.
	 * because we want multiple rays to solve glossy surfaces - we add 4 more rays, adding
	 * to each random offset ( 
	 * 
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/ 
    private List<Ray> constructRefractedRayList(Geometry geometry, Point3D point, Ray inRay) throws Exception{
    	List<Ray> rays = new ArrayList<Ray>();
    	Vector eps = geometry.getNormal(point);
    	Vector rayDirection = inRay.getDirection();
    	double angle = eps.dotProduct(inRay.getDirection());
    	eps = angle<0? eps.scale(-2) : eps.scale(2);
        Point3D p = point.add(eps);
        Ray mainRay = new Ray(p, inRay.getDirection());
        rays.add(mainRay);
        rays.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        rays.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        rays.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        rays.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        return rays;
    }
    
    private Double getRandomOffset() {
    	Double offset = 0.025;
    	Random rand = new Random();
    	return ((rand.nextDouble()*(offset*2))-offset);
    }
    
	/*************************************************
	 * FUNCTION
	 * constructReflectedRay
	 * 
	 * PARAMETERS
	 * Geometry, Point3D, Ray
	 * 
	 * RETURN VALUE
	 * Ray
	 * 
	 * MEANING
	 * The function calculates the ray created from the reflected light ray sended from 
	 * light source. (normal to the geometry * Ray direction), than adding epsilon vector.
	 * 
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) throws Exception {

        Vector v = inRay.getDirection().normalize();

        double angle = v.dotProduct(normal);
        Vector eps = normal.scale(-2 * angle);
        v = v.add(eps);

        Vector R = new Vector(v);
        R.normalize();
        Point3D p = point.add(R.scale(2));

        return new Ray(p, R);
    }

    
	/*************************************************
	 * FUNCTION
	 * constructReflectedRay
	 * 
	 * PARAMETERS
	 * Geometry, Point3D, Ray
	 * 
	 * RETURN VALUE
	 * Ray
	 * 
	 * MEANING
	 * The function calculates the ray created from the reflected light ray sended from 
	 * light source. (normal to the geometry * Ray direction), than adding epsilon vector.
	 * 
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/
    private List<Ray> constructReflectedRayList(Vector normal, Point3D point, Ray inRay) throws Exception {

        Ray main = constructReflectedRay(normal, point, inRay);
        List<Ray> list = new ArrayList<Ray>();
        list.add(main);
        
        Point3D p = main.getPOO();
        Vector rayDirection = main.getDirection();
        list.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        list.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        list.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        list.add(new Ray (p, rayDirection.add(new Point3D (getRandomOffset(),getRandomOffset(),0))));
        
        return list;
    }
    
	/*************************************************
	 * FUNCTION
	 * transparency
	 * 
	 * PARAMETERS
	 * Vector, Vector, point3D, geometry
	 * 
	 * RETURN VALUE
	 * Double
	 * 
	 * MEANING
	 * The function calculates the transparency of the specified light in given point.
	 * it reverses the direction of the ray (to get the dire. from the geometry
	 * back), adding an epsilon vector to the given point (scalen by +-2 according to 
	 * the sign of the angle between the light and the normal), than finding a
	 * map of all the geometries intersecting the ray (and in which points), 
	 * if the geometry is flat- removing its points from the map,
	 * than scaling all the points' Kt factor. 
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/  
    private double transparency(LightSource L, Vector n,Point3D point, Geometry geometry) throws Exception {
    	
        Vector lightDirection = L.getL(point).scale(-1);
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? 2 : -2);
        Point3D p = point.add(epsVector);

        Ray lightRay = new Ray(p, lightDirection);
        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(lightRay);

        // Flat geometry cannot self intersect
        if (geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }
        
//        Map<Geometry, List<Point3D>> temp = new HashMap<Geometry, List<Point3D>>();
        if (L instanceof PointLight) // trying to solve the problem of rays returnning back
        							// to the camera and "disappear"
        {
        	PointLight light = (PointLight)L;
        	double distance = light.getPosition().distance(point);
        	List<Geometry> PLList = new ArrayList<Geometry>();
        	for(Entry<Geometry, List<Point3D>> entryPerGeo : intersectionPoints.entrySet())
        		if (entryPerGeo.getValue().get(0).distance(point) <= distance)
        		{
        			//temp.put(entryPerGeo.getKey(), entryPerGeo.getValue());
        			PLList.add(entryPerGeo.getKey());
        		}
        	double ktr = 1;
        	for (Geometry geo : PLList)
        		ktr *= geo.getMaterial().getKt();
        	return ktr;
        }
        else
        {
        	double ktr = 1;
        	for (Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
        		ktr *= entry.getKey().getMaterial().getKt();
        	return ktr;
        }
 
//        double ktr = 1;
//      for (Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
//         ktr *= entry.getKey().getMaterial().getKt();
        //return 1;
    }
    
	/*************************************************
	 * FUNCTION
	 * calcSpecularComp
	 * 
	 * PARAMETERS
	 * double, Vector, Vector, Vector , double
	 * 
	 * RETURN VALUE
	 * Double
	 * 
	 * MEANING
	 * The function calculates the specular factor of a point, finding the reversed vector,
	 * finding R (L-2*(L*n)*n ), than if the angle is possitive, returning ks*(v*R)^S
	 * else - return 0
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/  
    private double calcSpecularComp(double ks, Vector v, Vector normal,Vector l, 
			double shininess) throws Exception {
        v = v.scale(-1).normalize();

        // R = L - 2(l*n) * n
        l = l.add(normal.scale(-2 * l.dotProduct(normal)));
        Vector R = new Vector(l).normalize();

        double k = 0;

        if (v.dotProduct(R) > 0) // prevents glowing at edges
            k = ks * Math.pow(v.dotProduct(R), shininess);
        
        return k;
	}
	
	/*************************************************
	 * FUNCTION
	 * calcSpecularComp
	 * 
	 * PARAMETERS
	 * double, Vector, Vector
	 * 
	 * RETURN VALUE
	 * Double
	 * 
	 * MEANING
	 * The function calculates the diffusive factor in a point.
	 * kd*(|normal*-L|)
	 * SEE ALSO
	 * Render.calcColor
	 **************************************************/ 
	private double calcDiffusiveComp(double kd, Vector normal, Vector l) throws Exception {
        return kd * Math.abs(normal.dotProduct(l.scale(-1)));
	}

	
	public void printGrid(int interval){
		for (int i=0;i<_imagewriter.getHeight();i++)
            for (int j=0;j<_imagewriter.getWidth();j++)
            {
                if(i%interval==0 || j%interval==0 )
                	_imagewriter.writePixel(j,i,Color.WHITE); 
            }   
	}

}
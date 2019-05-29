package renderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import elements.LightSource;
import geometries.FlatGeometry;
import geometries.Geometry;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

public class Render {
	
	private Scene _scene;
	private ImageWriter _imagewriter;
	private final int RECURSION_LEVEL = 3;
	
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

	// ***************** Operations ******************** //

	public ImageWriter getImageWriter() {
		return this._imagewriter;
	}
	
	public void writeToImage() {
		this._imagewriter.writeToimage();
	}
	
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
	 * the image, constructing the rays from the camera, finding the intersections woth 
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
					Entry<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
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
		
		while (geometries.hasNext()){
			Geometry geometry = geometries.next();
			ArrayList<Point3D> geometryIntersectionPoints = (ArrayList<Point3D>) geometry.findIntersections(ray);
			if(!(geometryIntersectionPoints.isEmpty()))
				intersectionPoints.put(geometry, geometryIntersectionPoints);
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
	private Entry<Geometry, Point3D> getClosestPoint(Map<Geometry,List<Point3D>> intersectionPoints) throws Exception
	{
		double distance = Double.MAX_VALUE;
		Point3D P0 = _scene.getCamera().getP0();
		Map<Geometry, Point3D> minDistancePoint = new HashMap<Geometry, Point3D>();
		for (Entry<Geometry, List<Point3D>> entry:intersectionPoints.entrySet())
			for (Point3D point: entry.getValue())
				if(P0.distance(point) < distance)
				{
					minDistancePoint.clear();
					minDistancePoint.put(entry.getKey(), new Point3D(point));
					distance = P0.distance(point);
				}
		return minDistancePoint.entrySet().iterator().next();
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
        return getClosestPoint(intersectionPoints);

    }

    /**

     * Inner function, only for calling the recursive calcColor function and start the
     * recursion level with '0'
     * @param geometry
     * @param point
     * @param ray
     * @return
     * @throws Exception
     */
    private Color calcColor(Geometry geometry, Point3D point, Ray ray) throws Exception {
        return calcColor(geometry, point, ray, 0);
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

    private Color calcColor(Geometry geometry, Point3D point, Ray inRay, int level) throws Exception {

        if (level == RECURSION_LEVEL) {
            return Color.BLACK;
        }

        Color ambientLight = _scene.getAmbientLight().getIntensity(null);
        Color emissionLight = geometry.getEmmission();

        Color inherentColors = addColors(ambientLight, emissionLight);
//        
        Iterator<LightSource> lights = _scene.getLightsIterator();

        Color lightReflected = new Color(0, 0, 0);
        Vector normal = geometry.getNormal(point).normalize();

        while (lights.hasNext()) {
            LightSource light = lights.next();

            if (!occluded(light, point, geometry)) {

                Color lightIntensity = light.getIntensity(point);
                Vector L = light.getL(point).normalize();
                Color lightDiffuse = calcDiffusiveComp(geometry.getMaterial().getKd(),
                									   normal,
                									   L,
                									   lightIntensity);

                Color lightSpecular = calcSpecularComp(geometry.getMaterial().getKs(),
                							new Vector(_scene.getCamera().getP0(),point),
                							normal,
                							L,
                							geometry.getShininess(),
                							lightIntensity);

                lightReflected = addColors(lightDiffuse, lightSpecular);
            }
        }
        
        Color I0 = addColors(inherentColors, lightReflected);

        // Recursive calls
        // Recursive call for a reflected ray
        Ray reflectedRay = constructReflectedRay(normal, point, inRay);
        Entry<Geometry, Point3D> reflectedEntry = findClosestIntersection(reflectedRay);
        Color reflected = new Color(0, 0, 0);

        if (reflectedEntry != null) {
            reflected = calcColor(reflectedEntry.getKey(), reflectedEntry.getValue(), reflectedRay, level + 1);
            double kr = geometry.getMaterial().getKr();
            reflected = new Color((int) (reflected.getRed() * kr), (int) (reflected.getGreen() * kr), (int) (reflected.getBlue() * kr));
        }

        // Recursive call for a refracted ray
        Ray refractedRay = constructRefractedRay(geometry, point, inRay);
        Entry<Geometry, Point3D> refractedEntry = findClosestIntersection(refractedRay);
        Color refracted = new Color(0, 0, 0);

        if (refractedEntry != null) {
            refracted = calcColor(refractedEntry.getKey(), refractedEntry.getValue(), refractedRay, level + 1);
            double kt = geometry.getMaterial().getKt();
            refracted = new Color((int) (refracted.getRed() * kt), (int) (refracted.getGreen() * kt), (int) (refracted.getBlue() * kt));
        }

        // end of recursive calls

        Color envColors = addColors(reflected, refracted);
        Color finalColor = addColors(envColors, I0);

        return finalColor;
    }

    private Ray constructRefractedRay(Geometry geometry, Point3D point, Ray inRay) throws Exception {

    	Vector eps = geometry.getNormal(point);
    	double angle = eps.dotProduct(inRay.getDirection());
    	eps = angle<0? eps.scale(-2) : eps.scale(2);
        Point3D p = point.add(eps);

        if (geometry instanceof FlatGeometry) {
            return new Ray(p, inRay.getDirection());
        } 
        else {
            // Here, Snell's law can be implemented.
            // The refraction index of both materials had to be derived
            return new Ray(p, inRay.getDirection());
        }
    }

    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) throws Exception {

        Vector l = inRay.getDirection();
        l.normalize();

        double angle = l.dotProduct(normal);
        Vector eps = normal.scale(-2 * angle);
        l=l.add(eps);

        Vector R = new Vector(l);
        R.normalize();
        //double angle = normal.dotProduct(l);
        eps = angle>0 ? normal.scale(2) : normal.scale(-2); 
        Point3D p = point.add(eps);

        Ray reflectedRay = new Ray(p, R);

        return reflectedRay;
    }

    private boolean occluded(LightSource light, Point3D point, Geometry geometry) throws Exception {

        Vector lightDirection = light.getL(point).scale(-1).normalize();

       // Point3D geometryPoint = new Point3D(point);
        Vector epsVector = geometry.getNormal(point).scale(2);
        //epsVector.scale(2);
        Point3D p = point.add(epsVector);

        Ray lightRay = new Ray(p, lightDirection);
        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(lightRay);

        // Flat geometry cannot self intersect
        if (geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }

        for (Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
            if (entry.getKey().getMaterial().getKt() == 0)
                return true;

        return false;
    }
    
    private Color calcSpecularComp(double ks, Vector v, Vector normal,Vector l, 
			double shininess, Color lightIntensity) throws Exception {
        v= v.scale(-1).normalize();
        normal.normalize();
        l.normalize();

        l=l.add(normal.scale(-2 * l.dotProduct(normal)));
        Vector R = new Vector(l).normalize();

        double k = 0;

        if (v.dotProduct(R) > 0) // prevents glowing at edges
            k = ks * Math.pow(v.dotProduct(R), shininess);

        return new Color((int) (lightIntensity.getRed() * k),
        			 	 (int) (lightIntensity.getGreen() * k),
        			 	 (int) (lightIntensity.getBlue() * k));
	}
	
	private Color calcDiffusiveComp(double kd, Vector normal, Vector l, Color lightIntensity) throws Exception {
		
        normal.normalize();
        l.normalize();

        double kColor = Math.abs(kd*normal.dotProduct(l));
		
		return new Color ( (int)(lightIntensity.getRed()*kColor),
						   (int)(lightIntensity.getGreen()*kColor),
						   (int)(lightIntensity.getBlue()*kColor));
	}

	
    private Color addColors( Color a, Color b) {
		int red = Math.max(0, Math.min(255, a.getRed() + b.getRed()));
		int green = Math.max(0, Math.min(255, a.getGreen() + b.getGreen()));
		int blue = Math.max(0, Math.min(255, a.getBlue() + b.getBlue()));
    
		return new Color(red,green,blue);
    }
	
	/**
	 * printGrid
	 * @param interval
	 */
	public void printGrid(int interval){
		for (int i=0;i<_imagewriter.getHeight();i++)
            for (int j=0;j<_imagewriter.getWidth();j++)
            {
                if(i%interval==0 || j%interval==0 )
                	_imagewriter.writePixel(j,i,Color.WHITE); 
            }   
	}
}
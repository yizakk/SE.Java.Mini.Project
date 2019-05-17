package renderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import elements.LightSource;
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
//Constructor get Scene and ImageWriter
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
					_imagewriter.writePixel(i, j, calcColor(closestPoint.getKey(),closestPoint.getValue()));
				}
			}
		}	
	}
	
	/**
	 * returning map of geometries and for each- it's intersections 
	 * @param ray
	 * @return
	 * @throws Exception 
	 */
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
	/**
	 * receives a list of points and finds the nearest
	 * @param intersectionPoints
	 * @return
	 * @throws Exception 
	 */
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
	
	private Color calcColor(Geometry geometry, Point3D p)
	{
		Color ambientLight = _scene.getAmbientLight().getIntensity();
		Color emissionLight = geometry.getEmmission();
	
		Color color = addColors(ambientLight,emissionLight); //base = ambient+emission
		
		
	//	color = addColors(color, calcSpecularComp(geometry.getMaterial().getKs(),
	//											 new Vector(_scene.getCamera().getP0(),p),geometry.getNormal(p)))
		return new Color(color.getRed(),color.getGreen(),color.getBlue());
	}
	
	private Color calcSpecularComp(double ks, Vector v, Vector normal,Vector l, 
								   double shininess, Color lightIntensity) {
		return null;
	}
			private Color calcDiffusiveComp(double kd, Vector normal, Vector l,
											Color lightIntensity) {
				return null;
			}
	
	
    private Color addColors( Color a, Color b)
    {
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

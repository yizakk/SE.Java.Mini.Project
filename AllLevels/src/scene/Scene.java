package scene;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Box;
import geometries.Boxable;
import geometries.Geometry;
import primitives.Coordinate;
import primitives.MyColor;
import primitives.Point3D;
import primitives.Vector;
public class Scene {

	String _sceneName = "scene";
	Color _background;
	AmbientLight _ambientLight;
	List<Geometry> _geometries = new ArrayList<Geometry>();
	List<LightSource> _lights = new ArrayList<LightSource>();
	Camera _camera;
	double _screenDistance;
	public boolean boxing = false;

	/**
	 * default constructor setting background =black.  distance=100
	 */
	public Scene() {
		this._background = Color.BLACK;
		this._ambientLight = new AmbientLight();
		this._camera = new Camera();
		this._screenDistance = 100;
	}
	// ***************** Constructors ********************** //
	public Scene(String sceneName, Color background, AmbientLight ambientLight, ArrayList<Geometry> geometries, Camera camera, double screenDistance) throws Exception 
	{
		this._sceneName = sceneName;
		this._background = background;
		this._ambientLight = new AmbientLight(ambientLight);
		this._geometries = geometries;
		this._camera = new Camera(camera);
		this._screenDistance = screenDistance;
	}
	/**
	 * copy constructor
	 */
	public Scene(Scene sce) {
		this._sceneName = sce._sceneName;
		this._background = sce._background;
		this._ambientLight = sce._ambientLight;
		this._geometries = sce._geometries;
		this._camera = sce._camera;
		this._screenDistance =sce._screenDistance ;
	}

	//****************************** Getters / Setters *******************//

	public Color getBackground() {
		return MyColor.copyColor(this._background);
	}
	public AmbientLight getAmbientLight() {
		return new AmbientLight(this._ambientLight);
	}
	public Camera getCamera() {	return _camera; }
	public String getSceneName() {	return _sceneName;	}
	public double getScreenDistance() {	return _screenDistance; }
	
	public void setBackground(Color background){_background = background;}
	public void setCamera(Camera camera) {_camera= camera;	}
	public void setSceneName(String sceneName) { _sceneName=sceneName; }
	public void setScreenDistance(double screenDistance) {_screenDistance=screenDistance;}

	public void setAmbientLight(AmbientLight ambientLight)
	{_ambientLight = new AmbientLight(ambientLight);}
	
    public void setAmbientLight(Color color, double k) {
        this._ambientLight = new AmbientLight(color.getRed(), color.getGreen(), color.getBlue(), k);
    }
    
    public void setAmbientLight(Color color) {
        this._ambientLight = new AmbientLight(color.getRed(), color.getGreen(), color.getBlue());
    }

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Scene))
				return false;
		Scene other = (Scene) obj;

		return (Double.doubleToLongBits(_screenDistance) != Double.doubleToLongBits(other._screenDistance)
				&& this._sceneName==other._sceneName && _background.equals(other._background)
				&& this._ambientLight.equals(other._ambientLight) 
				&& this._camera.equals(other._camera) 
				&& this._geometries.equals(other._geometries)
				&& this._lights.equals(other._lights));
	}

	/**
	 * toStrint - print
	 */
	@Override
	public String toString() {
		return "Scene [_sceneName=" + _sceneName + ", _background=" + _background + ", _ambientLight=" + _ambientLight
				+ ", _geometries=" + _geometries + ", _camera=" + _camera + ", _screenDistance=" + _screenDistance + "]";
	}
	/**
	 * Add Geometry Function
	 * @param geometry
	 */
	public void addGeometry(Geometry geometry)
	{
		// to revert all boxing - remove from here...
		if(boxing) {
			if (geometry instanceof Boxable) // if boxable - adding to scene the "boxed" geo
				this._geometries.add(((Boxable)geometry).insertIntoBox());
			else // otherwise - (plane) inserting the geo as is
				this._geometries.add(geometry);
		}
		else
			// ...to here 
		this._geometries.add(geometry); // if boxing option is disabled - inserting any geo as is
	}
	

	/*************************************************
	 * FUNCTION
	 * boxgeometries1
	 * 
	 * PARAMETERS
	 * 
	 * RETURN VALUE
	 * 
	 * MEANING
	 * in ray acceleration we want to insert any geometry into a box, than box close boxes into
	 * bigger box, and repeat until there isn't close box.
	 *  
	 * 
	 * SEE ALSO
	 * @return boolean
	 * 
	 **************************************************/
	
	public boolean boxCloseBoxes() {
		
		if(boxing)
		{
			boolean changedAnything = true;
			while (changedAnything ) 
			{
				changedAnything = false;
				for (Geometry _box : this._geometries) 
				{
					if (_box instanceof Box) 
					{
						List<Box> boxesToPutTogether = new ArrayList<Box>();
						Box box =(Box)_box;
						Vector boxMidVec = new Vector(new Point3D(box.getDepth()/2, box.getHeight()/2, box.getWidth()/2));
						Point3D center = box.getLocation().add(boxMidVec);
						for (Geometry _anotherBox : _geometries) 
						{
							if (_anotherBox instanceof Box && _anotherBox != _box) 
							{            
								Box anotherBox =(Box)_anotherBox;
								Vector anotherBoxMidVec = new Vector(new Point3D(anotherBox.getDepth()/2,anotherBox.getHeight()/2,anotherBox.getWidth()/2));
								Point3D anotherCenter = anotherBox.getLocation().add(anotherBoxMidVec);
								if (new Vector (anotherCenter.subtract(center)).length() < 5*Math.min(boxMidVec.length(), anotherBoxMidVec.length()))
									boxesToPutTogether.add(anotherBox);
							}
						}
						// box together all boxes and remove them all from the geometry to put the bigger box instead
						if (!boxesToPutTogether.isEmpty()) 
						{
							changedAnything = true;
							boxesToPutTogether.add(box);
							double  minX=Double.MAX_VALUE, 
									minY=Double.MAX_VALUE, 
									minZ=Double.MAX_VALUE, 
									
									maxX=Double.MIN_VALUE, 
									maxY=Double.MIN_VALUE, 
									maxZ=Double.MIN_VALUE;
							
							//Iterator<Geometry> it = boxesToPutTogether;
							for (Box b : boxesToPutTogether) 
							{
								double  bX = b.getLocation().getX().getCoordinate(),
										bY = b.getLocation().getY().getCoordinate(),
										bZ = b.getLocation().getZ().getCoordinate();
								minX = Math.min(minX, bX);
								maxX = Math.max(maxX, bX +b.getDepth());
								minY = Math.min(minY, bY);
								maxY = Math.max(maxY, bY +b.getHeight());
								minZ = Math.min(minZ, bZ);
								maxZ = Math.max(maxZ, bZ +b.getWidth());
							}
							Box bigBox = new Box(new Point3D (minX, minY, minZ),
									maxX - minX, maxY - minY, maxZ - minZ);
							for (Box b : boxesToPutTogether) 
							{
								bigBox.addGeometry(b);
								_geometries.remove(b);
							}
							_geometries.add(bigBox);
							break;
						}          
					}
				}
			}
			
			return true;
		} // end of if(boxing)
	return false;
	}
	
	
	
	/**
	 * getGeometriesIterator
	 * @return
	 */
	public Iterator<Geometry> getGeometriesIterator()
	{
		return _geometries.iterator();
	}
	
	public void addLight(LightSource light) {
		_lights.add(light);
	}
	public Iterator<LightSource> getLightsIterator() {
		return this._lights.iterator();
	}
	public void clearAllGeometry() {
		_geometries.clear();
		
	}
}
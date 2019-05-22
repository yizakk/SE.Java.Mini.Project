package scene;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometry;
public class Scene {

	String _sceneName = "scene";
	Color _background;
	AmbientLight _ambientLight;
	List<Geometry> _geometries = new ArrayList<Geometry>();
	List<LightSource> _lights = new ArrayList<LightSource>();
	Camera _camera;
	double _screenDistance;

	/**
	 * default constructor setting background =black.  distance=100
	 */
	public Scene() {
		this._background = Color.BLACK;
		this._ambientLight = new AmbientLight();
		//this._geometries = new ArrayList<Geometry>();
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
		return new Color(this._background.getRed(),
					   	 this._background.getGreen(),
						 this._background.getBlue());
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
		this._geometries.add(geometry);
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
}
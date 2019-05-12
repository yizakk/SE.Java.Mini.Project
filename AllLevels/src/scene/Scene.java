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
	ArrayList<Geometry> _geometries = new ArrayList<Geometry>();
	List<LightSource> _lights = new ArrayList<LightSource>();
	Camera _camera;
	double _screenDistance;

	/**
	 * default constructor
	 */
	public Scene() {
		this._sceneName = "";
		this._background = Color.BLACK;
		this._ambientLight = new AmbientLight();
		this._geometries = new ArrayList<Geometry>();
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
	/**
	 * get_sceneName
	 * @return
	 */
	public String get_sceneName() {
		return _sceneName;
	}
	/**
	 * set_sceneName
	 * @param sceneName
	 */
	public void set_sceneName(String sceneName) {
		this._sceneName = sceneName;
	}
	/**
	 * get_background
	 * @return
	 */
	public Color get_background() {
		return new Color(this._background.getRGB());
	}
	/**
	 * set_background
	 * @param background
	 */
	public void set_background(Color background) {
		this._background = background;
	}
	/**
	 * get_ambientLight
	 * @return
	 */
	public AmbientLight get_ambientLight() {
		return new AmbientLight(this._ambientLight);
	}
	/**
	 * set_ambientLight
	 * @param _ambientLight
	 */
	public void set_ambientLight(AmbientLight ambientLight) {
		this._ambientLight = ambientLight;
	}
	/**
	 * get_geometries
	 * @return
	 */
	public ArrayList<Geometry> get_geometries() {
		return new ArrayList<Geometry>(this._geometries);
	}
	/**
	 * set_geometries
	 * @param _geometries
	 */
	public void set_geometries(ArrayList<Geometry> geometries) {
		this._geometries = new ArrayList<Geometry>(geometries);
	}
	/**
	 * get_camera
	 * @return
	 * @throws Exception 
	 */
	public Camera get_camera() throws Exception {
		return new Camera(_camera);
	}
	/**
	 * set_camera
	 * @param _camera
	 * @throws Exception 
	 */
	public void set_camera(Camera camera) throws Exception {
		this._camera = new Camera(camera);
	}
	/**
	 * get_screenDistance
	 * @return
	 */
	public double get_screenDistance() {
		return _screenDistance;
	}
	/**
	 * set_screenDistance
	 * @param _screenDistance
	 */
	public void set_screenDistance(double _screenDistance) {
		this._screenDistance = _screenDistance;
	}
	
	// ***************** Administration ******************** //
	/**
	 * equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scene other = (Scene) obj;
		if (_ambientLight == null) {
			if (other._ambientLight != null)
				return false;
		} else if (!_ambientLight.equals(other._ambientLight))
			return false;
		if (_background == null) {
			if (other._background != null)
				return false;
		} else if (!_background.equals(other._background))
			return false;
		if (_camera == null) {
			if (other._camera != null)
				return false;
		} else if (!_camera.equals(other._camera))
			return false;
		if (_geometries == null) {
			if (other._geometries != null)
				return false;
		} else if (!_geometries.equals(other._geometries))
			return false;
		if (_sceneName == null) {
			if (other._sceneName != null)
				return false;
		} else if (!_sceneName.equals(other._sceneName))
			return false;
		if (Double.doubleToLongBits(_screenDistance) != Double.doubleToLongBits(other._screenDistance))
			return false;
		return true;
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
	public Iterator<Geometry>getGeometriesIterator()
	{
		return _geometries.iterator();
	}

}
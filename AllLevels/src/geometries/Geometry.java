package geometries;
import primitives.*;
import java.awt.Color;
import java.util.List;
public abstract class Geometry {
	
	private double _nShininess = 1;
	private Color _emmission = new Color(0, 0, 0);
	private Material _material;

 //****************Constructor****************/
/**
 * Default C-Tor, shininess=1, emmission=black, Material= {Kd=1, Ks=1, Kr=0, Kt=0 index=19}
 */
	public Geometry() 
	{ _material=new Material();	}
	
	/**
	 * _color=color, Material={Kd=1, Ks=1, Kr=0, Kt=0 index=19}, shininess=1
	 * @param color
	 */
	public Geometry(Color color) 
	{ _emmission = MyColor.copyColor(color); _material=new Material();}
	
	public Geometry(Color color, Material m) {
		_emmission = MyColor.copyColor(color); 
		_material=new Material(m);
		}
	
	// ***************** Getters/Setters ********************** //
	public double getShininess() { return _nShininess; }
	public void setShininess(double n) { _nShininess = n; }
	public Color getEmmission() {return new Color(_emmission.getRed(),_emmission.getGreen(),_emmission.getBlue());}
	public Material getMaterial() { return new Material(_material); }
	
	public void setEmmission(Color emmission) 
	{ this._emmission = MyColor.copyColor(emmission); }
	
	/**
	 * 
	 * @param i  Refraction index
	 * @param d Diffusion attenuation coefficient
	 * @param e Specular attenuation coefficient
	 * @param f Reflection coefficient (1 for mirror)
	 * @param g Refraction coefficient (1 for transparent)
	 */
	public void setMaterial(int i, double d, double e, double f, double g)
	{ _material = new Material(i,d,e,f,g);}
	public void setMaterial(Material m) { _material = new Material(m); }
    public void setKs(double ks) {  _material.setKs(ks); }
    public void setKd(double kd) {  _material.setKd(kd); }
    /**
     *  Reflection coefficient (1 for mirror)
     * @param Kr
     */
    public void setKr(double Kr) {  _material.setKr(Kr); } 
    /**
     * Refraction coefficient (1 for transparent)
     * @param Kt
     */
    public void setKt(double Kt) {  _material.setKt(Kt); }
    
	// ***************** Administration ******************** //
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_emmission == null) ? 0 : _emmission.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Geometry))
			return false;
		Geometry other = (Geometry) obj;
		return  _emmission.equals(other._emmission) 
				&& Double.doubleToLongBits(this._nShininess)==Double.doubleToLongBits(other._nShininess);
	}
	//***************abstract Vector getNormal Function************
	public abstract Vector getNormal(Point3D point) throws Exception;

	//***************abstract findIntersection Function**************//
	public abstract List<Point3D> findIntersections(Ray ray) throws Exception;
	
	@Override
	public String toString() {
		return "Geometry [_color=" + _emmission + "]";
	}
}
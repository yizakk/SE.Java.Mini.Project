package geometries;
import primitives.*;
import java.awt.Color;
import java.util.List;
public abstract class Geometry {
	
	/*
	//**************** for stage 4 *******************
	
	//public Material getMaterial();
	 * 	//public void setMaterial(Material _material);
	 
	 * ************************************************
	*/
	private double _nShininess = 1;
	private Color _emmission = new Color(0, 0, 0);
	private Material _material = new Material();

 //****************Constructor****************/

	public Geometry() {	this._emmission= new Color(0, 0, 0); }
	public Geometry(Color color) {	this._emmission = color; }
	
	// ***************** Getters/Setters ********************** //
	public double getShininess() { return _nShininess; }
	public void setShininess(double n) { _nShininess = n; }
	public Color getEmmission() {return _emmission;}
	public void setEmmission(Color emmission) {this._emmission = emmission;}
	public Material getMaterial() { return new Material(_material); }
	
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


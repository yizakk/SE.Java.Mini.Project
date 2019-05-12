package geometries;
import primitives.*;
import java.awt.Color;
import java.util.List;
public abstract class Geometry {
	Color _emmission; 

 //****************Constructor****************/

	public Geometry() {
		this._emmission= new Color(0, 0, 0);
	}
	public Geometry(Color color) {
		this._emmission = color;
	}
	// ***************** Getters/Setters ********************** //
	public Color getEmmission() {return _emmission;}
	public void setEmmission(Color emmission) {this._emmission = emmission;}
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		if (_emmission == null) {
			if (other._emmission != null)
				return false;
		} else if (!_emmission.equals(other._emmission))
			return false;
		return true;
	}
	//***************abstract Vector getNormal Function************

	public abstract Vector getNormal(Point3D point) throws Exception;

	//***************abstract findIntersection Function**************//
	public abstract List<Point3D> findIntersection(Ray ray) throws Exception;
	
	
	@Override
	public String toString() {
		return "Geometry [_color=" + _emmission + "]";
	}


}


package geometries;
import java.awt.Color;
public abstract class RadialGeometry extends Geometry
{
	protected double _radius;
	// ***************** Constructors ********************** //
	/**
	 * Default C-Tor, radius=0, Geometry={shininess=1, emmission=black,Material= {Kd=1, Ks=1, Kr=0, Kt=0 index=19} }
	 */
	public RadialGeometry()
	{
		super();
		_radius = 0;
	}
	public RadialGeometry(double radius)
	{
		super();
		_radius = radius;
	}

	public RadialGeometry(Color c, double _rad)
	{
		super(c);
		_radius = _rad;
	}

	//copy_constractor
	public RadialGeometry(RadialGeometry R)
	{
		super(R.getEmmission());
		_radius=R._radius;
	}

	/********************** Getters/Setters *********************/

	public double getRadius() {return _radius;}
	public void setRadius(double radius) {this._radius = radius;}

	// toString - print

	@Override
	public String toString() {
		return "RadialGeometry [_radius=" + _radius + "]";
	}
}



package elements;
//import primitives.Point3D;
import java.awt.Color;
import primitives.Point3D;
public class AmbientLight {
	Color _color;
	private final double _Ka = 1.0;
	// ***************** Constructors ********************** //
	public AmbientLight() {
		super();
		this._color= Color.WHITE;
	//	this._Ka = 1.0; 
	}	
	public AmbientLight(Color color, double _ka) {
		super();
		this._color = color;
	//	this._Ka = _ka;
	}
	public AmbientLight(AmbientLight other)
	{
		super();
		this._color=other._color;
		//this._Ka=other._Ka;
	}
	public AmbientLight(int r,int g, int b) 
	{
		super();
		this._color = new Color(r,g,b);
	//	this._Ka=1.0;
	}
	//*******************GETTES & SETTERS *******************//
	public Color get_color() {return _color;}
	public void set_color(Color _color) {this._color = _color;}
	public double get_ka() {return _Ka;}
	//public void set_ka(double _ka) {this._Ka = _ka;}
	public Color getIntensity(Point3D p){
        return new Color((int)((this.get_color().getRed())*_Ka),(int)((this.get_color().getGreen())*_Ka),(int)((this.get_color().getBlue())*_Ka));
	}
	public Color getIntensity()
	{
        return new Color((int)((this.get_color().getRed()/255)*_Ka),(int)((this.get_color().getGreen()/255)*_Ka),(int)((this.get_color().getBlue()/255)*_Ka));

	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_color == null) ? 0 : _color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(_Ka);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AmbientLight other = (AmbientLight) obj;
		if (_color == null) {
			if (other._color != null)
				return false;
		} else if (!_color.equals(other._color))
			return false;
		if (Double.doubleToLongBits(_Ka) != Double.doubleToLongBits(other._Ka))
			return false;
		return true;
	}

	
	
	
	
}

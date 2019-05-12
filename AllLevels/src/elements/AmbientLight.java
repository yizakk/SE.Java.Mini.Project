package elements;
import java.awt.Color;
import primitives.Point3D;
import primitives.Vector;
public class AmbientLight extends Light
{

	private double _Ka ; // the transparency of the color
	
	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting color=white, _Ka=1.0
	 */
	public AmbientLight() {
		super();
		this._Ka = 1.0; 
	}	
	public AmbientLight(Color color, double _ka) {
		super(color);
		this._Ka = _ka;
	}
	public AmbientLight(AmbientLight other)
	{
		super(other._color);
		this._Ka=other._Ka;
	}
	public AmbientLight(int r,int g, int b) 
	{
		super(new Color(r,g,b)); 
		this._Ka=1.0;
	}
	//*******************GETTES & SETTERS *******************//
	public Color get_color() {return _color;}
	public void set_color(Color _color) {this._color = _color;}
	public double get_ka() {return _Ka;}
	public void set_ka(double _ka) {this._Ka = _ka;}
	public Color getIntensity(Point3D p){
        return new Color(
        		(int)((this.get_color().getRed())*_Ka)
        		,(int)((this.get_color().getGreen())*_Ka)
        		,(int)((this.get_color().getBlue())*_Ka));
	}
	public Color getIntensity()
	{
        return new Color(
        		(int)((this.get_color().getRed()/255)*_Ka),
        		(int)((this.get_color().getGreen()/255)*_Ka),
        		(int)((this.get_color().getBlue()/255)*_Ka));
	}
	
	//*************************  Administration   *******************//
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
		if (!(obj instanceof AmbientLight))
			return false;
		AmbientLight other = (AmbientLight) obj;

		return Double.doubleToLongBits(_Ka) == Double.doubleToLongBits(other._Ka) 
				&& this._color.equals(other._color);
	}
	@Override
	public Vector getL(Point3D point) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

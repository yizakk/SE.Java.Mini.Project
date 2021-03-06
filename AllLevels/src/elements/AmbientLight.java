package elements;
import java.awt.Color;
import java.util.Map;

import primitives.MyColor;
import primitives.Point3D;
import primitives.Vector;

public class AmbientLight extends Light
{
	private double _Ka=1.0 ; // the transparency of the color
	
	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting color=black, _Ka=1.0
	 */
	public AmbientLight() {
		super(new MyColor(0,0,0));
		this._Ka = 1.0; 
	}	
	public AmbientLight(MyColor color, double _ka) {
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
		super(new MyColor(r,g,b)); 
		//this._Ka=1.0;
	}
	
	public AmbientLight(int red, int green, int blue, double k) {
		this(red,green,blue);
		_Ka=k;
	}
	
	//*******************GETTES & SETTERS *******************//
	public Color getColor() {return _color;}
	public void setColor(MyColor _color) {this._color = _color;}
	public double getKa() {return _Ka;}
	public void setKa(double _ka) {this._Ka = _ka;}
	
	@Override
	public MyColor getIntensity(Point3D p){
		return _color.scaleColor(_Ka);
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
	// Ambient Light hasn't L vector, but it implements LghtSource Interface
	// so return null
	@Override
	public Vector getL(Point3D point) {
		
		return null;
	}
	
}

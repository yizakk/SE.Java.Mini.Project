package elements;

import java.awt.Color;

import primitives.MyColor;

public abstract class Light implements LightSource {

	protected MyColor _color;
	
	// ***************** Constructors ********************** //
	/**
	 * Default constructor, setting color=white 
	 */
	public Light() {
		_color= new MyColor(255,255,255);
	}
	
	public Light (MyColor color) 
	{
		_color= color;
	}
	
	public Light (Color color) 
	{
		_color= new MyColor(color);
	}
}
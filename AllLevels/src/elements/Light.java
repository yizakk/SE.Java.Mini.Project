package elements;

import java.awt.Color;

public abstract class Light implements LightSource {

	protected Color _color;
	
	// ***************** Constructors ********************** //
	/**
	 * Default constructor, setting color=white 
	 */
	public Light() {
		_color= Color.WHITE;
	}
	
//	public Light(int r,int g, int b) {
//		_color = 
//	}
	public Light (Color color) 
	{
		_color= color;
	}
	// ***************** Getters/Setters ********************** //
//	public abstract Color getIntensity();
		
}

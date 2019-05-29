package elements;
import java.awt.Color;

public class MyColor {
	/**
	 * Scaling a Color instance by double factor. returning a new color = (red*k,green*k,blue*k)
	 * @param a - the Color instance to scale
	 * @param k - scaling factor
	 * @return Color
	 */
	public static Color scaleColor(Color a, double k) {
		int red = (int) (a.getRed() * k);
		if(red>255)
			red = 255;
		else if(red<0)
			red =0;
		int green = (int)(a.getGreen()*k);
		if(green>255)
			green =255;
		else if(green<0)
			green =0;
		int blue = (int)(a.getBlue()*k);
		if(blue>255)
			blue =255;
		else if(blue<0)
			blue =0;
		return new Color(red,green,blue);
	}
	
	/**
	 * Copying a Color instance
	 * @param a- the origin
	 * @return Color
	 */
	public static Color copyColor(Color a)
	{
		return new Color(a.getRed(),a.getGreen(),a.getBlue());
	}
	
	/**
	 *  Adding two colors returning a new color = (a.red+b.red, a.green+b.green, a.blue+b.blue) 
	 * @param a
	 * @param b
	 * @return Color
	 */
    public static Color addColors( Color a, Color b) {
		int red = Math.max(0, Math.min(255, a.getRed() + b.getRed()));
		int green = Math.max(0, Math.min(255, a.getGreen() + b.getGreen()));
		int blue = Math.max(0, Math.min(255, a.getBlue() + b.getBlue()));
    
		return new Color(red,green,blue);
    }
}
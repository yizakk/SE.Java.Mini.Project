package primitives;
import java.awt.Color;

/**
 * An extension for the Color class of java.awt, for scaling,copying and adding two colors into one.
 * Some colors operations
 * @author נוסבאום
 *
 */
public class MyColor extends Color {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MyColor ()
	{
		super(0,0,0);
	}
	
	public MyColor( int red, int green, int blue)
	{
		super(red,green,blue);
	}
	
	public MyColor(MyColor other) {
		super(other.getRed(), other.getGreen(), other.getBlue());
	}

	public MyColor(Color emmission) {
		super( emmission.getRed(), emmission.getGreen(), emmission.getBlue());
	}

	/**
	 * Scaling a Color instance by double factor. returning a new color = (red*k, green*k, blue*k)
	 * @param a - the Color instance to scale
	 * @param k - scaling factor
	 * @return Color
	 */
	
	public MyColor scaleColor(double k) {
		int red = (int) (this.getRed() * k);
		if(red>255)
			red = 255;
		else if(red<0)
			red =0;
		int green = (int)(this.getGreen()*k);
		if(green>255)
			green =255;
		else if(green<0)
			green =0;
		int blue = (int)(this.getBlue()*k);
		if(blue>255)
			blue =255;
		else if(blue<0)
			blue =0;
		return new MyColor(red,green,blue);
	}
	
	/**
	 * Copying a Color instance. returning a cloned Color instance
	 * @param a- the origin
	 * @return Color
	 */
	public MyColor copyColor()
	{
		return new MyColor(this.getRed(),this.getGreen(),this.getBlue());
	}
	
	/**
	 *  Adding two colors returning a new color = (a.red+b.red, a.green+b.green, a.blue+b.blue)
	 *  validating colors are in range (0-255), by choosing the maximum(0, minimum(color,255)) 
	 * @param a
	 * @param b
	 * @return Color
	 */
    public MyColor addColors(Color b) {
		int red = Math.max(0, Math.min(255, this.getRed() + b.getRed()));
		int green = Math.max(0, Math.min(255, this.getGreen() + b.getGreen()));
		int blue = Math.max(0, Math.min(255, this.getBlue() + b.getBlue()));
    
		return new MyColor(red,green,blue);
    }
}
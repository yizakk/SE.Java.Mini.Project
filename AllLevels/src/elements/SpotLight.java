package elements;

import java.awt.Color;

import primitives.MyColor;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
	
	private Vector _direction;
	
	//**************************  Constructors  ********************************//
	public SpotLight(MyColor color, Point3D position, Vector direction, double kc, double kl, double kq) throws Exception {
		super(color, position, kc, kl, kq);
		_direction = new Vector (direction).normalize();
	}

	/*************************************************
	 * FUNCTION
	 * getIntensity
	 * 
	 * PARAMETERS
	 * Point3D - the point in which to calculate the exact intensity
	 * 
	 * RETURN VALUE
	 * Color 
	 * 
	 * MEANING
	 * This function calculates the intensity in a point according to the equalence-
	 * (I0*|d*L|) / (Kc+Kl*d+Kq*d*d).
	 * It calls the super(PointLight) getIntensity function that returns 
	 * I0 / (Kc+Kl*d+Kq*d*d), than scaling it by |D dotProduct L|
	 * 
	 * SEE ALSO
	 * elements.Light.getIntensity
	 **************************************************/
	@Override
	public MyColor getIntensity(Point3D point) 
	{
        MyColor pointColor = super.getIntensity(point);

        Vector l = getL(point);
        try {
        l.normalize();
        }
        catch(Exception a)
        {
        	l=new Vector(1,1,1);
        }
        double k = Math.abs(_direction.dotProduct(l));

        return pointColor.scaleColor(k);
	}
	
	// function getL is implemented in the father - PointLight
	// because they are equal
}

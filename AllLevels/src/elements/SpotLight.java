package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
	
	private Vector _direction;
	
	//**************************  Constructors  ********************************//
	public SpotLight(Color color, Point3D position, Vector direction, double kc, double kl, double kq) throws Exception {
		super(color, position, kc, kl, kq);
		_direction = new Vector (direction).normalize();
	}

	@Override
	public Color getIntensity(Point3D point) 
	{
        Color pointColor = super.getIntensity(point);

        Vector l = getL(point);
        try {
        l.normalize();
        }
        catch(Exception a)
        {
        	l=new Vector();
        }
        double k = Math.abs(_direction.dotProduct(l));

        if (k > 1) k = 1; 

        return new Color((int) (pointColor.getRed() * k),
                (int) (pointColor.getGreen() * k),
                (int) (pointColor.getBlue() * k));
	}
	
	// function getL is implemented in the father - PointLight
	// because they are equal
}

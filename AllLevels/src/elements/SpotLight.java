package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
	
	private Vector _direction;
	
	//**************************  Constructors  ********************************//
	public SpotLight(Color color, Point3D position, Vector direction, double kc, double kl, double kq) {
		super(color, position, kc, kl, kq);
		_direction = new Vector (direction);
	}

	@Override
	public Color getIntensity(Point3D point)
	{
		double attenuation = this.getAttenuation(point); // attenuation 
		double cosine = _direction.dotProduct(getL(point)); // D*L
		return new Color( (int) (this._color.getRed()*cosine / attenuation),
						  (int) (this._color.getGreen()*cosine / attenuation),
						  (int) (this._color.getBlue()*cosine / attenuation)); 
	}
	
	// function getL is implemented in the father - PointLight
	// because they are equal
}

package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light
{
	private Vector _direction;
	
	// ***************** Constructors ********************** //
	public DirectionalLight(Color color, Vector direction) {
		super(color);
		setDirection(direction);
	}
	// ***************** Getters/Setters ********************** //

	public Vector getDirection() { return new Vector(_direction); }
	public void setDirection(Vector direction) { _direction = new Vector(direction); }
	
	@Override
	public Vector getL(Point3D point) {
		try {
		return new Vector(_direction).normalize();
		}
		catch(Exception a)
		{
			return new Vector(1,1,1);
		}
	}
	@Override
	public Color getIntensity(Point3D point) {
		return new Color(_color.getRed(),_color.getGreen(),_color.getBlue());
	}

}

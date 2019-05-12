package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public interface LightSource 
{

	public abstract Color getIntensity(Point3D point);
	public abstract Vector getL(Point3D point);
}

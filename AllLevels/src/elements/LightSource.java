package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public interface LightSource 
{
	/*************************************************
	 * FUNCTION
	 * getIntensity
	 * 
	 * PARAMETERS
	 * Point3D - the point in which to calculate the intensity of a light source
	 * RETURN VALUE
	 * 
	 * MEANING
	 * This function is a prototype for each light source to implement because each 
	 * light source has different intensity calculation (considering the attenuation etc.). 
	 * 
	 * SEE ALSO
	 * 
	 **************************************************/
	public abstract Color getIntensity(Point3D point);

	/*************************************************
	 * FUNCTION
	 * getL
	 * 
	 * PARAMETERS
	 * Point3D - the point in which to calculate the vector from the light position
	 * to it.
	 * 
	 * MEANING
	 * This function returns the 'L' vector (from the light source into a point at the
	 * view panel)
	 * SEE ALSO
	 * 
	 **************************************************/
	public abstract Vector getL(Point3D point);
}

package elements;

import java.awt.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light {

	Point3D _position;
	double _Kc, _Kl, _Kq;
	
	// ***************** Constructors ********************** //
		
	public PointLight(Color color, Point3D position, double kc, double kl, double kq) {
		super(color);
		setPosition(position);
		_Kc = kc;
		_Kl = kl;
		_Kq = kq;	
	}
	
	// ***************** Getters/Setters ********************** //
	
	public void setPosition(Point3D p) { _position = new Point3D(p); }
	public void setKc(double kc) { _Kc = kc; }
	public void setKl(double kl) { _Kl = kl; }
	public void setKq(double kq) { _Kq = kq; }
	
	public Point3D getPosition() { return new Point3D(_position);}
	public double getKc() { return _Kc; }
	public double getKl() { return _Kl; }
	public double getKq() { return _Kq; }
	
	/*************************************************
	 * FUNCTION
	 * getIntensity
	 * 
	 * PARAMETERS
	 * Point3D - the point in which to calculate the exact intensity
	 * 
	 * RETURN VALUE
	 * Color - the new color equal to the origin / (Kc*(Kj*d)*(Kq*d*d))
	 * 
	 * MEANING
	 * This functions calculates the exact intensity of the PointLight in specific point, 
	 * considering the attenuation from the position of the point
	 * 
	 * SEE ALSO
	 * elements.Light.getIntensity
	 **************************************************/
	
	@Override
	public Color getIntensity(Point3D point) {
		double attenuation = getAttenuation(point);
		
		if((1/attenuation)>1)
			attenuation=1;
		
		// validating numbers are in the RGB range 
		int red = Math.max(0, Math.min(255,(int)(_color.getRed()/attenuation)));
		int green = Math.max(0, Math.min(255,(int)(_color.getGreen()/attenuation)));
		int blue = Math.max(0, Math.min(255,(int)(_color.getBlue()/attenuation)));
	    
		return new Color(red,green,blue);
	}
	
	protected double getAttenuation(Point3D point) {
		double distance = _position.distance(point);
		return _Kc + _Kl* distance+ _Kq*distance*distance; // iL = I0 / (kc+Kl*d+kq*d^2)	
	}

	@Override
	public Vector getL(Point3D point) {
		// P0-point
		return new Vector (point,_position);
	}
}
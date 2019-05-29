package primitives;
import static primitives.Util.*;

import java.text.DecimalFormat;
public final class Coordinate
{
	//private static final double EPSILON = 0.0000001;
	private double _coordinate; 

	public static Coordinate ZERO = new Coordinate(0.0);

	// ***************** Constructors ********************** //
	//Default Constructor
	public Coordinate()  {
		this._coordinate = 0.0;
	}
	//Constructor gets double
	public Coordinate(double coordinate)  {
		// if it too close to zero make it zero
		_coordinate = alignZero(coordinate);
	}
	//Copy Constructor
	public Coordinate(Coordinate coordinate)  {
		_coordinate = coordinate._coordinate;
	}

	// ***************** Getters/Setters ********************** //
	public double getCoordinate() {
		return _coordinate;
	}
	public void setCoordinate(double coordinate) {
		this._coordinate = coordinate;
	}

	// ***************** Administration ******************** //
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_coordinate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return Double.doubleToLongBits(_coordinate) == 
   			   Double.doubleToLongBits(other._coordinate);
	}
	//CompareTo - check if some coordinate equal to the another
	public int compareTo(Coordinate coordinate)
	{
		return this.equals(coordinate)?0:1;
	}
	/************** Operations ***************/
	//add coordinate one to another 
	public Coordinate add(Coordinate coordinate) {
		return new Coordinate(uadd(_coordinate, coordinate._coordinate));
	}
	//subtract coordinate one to another 
	public Coordinate subtract(Coordinate coordinate) {
		return new Coordinate(usubtract(_coordinate, coordinate._coordinate));
	}
	//multiply coordinate by number
	public Coordinate scale(double num) {
		return new Coordinate(uscale(_coordinate, num));
	}
	//multiply coordinate by another
	public Coordinate multiply(Coordinate other) {
		return new Coordinate(uscale(_coordinate, other._coordinate));
	}
	//toString - printing the coordinate 
	@Override
	public String toString() {
		//DecimalFormat df = new DecimalFormat ("0.0#");
		return new DecimalFormat("0.0#").format(_coordinate);
	}
}
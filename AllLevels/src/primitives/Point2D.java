package primitives;
//import static primitives.Util.uscale;
public class Point2D
{
	protected Coordinate _x;
	protected Coordinate _y;
	// ***************** Constructors ********************** //
	//default Constructor
	Point2D()
	{
		this._x= new Coordinate();
		this._y= new Coordinate();
	}
	//Constructor get 2 double
	Point2D(double x, double y)
	{
		_x=new Coordinate(x);
		_y=new Coordinate(y);
	}
	//Constructor get 2 Coordinate
	Point2D(Coordinate x, Coordinate y)
	{
		_x=new Coordinate(x);
		_y=new Coordinate(y);
	}

	public Point2D(Point2D point2D) {
		_x  = new Coordinate(point2D._x);
		_y  = new Coordinate(point2D._y);
	}
	// ***************** Getters/Setters ********************** //
	public Coordinate getX() { return new Coordinate(_x);}
	public Coordinate getY() { return new Coordinate(_y);}
	//
	//Important! "_x" is NOT equal to the second "_x" but this is the prototype in the moodle file so i did like the file

	public void setX(Coordinate x) { _x=new Coordinate(x);}
	public void setY(Coordinate y) { _y=new Coordinate(y);}

	// ***************** Administration ******************** //

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_x == null) ? 0 : _x.hashCode());
		result = prime * result + ((_y == null) ? 0 : _y.hashCode());
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
		Point2D other = (Point2D) obj;
		if (_x == null) {
			if (other._x != null)
				return false;
		} else if (!_x.equals(other._x))
			return false;
		if (_y == null) {
			if (other._y != null)
				return false;
		} else if (!_y.equals(other._y))
			return false;
		return true;
	}

	//CompareTo - check if some Point2D equal to the another
	public int compareTo(Point2D point2D) {
		return this.equals(point2D)?0:1;
	}
	//toString - print 
	@Override
	public String toString() {
		return "("+_x.toString()+","+_y.toString()+")";
	}
}

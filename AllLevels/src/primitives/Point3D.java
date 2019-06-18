package primitives;
import static java.lang.StrictMath.sqrt;
public class Point3D extends Point2D
{
	private Coordinate _z;

	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting the point to (0,0,0)
	 */
	public Point3D()
	{
		super();
		this._z = new Coordinate();
	}
	//Constructor get 3 coordinates
	public Point3D(Coordinate x, Coordinate y,Coordinate z)
	{
		super(x,y);
		_z = new Coordinate(z);
	}
	//Constructor get 3 doubles
	public Point3D(double x, double y, double z)
	{
		super(x,y);
		_z=new Coordinate(z);
	}
	//Copy Constructor
	public Point3D(Point3D point3D)
	{
		super(point3D.getX(), point3D.getY());
		this._z =new Coordinate(point3D.getZ());
	}
	// ***************** Getters/Setters ********************** //

	public Coordinate getZ() { return new Coordinate(_z);}
	//Important! "_z" is NOT equal to the second "_z" but this is the prototype in the moodle file so i did like the file
	public void setZ(Coordinate z) { _z=new Coordinate(z); }

	// ***************** Administration ******************** //

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_z == null) ? 0 : _z.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point3D other = (Point3D) obj;
		if (_z == null) {
			if (other._z != null)
				return false;
		} else if (!_z.equals(other._z))
			return false;
		return true;
	}
	//Check if equals between 2 Points
	public int compareTo(Point3D other) {
		return this.equals(other) ? 0 : 1;
	}
	//toString - print 
	@Override
	public String toString() {
		return "("+_x.toString()+", "+_y.toString()+", "+_z.toString()+")";
	}
	
	// ***************** Operations ******************** //
	/**
	 * adding a vector to Point3D, without changing the origin point
	 * @param vector
	 * @return copy of the updated point3d
	 */
	public Point3D add(Vector vector) {
		Point3D tempHead = vector.getHead();
		return new Point3D( (_x.add(tempHead._x)),
							(_y.add(tempHead._y)),
							(_z.add(tempHead._z)));
	}
	
	/**
	 * subtracting a vector from point, without changing the origin point
	 * @param vector
	 * @return copy of the updated point3d
	 */
	public Point3D subtract(Vector vector) {
		return new Point3D(_x.subtract(vector.getHead().getX()),
						   _y.subtract(vector.getHead().getY()),
						   _z.subtract(vector.getHead().getZ()));			
	}
	
	/**
	 * subtracting a point from another (left-right), without changing the origin point
	 * 
	 * @param point
	 * @return updated point
	 */
	public Point3D subtract(Point3D point) {
		if(point!=null) {
			return new Point3D( _x.subtract(point._x),
					_y.subtract(point._y),
					_z.subtract(point._z));
		}
		else
			return new Point3D(0,0,0);
	}

	/**
	 * Calculating the distance between two points
	 * @param the right-hand point
	 * @return double
	 */

	public double distance(Point3D point) {
		double X=new Coordinate(_x).subtract(point._x).getCoordinate();
		double Y=new Coordinate(_y).subtract(point._y).getCoordinate();
		double Z=new Coordinate(_z).subtract(point._z).getCoordinate();
		return sqrt(X*X+Y*Y+Z*Z);
	}

	//Calculate the squared Distance

	public double poweredDistance(Point3D p) {
		double distance= distance(p);
		return distance*distance;
	}

	/**
	 * scaling the point3d in given factor, without changing the origin point
	 * @param num
	 * @return new point equal to the origin point scaled by the given factor
	 */

	public Point3D scale(double num) {
		return new Point3D(_x.scale(num),
						   _y.scale(num),
						   _z.scale(num));
	}
	public Vector vector(Point3D point3d) {
		return new Vector (this, point3d);
	}
}
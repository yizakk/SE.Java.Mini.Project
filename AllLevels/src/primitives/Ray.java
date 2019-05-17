package primitives;

public class Ray
{
	private Point3D _POO; // P0
	private Vector _direction; // Ray Direction

	// ***************** Constructors ********************** //
	//Default Constructor
	public Ray()
	{
		_POO = new Point3D();
		_direction = new Vector();
	}
	//Copy constructor
	public Ray(Ray ray)
	{
		this._POO = ray.getPOO();
		this._direction = ray.getDirection();
	}

	public Ray(Point3D point, Vector direction) throws Exception
	{
		this._POO = point;
		this._direction = direction.normalize();
	}

	// ***************** Getters/Setters ********************** //
	public void setPOO(Point3D pnt) { this._POO = new Point3D(pnt); }
	public void setDirection(Vector _direction) throws Exception
	{ this._direction = new Vector(_direction).normalize();  }
	public Vector getDirection() { return new Vector(_direction); }
	public Point3D getPOO()        { return new Point3D(_POO); }
	
	//******************* Administration ****************//	
	@Override
	public boolean equals(Object other)
	{
		if(other==this)
			return true;
		if(!(other instanceof Ray))
			return false;
		Ray ray = (Ray) other;
		return this._POO.equals(ray._POO) && this._direction.equals(ray._direction);
		
	}
	
	@Override
	public String toString() {
		return "Ray [point=" + _POO + ", direction=" + _direction + "]";
	}
}
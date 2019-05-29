package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import primitives.*;
public class Plane extends Geometry implements FlatGeometry
{
	private Vector _normal;
	private Point3D _Q;
	
	// ***************** Constructors ********************** //
	public Plane()
	{
		super();
		this._Q = new Point3D();
		this._normal = new Vector();
	}
//copy con
	public Plane(Plane plane) {
		super(plane.getEmmission());
		this._Q=new Point3D(plane._Q);
		this._normal=new Vector(plane._normal);
	}
	
	public Plane(Vector normal, Point3D q)
	{
		super();
		_normal = new Vector(normal);
		_Q = new Point3D(q);
	}

	public Plane(Color c, Vector normal, Point3D q)
	{
		super(c);
		_normal = new Vector(normal);
		_Q = new Point3D(q);
	}


	/************** Getters/Setters ***********/
	@Override
	public Vector getNormal(Point3D point){return new Vector(_normal);}
	public Point3D getQ() {return _Q;}
	public void setNormal(Vector normal) {this._normal = new Vector(normal);}
	public void setQ(Point3D _point3) {this._Q = new Point3D(_point3);}
	// ***************** Administration ******************** //
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_Q == null) ? 0 : _Q.hashCode());
		result = prime * result + ((_normal == null) ? 0 : _normal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj==null || !(obj instanceof Plane))
				return false;
		Plane other = (Plane) obj;
		return  super.equals(obj)
				&& this._normal.equals(other._normal) 
				&& this._Q.equals(other._Q);
	}
	//Check if equals between 2 Planes
	public int compareTo(Plane plane) {
		return this.equals(plane) ? 0 : 1;
	}
	
	@Override
	public List<Point3D> findIntersections(Ray ray) throws Exception
	{
		ArrayList<Point3D> intersectionPoint = new ArrayList<Point3D>();
		
		Point3D P0 = ray.getPOO();
		//Point3D Q0 = new Point3D(this._Q);	
		Vector v = new Vector(P0,_Q); //v = p0-q0

		//Ray Direction
		Vector V = ray.getDirection();
		// finding -N
		Vector N = getNormal(null); //new Vector(_normal.getHead());
		double NV = N.dotProduct(V);
		double t = (N.scale(-1).dotProduct(v))/NV;	

		if (t>=0)
		{
			P0 = P0.add(V.scale(t)); // P=P0+t*V (scaling V by t, and adding it to P0)
			intersectionPoint.add(P0);
			return intersectionPoint;
		}
		else 
			return intersectionPoint;
	}
	

	/************** To String - Print ***************/
	@Override
	public String toString() { return "Normal:"+_normal.toString()+" "+"Point:"+_Q.toString();}
	public Vector get_normal() {
		return _normal;
	}

}

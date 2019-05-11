package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import primitives.*;
public class Plane extends Geometry
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

	public Plane(Plane plane) {
		super(plane.getEmmission());
		this._Q=plane._Q;
		this._normal=plane._normal;
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
	/*public Plane(Point3D _p1,Point3D _p2,Point3D _p3) throws Exception
	{
		this._Q=_p3;
		Vector v1=new Vector(_p2,_p1);
		Vector v2=new Vector(_p3,_p1);;
		this._normal=v1.crossProduct(v2).normalize();
	}*/

	/************** Getters/Setters ***********/
	public Vector getNormal(Point3D point){return this._normal;}
	public Point3D getQ() {return _Q;}
	public void setNormal(Vector _normal) {this._normal = _normal;}
	public void setQ(Point3D _point3) {this._Q = _point3;}
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plane other = (Plane) obj;
		if (_Q == null) {
			if (other._Q != null)
				return false;
		} else if (!_Q.equals(other._Q))
			return false;
		if (_normal == null) {
			if (other._normal != null)
				return false;
		} else if (!_normal.equals(other._normal))
			return false;
		return true;
	}
	//Check if equals between 2 Planes
	public int compareTo(Plane plane) {
		return this.equals(plane) ? 0 : 1;
	}
	/**
	@Override
	public List<Point3D> findIntersection(Ray ray){
		//create -N
		Vector NegetiveN = new Vector(this._normal.getHead());
		NegetiveN.scale(-1);
		//create (P0-Q0) = PQ
		Point3D temp = ray.getPOO();
		temp.subtract(this._Q);
		Vector PQ = new Vector(temp);
		//create N*V
		Vector NV = new Vector(this._normal);
		//create t
		double nv = NV.dotProduct(ray.getDirection());
		double t = (NegetiveN.dotProduct(PQ))/nv;
		//create p
		Point3D p=new Point3D(ray.getPOO());
		Vector temp1 = new Vector(ray.getDirection());
		temp1.scale(t);
		p.add(temp1);
		ArrayList<Point3D> array=new ArrayList<Point3D>();
		array.add(p);
		return array;
   
	}**/
	@Override
	public List<Point3D> findIntersection(Ray ray) throws Exception
	{
		ArrayList<Point3D> intersectionPoint = new ArrayList<Point3D>();
		Point3D P0 = ray.getPOO();
		Point3D Q0 = new Point3D(this._Q);
		//v = p0-q0
		Vector v = new Vector(Q0,P0);

		//Ray Direction
		Vector V = new Vector(ray.getDirection());
		// finding -N
		Vector N = new Vector(_normal.getHead());
		double NV = N.dotProduct(V);
		double t = (N.scale(-1).dotProduct(v))/NV;	
		//*****************************to delete*******************************************
		
		if (t>=0)
		{
			V = V.scale(t);
			
			P0.add(V);
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

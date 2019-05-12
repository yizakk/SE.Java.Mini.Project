package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
public class Triangle extends Geometry {
	
	Point3D _p1;
	Point3D _p2;
	Point3D _p3;
	
	// ***************** Constructors ********************** //
	//Default Constructor
	public Triangle()
	{
		super();
		_p1=new Point3D();
		_p2=new Point3D();
		_p3=new Point3D();
	}
	/*********Copy Constructor*************/
	public Triangle(Triangle other)
	{
		super(other.getEmmission());
		this._p1 =new Point3D(other._p1);
		this._p2 =new Point3D(other._p2);
		this._p3 =new Point3D(other._p3);
	}
	public Triangle(Point3D p1, Point3D p2, Point3D p3)
	{
		_p1=new Point3D(p1);
		_p2=new Point3D(p2);
		_p3=new Point3D(p3);
	}
	public Triangle(Color c,Point3D p1, Point3D p2, Point3D p3)
	{
		super(c);
		_p1=new Point3D(p1);
		_p2=new Point3D(p2);
		_p3=new Point3D(p3);
	}
	// ***************** Getters/Setters ********************** //

	public Point3D getP1() {return new Point3D(_p1);}
	public Point3D getP2() {return new Point3D(_p2);}
	public Point3D getP3() {return new Point3D(_p3);}
	public void setP1(Point3D p1) {p1=new Point3D(_p1);}
	public void setP2(Point3D p2) {p2=new Point3D(_p2);}
	public void setP3(Point3D p3) {p3=new Point3D(_p3);}



	// ***************** Operations ******************** //
	@Override
	public Vector getNormal(Point3D point) throws Exception 
	{
		Vector v1=new Vector(_p1,_p2);
		Vector v2=new Vector(_p1,_p3);
		return v1.crossProduct(v2).normalize().scale(-1);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		if (_p1 == null) {
			if (other._p1 != null)
				return false;
		} else if (!_p1.equals(other._p1))
			return false;
		if (_p2 == null) {
			if (other._p2 != null)
				return false;
		} else if (!_p2.equals(other._p2))
			return false;
		if (_p3 == null) {
			if (other._p3 != null)
				return false;
		} else if (!_p3.equals(other._p3))
			return false;
		return true;
	}

	public int compareTo(Triangle triangle)
	{
		return this.equals(triangle) ? 0:1;
	}




	//function that find intersections points between the ray and the Triangle
	public List<Point3D> findIntersection(Ray ray) throws Exception
	{
		Point3D p0 = new Point3D(ray.getPOO());
		Point3D p1 = new Point3D(_p1);
		p1.subtract(p0);
		// v1= p1-p0
		Vector v1 = new Vector(p1);
		
		Point3D p2 = new Point3D(_p2);
		
		
		//p1 = new Point3D(_p1);
		p2.subtract(p0);
		// v2= p2-p0
		Vector v2 = new Vector(p2);
		Vector N = v1.crossProduct(v2);
		Plane plane = new Plane(N,_p1);
		List<Point3D> array = new ArrayList<Point3D>();
		array = plane.findIntersection(ray);
		if(array.isEmpty())
		{
			return array;	
		}
		Point3D p = new Point3D(array.get(0));
		p.subtract(ray.getDirection().getHead());
		Vector t1=new Vector(new Point3D(_p1).subtract(p0));
		Vector t2=new Vector(new Point3D(_p2).subtract(p0));
		Vector t3=new Vector(new Point3D(_p3).subtract(p0));
		Vector N1=t1.crossProduct(t2).normalize();
		Vector N2=t2.crossProduct(t3).normalize();
		Vector N3=t3.crossProduct(t1).normalize();
		double n1=N1.dotProduct(new Vector(p));
		double n2=N2.dotProduct(new Vector(p));
		double n3=N3.dotProduct(new Vector(p));
		//if it is the same sign then return the array intersection**/

		if(n1>0&&n2>0&&n3>0||n1<0&&n2<0&&n3<0)
		{
			//System.out.println("same sign - positive - findIntersection- triangle\n");
			array.add(p);
			return array;
		}
		else {
		//	System.out.println("diffrent sign - findIntersection- triangle\n");			
			return new ArrayList<Point3D>();
			}
	}

	//toString - print
	@Override
	public String toString() {
		return "Triangle [_p1=" + _p1 + ", _p2=" + _p2 + ", _p3=" + _p3 + "]";
	}
}


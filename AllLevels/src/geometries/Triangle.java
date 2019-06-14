package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
public class Triangle extends Geometry implements FlatGeometry, Boxable {
	
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
		super();
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
	public Triangle(Point3D p1, Point3D p2, Point3D p3, Color color, Material material) {
		super(color, material);
		_p1=new Point3D(p1);
		_p2=new Point3D(p2);
		_p3=new Point3D(p3);
	}
	// ***************** Getters/Setters ******************************************* //
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
		Vector v1=new Vector(_p2,_p1);
		Vector v2=new Vector(_p3,_p1);
		return v1.crossProduct(v2).normalize().scale(-1);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Triangle))
			return false;

		Triangle other = (Triangle) obj;
		return _p1.equals(other._p1) && _p2.equals(other._p2) && _p2.equals(other._p3);
	}

	public int compareTo(Triangle triangle)
	{
		return this.equals(triangle) ? 0:1;
	}

	//function that find intersections points between the ray and the Triangle
	public List<Point3D> findIntersections(Ray ray) throws Exception
	{
		Point3D P0 = ray.getPOO();

        Vector v1 = new Vector(_p1,_p2).normalize();
        Vector v2 = new Vector(_p1,_p3).normalize();
		Vector Normal = v1.crossProduct(v2);
       // Vector N = this.getNormal(_p1);
		Plane plane = new Plane(Normal,_p1);
		
		// looking for intersections between ray and the plane created from the triangle,
		//if there is no point of intersection- return empty list
		List<Point3D> array = plane.findIntersections(ray);
		if(array.isEmpty())
			return array;	
		
		Vector p_P0 = new Vector(array.get(0),P0); //the vector between intersection point and P0
		Vector t1=new Vector(_p1,P0);
		Vector t2=new Vector(_p2,P0);
		Vector t3=new Vector(_p3,P0);
		
		Vector N1=t1.crossProduct(t2).normalize();
		Vector N2=t2.crossProduct(t3).normalize();
		Vector N3=t3.crossProduct(t1).normalize();
		
		double n1=N1.dotProduct(p_P0);
		double n2=N2.dotProduct(p_P0);
		double n3=N3.dotProduct(p_P0);
		//if it is the same sign then return the array intersection**/

		if(n1 >= 0&& n2>=0&&n3>=0||n1<=0&&n2<=0&&n3<=0)
			return array;
		
		else {
			return new ArrayList<Point3D>();
		}
	}

	// find min and max for x,y,z than 
	//ואז מיקום הנקודה של הבוקס יהיה מינימום של כל אחד פחות 1 , 
	//אורך רוחב וגובה יהיו עבור כל אחד - מקס פחות מינ ועוד 1
	@Override
	public Box insertIntoBox() {
		double x1 = _p1.getX().getCoordinate();
		double y1 = _p1.getY().getCoordinate();
		double z1 = _p1.getZ().getCoordinate();
		
		double x2 = _p2.getX().getCoordinate();
		double y2 = _p2.getY().getCoordinate();
		double z2 = _p2.getZ().getCoordinate();
		
		double x3 = _p3.getX().getCoordinate();
		double y3 = _p3.getY().getCoordinate();
		double z3 = _p3.getZ().getCoordinate();

		// Finding the Min of each from x,y,z
		double minX = minOf3Points(x1, x2, x3);
		double minY = minOf3Points(y1, y2, y3);
		double minZ = minOf3Points(z1, z2, z3);

		// Finding the Max of each from x,y,z
		double maxX = maxOf3Points(x1, x2, x3);
		double maxY = maxOf3Points(y1, y2, y3);
		double maxZ = maxOf3Points(z1, z2, z3);
		
		Box box = new Box(new Point3D(minX-1,minY-1,minZ-1), maxY-minY+1,maxZ-minZ+1,maxX-minX+1);
		box.addGeometry(this);
		return box;
	}
	
	private double minOf3Points(double x1, double x2, double x3) {
		double min = Math.min(x1,x2);
		return Math.min(min, x3);
	}
	
	private double maxOf3Points(double x1, double x2, double x3) {
		double max = Math.max(x1,x2);
		return Math.max(max, x3);
	}
	
	//toString - print
	@Override
	public String toString() {
		return "Triangle [_p1=" + _p1 + ", _p2=" + _p2 + ", _p3=" + _p3 + "]";
	}
}
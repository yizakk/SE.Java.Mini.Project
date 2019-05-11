package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import primitives.*;
public class Sphere extends RadialGeometry {
	Point3D _center;
	//**************** Constructors *****************
	//Default Constractor
	public Sphere() {
		super();
		this._center = new Point3D();	
	}
	//Copy constructor
	public Sphere(Sphere sphere) {
		super(sphere);
		this._center=sphere._center;
	}
	public Sphere(double _raduis,Point3D center)
	{
		super(_raduis);
		this._center=new Point3D(center);
	}

	public Sphere(Color c,double _radius, Point3D center) {
		super(c,_radius);
		this._center = new Point3D(center);
	}
	/**********************Get/Set******************************/
	public Point3D getCenter() {return _center;}
	public void setCenter(Point3D center) {this._center = center;}
	public Vector getNormal(Point3D point) throws Exception
	{
		return new Vector(_center,point).normalize();
	}

	/**********************Equals******************************/
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Sphere other = (Sphere) obj;
			if (_center == null) {
				if (other._center != null)
					return false;
			} else if (!_center.equals(other._center))
				return false;
			return true;
		}
		
		public int compareTo(Sphere sphere)
		{
			return this.equals(sphere) ? 0:1;
		}
		
	/***************************************findIntersection**********************/

	@Override
	public List<Point3D> findIntersection(Ray ray) 
	{
		Point3D center = new Point3D(_center);
		center.substract(ray.getPOO());
		Vector L = new Vector(center);
		Vector V = new Vector(ray.getDirection());
		double tm =L.dotProduct(V);
		//find D by pitagoras no using Math library
		double d = Math.sqrt(Math.pow(L.length(), 2)-Math.pow(tm, 2));
		double th = Math.sqrt(Math.pow(this.getRadius(), 2)-(Math.pow(d, 2)));
		double t1 = tm-th;
		double t2 = tm + th;
		ArrayList<Point3D> array = new ArrayList<Point3D>();
		if (d>this.getRadius())
			return array;	    
		if (t1>0)
		{
			Vector pv1=new Vector(V);
			pv1.scale(t1);
			Point3D p1 = new Point3D(ray.getPOO());
			p1.add(pv1);
			array.add(p1);
		}
		if (t2>0)
		{
			Vector pv2=new Vector(V);
			pv2.scale(t2);
			Point3D p2 = new Point3D(ray.getPOO());
			p2.add(pv2);
			array.add(p2);
		}
		return array;
	}
	
	@Override
	public String toString() {
		return "Point:"+_center.toString()+" "+"Radius:"+this._radius+" ";
	}

}




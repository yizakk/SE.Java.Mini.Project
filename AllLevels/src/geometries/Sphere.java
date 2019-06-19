package geometries;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import primitives.*;
public class Sphere extends RadialGeometry implements Boxable {
	
	Point3D _center;
	
	//**************** Constructors *****************
	/**
	 * Default C-Tor, center=(0,0,0), Radial={radius=0, Geometry={shininess=1, emmission=black,Material= {Kd=1, Ks=1, Kr=0, Kt=0 index=19} } }
	 */
	public Sphere() {
		super();
		this._center = new Point3D();	
	}
	/**
	 * Copy C-Tor
	 * @param sphere
	 */
	public Sphere(Sphere sphere) {
		super(sphere);
		this._center=sphere.getCenter();
	}
	/**
	 * radius=_radius 
	 * Radial={Geometry={shininess=1, emmission=black,Material= {Ks=1, Ks=1, Kr=0, Kt=0 index=19} } }
	 * @param _raduis
	 * @param center
	 */
	public Sphere(double _raduis,Point3D center)
	{
		super(_raduis);
		this._center=new Point3D(center);
	}
/**
 * 
 * @param c - Color
 * @param _radius
 * @param center
 */
	public Sphere(Color c,double _radius, Point3D center) {
		super(c,_radius);
		this._center = new Point3D(center);
	}
	
	public Sphere(double _radius, Point3D center,Color c) {
		super(c,_radius);
		this._center = new Point3D(center);
	}
	
	
	/**********************Get/Set******************************/
	public Point3D getCenter() {return new Point3D( _center);}
	public void setCenter(Point3D center) {this._center = center;}
	
	@Override
	public Vector getNormal(Point3D point) throws Exception {
		return new Vector(_center,point).normalize();
	}

	/**********************Equals******************************/
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj) || obj==null || !(obj instanceof Sphere))
				return false;

			Sphere other = (Sphere) obj;
			return this._center.equals(other._center);
		}
		
		public int compareTo(Sphere sphere)
		{
			return this.equals(sphere) ? 0:1;
		}
		
	/***************************************findIntersections* @throws Exception **********************/

	@Override
	public List<Point3D> findIntersections(Ray ray)  
	{
		Point3D P0 = ray.getPOO();
		//Point3D center = P0.subtract(_center); 
		Vector L = new Vector(_center,P0); // center = Q0 - P0
		//direction vector of ray
		//double distance = P0.distance(_center); 
		Vector V = ray.getDirection();
		double tm =L.dotProduct(V);
		//find D by pitagoras using Math library
		double length = L.length();
		double d = Math.sqrt(length*length - tm*tm);
		//find th by pitagoras using Math library
		double th = Math.sqrt(_radius*_radius- d*d);
		double t1 = tm-th;
		double t2 = tm + th;
		ArrayList<Point3D> array = new ArrayList<Point3D>();
		if (d>this.getRadius())
			return array;	    
		if (t1>0)
		{
			Point3D p1 = P0.add(V.scale(t1)); // P1 = P0+t1*direction
			array.add(p1);
		}
		if (t2>0)
		{
			Point3D p2 = P0.add(V.scale(t2)); // P2= P0+t2*direction
			array.add(p2);
		}
		return array;
	}
	
	public Box insertIntoBox() {
		Point3D loc = new Point3D(_center.getX().getCoordinate() - _radius - 1, _center.getY().getCoordinate() - _radius - 1, _center.getZ().getCoordinate() - _radius - 1);
		Box box = new Box(loc, _radius*2+2,_radius*2+2,_radius*2+2);
		//box.setKt(1);
		box.addGeometry(this);
		return box;
		
	}
	
	@Override
	public String toString() {
		return "Point:"+_center.toString()+" Radius:"+this._radius+". ";
	}
}
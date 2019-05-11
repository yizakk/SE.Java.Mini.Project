package geometries;
import java.awt.Color;
import java.util.ArrayList;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
public class Cylinder extends RadialGeometry {

	Point3D _axisPoint;
	Vector _axisDirection;

	// ***************** Constructors ********************** //
	//default constructor
	public Cylinder() {
		super();
		this._axisPoint = new Point3D();
		this._axisDirection = new Vector();
	}
	public Cylinder(Point3D axisPoint, Vector axisDirection) {
		super();
		this._axisPoint = new Point3D(axisPoint);
		this._axisDirection = new Vector(axisDirection);
	}
	public Cylinder(Color c, double r,Point3D axisPoint, Vector axisDirection) {
		super(c,r);
		this._axisPoint = new Point3D(axisPoint);
		this._axisDirection = new Vector(axisDirection);
	}
	/**
	 * copy constructor
	 * @param cylinder
	 */
	public Cylinder(Cylinder cylinder) {
		super();
		this._axisPoint = cylinder._axisPoint;
		this._axisDirection = cylinder._axisDirection;
	}
	/**
	 * get_axisPoint
	 * @return
	 */
	public Point3D get_axisPoint() {
		return new Point3D(_axisPoint);
	}
	/**
	 * set_axisPoint
	 * @param axisPoint
	 */
	public void set_axisPoint(Point3D axisPoint) {
		this._axisPoint = new Point3D(axisPoint);
	}
	/**
	 * get_axisDirection
	 * @return
	 */
	public Vector get_axisDirection() {
		return new Vector(_axisDirection);
	}
	/**
	 * set_axisDirection
	 * @param axisDirection
	 */
	public void set_axisDirection(Vector axisDirection) {
		this._axisDirection = new Vector(axisDirection);
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
		Cylinder other = (Cylinder) obj;
		if (_axisDirection == null) {
			if (other._axisDirection != null)
				return false;
		} else if (!_axisDirection.equals(other._axisDirection))
			return false;
		if (_axisPoint == null) {
			if (other._axisPoint != null)
				return false;
		} else if (!_axisPoint.equals(other._axisPoint))
			return false;
		return true;
	}
	
	/************s*****findIntersections***********/
	@Override
	public ArrayList<Point3D> findIntersection(Ray ray) {
		return null;
	}
	/**************GetNormal**********************/
	@Override
	public Vector getNormal(Point3D point) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

/***************toString - print****************/
	@Override
	public String toString() {
		return "Cylinder [_axisPoint=" + _axisPoint + ", _axisDirection=" + _axisDirection + "]";
	}


}

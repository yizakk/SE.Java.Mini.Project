package primitives;

import static java.lang.StrictMath.sqrt;
import static primitives.Util.*;

public class Vector {
	private Point3D _head;

	// ***************** Constructors ********************** //
	//	Default Constractor
	public Vector() 
	{
		this._head = new Point3D();
	}
	// Constractor get Point3D Object
	public Vector(Point3D head) 
	{
		_head = new Point3D(head);
	}
	//Copy constractor
	public Vector(Vector v) {
		_head = new Point3D(v._head);
	}
	//Constractor get 3 double
	public Vector(double xHead, double yHead, double zHead) {
		_head = new Point3D(xHead, yHead, zHead);
	}
	//Constractor get 2 Point
	public Vector(Point3D p1, Point3D p2)
	{
		_head = new Point3D(new Point3D(p2).subtract(p1));
	}
	/*
	//Ctor get 3 coordinate - check with line 74 in point3D

	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		_head = new Point3D(x, y, z);
	}
	 */

	// ***************** Getters/Setters ********************** //
	public Point3D getHead() {return new Point3D(_head);}
	public void setHead(Point3D head) {this._head = head;}

	// ***************Administration**********************//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_head == null) ? 0 : _head.hashCode());
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
		Vector other = (Vector) obj;
		if (_head == null) {
			if (other._head != null)
				return false;
		} else if (!_head.equals(other._head))
			return false;
		return true;
	}
	//Check if equals between 2 Vectors
	public int compareTo(Vector vector) {
		return this.equals(vector) ? 0 : 1;
	}
	@Override
	public String toString() {
		return "Vector via: " + _head.toString();
	}
	/**
	 * CrossProduct 2 vec
	 * 
	 * @param vector
	 * @return
	 */

	// ****************** Operations *************//
	//Add Vector to another
	public Vector add(Vector vec)
	{
		_head.add(vec);
		return new Vector(_head);
	}
	//Substact Vector from another
	public Vector subtract(Vector vec) {
		_head.substract(vec);
		return new Vector(_head);
	}
	//scale vector in some number
	public Vector scale(double scalingFactor)
	{
		return new Vector(new Point3D(this._head.scale(scalingFactor)));
	}
	//Cross product , return the new Vector
	public Vector crossProduct(Vector vector) 
	{
		double x1 = _head.getX().getCoordinate();
		double y1 = _head.getY().getCoordinate();
		double z1 = _head.getZ().getCoordinate();

		double x2 = vector._head.getX().getCoordinate();
		double y2 = vector._head.getY().getCoordinate();
		double z2 = vector._head.getZ().getCoordinate();

		Coordinate i = new Coordinate(usubtract(uscale(y1, z2), uscale(z1, y2)));
		Coordinate j = new Coordinate(usubtract(uscale(x1, z2), uscale(z1, x2))).scale(-1);
		Coordinate k = new Coordinate(usubtract(uscale(x1, y2), uscale(y1, x2)));
		return new Vector(new Point3D(i,j,k));
	}
	//return the length of the vector
	public double length() {
		double x = _head.getX().getCoordinate();
		double y = _head.getY().getCoordinate();
		double z = _head.getZ().getCoordinate();

		return sqrt(x * x + y * y + z * z);
	}
	/**
	 * Normalize vector (without using math library) return the new vector (with length = 1). 
	 * throws Exception if the length is 0 
	 */
	public Vector normalize() throws Exception {
		double length = length();

		if (length == 0)
			throw new ArithmeticException("Error! length=0");

		double x = _head.getX().getCoordinate();
		double y = _head.getY().getCoordinate();
		double z = _head.getZ().getCoordinate();
		_head = new Point3D(new Coordinate(x / length), new Coordinate(y / length), new Coordinate(z / length));
		return new Vector(_head);
	}
	
	//scalar product vectors return number
	public double dotProduct(Vector vec) {
		double x = this._head.getX().getCoordinate() * vec._head.getX().getCoordinate();
		double y = this._head.getY().getCoordinate() * vec._head.getY().getCoordinate();
		double z = this._head.getZ().getCoordinate() * vec._head.getZ().getCoordinate();
		return x + y + z;
	}
}

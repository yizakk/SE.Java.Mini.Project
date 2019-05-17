package geometries;

import primitives.Point3D;

public class Quadrangle {

	Triangle _tri1;
	Triangle _tri2;

	//****************************** constructors ******************************//
	public Quadrangle (Point3D P1, Point3D P2, Point3D P3, Point3D P4)
	{
		_tri1 = new Triangle(P1, P2, P4);
		_tri2 = new Triangle(P2, P3, P4);
	}
	
	
}

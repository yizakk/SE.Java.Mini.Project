package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Quadrangle extends Geometry {

	Triangle _tri1;
	Triangle _tri2;

	//****************************** constructors ******************************//
	/**
	 * Constructing 2 triangles: p1-p2-p4, p2-p3-p4
	 * @param P1
	 * @param P2
	 * @param P3
	 * @param P4
	 */
	public Quadrangle (Point3D P1, Point3D P2, Point3D P3, Point3D P4)
	{
		_tri1 = new Triangle(P1, P2, P4);
		_tri2 = new Triangle(P2, P3, P4);
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) throws Exception 
	{
		List<Point3D> list = new ArrayList<Point3D>();
		list = _tri1.findIntersections(ray);
		list.addAll(_tri2.findIntersections(ray));
		
		return list;
	}

	@Override
	public Vector getNormal(Point3D point) throws Exception {	
		return _tri1.getNormal(point);
	}
}
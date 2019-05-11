package UnitTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class PlaneTest {

	@Test
	public void test() throws Exception {
		/*Vector v = new Vector(1,2,3);
		Point3D p = new Point3D(3,4,5);
		Plane p1 = new Plane(p,v);
		
		Vector v1 = new Vector(p1.getNormal(new Point3D()));
		String str = v1.toString();
		assertEquals("Vector [_head=Point3D [_x=Coordinate [_coordinate=1.0], _y=Coordinate [_coordinate=2.0]_z=Coordinate [_coordinate=3.0]]]",str);

		Vector answer = new Vector(0,0, -1);

		Point3D directionPoint = new Point3D(0, 0, -1);
		Point3D planePoint = new Point3D(0, 100, -200);
		Point3D normalPoint = new Point3D(1, 1, -200);
		
		Vector direction = new Vector(directionPoint);
		
		Plane plane = new Plane(Color.white, planePoint, direction);
				
		Vector vector = plane.getNormal(normalPoint);
		assertEquals(answer, vector);
		*/

		// creating the expected values
		
		List<Point3D> answerList = new ArrayList<Point3D>();		
		Point3D answerPoint = new Point3D(0, 0, -200);		
		answerList.add(answerPoint);
		
		// building the plane
		
		Point3D directionPoint = new Point3D(0, 0, -1);
		Point3D planePoint = new Point3D(0, 100, -200);
				
		Vector direction = new Vector(directionPoint);
		
		Plane plane = new Plane(Color.white, direction, planePoint);
		// building the ray that will intersect the plane
		
		Point3D centerPoint = new Point3D(0,0,0);
		Vector vector = new Vector(0, 0, -5);
		Ray ray = new Ray(centerPoint, vector);
	
		// testing the findIntersection function
		
		List<Point3D> list = new ArrayList<Point3D>();
		list = plane.findIntersection(ray);

		assertEquals(answerList, list);
	}
}

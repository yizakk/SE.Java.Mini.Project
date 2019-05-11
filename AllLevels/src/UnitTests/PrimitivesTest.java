package UnitTests;
import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;
public class PrimitivesTest 
{
	@Test
	public void test() 
	{
		Point3D p1 = new Point3D();
		Point3D p2 = new Point3D();

		// Test01: Point3D compareTo
		int b = p1.compareTo(p2);
		assertEquals(0,b);
		
		///Test 02 + Test 03: Add + toString
		
		p1.add(new Vector(1.0,2.0,3.0));
		assertEquals(p1.toString(), "(1.0,2.0,3.0)");

		// Test04: Point3D subtract

		p2.setX(new Coordinate(3.0));
		p2.setY(new Coordinate(4.0));
		p2.setZ(new Coordinate(5.0));
		p1.substract(new Vector(p2));
		assertEquals(p1.toString(),"(-2.0,-2.0,-2.0)");

		// Test05: Point3D distance

		p1.setX(new Coordinate(7.0));
		p1.setY(new Coordinate(7.0));
		p1.setZ(new Coordinate(5.0));
		double dis = p1.distance(p2);
		assertEquals(5,dis,0);

		//	Test06: Vector Add test

		Vector v1 = new Vector();
		v1.add(new Vector(1.1, 2.2, 3.3));
		assertEquals("Vector via: (1.1,2.2,3.3)",v1.toString());
		v1.add(v1);
		assertEquals("Vector via: (2.2,4.4,6.6)",v1.toString());

		// Test07: Vector Substruct test

		Vector v3 = new Vector(1.0, 1.0, 1.0);
		v3.subtract(new Vector(1.0, 1.0, 1.0));
		assertEquals("Vector via: (0.0,0.0,0.0)",v3.toString());

		// Test08: Vector Scaling test

		Vector v2 = new Vector(1.2,2.4,3.6);
		v2.scale(5);
		assertEquals("Vector via: (6.0,12.0,18.0)", v2.toString());

		// Test09: Vector Dot product test

		v2.setHead(new Point3D(1.5,1.5,1.5));
		v3.setHead(new Point3D(2.0, 2.0, 2.0));
		double prod = v2.dotProduct(v3);
		assertEquals(9.0,prod,0);

		// Test10: Vector Length test

		v3.setHead(new Point3D(3,4,0));
		double len = v3.length();
		assertEquals(5,len,0);

		// Test11: Vector Normalize test

		v2.setHead(new Point3D(3.0,4.0,0.0));
		try {
			v2=v2.normalize();
		}
		catch(Exception a)
		{

		}
		assertEquals("Vector via: (0.6,0.8,0.0)",v2.toString());

		// Test12: Vector Cross product test

		v2.setHead(new Point3D(1,2,2));
		v3.setHead(new Point3D(2, 3, 1));
		v1 = v2.crossProduct(v3);
		assertEquals("Vector via: (4.0,-3.0,1.0)",v1.toString());

	}
}

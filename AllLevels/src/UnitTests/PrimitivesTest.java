package UnitTests;
import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;
public class PrimitivesTest 
{
    @Test
    public void Test01() {
        System.out.println("Test01: Point3D compareTo");
        Point3D point3D = new Point3D(2.0, -7.5, 9.25);
        Point3D instance = new Point3D(2.0, -7.5, 9.25);
        int expResult = 0;
        int result = instance.compareTo(point3D);
        assertEquals(expResult, result);
    }

    @Test
    public void Test02() {
        System.out.println("Test02: Point3D toString");
        Point3D instance = new Point3D(1.123, 2.569, 3.999);
        String expResult = "(1.12, 2.57, 4.00)"; 
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void Test03() {
        System.out.println("Test03: Point3D add");
        Vector vector = new Vector(1.25, -2.0, 3.0);
        Point3D instance = new Point3D(4.75, -5.0, 6.0);
        Point3D result = instance.add(vector);
        assertTrue("Add failed! ", result.compareTo(new Point3D(6.0, -7.0, 9.0)) == 0);
    }

    @Test
    public void Test04() {
        System.out.println("Test04: Point3D subtract");
        Vector vector = new Vector(1.0, 2.0, 3.0);
        Point3D instance = new Point3D(4.0, 5.0, 6.0);
        Point3D result = instance.subtract(vector);
        assertTrue("Substruct failed! ", result.compareTo(new Point3D(3.0, 3.0, 3.0)) == 0);
    }

    @Test
    public void Test05() {
        System.out.println("Test05: Point3D distance");
        Point3D point = new Point3D(-20.5, 55, 9.25);
        Point3D instance = new Point3D(75, -10, -100);
        double expResult = 159.0;
        double result = instance.distance(point);
        assertEquals("Worng distance", expResult, result, 0.01);
    }
 /************************************** Vector tests **************************************************************/  
	@Test
	public void Test06(){
              System.out.println("Test06: Vector Add test");
		
		Vector v1 = new Vector(1.0, 1.0, 1.0); 
		Vector v2 = new Vector(-1.0, -1.0, -1.0);
		
		Vector v3 = v1.add(v2);
		assertTrue(v3.compareTo(new Vector(0.0,0.0,0.0)) == 0);
		
		v3 = v2.add(v1);
		assertTrue(v3.compareTo(v3) == 0);
	}
	
	@Test
	public void Test07(){
              System.out.println("Test07: Vector Substruct test");
		
		Vector v1 = new Vector(1.0, 1.0, 1.0); 
		Vector v2 = new Vector(-1.0, -1.0, -1.0);
		
		Vector v3 = v1.subtract(v2);
		assertTrue(v3.compareTo(new Vector(2.0,2.0,2.0)) == 0);
		
		v3 = v2.subtract(v1);
		assertTrue(v3.compareTo(new Vector(-2.0,-2.0,-2.0)) == 0);
	}
	
	@Test
	public void Test08(){
              System.out.println("Test08: Vector Scaling test");

		Vector v1 = new Vector(1.0, 1.0, 1.0); 
		
		Vector v2 = v1.scale(1);
		assertTrue(v2.compareTo(v1) == 0);
		
		v2 = v1.scale(2);
		assertTrue(v2.compareTo(new Vector(2.0,2.0,2.0)) == 0);
		
		v2 = v1.scale(-2);
		assertTrue(v2.compareTo(new Vector(-2.0,-2.0,-2.0)) == 0);
	}
	
	@Test
	public void Test09(){
		System.out.println("Test09: Vector Dot product test");

            
		Vector v1 = new Vector(3.5,-5,10);		
		Vector v2 = new Vector(2.5,7,0.5);
		
		assertTrue(Double.compare(v1.dotProduct(v2), (8.75 + -35 + 5)) == 0);	
		
	}
	
	@Test
	public void Test10() {
              System.out.println("Test10: Vector Length test");

		Vector v = new Vector(3.5,-5,10);		
		assertTrue(v.length() ==  Math.sqrt(12.25 + 25 + 100));		
	}
	
	@Test
	public void Test11() throws Exception
	{
              System.out.printf("Test11: Vector Normalize test -> ");

		Vector v = new Vector(100,-60.781,0.0001);
              System.out.printf("Length = %f  ", v.length());
		
              Vector v1 = v.normalize();
              System.out.printf("Length = %f\n", v1.length());
              
		assertEquals("Incorrect length after normalize! ", 1, v1.length(), 1e-10);
		
		v = new Vector(0,0,0);
		
		try {
			v1 = v.normalize();
			fail("Didn't throw divide by zero exception!");
		} catch (ArithmeticException e) {
			assertTrue(true);			
		}
		
	}
	
	@Test
	public void Test12(){
              System.out.println("Test12: Vector Cross product test");
		
		Vector v1 = new Vector(3.5, -5.0, 10.0);		
		Vector v2 = new Vector(2.5, 7, 0.5);
		Vector v3 = v1.crossProduct(v2);
		
		assertEquals("", 0, v3.dotProduct(v2), 1e-10);
		assertEquals("", 0, v3.dotProduct(v1), 1e-10);
		
		Vector v4 = v2.crossProduct(v1);
		Vector v5 = v3.add(v4);
		assertEquals("", 0, v5.length(), 1e-10);
	}

	
	
	
//	@Test
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
		//now p2 is (3.0,4.0,5.0)
		p1.subtract((p2));
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

		// Test07: Vector Subtract test

		Vector v3 = new Vector(1.0, 1.0, 1.0);
		v3.subtract(new Vector(1.0, 1.0, 1.0));
		assertEquals("Vector via: (0.0,0.0,0.0)",v3.toString());

		// Test08: Vector Scaling test

		Vector v2 = new Vector(1.2,2.4,3.6);
		v2=v2.scale(5);
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
		try {
		v2.setHead(new Point3D(3.0,4.0,0.0));

		v2.normalize();
		}
		catch(Exception a)
		{

		}
		assertEquals("Vector via: (0.6,0.8,0.0)",v2.toString());

		// Test12: Vector Cross product test

		v2.setHead(new Point3D(1,2,2));
		v3.setHead(new Point3D(2, 3, 1));
		v1 = v2.crossProduct(v3);
		assertEquals("Vector via: (-4.0,3.0,-1.0)",v1.toString());

	}
}

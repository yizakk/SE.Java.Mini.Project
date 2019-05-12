package UnitTests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
//import geometries.*;
import primitives.*;
import org.junit.Test;

public class CameraTest {
	//**************************************Test13: Camera test************************************

	   @Test
	    public void Test13() throws Exception
	    {

	        final int WIDTH  = 11;
	        final int HEIGHT = 11;

	        Point3D[][] screen = new Point3D [HEIGHT][WIDTH];

	        Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0),
	                                     new Vector (0.0, 1.0, 0.0),
	                                     new Vector (0.0, 0.0, -1.0));

	        System.out.println("Test13: Camera test:\n" + camera);

	        for (int i = 0; i < HEIGHT; i++)
	        {
	             for (int j = 0; j < WIDTH; j++)
	             {

	                        Ray ray = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);

	                        screen[i][j] = ray.getPOO();

	                        System.out.print(screen[i][j]);
	                        System.out.println(ray.getDirection());

	                        // Checking z-coordinate
	                        assertTrue(Double.compare(screen[i][j].getZ().getCoordinate(), -1.0) == 0);

	                        // Checking all options
	                        double x = screen[i][j].getX().getCoordinate();
	                        double y = screen[i][j].getY().getCoordinate();
	                        double[] values = {0.0,3.0,6.0,9.0,12.0,15.0};
	                        
	                        int k = 0;
	                        for(; k < values.length; k++)
	                            if(values[k] == Math.abs(x))
	                                break;
	                        if(k == values.length)
	                                fail("Wrong x coordinate");
	                        
	                        k = 0;
	                        for(; k < values.length; k++)
	                            if(values[k] == Math.abs(y))
	                                break;
	                        if(k == values.length)
	                                fail("Wrong Y coordinate");
	                }
	                System.out.println("---");
	        }

	    }
	    
	   /************************************** Triangle test 
	 * @throws Exception ******************************************************/  
	    @Test
	    public void Test14() throws Exception
	    {
	            final int WIDTH  = 3;
	            final int HEIGHT = 3;
	          
	            System.out.println("Test14: Triangle intersection test");

	            Ray[][] rays = new Ray [HEIGHT][WIDTH];

	            Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0),
	                                       new Vector (0.0, 1.0, 0.0),
	                                       new Vector (0.0, 0.0, -1.0));

	            Triangle triangle1 = new Triangle(new Point3D( 0,  1, -2),
	                                             new Point3D( 1, -1, -2),
	                                             new Point3D(-1, -1, -2));

	            Triangle triangle2 = new Triangle(new Point3D( 0,  10, -2),
	                                              new Point3D( 1, -1, -2),
	                                              new Point3D(-1, -1, -2));

	            List<Point3D> intersectionPointsTriangle1 = new ArrayList<Point3D>();
	            List<Point3D> intersectionPointsTriangle2 = new ArrayList<Point3D>();

	            System.out.println("Camera:\n" + camera);

	            for (int i = 0; i < HEIGHT; i++){
	                    for (int j = 0; j < WIDTH; j++){

	                            rays[i][j] = camera.constructRayThroughPixel(
	                                            WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);

	                            List<Point3D> rayIntersectionPoints1  = triangle1.findIntersection(rays[i][j]);
	                            List<Point3D> rayIntersectionPoints2  = triangle2.findIntersection(rays[i][j]);

	                            for (Point3D iPoint: rayIntersectionPoints1)
	                                    intersectionPointsTriangle1.add(iPoint);

	                            for (Point3D iPoint: rayIntersectionPoints2)
	                                    intersectionPointsTriangle2.add(iPoint);
	                    }
	            }

	            assertTrue(intersectionPointsTriangle1. size() == 1);
	            assertTrue(intersectionPointsTriangle2.size() == 2);

	            System.out.println("Intersection Points:");
	            for (Point3D iPoint: intersectionPointsTriangle1){
	                    System.out.println(iPoint);
	                    assertEquals("Bad Intersection! ","(0.00, 0.00, -2.00)", iPoint.toString());
	            }
	            System.out.println("--");
	            for(int i = 0; i < intersectionPointsTriangle2.size(); i++)
	            {
	              Point3D iPoint = intersectionPointsTriangle2.get(i);
	              System.out.println(iPoint); 
	              switch(i)
	              {
	                  case 0:
	                    assertEquals("Bad Intersection! ","(0.00, 6.00, -2.00)", iPoint.toString());
	                    break;
	                  case 1:
	                    assertEquals("Bad Intersection! ","(0.00, 0.00, -2.00)", iPoint.toString());
	                    break;
	              }
	            }
	    }
	    
	    @Test
	    public void Test15() throws Exception
	    {
	        System.out.print("Test15: Triangle getNormal test: ");
	        Triangle triangle = new Triangle(new Point3D( 0,  1, -2),
	                                         new Point3D( 1, -1, -2),
	                                         new Point3D(-1, -1, -2));
	        Vector normal = triangle.getNormal(new Point3D());
	        System.out.println(normal);
	        assertEquals("Bad normal! ", normal.toString(),"(0.00, 0.00, 1.00)");
	    }
	    /************************************** Sphere test 
	     * @throws Exception ******************************************************/  
	    @Test
	    public void Test16() throws Exception{

	            final int WIDTH  = 3;
	            final int HEIGHT = 3;
	            
	            System.out.println("Test16: Sphere intersection test");

	            Ray[][] rays = new Ray [HEIGHT][WIDTH];

	            Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0),
	                                       new Vector (0.0, 1.0, 0.0),
	                                       new Vector (0.0, 0.0, -1.0));

	            Sphere sphere1 = new Sphere(1, new Point3D(0.0, 0.0, -3.0));
	            Sphere sphere2 = new Sphere(10, new Point3D(0.0, 0.0, -3.0));

	            // Only the center ray intersect the sphere in two locations
	            List<Point3D> intersectionPointsSphere1 = new ArrayList<Point3D>();

	            // The sphere encapsulates the view plane - all rays intersect with the sphere once
	            List<Point3D> intersectionPointsSphere2 = new ArrayList<Point3D>();

	            System.out.println("Camera:\n" + camera);

	            for (int i = 0; i < HEIGHT; i++){
	                    for (int j = 0; j < WIDTH; j++){

	                            rays[i][j] = camera.constructRayThroughPixel(
	                                            WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);

	                            List<Point3D> rayIntersectionPoints1 = sphere1.findIntersection(rays[i][j]);
	                            List<Point3D> rayIntersectionPoints2 = sphere2.findIntersection(rays[i][j]);

	                            for (Point3D iPoint: rayIntersectionPoints1)
	                                    intersectionPointsSphere1.add(iPoint);

	                            for (Point3D iPoint: rayIntersectionPoints2)
	                                    intersectionPointsSphere2.add(iPoint);

	                    }
	            }
	            System.out.println("Result = " +intersectionPointsSphere1.size());
	            assertTrue(intersectionPointsSphere1.size() == 2);
	            assertTrue(intersectionPointsSphere2.size() == 9);

	            System.out.println("Intersection Points:");
	            for (Point3D iPoint: intersectionPointsSphere1)
	            {

	                    assertTrue(iPoint.compareTo(new Point3D(0.0, 0.0, -2.0)) == 0 || 
	                               iPoint.compareTo(new Point3D(0.0, 0.0, -4.0)) == 0);
	                    System.out.println(iPoint);
	            }
	    }
	    @Test
	    public void Test17() throws Exception
	    {
	        System.out.print("Test17: Sphere getNormal test: ");
	        Sphere sphere = new Sphere(10, new Point3D(0.0, 0.0, -3.0));
	        Vector normal = sphere.getNormal(new Point3D(5.0,5.0,-3.0));
	        System.out.println(normal);
	        assertEquals("Bad normal! ", normal.toString(),"(0.71, 0.71, 0.00)");
	    }
	/************************************ Plane test 
	 * @throws Exception *****************************************************/
	    @Test
	    public void Test18() throws Exception
	    {

	            final int WIDTH  = 3;
	            final int HEIGHT = 3;
	            
	             System.out.println("Test18: Plane intersection test");

	            Ray[][] rays = new Ray [HEIGHT][WIDTH];

	            Camera camera = new Camera(new Point3D(0.0 ,0.0 ,0.0),
	                                       new Vector (0.0, 1.0, 0.0),
	                                       new Vector (0.0, 0.0, -1.0));

	            // plane orthogonal to the view plane
	            Plane plane1  = new Plane(new Vector(0.0, 0.0, -1.0), new Point3D(0.0, 0.0, -3.0));

	            // 45 degrees to the view plane
	            Plane plane2 = new Plane(new Vector(0.0, 0.25, -1.0), new Point3D(0.0, 0.0, -3.0));

	            List<Point3D> intersectionPointsPlane1 = new ArrayList<Point3D>();
	            List<Point3D> intersectionPointsPlane2 = new ArrayList<Point3D>();

	            System.out.println("Camera:\n" + camera);

	            for (int i = 0; i < HEIGHT; i++){
	                    for (int j = 0; j < WIDTH; j++){

	                            rays[i][j] = camera.constructRayThroughPixel(
	                                            WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);

	                            List<Point3D> rayIntersectionPoints1  = plane1.findIntersection(rays[i][j]);
	                            List<Point3D> rayIntersectionPoints2  = plane2.findIntersection(rays[i][j]);

	                            for (Point3D iPoint: rayIntersectionPoints1)
	                                    intersectionPointsPlane1.add(iPoint);

	                            for (Point3D iPoint: rayIntersectionPoints2)
	                                    intersectionPointsPlane2.add(iPoint);	
	                    }
	            }

	            assertTrue(intersectionPointsPlane1.size() == 9);
	            assertTrue(intersectionPointsPlane2.size() == 9);

	            System.out.println("Plane1 intersetions");
	            for (Point3D iPoint: intersectionPointsPlane1)
	                    System.out.println(iPoint);
	            System.out.println("---");
	            System.out.println("Plane2 intersetions");
	            for (Point3D iPoint: intersectionPointsPlane2)
	                    System.out.println(iPoint);
	    }
	
	
	
	//@Test
	public void testConstructRay1() throws Exception {
		Vector vup = new Vector(0, 1, 0);
		Vector vto = new Vector(0, 0, -1);

		Camera c = new Camera(vup, vto);
		Ray ray = c.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Point3D centerPoint = new Point3D(100,-100,-100);
		Vector direction = new Vector(-0.57735026918962577, -0.5773502691896257, -0.5773502691896257);

		Ray answer = new Ray(centerPoint, direction);

		assertEquals(answer, ray);
	}

}






package UnitTests;
import static org.junit.Assert.assertEquals;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class SphereTest {

	@Test
	public void test() throws Exception {
		/*Point3D p=new Point3D(80, 79, 38);
		Point3D p1=new Point3D(80, 76, 24);
		Sphere s1=new Sphere(p,100);
		Vector v= new Vector(s1.getNormal(p1));
		String str = v.toString();
		assertEquals("Vector [_head=Point3D [_x=Coordinate [_coordinate=0.0], _y=Coordinate [_coordinate=-0.20952908873087345]_z=Coordinate [_coordinate=-0.9778024140774094]]]",str);
		
	Vector answer = new Vector(0.7071067811865475, 0.7071067811865475, 0.0);
		
		Point3D p4 = new Point3D(0, 0, -400);
		Point3D p5 = new Point3D(1,1, -400);
		
		Sphere sphere = new Sphere(Color.white,p4,200);
			
		Vector vector = sphere.getNormal(p5);
		assertEquals(answer, vector);
		*/
		

		// creating the expected values
		List<Point3D> answerList1 = new ArrayList<Point3D>();
		List<Point3D> answerList2 = new ArrayList<Point3D>();
	
		Point3D answerPoint1 = new Point3D(0, 0, -200);
		Point3D answerPoint2 = new Point3D(0, 0, -600);
		
		answerList2.add(answerPoint1);
		answerList2.add(answerPoint2);
		
		
		// building the spheres
		
		Point3D p1 = new Point3D(0, 0, -400);
		Point3D p2 = new Point3D(p1);		
		Point3D centerPoint = new Point3D(0,0,0);
		
		Vector direction1 = new Vector(50, -50, -50);
		Vector direction2 = new Vector(0, 0, -5);
		Sphere sphere1 = new Sphere(Color.white,200, p1);
		Sphere sphere2 = new Sphere(Color.white,200, p2);
		
		// building the ray that will intersect the spheres
		
		Ray ray1 = new Ray(centerPoint, direction1);
		Ray ray2 = new Ray(centerPoint, direction2);
		
	  // testing the findIntersection functions
		List<Point3D> list1 = new ArrayList<Point3D>();
		list1 = sphere1.findIntersection(ray1);
		assertEquals(answerList1, list1);
		
		
		List<Point3D> list2 = new ArrayList<Point3D>();
		list2 = sphere2.findIntersection(ray2);
		assertEquals(answerList2, list2);
		
	}

}

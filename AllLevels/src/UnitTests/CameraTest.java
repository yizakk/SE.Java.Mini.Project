package UnitTests;
import static org.junit.Assert.*;
import elements.*;
//import geometries.*;
import primitives.*;
import org.junit.Test;

public class CameraTest {
	//**************************************Test13: Camera test************************************

	@Test
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






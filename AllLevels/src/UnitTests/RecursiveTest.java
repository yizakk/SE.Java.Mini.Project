package UnitTests;

import java.awt.Color;
import org.junit.Test;

import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class RecursiveTest
{
	@Test
	public void recursiveTest() throws Exception
       {
		Scene scene = new Scene();
		scene.setScreenDistance(300);
		
		Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
		sphere.setShininess(20);
		sphere.setEmmission(new Color(0, 0, 100));
        sphere.setKt(0.8);
		scene.addGeometry(sphere);
		
		Sphere sphere2 = new Sphere(250, new Point3D(0.0, 0.0, -1000));
		sphere2.setShininess(20);
		sphere2.setEmmission(new Color(100, 20, 20));
		sphere2.setKt(0);
		scene.addGeometry(sphere2);
		
		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150), 
					   new Vector(2, 2, -3), 0.1, 0.00001, 0.000005));
	
		ImageWriter imageWriter = new ImageWriter("Recursive Test 11", 500, 500, 500, 500);
		
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void recursiveTest2() throws Exception{
		
		Scene scene = new Scene();
		scene.setScreenDistance(250);
		
		Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000));
		sphere.setShininess(20);
		sphere.setEmmission(new Color(0, 0, 100));
		sphere.setKt(0.5);
		scene.addGeometry(sphere);
		
		Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000));
		sphere2.setShininess(20);
		sphere2.setEmmission(new Color(100, 20, 20));
		sphere2.setKt(0);
		scene.addGeometry(sphere2);
		
		// mirror triangle
		Triangle triangle = new Triangle(new Point3D(  1500, -1500, -1500),
				 						 new Point3D( -1500,  1500, -1500),
				 						 new Point3D(  200,  200, -375));
		// base triangle
		Triangle triangle2 = new Triangle(new Point3D(  1500, -1500, -1500),
										  new Point3D( -1500,  1500, -1500),
										  new Point3D( -1500, -1500, -1500));
		
		triangle.setEmmission(new Color(20, 20, 20));
		triangle2.setEmmission(new Color(20, 20, 20));
		triangle.setKr(1);
		triangle2.setKr(0.5);
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

		scene.addLight(new SpotLight(new Color(255, 200, 200),  new Point3D(50, 50, -50), 
				   new Vector(-2, -2, -3), 0, 0.00001, 0.000005));
	
		ImageWriter imageWriter = new ImageWriter("Recursive Test 2", 500, 500, 500, 500);
		
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
		
	}
	
	@Test
	public void recursiveTest3() throws Exception{
		
		Scene scene = new Scene();
		scene.setScreenDistance(300);
		
		Sphere sphere = new Sphere(300, new Point3D(0, 0, -1000));
		sphere.setShininess(20);
		sphere.setEmmission(new Color(0, 0, 100));
		sphere.setKt(0.5);
		scene.addGeometry(sphere);
		
		Sphere sphere2 = new Sphere(150, new Point3D(0, 0, -1000));
		sphere2.setShininess(20);
		sphere2.setEmmission(new Color(100, 20, 20));
		sphere2.setKt(0);
		scene.addGeometry(sphere2);
		
		Triangle triangle = new Triangle(new Point3D(  2000, -1000, -1500),
				 						 new Point3D( -1000,  2000, -1500),
				 						 new Point3D(  700,  700, -375));
		
		Triangle triangle2 = new Triangle(new Point3D(  2000, -1000, -1500),
										  new Point3D( -1000,  2000, -1500),
										  new Point3D( -1000, -1000, -1500));
		
		triangle.setEmmission(new Color(20, 20, 20));
		triangle2.setEmmission(new Color(20, 20, 20));
		triangle.setKr(1);
		triangle2.setKr(0.5);
		scene.addGeometry(triangle);
		scene.addGeometry(triangle2);

		scene.addLight(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150), 
				   new Vector(-2, -2, -3), 0, 0.00001, 0.000005));
	
		ImageWriter imageWriter = new ImageWriter("Recursive Test 3", 500, 500, 500, 500);
		
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
	}
}
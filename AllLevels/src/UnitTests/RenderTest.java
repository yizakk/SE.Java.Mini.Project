package UnitTests;

import java.awt.Color;

import org.junit.Test;

import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class RenderTest {
	
	@Test
	
		public void basicRendering() throws Exception{
			
			Scene scene = new Scene();
			scene.setScreenDistance(150);
			Sphere sphere = new Sphere(Color.MAGENTA,50,new Point3D(0.0, 0.0, -150));
			scene.addGeometry(sphere);
			
			Triangle triangle = new Triangle(Color.red,new Point3D( 100, 0, -149),
					 						 new Point3D(  0, 100, -149),
					 						 new Point3D( 100, 100, -149));
			
			Triangle triangle2 = new Triangle(Color.cyan,new Point3D( 100, 0, -149),
					 			 			  new Point3D(  0, -100, -149),
					 			 			  new Point3D( 100,-100, -149));
			
			Triangle triangle3 = new Triangle(Color.yellow,new Point3D(-100, 0, -149),
					 						  new Point3D(  0, 100, -149),
					 						  new Point3D(-100, 100, -149));
			
			Triangle triangle4 = new Triangle(Color.green,new Point3D(-100, 0, -149),
					 			 			  new Point3D(  0,  -100, -149),
					 			 			  new Point3D(-100, -100, -149));
     //		sphere.setEmmission(Color.green);
			scene.addGeometry(triangle);
			scene.addGeometry(triangle2);
			scene.addGeometry(triangle3);
			scene.addGeometry(triangle4);
			
			ImageWriter imageWriter = new ImageWriter("Render test", 500, 500, 500, 500);
			
			Render render = new Render(imageWriter, scene);
			render.renderImage();
			render.printGrid(50);
			render.writeToImage();
			//imageWriter.writeToimage();
		}

	}
package UnitTests;

import static org.junit.Assert.*;

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

public class Shadow {

	
	@Test
	public void shadowTest() throws Exception{
		
		Scene scene = new Scene();
		Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
		sphere.setShininess(20);
		sphere.setEmmission(new Color(0, 0, 100));
		
		scene.addGeometry(sphere);
		
		Triangle triangle = new Triangle(new Point3D(  3500,  3500, -2000),
				 						 new Point3D( -3500, -3500, -1000),
				 						 new Point3D(  3500, -3500, -2000));

		Triangle triangle2 = new Triangle(new Point3D(  3500,  3500, -2000),
				  						  new Point3D( -3500,  3500, -1000),
				  						  new Point3D( -3500, -3500, -1000));
		
		scene.addGeometry(triangle);
//		scene.addGeometry(triangle2);
		
		scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100), 
				   new Vector(-2, -2, -3), 0, 0.000001, 0.0000005));
		
		ImageWriter imageWriter = new ImageWriter("shadow test", 500, 500, 500, 500);
		
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
		
	}

}

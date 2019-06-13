package UnitTests;

import java.awt.Color;

//import javax.imageio.ImageWriter;

import org.junit.Test;

import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
//import geometries.Plane;
import geometries.Quadrangle;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;


public class Code1 {
	@Test
	public void test() throws Exception {

		Scene scene = new Scene();
     	scene.setScreenDistance(250);

//	**************************** Background  **********************************   	
     	
     	// working
     	Quadrangle q1 = new Quadrangle(new Point3D(-1500,-1500, -1500), // left-down corner
     								  new Point3D( 1500, -1500, -1500),//right-down corner 
     					 			  new Point3D(1300, 1200, 0), // right-up corner
     					 			  new Point3D( -1500, 1200, 00));// left-up corner
     	q1.setEmmission(new Color(20,20,20));
     	q1.setShininess(20);
     	q1.setKr(0.1);
     	q1.setKt(0.6);
//     	scene.addGeometry(q1);
     	
//**************************** Spheres  ********************************** //
     	
     	// mirror sphere - left down
     	Sphere s1 = new Sphere(50, new Point3D(-250,-350,-500));
     	s1.setEmmission(new Color (100,120,180));
     	s1.setShininess(20);
     	s1.setKr(1);
     	s1.setKt(0.1);
     	
     	// mirror sphere 2 - left up
     	Sphere s4 = new Sphere(50, new Point3D(150,-350,-600));
     	s4.setEmmission(new Color (100,120,180));
     	s4.setShininess(20);
     	s4.setKr(1);
     	s4.setKt(0.1);
     	
     	// mirror sphere 2 - left up
     	Sphere s41 = new Sphere(50, new Point3D(200,-200,-600));
     	s41.setEmmission(new Color (100,120,180));
     	s41.setShininess(20);
     	s41.setKr(1);
     	s41.setKt(0.1);
     
     	// main blue circle
		Sphere blueBig = new Sphere(140, new Point3D(-80, -80, -500));
		blueBig.setShininess(20);
		blueBig.setEmmission(new Color(0, 0, 100));
        blueBig.setKt(1);
		
        // Inner little sphere 
		Sphere purpleLittle = new Sphere(50, new Point3D(-70.0, -70.0, -550));
		purpleLittle.setShininess(10);
		purpleLittle.setEmmission(new Color(100, 20, 20));
		purpleLittle.setKt(0.3);

		
		scene.addGeometry(purpleLittle);
		scene.addGeometry(s1);
     	scene.addGeometry(s41);
     	scene.addGeometry(s4);
		scene.addGeometry(blueBig);
	
//*********************************** Lights  ********************************** //  
		
// Adding the light at the left down side of the picture
		LightSource spotLeftLeft = new SpotLight(new Color(255, 100, 100), 
												new Point3D(-200, -200, -500), 
												new Vector(3.5, -4, -3), 0.1, 
												0.00001, 0.000005);
		

//  left down side of the picture
		LightSource spotLeftDownCorner = new SpotLight(new Color(255, 0, 00), 
													   new Point3D(-1500, -1500, -700), 
													   new Vector(2 , 2, 1.5), 0.1, 
													   0.00001, 0.000005);
		
//  right down side of the picture
		LightSource spotRightDown = new SpotLight(new Color(50, 80, 100), 
												  new Point3D(1500, -1500, -700), 
												  new Vector(-1, 1.5, 1.5), 0.1, 
												  0.00001, 0.000005);

	//  right down side of the picture
		LightSource spotMidCenter = new SpotLight(new Color(50, 80, 100), 
												  new Point3D(-100, 500, -700), 
												  new Vector(1, -1.5, 1.5), 0.1, 
												  0.00001, 0.000005);
// down Point Light	
		LightSource pointLeftCenter = new PointLight(new Color(255, 100, 100),
													 new Point3D(-1000, 00,-700), 
													 0, 0.00001, 0.000005);

// down Point Light	
		LightSource pointLeftUP = new PointLight(new Color(255, 0, 00),
													 new Point3D(-1500, 1500,-1500), 
													 0, 0.00001, 0.000005);
		
		LightSource dir = new DirectionalLight(new Color(255,0,120), new Vector(1,1,1));
		
		
		scene.addLight(spotLeftLeft);
		scene.addLight(spotLeftDownCorner);
		//scene.addLight(spotRightDown);
		scene.addLight(pointLeftCenter);
		scene.addLight(dir);
		scene.addLight(spotMidCenter);
//		scene.addLight(pointLeftUP);
		
		
//************************************* Triangles  ********************************** //   		
  	
		// mirror triangle
		Triangle mirror1 = new Triangle(new Point3D(  1500, -1500, -1500),
				 						 new Point3D( -1500,  1500, -1500),
				 						 new Point3D(  200,  200, -375));
		mirror1.setEmmission(new Color(20, 20, 20));
		mirror1.setKr(1);
		mirror1.setKt(0.5);

		// mirror triangle 2
		Triangle mirror2 = new Triangle(new Point3D(  150, 150, -100),
										  new Point3D( 150,  -150, -100),
										  new Point3D(  200,  200, -375));
		mirror2.setEmmission(new Color(20, 20, 20));
		mirror2.setKr(1);
		mirror2.setKt(0.5);

		// mirror triangle 3
		Triangle mirror3 = new Triangle(new Point3D( 1500, -1500, -100),//right-down
										  new Point3D( -1500, 1500, -100),//left-up
										  new Point3D( -1500, -1500, 100));//left-down
		mirror3.setEmmission(new Color(60, 60, 60));
		mirror3.setKr(1);
		mirror3.setKt(0);
		
		// base triangle
		Triangle baseTriangle = new Triangle(new Point3D(  1500, -1500, -1500),
										  new Point3D( -1500,  1500, -1500),
										  new Point3D( -1500, -1500, -1500));
		baseTriangle.setEmmission(new Color(60, 60, 60));
		baseTriangle.setKr(0);

		scene.addGeometry(baseTriangle);
//		scene.addGeometry(mirror2);
//		scene.addGeometry(mirror3);
		scene.addGeometry(mirror1);

     	
//**************************** Rendering  ********************************** //     	
 
     	ImageWriter imageWriter = new ImageWriter("AddTest1", 500, 500, 500, 500);
		Render render = new Render(imageWriter,scene);
		
		render.renderImage();
//		render.printGrid(50);
		render.writeToImage();
     	
	}
}
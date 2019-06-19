package UnitTests;

import java.awt.Color;

//import javax.imageio.ImageWriter;

import org.junit.Test;

import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
//import geometries.Plane;
import geometries.Quadrangle;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;


public class QuadRefrctionMultipleTest {

	@Test
	public void HighResolution() throws Exception {
			
	Scene scene = new Scene();
	scene.setCamera(new Camera(new Point3D(0,0,0),
							   new Vector(0,1,0),
							   new Vector(0,0,1)));
 	scene.setScreenDistance(250);
//    	scene.setAmbientLight(new Color(255,255,255), 0);
//    	scene.setBackground(Color.WHITE);
//**************************** Background  **********************************   	//
 	
 	Quadrangle qFloor = new Quadrangle(new Point3D(-600,-200, 200), // left-down corner
 								   new Point3D( 600, -200, 200),//right-down corner 
 					 			   new Point3D( 200, -300, 500), // right-up corner
 					 			   new Point3D( -200, -300, 500));// left-up corner
 	qFloor.setEmmission(new Color(100,60,20));
 	qFloor.setShininess(80);
 	qFloor.setKr(1);
 	qFloor.setKt(0);

 	Quadrangle qGlass = new Quadrangle(new Point3D(-300,-600, 200), // left-down corner
			   						   new Point3D( 300, -600, 200),//right-down corner 
			   						   new Point3D( 200, -100, 450), // right-up-Opp corner
			   						   new Point3D( -200, -100, 450));// left-up-Opp corner
 	qGlass.setEmmission(new Color(40,40,40));
 	qGlass.setShininess(80);
 	qGlass.setKr(0.7);
 	qGlass.setKt(0.5);
 	qGlass.setKd(0);
 	qGlass.setKs(0);
 	
	
 	// ממול קיר wall
 	Quadrangle qOppWall=new Quadrangle(new Point3D( -200, 200, 500), // left-up corner
 									   new Point3D( -240, -300, 600),// Left-down-Far corner 
 									   new Point3D( 240,  -300, 600), // right-down-Far corner
 									   new Point3D(  200,  200, 500));// left-up corner
 	qOppWall.setEmmission(new Color(30,30,30));
 	qOppWall.setShininess(10);
 	qOppWall.setKr(1);
 	qOppWall.setKt(0.6);
 	
 	scene.addGeometry(qGlass);
 	scene.addGeometry(qFloor);
 	scene.addGeometry(qOppWall);
 	
//**************************** Spheres  ********************************** //
 	

 	// mirror sphere 2 - left up
 	Sphere rightRightBack = new Sphere(new Color (140,30,180), 60, new Point3D(130,-110,350));
 	rightRightBack.setShininess(10);
 	rightRightBack.setKr(0.2);
 	rightRightBack.setKt(0.2);


 	// main blue circle
	Sphere blueBig = new Sphere(new Color(0, 0, 150), 60, new Point3D(0, -110, 350));
	blueBig.setShininess(20);
	blueBig.setKr(0.8);
    blueBig.setKt(0.2);
	
    // Inner little sphere 
	Sphere leftLittleBlue = new Sphere(new Color(30, 60, 160), 60, new Point3D(-130, -110, 350));
	leftLittleBlue.setShininess(40);
	leftLittleBlue.setKr(0.2);
	leftLittleBlue.setKt(0.2);
	
	scene.addGeometry(leftLittleBlue);
	scene.addGeometry(blueBig);
 	scene.addGeometry(rightRightBack);

//*********************************** Lights  ********************************** //  

//down Point Light	
	LightSource pointCenterUp = new PointLight(new Color(55, 240, 150), // green-yellow
												 new Point3D(00, 600 , 450), 
												 0, 0.00001, 0.000005);

//down Point Light	
	LightSource pointLeftUP = new PointLight(new Color(60, 0, 00), // RED
												 new Point3D(-200, 200, 100), 
												 0, 0.00001, 0.000005);
	
	LightSource dir = new DirectionalLight(new Color(40,20,20), new Vector(1,1.5,1));
	
	SpotLight spotLeftMiddle = new SpotLight(new Color(90,20,20), new Point3D(-200,100,450), 
											 new Vector(1,-1,-1), 0, 0.00001, 0.000005);
	
	SpotLight spotRightBack = new SpotLight(new Color(40,40,20), new Point3D(200,100,250), 
											new Vector(-1,-1,1), 0, 0.00001, 0.000005);
		
	scene.addLight(spotRightBack);
	scene.addLight(spotLeftMiddle);
//	scene.addLight(pointLeftUP);
	scene.addLight(dir);
			
//************************************* Triangles  ********************************** //   		
	
	Triangle tGlass = new Triangle( new Point3D(-100,-100,75),
			new Point3D(100,-100,125),
			new Point3D(-100,-100,125),
			new Color(198,198,220),
			new Material(0.1,1,0.5,0.0,99));
// 	scene.addGeometry(tGlass);
	
	// base triangle
	Triangle tBetweenCameraAndGeometries = new Triangle(new Point3D(1000,  -1000, 50),
									  			      new Point3D( -1000, -1000, 50),
									  			      new Point3D( 1000, 1000, 50));
	tBetweenCameraAndGeometries.setEmmission(new Color(20, 20, 20));
	tBetweenCameraAndGeometries.setKd(0);
	tBetweenCameraAndGeometries.setKs(0);
	tBetweenCameraAndGeometries.setKr(0);
	tBetweenCameraAndGeometries.setKt(1);

//	scene.addGeometry(tBetweenCameraAndGeometries);
	
//*********************************** Not in use  ********************************** //       	
	
	Tube t = new Tube(new Material(19,0.3,0.3,0.5,0.5), new Color(120,120,120), 150, 
					  new Point3D(-100,0,200), new Point3D(100,0,200));
	
//	scene.addGeometry(t);
 	
//**************************** Rendering  ********************************** //     	
 	ImageWriter imageWriter = new ImageWriter("", 500, 500, 500, 500);
	Render render = new Render(imageWriter,scene);
	
//	render.multipleRaysOn = false;

	String FileName= "Spheres".concat(scene.boxing?"Boxing":"").
							   concat(render.multipleRaysOn?"MultipleRays":"")
							   /*.concat(Calendar.getInstance().getTime().toString())*/
									   ;
    render.setImageWriterName(FileName);
    
	render.renderImage();
	render.writeToImage(); 

	String path = render.getImageWriter().getRenderingDirectory();
	Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");
}
}
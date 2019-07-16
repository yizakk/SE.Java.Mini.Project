package UnitTests;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.junit.Test;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Quadrangle;
import geometries.Sphere;
import geometries.Triangle;
import primitives.MyColor;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;


public class MultipleRefractionRaysTest {
	/**
	 * The scene shows floor (the walls in right and left are disabled), and a mirror in the 
	 * front of it, with 3 spheres, 2 spots, one pointLight, and a triangle between the
	 * camera and other geometries, which differ the refracted multiple rays from normal 
	 * rays (because it makes you view the scene through a glass)
	 * to add accelerating - change the scene.boxing field to true
	 * @throws Exception
	 */

	@Test
	public void HighResolution() throws Exception {
		
		Scene scene = new Scene();
		scene.setCamera(new Camera(new Point3D(0,0,0),
								   new Vector(0,1,0),
								   new Vector(0,0,1)));
     	scene.setScreenDistance(250);
//	**************************** Background  **********************************   	//
     	
     	Quadrangle qFloor = new Quadrangle(new Point3D(-600,-200, 200), // left-down corner
     								   new Point3D( 600, -200, 200),//right-down corner 
     					 			   new Point3D( 200, -140, 500), // right-up corner
     					 			   new Point3D( -200, -140, 500));// left-up corner
     	qFloor.setEmmission(new MyColor(100,60,20));
     	qFloor.setShininess(80);
     	qFloor.setKr(1);
     	qFloor.setKt(0);

     	Quadrangle qGlass = new Quadrangle(new Point3D(-400,-200, 200), // left-down corner
				   						   new Point3D( 400, -200, 200),//right-down corner 
				   						   new Point3D( 200, -115, 320), // right-up-Opp corner
				   						   new Point3D( -200, -115, 320));// left-up-Opp corner
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
     	Sphere rightRightBack = new Sphere(new Color (140,30,180), 60, new Point3D(130,-100,350));
     	rightRightBack.setShininess(10);
     	rightRightBack.setKr(0.2);
     	rightRightBack.setKt(0.2);
    

     	// main blue circle
		Sphere blueBig = new Sphere(new Color(0, 0, 150), 60, new Point3D(0, -100, 350));
		blueBig.setShininess(20);
		blueBig.setKr(0.8);
        blueBig.setKt(0.2);
		
        // Inner little sphere 
		Sphere leftLittleBlue = new Sphere(new Color(30, 60, 160), 60, new Point3D(-130, -100, 350));
		leftLittleBlue.setShininess(40);
		leftLittleBlue.setKr(0.2);
		leftLittleBlue.setKt(0.2);
		
		scene.addGeometry(leftLittleBlue);
		scene.addGeometry(blueBig);
     	scene.addGeometry(rightRightBack);
	
//*********************************** Lights  ********************************** //  

		LightSource dir = new DirectionalLight(new Color(40,20,20), new Vector(1,1.5,1));
		
		SpotLight spotLeftMiddle = new SpotLight(new MyColor(90,20,20), new Point3D(-200,100,450), 
												 new Vector(1,-1,-1), 0, 0.00001, 0.000005);
		
		SpotLight spotRightBack = new SpotLight(new MyColor(40,40,20), new Point3D(200,100,250), 
												new Vector(-1,-1,1), 0, 0.00001, 0.000005);
		
		PointLight up = new PointLight(new MyColor(100,30,30), new Point3D(0,100,300), 0, 0.00001, 0.000005);
		scene.addLight(spotRightBack);
		scene.addLight(up);
//		scene.addLight(spotLeftMiddle);
		//scene.addLight(pointLeftUP);
//		scene.addLight(dir);
				
//************************************* Triangles  ********************************** //   		
		
		// base triangle
		Triangle tBetweenCameraAndGeometries = new Triangle(new Point3D(1000,  -1000, 50),
										  			      new Point3D( -1000, -1000, 50),
										  			      new Point3D( 1000, 1000, 50));
		tBetweenCameraAndGeometries.setEmmission(new Color(20, 20, 20));
		tBetweenCameraAndGeometries.setKd(0);
		tBetweenCameraAndGeometries.setKs(0);
		tBetweenCameraAndGeometries.setKr(0);
		tBetweenCameraAndGeometries.setKt(1);

//		scene.addGeometry(tBetweenCameraAndGeometries);
     	
//**************************** Rendering  ********************************** //     	
     	ImageWriter imageWriter = new ImageWriter("", 500, 500, 500, 500);
		Render render = new Render(imageWriter,scene);
		
//		render.multipleRefractionRaysOn = false;
//		render.multipleReflectionRaysOn = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Timestamp(0));
		String FileName= "Spheres".concat(scene.boxing?"Boxing":"").
								   concat(render.multipleRefractionRaysOn?"MultipleRays":"")
								   .concat(timeStamp)
								   /*.concat(Calendar.getInstance().getTime().toString())*/
										   ;
	    render.setImageWriterName(FileName);
	    
		render.renderImage();
		render.writeToImage(); 

		String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");
	}
	//************************************************** End Of Test *********************************** //	
}
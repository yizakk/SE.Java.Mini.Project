package UnitTests;

import java.awt.Color;
import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Quadrangle;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;


public class IzakTest {
	
//	@Test
	public void newTest() throws Exception {
	
		Scene scene = new Scene();
		scene.setCamera(new Camera(new Point3D(0,0,0),
								   new Vector(0,1,0),
								   new Vector(0,0,1)));
     	scene.setScreenDistance(250);

//	**************************** Background  **********************************   	//

//*****************************************         Planes        ********************************** //
     	
     	Plane p1 = new Plane(Color.yellow, new Vector (0.5,-1,1) , new Point3D(100,0,150));
     	
     	Plane p2 = new Plane(new Color(100,0,0), new Vector (0.5,1,1) , new Point3D(100,0,150));

     	scene.addGeometry(p1);
     	scene.addGeometry(p2);
     	

//**************************** Spheres  ********************************** //
     	
     	// mirror sphere 2 - left up
     	Sphere s4 = new Sphere(new Color (100,120,180),50, new Point3D(150,-350,-600));
     	s4.setShininess(20);
     	s4.setKr(1);
     	s4.setKt(0.1);
     	
     	// mirror sphere 2 - left up
     	Sphere rightRightBack = new Sphere(new Color (100,70,180), 30, new Point3D(120,-100,350));
     	rightRightBack.setShininess(10);
     	rightRightBack.setKr(1);
     	rightRightBack.setKt(0.8);
    
     	Sphere closeRightMirror = new Sphere(new Color (100,70,180), 30, new Point3D(80,00,150));
     	closeRightMirror.setShininess(20);
     	closeRightMirror.setKr(0.5);
     	closeRightMirror.setKt(0.7);

     	// main blue circle
		Sphere blueBig = new Sphere(new Color(0, 0, 150), 60, new Point3D(0, -100, 350));
		blueBig.setShininess(20);
        blueBig.setKt(0);
        blueBig.setKr(0.8);
		
        // Inner little sphere 
		Sphere leftLittleBlue = new Sphere(new Color(30, 60, 160), 30, new Point3D(-110, -100, 350));
		leftLittleBlue.setShininess(20);
		leftLittleBlue.setKt(0.3);

		
//		scene.addGeometry(leftLittleBlue);
//		scene.addGeometry(blueBig);
 //    	scene.addGeometry(rightRightBack);
//		scene.addGeometry(closeRightMirror);
//    	scene.addGeometry(s4);
	
//*********************************** Lights  ********************************** //  

// down Point Light	
		LightSource pointCenterUp = new PointLight(new Color(55, 240, 150), // green-yellow
													 new Point3D(00, 600 , 450), 
													 0, 0.00001, 0.000005);

// down Point Light	
		LightSource pointLeftUP = new PointLight(new Color(120, 0, 00), // RED
													 new Point3D(500, 200, 00), 
													 0, 0.00001, 0.000005);
		
		LightSource dir = new DirectionalLight(new Color(155,60,120), new Vector(1,1,1));
		
		SpotLight spotLeftMiddle = new SpotLight(new Color(120,80,40), new Point3D(-200,00,50), 
												 new Vector(1,1,1), 0, 0.00001, 0.000005);
		
		SpotLight spotRightBack = new SpotLight(new Color(80,80,40), new Point3D(200,00,50), 
												new Vector(-1,1,1), 0, 0.00001, 0.000005);
		
		
//		scene.addLight(pointCenterUp);
		scene.addLight(spotRightBack);
		scene.addLight(spotLeftMiddle);
//		scene.addLight(dir);
	
		
//************************************* Triangles  ********************************** //   		
  	
	
		// base triangle
		Triangle upPointLightBlockTriangle = new Triangle(new Point3D(-1000,  1000, 50),
										  			      new Point3D( -1000, -1000, 50),
										  			      new Point3D( 1000, 1000, 50));
		upPointLightBlockTriangle.setEmmission(new Color(6, 6, 6));
		upPointLightBlockTriangle.setKd(0);
		upPointLightBlockTriangle.setKs(0);
		upPointLightBlockTriangle.setKr(0);
		upPointLightBlockTriangle.setKt(1);

		scene.addGeometry(upPointLightBlockTriangle);
		
//*********************************** Not in use  ********************************** //       	

     	
//**************************** Rendering  ********************************** //     	
		String FileName = "DiffCameraTest2";
     	ImageWriter imageWriter = new ImageWriter(FileName, 500, 500, 500, 500);
		Render render = new Render(imageWriter,scene);
		
		render.multipleRaysOn = false;
		render.renderImage();
		render.writeToImage(); 

		String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");
	}	
		
	
//*****************************************************     End Of Point Light ******************** // 

	/**
	 * The scene shows floor (the walls in right and left are disabled), and a mirror in the 
	 * front of it, with 3 spheres, 2 spots, one pointLight, and a triangle between the
	 * camera and other geometries, which differ the refracted multiple rays from normal 
	 * rays (because it ,akes you view the scene through a glass)
	 * to add accelerating - change the scene.boxing field to true
	 * @throws Exception
	 */
	@Test
	public void HighResolutionWithTriangle() throws Exception {
		
		Scene scene = new Scene();
		scene.setCamera(new Camera(new Point3D(0,0,0),
								   new Vector(0,1,0),
								   new Vector(0,0,1)));
     	scene.setScreenDistance(400);

     	scene.boxing = true;
//	**************************** Background  **********************************   	//
     	
     	Quadrangle qFloor = new Quadrangle(new Point3D(-600,-600, 200), // left-down corner
     								   new Point3D( 600, -600, 200),//right-down corner 
     					 			   new Point3D( 200, -100, 500), // right-up corner
     					 			   new Point3D( -200, -100, 500));// left-up corner
     	qFloor.setEmmission(new Color(100,20,20));
     	qFloor.setShininess(40);
     	qFloor.setKr(0.2);
     	qFloor.setKt(0.2);
     	
     	Quadrangle qLeftWall=new Quadrangle(new Point3D( -600, 600, 200), // left-up corner
     									new Point3D( -600, -650, 200),//right-down corner 
     									new Point3D( -200,  -100, 500), // right-down-Opposite corner
     									new Point3D(  -200,  200, 500));// left-up-Opposite corner
     	qLeftWall.setEmmission(new Color(60,20,60));
     	qLeftWall.setShininess(10);
     	qLeftWall.setKr(1);
     	qLeftWall.setKt(0.2);
     	
     	Quadrangle qRightWall=new Quadrangle(new Point3D( 600, 600, 200), // left-up corner
     										 new Point3D( 600,  -600, 200),// left-up corner
     										 new Point3D(  200, -200, 500),//right-down corner 
     										 new Point3D(  200,  200, 500)); // right-up corner
     	qRightWall.setEmmission(new Color(60,20,60));
     	qRightWall.setShininess(10);
     	qRightWall.setKr(1);
     	qRightWall.setKt(0.2);
     	
     	// ממול קיר wall
     	Quadrangle qOppWall=new Quadrangle(new Point3D( -200, 200, 500), // left-up corner
     									   new Point3D( -240, -300, 600),// Left-down-Far corner 
     									   new Point3D( 240,  -300, 600), // right-down-Far corner
     									   new Point3D(  200,  200, 500));// left-up corner
     	qOppWall.setEmmission(new Color(20,20,20));
     	qOppWall.setShininess(10);
     	qOppWall.setKr(1);
     	qOppWall.setKt(0.6);

     	Quadrangle qRoof=new Quadrangle(new Point3D( -600, 200, 200), // left-up corner
     									new Point3D(  600,  200, 200),// left-up corner
     									new Point3D(  -200, 200, 500),//right-down-Opposite corner 
     									new Point3D(  200,  200, 500)); // right-up-Opposite corner
     	qRoof.setEmmission(new Color(60,20,60));
     	qRoof.setShininess(30);
     	qRoof.setKr(1);
     	qRoof.setKt(0.2);

     	
     	scene.addGeometry(qFloor);
     	scene.addGeometry(qOppWall);
//     	scene.addGeometry(qRoof);
//     	scene.addGeometry(qLeftWall);
//     	scene.addGeometry(qRightWall);
     	
//*****************************************         Planes        ********************************** //
     	
     	Plane p1 = new Plane(Color.WHITE, new Vector (0.2,0.2,-1) , new Point3D(0,0,450));
//     	scene.addGeometry(p1);
     	
//**************************** Spheres  ********************************** //
     	

     	// mirror sphere 2 - left up
     	Sphere rightRightBack = new Sphere(new Color (140,30,180), 60, new Point3D(140,-180,350));
     	rightRightBack.setShininess(10);
     	rightRightBack.setKr(1);
     	rightRightBack.setKt(0.5);
    

     	// main blue circle
		Sphere blueBig = new Sphere(new Color(0, 0, 150), 60, new Point3D(0, -180, 350));
		blueBig.setShininess(20);
		blueBig.setKr(1);
        blueBig.setKt(0.4);
		
        // Inner little sphere 
		Sphere leftLittleBlue = new Sphere(new Color(30, 60, 160), 60, new Point3D(-130, -160, 380));
		leftLittleBlue.setShininess(40);
		leftLittleBlue.setKr(1);
		leftLittleBlue.setKt(0.4);
		
		scene.addGeometry(leftLittleBlue);
		scene.addGeometry(blueBig);
     	scene.addGeometry(rightRightBack);
	
//*********************************** Lights  ********************************** //  

// down Point Light	
		LightSource pointCenterUp = new PointLight(new Color(55, 240, 150), // green-yellow
													 new Point3D(00, 600 , 450), 
													 0, 0.00001, 0.000005);

// down Point Light	
		LightSource pointLeftUP = new PointLight(new Color(120, 0, 00), // RED
													 new Point3D(-200, 200, 100), 
													 0, 0.00001, 0.000005);
		
		LightSource dir = new DirectionalLight(new Color(155,20,120), new Vector(1,1.5,1));
		
		SpotLight spotLeftMiddle = new SpotLight(new Color(120,80,40), new Point3D(-200,300,50), 
												 new Vector(1,-1,1.5), 0, 0.00001, 0.000005);
		
		SpotLight spotRightBack = new SpotLight(new Color(40,40,20), new Point3D(200,300,50), 
												new Vector(-1,-1,1), 0, 0.00001, 0.000005);
			
		scene.addLight(spotRightBack);
		scene.addLight(spotLeftMiddle);
		scene.addLight(pointLeftUP);
//		scene.addLight(dir);
				
//************************************* Triangles  ********************************** //   		
  	
		// base triangle
		Triangle tBetweenCameraAndGeometries = new Triangle(new Point3D(1000,  -1000, 50),
										  			      new Point3D( -1000, -1000, 50),
										  			      new Point3D( 1000, 1000, 50));
		tBetweenCameraAndGeometries.setEmmission(new Color(6, 6, 6));
		tBetweenCameraAndGeometries.setKd(0);
		tBetweenCameraAndGeometries.setKs(0);
		tBetweenCameraAndGeometries.setKr(0);
		tBetweenCameraAndGeometries.setKt(1);

		scene.addGeometry(tBetweenCameraAndGeometries);
		
//*********************************** Not in use  ********************************** //       	

     	
//**************************** Rendering  ********************************** //     	
		String FileName = "WithOutMultipleRays";
     	ImageWriter imageWriter = new ImageWriter(FileName, 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter,scene);
		
		render.multipleRaysOn = false;
		render.renderImage();
		render.writeToImage(); 

		String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");
	}
	//************************************************** End Of Test *********************************** //	

//@Test
	  public void bigImageTest() throws Exception {    
	    Scene scene = new Scene();
	    
//	    scene.boxing = true;

	    scene.setCamera((new Camera(new Point3D(-5000,0,0), new Vector(1,0,0), new Vector(0,1,0),1))); //NOTE: in this test, vTo is NOT the usual 0,0,-1. It is 1,0,0!!
	    scene.setScreenDistance(5300);
	    scene.addLight(new PointLight(new Color(200,200,200), new Point3D(-30,50,60), 1, 0.00005, 0.000003));
	    scene.addLight(new PointLight(new Color(00,00,200), new Point3D(80,80,120), 1, 0.00005, 0.000003));
	    scene.addLight(new SpotLight(new Color(200,00,000), new Point3D(30,0,-60), new Vector(2,0,1), 1, 0.00005, 0.00003));
	    
	    scene.addLight(new DirectionalLight(new Color(10, 100, 10), new Vector(-0.5,0,0)));
	    
	    Sphere s;
	    

	    // Remove this loop and all it's contents if you wish the rendering to take less than 30 minutes!!
	    for (double x = -50; x<=50; x+=5)
	    	for (double y = -(50-Math.abs(x)); y<=50-Math.abs(x); y+=5) {
	    		double z = Math.sqrt(2500 - x*x - y*y);
	    		s = new Sphere(5, new Point3D(x + 60, y, z),new Color((int)Math.abs(x+y+z)%25,
	    				(int)Math.abs(x+y+z+10)%25,
	    				(int)Math.abs(x+y+z+20)%25));
	    		s.setMaterial(new Material(1.0,0.1,0,0.6,99));
	    		//s.setShininess(99);
	    		scene.addGeometry(s);
	    		if (z != 0 ) {
	    			s = new Sphere(5, new Point3D(x + 60, y,-z),new Color((int)Math.abs(x+y+z)%25,
	    					(int)Math.abs(x+y+z+10)%25,
	    					(int)Math.abs(x+y+z+20)%25));
	    			s.setMaterial(new Material(1.0,0.1,0,0.6,99));
	    		//	s.setShininess(99);
	    			scene.addGeometry(s);
	    		}
	        
	      }

	    s = new Sphere(70, new Point3D(80, 0,120),new Color(0,0,0));
	    s.setMaterial(new Material(0.05,1,1,0,15));
	    scene.addGeometry(s);
	    
	    s = new Sphere(30, new Point3D(60,0,0),new Color(75,0,25));
	    s.setMaterial(new Material(0.2,1,1,0,15));
	    scene.addGeometry(s);
	    
	    s = new Sphere(800, new Point3D(60,-900,0),new Color(0,0,0));
	    s.setMaterial(new Material(0.1,1,1,0,15));
	    scene.addGeometry(s);
	    
	    s = new Sphere(800, new Point3D(60,900,0),new Color(0,0,0));
	    s.setMaterial(new Material(0.1,1,1,0,15));
	    scene.addGeometry(s);

	    Plane p = new Plane(new Point3D(250, -200, -150),
				  new Point3D(250,200,-150),
				  new Point3D(250, -200, 200),new Color(15,15,15), 
				  new Material(0.7,1,0.3,0.0,99));
	    scene.addGeometry(p);

	    Triangle t =new Triangle(new Point3D(-5000, -200, -70),
	    						 new Point3D(150, 200, -70),
	    						 new Point3D(150, -200, -70),
	    						 new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
	    scene.addGeometry(t);
	    
	    t= new Triangle(new Point3D(-5000,200,-70),
	    				new Point3D(150,200,-70),
	    				new Point3D(-5000,-200,-70),
	    				new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
	    scene.addGeometry(t);

	    scene.setAmbientLight(new AmbientLight(new Color(0,0,0), 0));
	    
  
	    String FileName= "RRRNoBoxing";
	    ImageWriter imageWriter = new ImageWriter(FileName, 500, 500, 500, 500);
	    Render render = new Render(imageWriter,scene);
	    render.multipleRaysOn = false;
	    render.renderImage();
	    render.writeToImage();
		String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");	  
	}
}
package UnitTests;

import static org.junit.Assert.*;

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
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;


public class BoxingTest {

//	@Test
	public void test() throws Exception {
		Triangle t = new Triangle() ;
		
		Scene scene = new Scene();
		scene.setCamera(new Camera(new Point3D(0,0,0),
								   new Vector(0,1,0),
								   new Vector(1,0,0)));
     	scene.setScreenDistance(400);
     	scene.boxing = true;

     	Quadrangle qFloor = new Quadrangle(new Point3D(200,-600, -600), // left-down corner
     			new Point3D( 200, -600, 600),//right-down corner 
     			new Point3D( 500, -100, 200), // right-up corner
     			new Point3D( 500, -100, -200));// left-up corner
     	qFloor.setEmmission(new Color(100,20,20));
     	qFloor.setShininess(40);
     	qFloor.setKr(0.2);
     	qFloor.setKt(0.2);

     	Quadrangle qOppWall=new Quadrangle(new Point3D( 500, 200, -200), // left-up corner
     			new Point3D( 600, -300, -240),// Left-down-Far corner 
     			new Point3D( 600,  -300, 240), // right-down-Far corner
     			new Point3D(  500,  200, 200));// left-up corner
     	qOppWall.setEmmission(new Color(20,20,20));
     	qOppWall.setShininess(10);
     	qOppWall.setKr(1);
     	qOppWall.setKt(0.6);

     	scene.addGeometry(qFloor);
     	scene.addGeometry(qOppWall);

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


     	SpotLight spotLeftMiddle = new SpotLight(new Color(120,80,40), new Point3D(50,300,-200), 
     			new Vector(1,-1,1.5), 0, 0.00001, 0.000005);

     	SpotLight spotRightBack = new SpotLight(new Color(40,40,20), new Point3D(200,300,50), 
     			new Vector(-1,-1,1), 0, 0.00001, 0.000005);
     	
     	LightSource pointLeftUP = new PointLight(new Color(120, 0, 00), // RED
     			new Point3D(-200, 200, 100), 
     			0, 0.00001, 0.000005);
     	
     	scene.addLight(spotRightBack);
     	scene.addLight(spotLeftMiddle);
     	scene.addLight(pointLeftUP);

		String FileName = "Checking triangles";
     	ImageWriter imageWriter = new ImageWriter(FileName, 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter,scene);
		
		scene.boxGeometries1();
		
		render.multipleRaysOn = false;
		
		render.renderImage();
		render.writeToImage(); 

		String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");
	}

	
	@Test
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
	    
	    scene.boxGeometries1();
	    ImageWriter imageWriter = new ImageWriter("", 500, 500, 500, 500);
//	    render.multipleRaysOn = false;
	    Render render = new Render(imageWriter,scene);
// The filename is changing by the mode of the "boxing" field in scene and "multipleRaysOn" in render
// if you don't have this fields - simply change fileName into a string "....."

	    String FileName= "RR".concat(scene.boxing?"Boxing":"") 
	    		.concat(render.multipleRaysOn?"MultipleRays":"");
	    render.setImageWriterName(FileName);
	    render.renderImage();
	    render.writeToImage();

/*  openning the file after the test finished. only works if you added in image writer a function
 *  
 */
	    String path = render.getImageWriter().getRenderingDirectory();
		Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");	  
	}


//	  @Test
	  public void bigImageTestOnlyBalls() throws Exception {    
		  Scene scene = new Scene();

		  scene.boxing = true;

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
		 // scene.addGeometry(s);

		  s = new Sphere(30, new Point3D(60,0,0),new Color(75,0,25));
		  s.setMaterial(new Material(0.2,1,1,0,15));
		  //scene.addGeometry(s);

		  s = new Sphere(800, new Point3D(60,-900,0),new Color(0,0,0));
		  s.setMaterial(new Material(0.1,1,1,0,15));
		  //scene.addGeometry(s);

		  s = new Sphere(800, new Point3D(60,900,0),new Color(0,0,0));
		  s.setMaterial(new Material(0.1,1,1,0,15));
		  //scene.addGeometry(s);

		  Plane p = new Plane(new Point3D(250, -200, -150),
				  new Point3D(250,200,-150),
				  new Point3D(250, -200, 200),new Color(15,15,15), 
				  new Material(0.7,1,0.3,0.0,99));
		  scene.addGeometry(p);

		  Triangle t =new Triangle(new Point3D(-5000, -200, -70),
				  new Point3D(150, 200, -70),
				  new Point3D(150, -200, -70),
				  new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
		  //scene.addGeometry(t);

		  t= new Triangle(new Point3D(-5000,200,-70),
				  new Point3D(150,200,-70),
				  new Point3D(-5000,-200,-70),
				  new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
		  //scene.addGeometry(t);

		  scene.setAmbientLight(new AmbientLight(new Color(0,0,0), 0));

		  scene.boxGeometries1();
		  String FileName= "RBoxingLessBigSpheres";
		  ImageWriter imageWriter = new ImageWriter(FileName, 500, 500, 500, 500);
		  Render render = new Render(imageWriter,scene);
		  render.multipleRaysOn = false;
		  render.renderImage();
		  render.writeToImage();
		  String path = render.getImageWriter().getRenderingDirectory();
		  Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");	  
	  }	



}

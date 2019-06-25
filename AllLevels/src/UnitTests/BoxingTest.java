package UnitTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
//	    scene.addGeometry(p);

	    Triangle t =new Triangle(new Point3D(-5000, -200, -70),
	    						 new Point3D(150, 200, -70),
	    						 new Point3D(150, -200, -70),
	    						 new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
	    scene.addGeometry(t);
	    
	    t = new Triangle(new Point3D(-5000,200,-70),
	    				new Point3D(150,200,-70),
	    				new Point3D(-5000,-200,-70),
	    				new Color(7,7,7), new Material(0.1,1,0.5,0.0,99));
	    scene.addGeometry(t);

	
	    /*
	     * this function takes all the boxes in the scene and connecting    
	     */
	    boolean boxesBoxed = false;
	    boxesBoxed = scene.boxCloseBoxes();
	    
	    ImageWriter imageWriter = new ImageWriter("", 500, 500, 500, 500);
	    Render render = new Render(imageWriter,scene);
	    
	    render.multipleRefractionRaysOn = false;

	    /*
	     * The filename is changing by the mode of the "boxing" field in scene and "multipleRaysOn" in render
	     * if you don't have this fields - simply change fileName into a string "....."
	     */
	    String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HHmm").format(Calendar.getInstance().getTime());
	    String FileName= "RR"+timeStamp.concat(scene.boxing?"Boxing":"") 
	    		.concat(render.multipleRefractionRaysOn?"MultipleRays":"").concat(boxesBoxed?"BoxesBoxed":"");
	    render.setImageWriterName(FileName);
	    
	    render.renderImage();
	    render.writeToImage();

	    /*  opening the file after the test finished. only works if you added in image writer a function
	     * returning the directory of which the file is writen to
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

		  scene.boxCloseBoxes();
		  String FileName= "RBoxingLessBigSpheres";
		  ImageWriter imageWriter = new ImageWriter(FileName, 500, 500, 500, 500);
		  Render render = new Render(imageWriter,scene);
		  render.multipleRefractionRaysOn = false;
		  render.renderImage();
		  render.writeToImage();
		  String path = render.getImageWriter().getRenderingDirectory();
		  Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileName +".jpg");	  
	  }	



}

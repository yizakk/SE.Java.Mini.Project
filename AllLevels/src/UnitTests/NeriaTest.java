package UnitTests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Quadrangle;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;


public class NeriaTest {
	@Test 
    public void testPart3_01() {
        Scene scene = new Scene();
        scene.getCamera().setP0(new Point3D(0, 0, 0));
        scene.setScreenDistance(150);

        Material material = new Material(20, 0.55, 0.1, 0.6,0.4);
        Point3D pSphere = new Point3D(-50, -100, -150);
        Sphere sphere = new Sphere(50, pSphere);
        sphere.setEmmission(new Color(10, 100, 20));
        sphere.setMaterial(material);
        scene.addGeometry(sphere);

        Point3D pSphere1 = new Point3D(-30, 0, -250);
        Sphere sphere1 = new Sphere(70,pSphere1);
        sphere1.setEmmission(new Color(110, 20, 10));
        sphere1.setMaterial(material);
        scene.addGeometry(sphere1);

        Point3D pSphere2 = new Point3D(-10, 150, -350);
        Sphere sphere2 = new Sphere(90,pSphere2 );
        sphere2.setEmmission(new Color(20, 20, 100));
        sphere2.setMaterial(material);
        scene.addGeometry(sphere2);


        Plane plane = new Plane(new Vector(1,0,0), new Point3D(-100, 0 , 0));
        plane.setMaterial(15, 0.1, 0.4, 0.2,1);
        plane.setEmmission(new Color(133, 133, 133));
        scene.addGeometry(plane);

        scene.addLight(new PointLight(new Color(130, 100, 130), new Point3D(150, 150, -50), 0, 0.00001, 0.000005));
        scene.addLight(new PointLight(new Color(110, 130, 30), new Point3D(150, 150, -50), 0, 0.00001, 0.000005));
//        scene.addLight(new PointLight(new Color(90, 30, 130), new Point3D(150, -150, -50), 0, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("testPart3_01", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    public void testPart3_02() {
        Scene scene = new Scene();
        scene.getCamera().setP0(new Point3D(0, 0, 0));
        scene.setScreenDistance(150);

        Material material = new Material(20, 0.35, 0.1, 0.6,0.4);

        Point3D pSphere = new Point3D(-50, -100, -150);
        Sphere sphere = new Sphere(50, pSphere);
        sphere.setEmmission(new Color(10, 100, 20));
        sphere.setMaterial(material);
        scene.addGeometry(sphere);

        Point3D pSphere1 = new Point3D(-30, 0, -250);
        Sphere sphere1 = new Sphere(70,pSphere1);
        sphere1.setEmmission(new Color(110, 20, 10));
        sphere1.setMaterial(material);
        scene.addGeometry(sphere1);

        Point3D pSphere2 = new Point3D(-10, 150, -350);
        Sphere sphere2 = new Sphere(90,pSphere2 );
        sphere2.setEmmission(new Color(20, 20, 100));
        sphere2.setMaterial(material);
        scene.addGeometry(sphere2);

        Plane plane = new Plane(new Vector(1,0,0), new Point3D(-100, 0 , 0));
        plane.setMaterial(15, 0.1, 0.4, 0.2,1);
        plane.setEmmission(new Color(133, 133, 133));
        scene.addGeometry(plane);

        scene.addLight(new SpotLight(new Color(130, 100, 130), new Point3D(150, 150, -50), //right light
                pSphere.vector(new Point3D(300, 0, -250)), 0, 0.00001, 0.000005));
        scene.addLight(new SpotLight(new Color(110, 130, 30), new Point3D(150, 150, -50), //right light
                pSphere1.vector(new Point3D(300, 0, -250)), 0, 0.00001, 0.00005));
        scene.addLight(new SpotLight(new Color(90, 30, 130), new Point3D(150, 150, -50), //right light
                pSphere2.vector(new Point3D(300, 0, -250)), 0, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("testPart3_02", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    public void testPart3_03() {
        Scene scene = new Scene();
        scene.getCamera().setP0(new Point3D(0, 0, 0));
        scene.setScreenDistance(200);

        Sphere sphere = new Sphere(80, new Point3D(0, 0, -250));
        sphere.setEmmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(new Point3D(0, 0, -450), //green triangle
                new Point3D(-2000, 0, -500),
                new Point3D(0, -4000, -500));
        triangle.setEmmission(new Color(0, 50, 0));
        scene.addGeometry(triangle);

        Triangle triangle1 = new Triangle(new Point3D(100, 100, -100), //red triangle
                new Point3D(90, 200, -90),
                new Point3D(-50, 100, -100));
        triangle1.setEmmission(new Color(80, 0, 0));
        scene.addGeometry(triangle1);

        Triangle triangle2 = new Triangle(new Point3D(-2000, -2000, -2000), //gray triangle
                new Point3D(-2000, 500, -2000),
                new Point3D(1500, 800, -800));
        triangle2.setEmmission(new Color(33, 33, 33));
        scene.addGeometry(triangle2);

        scene.addLight(new SpotLight(new Color(100, 80, 0), new Point3D(150, 150, -50), //right light
                new Point3D(0, 0, -100).vector(new Point3D(50, 0, 0)), 0, 0.000001, 0.0000005));

        Vector V = new Vector(new Point3D(-0.2, -0.6, -1)).normalize();
        scene.addLight(new SpotLight(new Color(220, 230, 60), new Point3D(0, 0, -350), //light behind the sphere
                V, 0, 0.00001, 0.00005));

        ImageWriter imageWriter = new ImageWriter("testPart3_03", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    
    public void testQuadrangle(){

        // regular table

        Scene scene = new Scene();
        scene.setScreenDistance(300);
        Camera cam = scene.getCamera();
        cam.setP0(new Point3D(0,0,0));
        scene.setCamera(cam);

        Plane plane = new Plane(new Vector(new Point3D(0,1,0)), new Point3D(0,-300,0));

        double tableLegRadius = 5;

        double tableThickness = 12;

        Point3D a = new Point3D(-125,50,-300);
        Point3D b = new Point3D(125,50,-300);
        Point3D c = new Point3D(125,50,-800);
        Point3D d = new Point3D(-125,50,-800);

        // A
        Vector u = a.vector(b).normalize();
        Vector v = a.vector(d).normalize();
        u = u.add(v);
        u = u.normalize();
        u = u.scale(tableLegRadius +25);
        Point3D A = new Point3D(a);
        A = A.add(u);


        // B
        u = b.vector(a).normalize();
        v = b.vector(c).normalize();
        u = u.add(v);
        u = u.normalize();
        u = u.scale(tableLegRadius +25);
        Point3D B = new Point3D(b);
        B = B.add(u);


        // C
        u = c.vector(d).normalize();
        v = c.vector(b).normalize();
        u = u.add(v);
        u = u.normalize();
        u = u.scale(tableLegRadius +25);
        Point3D C = new Point3D(c);
        C = C.add(u);


        // D
        u = d.vector(c).normalize();
        v = d.vector(a).normalize();
        u = u.add(v);
        u = u.normalize();
        u = u.scale(tableLegRadius +25);
        Point3D D = new Point3D(d);
        D = D.add(u);

        Point3D A1 = new Point3D(A.getX().getCoordinate(),A.getY().getCoordinate()+tableThickness,A.getZ().getCoordinate());
        Point3D B1 = new Point3D(B.getX().getCoordinate(),B.getY().getCoordinate()+tableThickness,B.getZ().getCoordinate());
        Point3D C1 = new Point3D(C.getX().getCoordinate(),C.getY().getCoordinate()+tableThickness,C.getZ().getCoordinate());
        Point3D D1 = new Point3D(D.getX().getCoordinate(),D.getY().getCoordinate()+tableThickness,D.getZ().getCoordinate());



        Quadrangle q1 = new Quadrangle(A,B,C,D);
        Quadrangle q2 = new Quadrangle(A1,B1,C1,D1);
        Quadrangle q3 = new Quadrangle(A,B,B1,A1);
        Quadrangle q4 = new Quadrangle(B,C,C1,B1);
        Quadrangle q5 = new Quadrangle(C,D,D1,C1);
        Quadrangle q6 = new Quadrangle(D,A,A1,D1);
        Sphere sphere = new Sphere(100, new Point3D(600,800,-750));
        sphere.setEmmission(new Color(169, 164, 166));


        plane.setEmmission(new Color(0,60,110));
        q1.setEmmission(new Color(3, 120, 0));
        q2.setEmmission(new Color(3, 120, 0));
        q3.setEmmission(new Color(3, 120, 0));
        q4.setEmmission(new Color(3, 120, 0));
        q5.setEmmission(new Color(3, 120, 0));
        q6.setEmmission(new Color(3, 120, 0));

        scene.addGeometry(plane);
        scene.addGeometry(sphere);
        scene.addGeometry(q1);
        scene.addGeometry(q2);
        scene.addGeometry(q3);
        scene.addGeometry(q4);
        scene.addGeometry(q5);
        scene.addGeometry(q6);

        scene.addLight(new SpotLight(new Color(200, 200, 255),  new Point3D(300, 650, -50),new Vector(new Point3D(-2, -2, -3)),
                0, 0.00001, 0.000005));
        scene.addLight(new SpotLight(new Color(200, 200, 80),  new Point3D(0,0,0),new Vector(new Point3D(600, 800,-750)),
                0, 0.00001, 0.00005));


        ImageWriter imageWriter = new ImageWriter("Test Quadrangle", 1000, 1000, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

}
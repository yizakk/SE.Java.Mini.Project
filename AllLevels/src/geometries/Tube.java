package geometries;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
//import Tools.Calcs;

/**
 * This class represents a finite cylinder.
 * The cylinder has a length value that is represented by
 * (p2 - p1) vector.
 * This vector is one of the fields in this class.
 */
public class Tube extends Cylinder{


    //NOTE THAT IN CALCULATION WE TREAT P1 AS THE `STARTING POINT`
    //OF THE CYLINDER

    private Point3D _p1; //The point from where the cylinder starts.
    private Point3D _p2; //The point from where the cylinder ENDS.
    private Vector _direction;
//*************************CONSTRUCTORS***********************//


    public Tube(Material material,Color emission,double radius, Point3D p1, Point3D p2) throws Exception {
       
    	super(material,emission, radius, new Vector(new Point3D(1,0,0)), p1);

        _direction = new Vector(p2,p1).normalize();
//        _direction.substract(new Vector(p1));
//        _direction = _direction.normalization();

        if (_direction.length() == 0)
            throw new IllegalArgumentException("direction vector cannot be (0,0,0)");
        _p1 = new Point3D(p1);
        _p2 = new Point3D(p2);
    }

    /**
     * A copy constructor
     * @param other The object being copied
     */
    public Tube(Tube other) {
        super(other);
        _p1 = new Point3D(other._p1);
        _p2 = new Point3D(other._p2);
        _direction = new Vector(other._direction);
    }

//***************************GETTERS**************************//

    public Vector getDirection() { return new Vector(_direction); }
    public Point3D getP1() { return new Point3D(_p1); }
    public Point3D getP2() { return new Point3D(_p2); }

//**************************OPERATIONS************************//

    /**
     * Inner function to check if a given point is on this cylinder.
     * @param point The point that the function checks
     * @return True if the point on this cylinder and false otherwise.
     */
    private boolean _pointOn(Point3D point) {
        //The vector from p1 (start point) to the given point.
        Vector vector1 = new Vector(_p1,point);

        //The vector from p2 (end point) to the given point.
        Vector vector2 = new Vector(_p2,point);
        if (vector1.dotProduct(_direction) > 0 &&
        		vector2.dotProduct(_direction) < 0) {
        	return true;
        }

        //Checks if the point is on one of the edges of the cylinder
        Vector vector3 = new Vector(_p1,point);
        Vector vector4 = new Vector(_p2,point);
        double v3_dot_dir = vector3.dotProduct(_direction),
                v4_dot_dir = vector4.dotProduct(_direction);

        if (Util.isZero(v3_dot_dir)) {
            if (Util.usubtract(getRadius(), vector3.length()) >= 0)
                return true;
        }

        if (Util.isZero(v4_dot_dir)) {
            if (Util.usubtract(getRadius(), vector4.length()) >= 0)
                return true;
        }

        return false;
    }

    /**
     * Function to get the normal to this cylinder in
     * a given point that is on the cylinder
     * @throws Exception 
     */
    @Override
    public Vector getNormal(Point3D point) throws Exception {
        if (!_pointOn(point))
            return null;
        
        //We need the projection to multiply the _direction unit vector
        double projection =  new Vector(point,this._Point).dotProduct(this._Direction);

        Vector vector2 = _direction.scale(projection);
        //vector2.scale(projection);

        //This vector is orthogonal to the _direction vector.
        return new Vector(_Point,vector2.getHead());
        
       // return super.getNormal(point);
    }
    
    

    /**
     * Function that finds intersections with this final cylinder.
     * Note that unlike the Cylinder, here there are point that is not on the
     * cylinder whereas they on the Cylinder
     * @throws Exception 
     */
    @Override
    public ArrayList<Point3D> findIntersections(Ray ray) {

        ArrayList<Point3D> temp = super.findIntersections(ray);
        ArrayList<Point3D> intersections = new ArrayList<Point3D>();

        Plane planeP1 = new Plane(this._Direction, _p1);
        Plane planeP2 = new Plane(this._Direction, _p2);

        List<Point3D> temp1 = null;
        List<Point3D> temp2 = null;
        try {
        			temp1 = planeP1.findIntersections(ray);
        			temp2 = planeP2.findIntersections(ray);
        }
        catch(Exception ignores)
        {        }
        
        if(!(temp1==null) && !(temp2==null))
        {
        	for (Point3D point1 : temp1) {
        		if (point1.distance(_p1) <= getRadius())
        			intersections.add(point1);
        	}

        	for (Point3D point2 : temp2) {
        		if (point2.distance(_p2) <= getRadius())
        			intersections.add(point2);
        	}


        	for (Point3D p : temp) {
        		if (_pointOn(p))
        			intersections.add(p);
        	}
        }
        return intersections;
    }

    public Geometry Clone() {
        return new Tube(this);
    }

    public  void setShininess(int n){
//        Material m = getMaterial();
          this.setShininess(n);
//        set_material(m);
    }
}
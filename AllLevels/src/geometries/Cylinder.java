package geometries;
import java.awt.Color;
import java.util.ArrayList;

import primitives.Material;
import primitives.MyColor;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
public class Cylinder extends RadialGeometry {

	Point3D _Point;
	Vector _Direction;

	// ***************** Constructors ********************** //
	//default constructor
	public Cylinder() {
		super();
		this._Point = new Point3D();
		this._Direction = new Vector();
	}
	public Cylinder(Point3D axisPoint, Vector axisDirection) {
		super();
		this._Point = new Point3D(axisPoint);
		this._Direction = new Vector(axisDirection);
	}
	public Cylinder(MyColor c, double r,Point3D axisPoint, Vector axisDirection) {
		super(c,r);
		this._Point = new Point3D(axisPoint);
		this._Direction = new Vector(axisDirection);
	}
	/**
	 * copy constructor
	 * @param cylinder
	 */
	public Cylinder(Cylinder cylinder) {
		super();
		this._Point = cylinder._Point;
		this._Direction = cylinder._Direction;
	}
	public Cylinder(Material material, MyColor emission, double radius, Vector vector, Point3D p1) {
		super(material,emission, radius);
		_Point = new Point3D (p1);
		_Direction = new Vector(vector);
		
	}

	public Point3D getPoint() { return new Point3D(_Point);	}
	public void setPoint(Point3D axisPoint) { this._Point = new Point3D(axisPoint); }
	public Vector getDirection() { return new Vector(_Direction); }
	public void setDirection(Vector axisDirection) { this._Direction = new Vector(axisDirection); }

	/**********************Equals******************************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cylinder other = (Cylinder) obj;
		if (_Direction == null) {
			if (other._Direction != null)
				return false;
		} else if (!_Direction.equals(other._Direction))
			return false;
		if (_Point == null) {
			if (other._Point != null)
				return false;
		} else if (!_Point.equals(other._Point))
			return false;
		return true;
	}
	
	
	/**************GetNormal
	 * @throws Exception **********************/
	   @Override
	    public Vector getNormal(Point3D point) throws Exception {
	        //The vector from the point of the cylinder to given point
//	        Vector vector1 = new Vector(getPoint());

	        //We need the projection to multiply the _direction unit vector
	        double projection =  new Vector(point,this._Point).dotProduct(this._Direction);

	        Vector vector2 = getDirection().scale(projection);
	        //vector2.scale(projection);

	        //This vector is orthogonal to the _direction vector.
	        return new Vector(_Point,vector2.getHead());

	        //check.substract(vector2);
//	        return check.normalize();
	    }


	    @Override
	    public ArrayList<Point3D> findIntersections(Ray ray) {
	        ArrayList<Point3D> intersections = new ArrayList<Point3D>();

	        Point3D rayP0 = ray.getPOO();

	        Vector rayDirection = ray.getDirection(),
	                cylinderDirection = getDirection(),
	                DeltaP = rayP0.vector(getPoint()),
	                temp_for_use1, temp_for_use2, temp1,temp2;

	        double V_dot_Va = rayDirection.dotProduct(cylinderDirection),
	                DeltaP_dot_Va = DeltaP.dotProduct(cylinderDirection);

	        temp1 = cylinderDirection.scale(V_dot_Va);
	        temp_for_use1 = rayDirection.subtract(temp1);
	        //temp1.scale(V_dot_Va);
	        //temp_for_use1 = temp_for_use1.subtract(temp1);

	        //temp_for_use2 = DeltaP.subtract(Va.scale(DeltaP_dot_Va));
	        temp_for_use2 = DeltaP.subtract(cylinderDirection).scale(DeltaP_dot_Va);
	        //temp1 = new Vector(cylinderDirection);
	        //temp1.scale(DeltaP_dot_Va);
	        //temp_for_use2.subtract(temp1);

	        double A = temp_for_use1.dotProduct(temp_for_use1);
	        
	        temp1 = rayDirection.subtract(cylinderDirection).scale(V_dot_Va);
	        //temp2 = new Vector(cylinderDirection);
	        //temp2.scale(V_dot_Va);
	        //temp1.subtract(temp2);
	        temp2 = DeltaP.subtract(cylinderDirection).scale(DeltaP_dot_Va);
	        //temp3 = new Vector(cylinderDirection);
//	        temp3.scale(DeltaP_dot_Va);
//	        temp2.subtract(temp3);
	        double B = 2*temp1.dotProduct(temp2);

	        double C = temp_for_use2.dotProduct(temp_for_use2) - getRadius() * getRadius();
	        
	        
	        double desc = Util.usubtract(Util.uscale(B, B),Util.uscale(4*C,A)); /*Calcs.subtract(B*B, 4*A*C); */

	        if (desc < 0) {//No solution
	            return intersections;
	        }

	        double t1 = (-B+Math.sqrt(desc))/(2*A),
	                t2 = (-B-Math.sqrt(desc))/(2*A);

	        if (desc == 0) {//One solution
	            if (-B/(2*A) < 0)
	                return intersections;
	            temp1 = rayDirection.scale(-B/(2*A));
//	            temp1.scale(-B/(2*A));
	            Point3D tempP = rayP0.add(temp1);
//	            tempP.add(temp1.getHead());
	            //toReturn.add(new Vector(P.add(V.scale(-B/(2*A)).getHead())).getHead());
	            intersections.add(rayP0);
	            return intersections;
	        }
	        else if (t1 < 0 && t2 < 0){
	            return intersections;
	        }
	        else if (t1 < 0 && t2 > 0) {
	            //toReturn.add(new Vector(P.add(V.scale(t2).getHead())).getHead());

	            temp1 = rayDirection.scale(t2);
//	            temp1.scale(t2);
	            Point3D tempP =rayP0.add(temp1);
//	            tempP.add(temp1.getHead());
	            intersections.add(tempP);
	            return intersections;
	        }
	        else if (t1 > 0 && t2 < 0) {
	            //toReturn.add(new Vector(P.add(V.scale(t1).getHead())).getHead());

	        	temp1 = rayDirection.scale(t1);
//	            temp1.scale(t1);
	            Point3D tempP = rayP0.add(temp1);
//	            tempP.add(temp1.getHead());
	            intersections.add(tempP);
	            return intersections;
	        }
	        else {
	            //toReturn.add(new Vector(P.add(V.scale(t1).getHead())).getHead());

	            temp1 = rayDirection.scale(t1);
//	            temp1.scale(t1);
	            Point3D tempP = rayP0.add(temp1);
//	            tempP.add(temp1.getHead());
	            intersections.add(tempP);

	            //toReturn.add(new Vector(P.add(V.scale(t2).getHead())).getHead());

	            temp1 = rayDirection.scale(t2);
//	            temp1.scale(t2);
	            Point3D tempP2 = rayP0.add(temp1);
//	            tempP2.add(temp1.getHead());
	            intersections.add(tempP2);

	            return intersections;
	        }
	    }


/***************toString - print****************/
	@Override
	public String toString() {
		return "Cylinder [_axisPoint=" + _Point + ", _axisDirection=" + _Direction + "]";
	}
}
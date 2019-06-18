package geometries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Box extends Geometry {

	Point3D location; // closest left lower corner
	double height, width, depth;
	List<Geometry> geometries = new ArrayList<>();
	
//************************************************ C-tor *************************************//	
	
	
	public Box (Point3D Locat , double _height, double _width , double _depth)
	{
		this.location = new Point3D(Locat);
		height =_height;
		width = _width;
		depth = _depth;
		
	//	this.setKt(0);
	}
//************************************************ Get/Set **********************************//	
	
	public Point3D getLocation() { return new Point3D(this.location);}
	public double getHeight() { return height ;}
	public double getWidth() { return width ;}
	public double getDepth() { return depth ;}
	public void addGeometry(Geometry geo) {
		geometries.add(geo);
	}
	
	@Override
	public Vector getNormal(Point3D point) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Point3D> findIntersections(Ray r) throws Exception {

		List<Point3D> pts = new ArrayList<Point3D>();
		//frontal hit
		double rayXPoint =  r.getPOO().getX().getCoordinate();
		double rayYPoint =  r.getPOO().getY().getCoordinate();
		double rayZPoint =  r.getPOO().getZ().getCoordinate();
		
		double locationY = location.getY().getCoordinate();
		double locationX = location.getX().getCoordinate();
		double locationZ = location.getZ().getCoordinate();
		
		double rayDirectionX = r.getDirection().getHead().getX().getCoordinate();
		double rayDirectionY = r.getDirection().getHead().getY().getCoordinate();
		double rayDirectionZ = r.getDirection().getHead().getZ().getCoordinate();
		
		
		double t = (locationX - rayXPoint)/ rayDirectionX;
		Vector rayVector = r.getDirection().scale(t);
		
		double scaledRayY = rayVector.getHead().getY().getCoordinate();
		double scaledRayX = rayVector.getHead().getX().getCoordinate();
		double scaledRayZ = rayVector.getHead().getZ().getCoordinate();
				
		// match to refael test ( otherwise- switch x with z everywhere (depth and width)
		
		
		if (locationY < (rayYPoint + scaledRayY ) &&
			(locationY + height) > (rayYPoint +scaledRayY ) &&
			locationZ < rayZPoint + scaledRayZ &&
			locationZ + width > rayZPoint +scaledRayZ)
		{
			pts.add(new Point3D(rayXPoint + scaledRayX,
								rayYPoint + scaledRayY,
								rayZPoint + scaledRayZ));
			return pts;
		}
		
		
		// downward hit
		t = (locationY - rayYPoint)/ rayDirectionY;  
		rayVector = r.getDirection().scale(t);
		scaledRayY = rayVector.getHead().getY().getCoordinate();
		scaledRayX = rayVector.getHead().getX().getCoordinate();
		scaledRayZ = rayVector.getHead().getZ().getCoordinate();
		if (locationX < rayXPoint + scaledRayX &&
				locationX + depth > rayXPoint + scaledRayX &&
				locationZ < rayZPoint + scaledRayZ &&
				locationZ + width > rayZPoint + scaledRayZ) {
			pts.add(new Point3D(rayXPoint + scaledRayX,
								rayYPoint + scaledRayY,
								rayZPoint + scaledRayZ));
			//mpts.put(this, pts);
			return pts;
		}
		
		
		//upward hit
		t = (locationY + height - rayYPoint)/ rayDirectionY;
		rayVector = r.getDirection().scale(t);
		scaledRayY = rayVector.getHead().getY().getCoordinate();
		scaledRayX = rayVector.getHead().getX().getCoordinate();
		scaledRayZ = rayVector.getHead().getZ().getCoordinate();
		
		if (locationX < rayXPoint + scaledRayX &&
				locationX + depth > rayXPoint + scaledRayX &&
				locationZ < rayZPoint + scaledRayZ &&
				locationZ + width > rayZPoint + scaledRayZ) {
			pts.add(new Point3D(rayXPoint + scaledRayX,
								rayYPoint + scaledRayY,
								rayZPoint + scaledRayZ));
			return pts;
		}
		//left hit
		t = (locationZ - rayZPoint)/ rayDirectionZ;
		rayVector = r.getDirection().scale(t);
		scaledRayY = rayVector.getHead().getY().getCoordinate();
		scaledRayX = rayVector.getHead().getX().getCoordinate();
		scaledRayZ = rayVector.getHead().getZ().getCoordinate();
		if (locationX < rayXPoint + scaledRayX &&
				locationX + depth > rayXPoint + scaledRayX &&
				locationY < rayYPoint + scaledRayY &&
				locationY + height > rayYPoint + scaledRayY) {
			pts.add(new Point3D(rayXPoint + scaledRayX,
								rayYPoint + scaledRayY,
								rayZPoint + scaledRayZ));
			return pts;
		}
		
		
		//right hit
		t = (locationZ + width - rayZPoint)/ rayDirectionZ;
		rayVector = r.getDirection().scale(t);
		scaledRayY = rayVector.getHead().getY().getCoordinate();
		scaledRayX = rayVector.getHead().getX().getCoordinate();
		scaledRayZ = rayVector.getHead().getZ().getCoordinate();
		if (locationX < rayXPoint + scaledRayX &&
				locationX + depth > rayXPoint + scaledRayX &&
				locationY < rayYPoint + scaledRayY &&
				locationY + height > rayYPoint + scaledRayY) {
			pts.add(new Point3D(rayXPoint + scaledRayX,
								rayYPoint + scaledRayY,
								rayZPoint + scaledRayZ));
			return pts;
		}

		return pts;
	}
	
	
	public Map<Geometry, List<Point3D>> getInBoxRayIntersections(Ray ray) throws Exception
	{
		Iterator<Geometry> geos = geometries.iterator();
		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		
		while (geos.hasNext()){
			Geometry geometry = geos.next();
			if(geometry instanceof Box) // if we meet another box- open it
			{
				if(!((Box)geometry).findIntersections(ray).isEmpty()) {
					Map<Geometry,List<Point3D>> /*List<Point3D>*/ geometryIntersectionPoints = ((Box)geometry).getInBoxRayIntersections(ray);
					if(!(geometryIntersectionPoints.isEmpty()))
						intersectionPoints.putAll(geometryIntersectionPoints);
				}
				else
				{
					
				}
			}
			else // find the intersections (in the last call for recursion, or geo is a plane)
			{
				ArrayList<Point3D> geometryIntersectionPoints = (ArrayList<Point3D>) geometry.findIntersections(ray);
				if(!(geometryIntersectionPoints.isEmpty()))
					intersectionPoints.put(geometry, geometryIntersectionPoints);
			}
			
		}
		return intersectionPoints;
	}

	@Override
	public String toString()
	{
		return this.location.toString() + "\nd:"+this.depth +" height:"+height+" width"+width;
	}

}
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
		
	}
//************************************************ Get/Set **********************************//	
	
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
		double t = (location.getX().getCoordinate() - r.getPOO().getX().getCoordinate())/ r.getDirection().getHead().getX().getCoordinate();
		Vector rayVector = r.getDirection().scale(t);

		// match to refael test ( otherwise- switch x with z everywhere)
		
		if (location.getY().getCoordinate() < r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate() &&
			location.getY().getCoordinate() + height > r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate() &&
			location.getZ().getCoordinate() < r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate() &&
			location.getZ().getCoordinate() + width > r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())
		{
			pts.add(new Point3D(new Coordinate(r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate()),
								new Coordinate(r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()),
								new Coordinate(r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())));
			//mpts.put(this, pts);
			//return pts;
		}
		// downward hit
		t = (location.getY().getCoordinate() - r.getPOO().getY().getCoordinate())/ r.getDirection().getHead().getY().getCoordinate();  
		rayVector = r.getDirection().scale(t);
		if (location.getX().getCoordinate() < r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getX().getCoordinate() + depth > r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getZ().getCoordinate() < r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate() &&
				location.getZ().getCoordinate() + width > r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate()) {
			pts.add(new Point3D(new Coordinate(r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate()),
					new Coordinate(r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()),
					new Coordinate(r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())));
			//mpts.put(this, pts);
			return pts;
		}
		//upward hit
		t = (location.getY().getCoordinate() + height - r.getPOO().getY().getCoordinate())/ r.getDirection().getHead().getY().getCoordinate();
		rayVector = r.getDirection().scale(t);
		if (location.getX().getCoordinate() < r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getX().getCoordinate() + depth > r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getZ().getCoordinate() < r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate() &&
				location.getZ().getCoordinate() + width > r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate()) {
			pts.add(new Point3D(new Coordinate(r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate()),
					new Coordinate(r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()),
					new Coordinate(r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())));
			//mpts.put(this, pts);
			//return pts;
		}
		//left hit
		t = (location.getZ().getCoordinate() - r.getPOO().getZ().getCoordinate())/ r.getDirection().getHead().getZ().getCoordinate();
		rayVector = r.getDirection().scale(t);
		if (location.getX().getCoordinate() < r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getX().getCoordinate() + depth > r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getY().getCoordinate() < r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate() &&
				location.getY().getCoordinate() + height > r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()) {
			pts.add(new Point3D(new Coordinate(r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate()),
					new Coordinate(r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()),
					new Coordinate(r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())));
		//	mpts.put(this, pts);
		//	return pts;
		}
		//right hit
		t = (location.getZ().getCoordinate() + width - r.getPOO().getZ().getCoordinate())/ r.getDirection().getHead().getZ().getCoordinate();
		rayVector = r.getDirection().scale(t);
		if (location.getX().getCoordinate() < r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getX().getCoordinate() + depth > r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate() &&
				location.getY().getCoordinate() < r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate() &&
				location.getY().getCoordinate() + height > r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()) {
			pts.add(new Point3D(new Coordinate(r.getPOO().getX().getCoordinate() + rayVector.getHead().getX().getCoordinate()),
					new Coordinate(r.getPOO().getY().getCoordinate() + rayVector.getHead().getY().getCoordinate()),
					new Coordinate(r.getPOO().getZ().getCoordinate() + rayVector.getHead().getZ().getCoordinate())));
			//mpts.put(this, pts);
			//return pts;
		}

		return pts;
	}
	
	
	public Map<Geometry, List<Point3D>> getBoxRayIntersections(Ray ray) throws Exception
	{
		Iterator<Geometry> geos = geometries.iterator();
		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		
		while (geos.hasNext()){
			Geometry geometry = geos.next();
			ArrayList<Point3D> geometryIntersectionPoints = (ArrayList<Point3D>) geometry.findIntersections(ray);
			if(!(geometryIntersectionPoints.isEmpty()))
				intersectionPoints.put(geometry, geometryIntersectionPoints);
		}
		return intersectionPoints;
	}
}

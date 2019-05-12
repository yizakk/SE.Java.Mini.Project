package elements;
import primitives.*;
public class Camera {
	private Point3D _P0;
	private Vector _vUp;
	private Vector _vRight;
	private Vector _vTo;
	
	// ***************** Constructors ********************** //
	/**
	 * default constructor, setting p0=(0,0,10), vup=(0,1,0) vto=(0,0,-1) vright=(-1,0,0) 
	 */
	public Camera() {
		this._P0 = new Point3D(0,0,10);
		_vUp = new Vector(0,1,0);
		_vTo = new Vector(0,0,-1);
		_vRight = _vUp.crossProduct(_vTo);//(-1,0,0)
	}
	//Copy Constructor
	public Camera(Camera camera) throws Exception
	{
		this._P0 = new Point3D(camera._P0);
		_vUp = new Vector(camera._vUp).normalize();
		_vRight = new Vector(camera._vRight).normalize();
		_vTo = new Vector(camera._vTo).normalize();
		//normalize vectors//
	}
	//CTOR by vectors + normalize vectors//
	public Camera(Point3D P0, Vector vUp, Vector vTo) throws Exception {
		this._P0 = new Point3D(P0);
		_vUp = new Vector(vUp).normalize();
		_vTo = new Vector(vTo).normalize();
		_vRight = _vUp.crossProduct(_vTo).normalize();
		
	}
	//Not required in file
	public Camera(Vector vec1, Vector vec2) throws Exception
	{
		_vUp=new Vector(vec1).normalize();
		_vTo = new Vector(vec2).normalize();
		_vRight = new Vector(_vUp.crossProduct(_vTo));
		this._P0 = new Point3D();
	}

	/************GETTERS & SETTERS************/
	public Vector get_vUp() {return new Vector(_vUp);}
	public void set_vUp(Vector vUP) {_vUp = new Vector( vUP);}
	public Vector get_vTo() {return new Vector(_vTo);}
	public void set_vTo(Vector vTOWARD) {_vTo = new Vector(vTOWARD);}
	public Point3D getP0() {return new Point3D(_P0);}
	public void setP0(Point3D _p) {this._P0 = new Point3D(_p);}
	public Vector get_vRight() {return new Vector(_vRight);}
	//Not required in file
	public void setVRIGHT(Vector vRIGHT) {_vRight = new Vector(vRIGHT);}
	
	// ***************** Administration ********************** //
	@Override
	public String toString() {
		return "Camera [_p=" + _P0 + ", VUP=" + _vUp + ", VRIGHT=" + _vRight + ", VTOWARD=" + _vTo + "]";
	}
	
	
	/**
	 * function that creates a ray through a pixel
	 * @param Nx - The width of each single pixel
	 * @param Ny - The height of each single pixel
	 * @param x - Point
	 * @param y - Point
	 * @param screenDist - Distance from the main screen
	 * @param screenWidth - Screen width 
	 * @param screenHeight - Screen height
	 * @return Ray
	 * @throws Exception 
	 */
	public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y, double screenDist, double screenWidth,double screenHeight) throws Exception
	{
		Vector pc=new Vector(this._vTo);
		pc=pc.scale(screenDist);
		double Rx = screenWidth/Nx;
		double Ry = screenHeight/Ny;
		double Right = (x-(double)Nx/2)*Rx+(Rx/2);
		double UP = (y-(double)Ny/2)*Ry+(Ry/2);
		Vector Temp_VRIGHT = _vRight.scale(Right);
		Vector Temp_VUP = _vUp.scale(UP);	
		Vector P = new Vector(Temp_VRIGHT);
		P.subtract(Temp_VUP);
		P.add(pc);
		Point3D centerPoint= new Point3D(Right,UP*(-1),screenDist*(-1));
		return new Ray(centerPoint,P);
		
	}
}

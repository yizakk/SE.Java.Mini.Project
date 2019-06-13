package geometries;

/**
 * An interface generalizing all the geometries suitable to be inserted into a box
 * There is only one function each geometry have to implement - to insert it self into 
 * a Box 
 * @author נוסבאום
 *
 */
public interface Boxable {
	Box insertIntoBox();
}

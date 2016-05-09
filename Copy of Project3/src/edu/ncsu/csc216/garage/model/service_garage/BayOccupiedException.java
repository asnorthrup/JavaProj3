/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

/**Custom exception for when a service manager is trying to put a car in 
 * a bay that is already occupied
 * @author Andrew Northrup
 *
 */
public class BayOccupiedException extends Exception {
	/**Null constructor for the BayCarMismatch exception, to create the 
	 * exception. 
	 * 
	 */
	public BayOccupiedException(){
		super("Exception: Bay Occupied");
	}
	/**Constructor for the BayOccupied exception that allows the user to pass in a string
	 * @param s for the string that the exception gives to the client
	 */
	public BayOccupiedException(String s){
		super(s);
	}
}

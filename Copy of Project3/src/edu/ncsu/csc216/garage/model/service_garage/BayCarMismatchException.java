/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

/**Custom exception for when a service manager is trying to put a car in 
 * a bay that doesn't match the car type
 * @author Andrew Northrup
 *
 */
public class BayCarMismatchException extends Exception {
	/**Null constructor for the BayCarMismatch exception, to create the 
	 * exception. 
	 */
	public BayCarMismatchException(){
		super("Exception: Bay Mismatch");
	}
	/**Constructor for the BayCarMismatch exception that allows the user to pass in a string
	 * @param s for the string that the exception gives to the client
	 */
	public BayCarMismatchException(String s){
		super(s);
	}
}

/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

/**Custom exception (checked) that indicates that no bays are available 
 * @author Andrew Northrup
 *
 */
public class NoAvailableBayException extends Exception {
	/**Null constructor for the BayCarMismatch exception, to create the 
	 * exception. 
	 * 
	 */
	public NoAvailableBayException(){
		super("Exception: No Bay Available");
	}
	/**Constructor for the NoAvailableBay exception that allows the user to pass in a string
	 * @param s for the string that the exception gives to the client
	 */
	public NoAvailableBayException(String s){
		super(s);
	}
}

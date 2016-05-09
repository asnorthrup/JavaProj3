/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

/**Custom Exception for if the service manager passes in bad information to create vehicle
 * @author Andrew Northrup
 *
 */
public class BadVehicleInformationException extends Exception {
	/**Null constructor for if an exception is created
	 * 
	 */
	public BadVehicleInformationException(){
		super("Exception: Bad Vehicle Information");
	}
	/**Constructor for the BadVehicle exception that allows the user to pass in a string
	 * @param s for the string that the exception gives to the client
	 */
	public BadVehicleInformationException(String s){
		super(s);
	}
}

/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/** Class that represents a service bay
 * @author Andrew Northrup
 *
 */
public class ServiceBay {
	/**Instance variable indicating if service bay is occupied*/
	private boolean occupied;
	/**Instance variable for the vehicle in the service bay, if exists */
	private Vehicle myVehicle;
	/**Instance variable for the ID (name) of the bay */
	private String bayID;
	/**Instance variable to assign an ID number to next bay	 */
	private static int nexNumber;
	/**Static method that starts the numbering at 101, which sets nexNumber to 101 */
	public static void startBayNumberingAt101(){
		nexNumber = 101;
	}
	/**Creates a service bay but the prefix starts with the first non-whitespace character of
	 * the string passed in, or 1 if there is no such character passed in
	 * @param s representing the name of the bay ID to set the name to
	 */
	public ServiceBay(String s){
		if(ServiceBay.nexNumber == 0) { startBayNumberingAt101(); }
		if(s == null){
			this.bayID = "1" + String.valueOf(ServiceBay.nexNumber).substring(1, 3);
		} else if(!s.equals("") && s != null && s.trim().length() > 0 && Character.isDefined(s.trim().charAt(0))){
			this.bayID = s.trim().substring(0, 1) + String.valueOf(ServiceBay.nexNumber).substring(1, 3); //added length qualifier
		} else {
			this.bayID = "1" + String.valueOf(ServiceBay.nexNumber).substring(1, 3);
		}
		ServiceBay.nexNumber++; //no need to set occupied as false as that is what boolean is init to
	}
	/**Null constructor that creates a new empty service bay according to the current 
	 * bay numbering, then increments that number. The prefix is "1". 
	 * 
	 */
	public ServiceBay(){
		if(ServiceBay.nexNumber == 0) { startBayNumberingAt101(); }
		this.bayID = "1" + String.valueOf(ServiceBay.nexNumber).substring(1, 3);
		ServiceBay.nexNumber++; //no need to set occupied as false as that is what boolean is init to
	}
	/**Gets the name of the bay ID
	 * @return String for the name of the bay (ID)
	 */
	public String getBayID(){
		return this.bayID;
		
	}
	/**Determines whether bay is occupied
	 * @return true if bay is occupied, false if not occupied
	 */
	public boolean isOccupied(){
		return this.occupied;
		
	}
	/**Releases the vehicle that is currently in the bay, from the bay
	 * @return vehicle that is released from the bay
	 */
	public Vehicle release(){
		if(!this.occupied){ return null; } 
		this.occupied = false;
		Vehicle temp = this.myVehicle;
		this.myVehicle = null;
		return temp;
	}
	
	/**Puts a vehicle into the bay if it is not occupied and the car matches the type
	 * @param v for vehicle to occupy the bay with
	 * @throws BayCarMismatchException if car type doesn't match bay type
	 * @throws BayOccupiedException if bay is already occupied
	 */
	public void occupy(Vehicle v) throws BayOccupiedException, BayCarMismatchException{
		if(this.occupied){ throw new BayOccupiedException(); } //No such thing as bay mismatch for regular service bay - double check
		this.myVehicle = v;
		this.occupied = true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To string method for the service bay, which takes the form: BayID: <license owner name> or <EMPTY>
	 * @return String for the service bay string
	 */
	public String toString(){
		if(!this.occupied){
			return this.bayID + ":" + " EMPTY"; //need to make sure E lines up with start of license plate plus padding for name, see other project
		} else{
			String paddedLicense = myVehicle.getLicense();
			while(paddedLicense.length() < 9){
				paddedLicense = paddedLicense + " ";
			}
			return this.bayID + ":" + " " + paddedLicense + myVehicle.getName(); //need to count spaces for this and license plate
		}		
	}
}

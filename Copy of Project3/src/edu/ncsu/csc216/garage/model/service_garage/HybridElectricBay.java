/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**An service bay that is for hybrid electric vehicles only
 * @author Andrew Northrup
 *
 */
public class HybridElectricBay extends ServiceBay {
	/**Constructor for hybrid electric service bay
	 */
	public HybridElectricBay(){
		super("E");
	}
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.service_garage.ServiceBay#occupy(edu.ncsu.csc216.garage.model.vehicle.Vehicle)
	 */
	/**Allows a vehicle to occupy this hybrid electric service bay
	 * @param v for Vehicle that will be put into the hybrid electric service bay
	 * @throws BayCarMismatchException if the car type doesn't match hybrid electric vehicle
	 * @throws BayOccupiedException if the bay the vehicle is being put in is occupied
	 * 
	 */
	public void occupy(Vehicle v) throws BayCarMismatchException, BayOccupiedException{
		String vType = v.toString().trim().substring(0, 1);
		if(!vType.equals("E")) { throw new BayCarMismatchException(); }
		super.occupy(v);
	}
}

/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.BayCarMismatchException;
import edu.ncsu.csc216.garage.model.service_garage.BayOccupiedException;
import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**Class for a regular vehicle
 * @author Andrew Northrup
 *
 */
public class RegularCar extends Vehicle {
	/**Constructor for a regular vehicle
	 * @param s String for license plate number
	 * @param t String for the owner of the vehicle
	 * @param i int for the tier type of the vehicle
	 * @throws BadVehicleInformationException if the vehicle isn't passed proper information
	 */
	public RegularCar(String s, String t, int i) throws BadVehicleInformationException{
			super(s, t, i);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Vehicle#pickServiceBay(edu.ncsu.csc216.garage.model.service_garage.Garage)
	 */
	@Override
	/**Picks the service bay for the garage passed in
	 * @param g for the garage to look for the service bay to pick
	 * @throws NoAvailableBayException for if there is not a bay to put the vehicle into,
	 * which should trigger adding or keeping the vehicle to the waitlist
	 */
	public void pickServiceBay(Garage g) throws NoAvailableBayException{
		for(int i = 0; i < g.getSize(); i++){
			try {
				g.getBayAt(i).occupy(this);
				return;
			} catch (BayOccupiedException e) {
				System.out.println("bay occ error at bay" + i);
			} catch (BayCarMismatchException e) {
				System.out.println("bay mismatch error at bay" + i);
			}
		}
		throw new NoAvailableBayException();
	}
	

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Vehicle#toString()
	 */
	/**
	 * To String to create a string out of the regular vehicle 
	 * @return String for the string representation of a regular car vehicle
	 */
	
	public String toString(){
		//R <Tier> <license> <owner name>. 
		//Regular to string example: R Gold      NC123456  Doe, Jane
		//Hybrid to string example: E Platinum  79-27DC   Carter, June W.
		return "R " + super.toString(); //need to use parent to string	
	}
}

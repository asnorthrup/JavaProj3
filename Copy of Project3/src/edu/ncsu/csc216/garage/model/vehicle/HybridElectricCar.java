/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.BayCarMismatchException;
import edu.ncsu.csc216.garage.model.service_garage.BayOccupiedException;
import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**Class for a hybrid electric vehicle car
 * @author Andrew Northrup
 *
 */
public class HybridElectricCar extends Vehicle {
	
	/**Constructor for a hybrid electric vehicle
	 * @param s String for license plate number
	 * @param t String for the owner of the vehicle
	 * @param i int for the tier type of the vehicle
	 * @throws BadVehicleInformationException if the vehicle isn't passed proper information
	 */
	public HybridElectricCar(String s, String t, int i) throws BadVehicleInformationException{
		super(s, t, i);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Vehicle#pickServiceBay(edu.ncsu.csc216.garage.model.service_garage.Garage)
	 */
	/**Picks the service bay for the garage passed in
	 * @param g for the garage to look for the service bay to pick
	 * @throws NoAvailableBayException for if there is not a bay to put the vehicle into,
	 * which should trigger adding or keeping the vehicle to the waitlist
	 */
	@Override
	public void pickServiceBay(Garage g) throws NoAvailableBayException{
		//starts at end and moves up
		int i = g.getSize() - 1;
		while(i >= 0){
			try{
				g.getBayAt(i).occupy(this);
				return;
			} catch (BayOccupiedException e) {
				System.out.println("bay occupied error at bay" + i);
			} catch (BayCarMismatchException e) {
				System.out.println("bay mismatch error at bay" + i);
			}
			i--;
		}
		throw new NoAvailableBayException();
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Vehicle#toString()
	 */
	/**
	 * To String to create a string out of the hybrid electric vehicle
	 *@return String for the string to represent the hybrid electric vehicle
	 */
	public String toString(){
		//R <Tier> <license> <owner name>. 
		//Regular to string example: R Gold      NC123456  Doe, Jane
		//Hybrid to string example: E Platinum  79-27DC   Carter, June W.
		return "E " + super.toString(); //need to use parent to string;
	}
}

/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**Abstract class that represents a vehicle with a tiered structure of the package the owner of the
 * vehicle selected
 * @author Andrew Northrup
 *
 */
abstract public class Vehicle implements Tiered {
	/**Instance variable for the license number of the vehicle */
	private String license;
	/**Instance variable for the name of the owner of the vehicle */
	private String name;
	/**Instance variable for the index of the tier:0 is no tier, 1 is silver, 2 is gold, 3 is platinum	 */
	private int tierIndex;
	/**Static final variable for an array of the tiers as they relate to the packages */
	public static final String[] CUSTOMER_TIER = {"None", "Silver", "Gold", "Platinum"};
	
	/**Constructor for a vehicle
	 * @param s String for license plate number
	 * @param t String for the owner of the vehicle
	 * @param i int for the tier type of the vehicle
	 * @throws BadVehicleInformationException if the vehicle isn't passed proper information
	 */
	public Vehicle(String s, String t, int i) throws BadVehicleInformationException{
		if(s == null || t == null){ throw new BadVehicleInformationException(); }
		try{
			this.setLicense(s); //test to make sure errors work
		} catch(BadVehicleInformationException e){
			throw e;
		}
		try{
			this.setName(t); //test to make sure errors work
		} catch(BadVehicleInformationException e) {
			throw e;
		}
		try{
			this.setTier(i); //test to make sure errors work
		} catch(BadVehicleInformationException e) {
			throw e;
		}
		
	}
	
	/**Picks a service bay in a garage for that fits the vehicle
	 * @param g the garage to find the bay where this vehicle will be put into
	 * @throws NoAvailableBayException that signifies no bays are available that fit the criteria
	 */
	abstract public void pickServiceBay(Garage g) throws NoAvailableBayException;
	
	/**Determines if the vehicle meets the filter criteria the user has entered
	 * @param filt for string user typed in for criteria
	 * @return boolean for true if it meets the filter criteria and false if it does not
	 */
	public boolean meetsFilter(String filt){
		if(filt == null || filt.trim().equals("")) { return true; } //this line hasn't been tested yet
		String trimFiltLC = filt.trim().toLowerCase();
		String ownersNameLC = name.toLowerCase();
		int whereFiltStarts = ownersNameLC.indexOf(trimFiltLC);
		if(whereFiltStarts == -1){ return false; }
		if(whereFiltStarts == 0 || ownersNameLC.substring(whereFiltStarts, whereFiltStarts + trimFiltLC.length()).contains(" ") || (ownersNameLC.substring(whereFiltStarts - 1, whereFiltStarts).equals(" "))) { return true; }
		return false;
		//return name.toLowerCase().contains(trimFilt.toLowerCase()); //makes all lower case for comparison
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To string to return for a vehicle that should be overwritten by anything that
	 * extends the class
	 * @return String for the string to represent the vehicle
	 */
	public String toString(){
		//R <Tier> <license> <owner name>. 
		//Regular to string example: R Gold      NC123456  Doe, Jane
		//Hybrid to string example: E Platinum  79-27DC   Carter, June W.
		String paddedTier = CUSTOMER_TIER[tierIndex];
		if(paddedTier != null){
			while(paddedTier.length() < 10){
				paddedTier = paddedTier + " ";
			}
			String paddedLicense = license.trim();
			while(paddedLicense.length() < 10){
				paddedLicense = paddedLicense + " ";
			}
			return paddedTier + paddedLicense + name;
		}
		return null;
	}
	
	/**Gets the owner's name for the vehicle
	 * @return vehicle owner's name string
	 */
	public String getName(){
		return this.name;
		
	}
	
	/**Gets the vehicle license plate
	 * @return returns the vehicles license plate
	 */
	public String getLicense(){
		if(this.license != null){
			return this.license;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Tiered#getTier()
	 */
	/**Gets the tier of the vehicle
	 * @return int for the integer representation for the vehicle tier
	 * 
	 */
	public int getTier(){
		return this.tierIndex;
		
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.garage.model.vehicle.Tiered#compareToTier(edu.ncsu.csc216.garage.model.vehicle.Tiered)
	 */
	/** Compares the tier of this vehicle to the tier of another vehicle
	 *@return int for  Compare the tier status of this object with another. 
	 *Returns 0 if the two match, a negative number if the tier status of this 
	 *object is less than the other's, a positive number if the tier status of 
	 *this object is greater.
	 *@param t for Tiered object to check against to see order in the tier
	 */
	public int compareToTier(Tiered t) {
		if(t.getTier() == this.tierIndex){ return 0; }
		if(t.getTier() > this.tierIndex){ return -1; }
		else { return 1; }
		
	}
	
	/**Sets the vehicle's tier to the passed in integer representation of the vehicle's tier: 
	 * @param i for the tier:0 is no tier, 1 is silver, 2 is gold, 3 is platinum	  
	 * @throws BadVehicleInformationException if the tier is not acceptable
	 */
	public void setTier(int i) throws BadVehicleInformationException{
		if(i < 0 || i > 3) { throw new BadVehicleInformationException("Invalid tier."); }
		this.tierIndex = i;
	}
	
	/**Sets the owner of the vehicle's name
	 * @param n for the name of the owner to set the vehicle to
	 * @throws BadVehicleInformationException for if the owner is not a valid name
	 */
	private void setName(String n) throws BadVehicleInformationException{
		String nt = n.trim();
		if(nt.length() == 0) {throw new BadVehicleInformationException("Owner name cannot be blank."); }
		this.name = nt;
	}
	
	/**Sets the license plate number for the vehicle
	 * @param l for string of the license plate number
	 * @throws BadVehicleInformationException if the license plate number is not valid
	 */
	private void setLicense(String l) throws BadVehicleInformationException{
		String tl = l.trim();
		if(tl.length() == 0) {throw new BadVehicleInformationException("License cannot be blank."); }
		if(tl.length() > 8) {throw new BadVehicleInformationException("License cannot be more than 8 characters."); }
		if(tl.contains(" ")) {throw new BadVehicleInformationException("License cannot contain a blank."); }
		this.license = tl;
	}
	
}

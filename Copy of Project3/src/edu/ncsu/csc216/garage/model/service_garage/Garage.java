/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**This is the class that represents the garage, which contains service bays
 * @author Andrew Northrup
 *
 */
public class Garage {
	/**Static Constant Instance variable for maximum number of service bays */
	private static final int MAX_ROOMS = 30;
	/**Static Constant for the initial number of garage bays created at open */
	private static final int DEFAULT_SIZE = 8;
	/**Instance variable for the total number of open(not occupied) bays */
	private int size;
	/**Instance variable for count of regular service bays in service*/
	private int regularBayCount;
	/**Instance variable for list*/
	private ServiceBay[] bayList;

	/**Null constructor for creating the garage.
	 * I think it will create the minimum number of bays if init bays (private method) is less than
	 * default size
	 * 
	 */
	public Garage(){
		ServiceBay.startBayNumberingAt101();
		bayList = new ServiceBay[MAX_ROOMS];
		size = 0;
		initBays(DEFAULT_SIZE);
	}
	
	/**Private method for initializing bays
	 * @param i integer for number of bays to initialize
	 */
	private void initBays(int i){
		while(size < DEFAULT_SIZE){
			addRepairBay();
		}
	}
	/**Adds a repair bay to the open(not same as occupied) bays. Must make call
	 * to determine that at least 1/3 of the open bays are dedicated to hybrid/electric
	 * vehicles
	 */
	public void addRepairBay(){
		//There is a constructor for both service bay and hybridelectric bay, call
		//the correct one based on formula of next making 1/3 HBEs or not.
		if(size >= MAX_ROOMS){ throw new IndexOutOfBoundsException(); }
		double pctRegBaysPotential = 0;
		pctRegBaysPotential = (((double)regularBayCount + 1) / ((double)size + 1));
		double maxRegBays = 2.0 / 3.0;
		if(size == 0 || (pctRegBaysPotential > (maxRegBays))) { //added 1 because if add one more can't be greater than 70%
			HybridElectricBay newSB = new HybridElectricBay();
			bayList[size] = newSB;
			size++;
		} else {
			ServiceBay newSB = new ServiceBay();
			regularBayCount++;
			addtoFrontBayList(newSB);
			size++;
		}
	}
	//helper method to add to front of list
	private void addtoFrontBayList(ServiceBay sb){
		int j = size;
		while(j > 0){
			bayList[j] = bayList[j - 1];
			j--;
		}
		bayList[0] = sb;
	}
	
	
	/**Gets the number of currently empty and open repair bays
	 * @return int for the number of empty and open repair bays
	 */
	public int numberOfEmptyBays(){
		int emptyBays = 0;
		for(int k = 0; k < size; k++){
			if(!bayList[k].isOccupied()) {
				emptyBays++;
			}
		}
		return emptyBays;
	}
	/**Gets a service bay at a specified index (integer). Enables client code to go through every open (not necessarily empty) service bay in the garage
	 * @param i integer of the index of service bay trying to get 
	 * @return Service bay at the int passed in as the parameter
	 */
	public ServiceBay getBayAt(int i){
		if(i > size - 1 || i < 0) { return null; }
		return bayList[i];
		
	}
	/**Gets total number of opened (not necessarily empty) service bays
	 * @return integer for the total number of empty service bays
	 */
	public int getSize(){
		return size;
	}

	/**Removes the vehicle currently in the service bay at the index passed in and returns it.
	 * @param i for the index of the service bay that has a vehicle to remove
	 * @return vehicle that was release from the service bay at passed in index
	 */
	public Vehicle release(int i){
		if(i > size - 1 || i < 0) { return null; }
		return bayList[i].release();		
	}
	
}

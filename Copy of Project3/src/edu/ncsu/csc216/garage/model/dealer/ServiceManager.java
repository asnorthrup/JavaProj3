/**
 * 
 */
package edu.ncsu.csc216.garage.model.dealer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;
import edu.ncsu.csc216.garage.model.util.SimpleIterator;
import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Tiered;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;
import edu.ncsu.csc216.garage.model.vehicle.VehicleList;

/**Concrete class that implements Manageable for the service manager
 * to manage the vehicle list and the garage
 * @author Andrew Northrup
 *
 */
public class ServiceManager implements Manageable {
	private VehicleList serviceManVL;
	private Garage g;
	/**Class null constructor creates a service 
	 *manager with no vehicles awaiting service.
	 * 
	 */
	public ServiceManager(){
		serviceManVL = new VehicleList();
		g = new Garage();
	}
	
	/**Initializes the list of vehicles awaiting 
	 * service with data from the Scanner if scanner object is passed in
	 * @param scanner for reading the vehicles in line from a file
	 */
	public ServiceManager(Scanner scanner){
		if(scanner == null){
			serviceManVL = new VehicleList();
		} else{
			serviceManVL = new VehicleList(scanner);
		}
		g = new Garage();
	}
	
	/** Fills the open, empty service 
	 * bays with vehicles on the waiting list.
	 */
	public void fillServiceBays(){
		//fill bay and add to remove array, which should be int of numbers on list
		Integer[] removeVehics = new Integer[g.numberOfEmptyBays()];
		SimpleIterator<Vehicle> iter = serviceManVL.iterator();
		Integer i = -1;
		int indexOfElementsInArray = 0;
		//something is wrong with cars picking bays, need to test that more thoroughly
		while(iter.hasNext()){
			Vehicle lookat = iter.next();
				try{
					i++;
					lookat.pickServiceBay(g);
					removeVehics[indexOfElementsInArray] = i;
					indexOfElementsInArray++;
				} catch(NoAvailableBayException e) {
					System.out.println(lookat.getLicense() + "cannot find bay"); //R7 is not throwing this
				}
		}
		//if not enough to fill array will throw npe, so trim remove vehics array
		int count = 0;
		for (Integer m : removeVehics) {
		    if (m != null) {
		        count++;
		    }
		}
		// Step 3
		Integer[] trimRemoveVehics = new Integer[count];
		// Step 4
		int index = 0;
		for (Integer m : removeVehics) {
		    if (m != null) {
		    	trimRemoveVehics[index++] = m;
		    }
		}
		//need to sort array so remove from end first
		Arrays.sort(trimRemoveVehics, Collections.reverseOrder());
		//this method should remove them from vehicle list
		for(int k = 0; k < count; k++){
			serviceManVL.remove("", trimRemoveVehics[k]);
		}
	}

	
	/** Calls VehicleList.remove(String, int), which  Removes the vehicle that 
	 * appears in the filtered list in the given position.
	 * @param filter for string that was input into the filter text field, True if filter is a prefix to the owner's 
	 * last name. The check is case insensitive. A filter of null or the 
	 * null string (e.g., "") would return true. 
	 * @param position of the vehicle to remove from the filtered list
	 * @return Tiered vehicle that implements the tiered interface that was removed
	 */
	public Tiered remove(String filter, int position){
		Vehicle temp = null;
		try{
			temp = serviceManVL.remove(filter, position);
		} catch(IndexOutOfBoundsException e){
			return null;
		}
		return temp;
	}
	/**Calls Garage.release(int), See ServiceBay.release() Removes the vehicle currently in the service bay and returns it.
	 * @param i for the integer of the service bay to release the vehicle from
	 * @return Tiered vehicle in the garage that was released.
	 */
	public Tiered releaseFromService(int i){
		return g.release(i);
	}
	/**Adds a new bay to the garage, they type is determined by the service bay class
	 * 
	 */

	public void addNewBay(){
		g.addRepairBay();
	}
	/** Prints the waiting list of vehicles based on the filter criteria
	 * @param s string for the filter the user typed in
	 * @return String representation of vehicles that meet the filter criteria
	 */
	public String printWaitList(String s){
		String waitListPrint = "";
		SimpleIterator<Vehicle> iter = serviceManVL.iterator();
		while(iter.hasNext()){
			Vehicle tmp = iter.next();
			if(tmp.meetsFilter(s)){
				waitListPrint = waitListPrint + tmp.toString() + '\n';
			}
		}
		return waitListPrint;
	}
	/**Prints out the service bays of open service bays
	 * @return String representation of service bays
	 */
	public String printServiceBays(){
		String servBaysPrint = "";
		for(int i = 0; i < g.getSize(); i++){
			servBaysPrint = servBaysPrint + g.getBayAt(i).toString() + '\n'; 
		}
		return servBaysPrint;
		
	}
	
	/** Puts a vehicle on the waiting list of vehicles awaiting service
	 * @param s String for hybrid or regular vehicle
	 * @param t String for license number
	 * @param u String for owners name
	 * @param i integer for the tier status
	 */
	public void putOnWaitingList(String s, String t, String u, int i){
		if(s == null || t == null || u == null) { return; }
		if(s.equals("E")) { 
			try {
				HybridElectricCar newCar = new HybridElectricCar(t, u, i);
				serviceManVL.add(newCar);
			} catch (BadVehicleInformationException e) {
				System.out.println("Bad Vehicle Exception caught");
			} 
		} else if(s.equals("R")) { 
			try {
				RegularCar newCar = new RegularCar(t, u, i);
				serviceManVL.add(newCar);
			} catch (BadVehicleInformationException e) {
				System.out.println("Bad Vehicle Exception caught");
			} 
		}
	}
	
	/**Puts a vehicle on the waiting list by the interface Tiered which is implemented by vehicle so can use those
	 * methods on a Tiered object
	 * @param t for the tiered object, which is only vehicle in this program
	 */
	public void putOnWaitingList(Tiered t){
		if(t != null){
			serviceManVL.add((Vehicle)t);
		}
	}
	
	/**Updates the tier for the item meeting the given filter from the list of items
	 * awaiting service
	 * @param s string for filter criteria 
	 * @param i position in the filtered (or unfiltered) list of waiting items
	 * @return Tiered object based on the string filter criteria and position passed to method for the filtered list
	 */
	public Tiered getWaitingItem(String s, int i){
		Tiered temp = null;
		if(s == null){ s = ""; }
		try{
			temp = serviceManVL.get(s, i);
		} catch(IndexOutOfBoundsException e){
			return null;
		}
		if(serviceManVL.get(s, i) == null) { return null; }
		return temp;
	}
}

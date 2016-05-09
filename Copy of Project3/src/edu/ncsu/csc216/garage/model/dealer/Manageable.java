package edu.ncsu.csc216.garage.model.dealer;

import edu.ncsu.csc216.garage.model.vehicle.*;

/**
 * Operations that must be supported to manage a service facility that has a list
 * of "tiered" items awaiting service and service bays where service is delivered. 
 * 
 * @author Jo Perry
 * @author Jason King
 */
public interface Manageable {
	
	/**
	 * Puts an item in the service wait list.
	 * @param t  Type of item
	 * @param b  Item data (such as license)
	 * @param c  Item data (such as owner name)
	 * @param x  Item tier
	 */
	public void putOnWaitingList(String t, String b, String c, int x);
	
	/**
	 * Puts an item in the list of those awaiting service.
	 * @param v  The item to put on waiting list
	 */
	public void putOnWaitingList(Tiered v);
	
	/**
	 * Returns the Tiered item meeting the given filter and position from the list of items awaiting service.
	 * @param filter Filters the list of items considered
	 * @param position Position in the filtered list of items
	 * @return The item at the position in the list that meets the given filter
	 */
	public Tiered getWaitingItem(String filter, int position);
	
	/**
	 * Removes an item meeting the given filter from the list of items awaiting service.
	 * @param filter  Filters the list of items considered for removal
	 * @param position  Position in the filtered list of the item to be removed
	 * @return  The item that was removed, or null if nothing was removed
	 */
	public Tiered remove(String filter, int position);
	
	/**
	 * Fills the service bays with items awaiting service.
	 */
	public void fillServiceBays();
	
	/**
	 * Releases the item from the given service bay.
	 * @param bay  Location of the bay where the item is being serviced
	 * @return  Item that was released from service, or null if the bay was empty
	 */
	public Tiered releaseFromService(int bay);
	
	/**
	 * Adds a new service bay to the service area.
	 */
	public void addNewBay();
	
	/**
	 * A string representation of the list of items awaiting service that meet 
	 *    the given filter.
	 * @param filter  Determines which items are are of interest
	 * @return String  String representation of the filtered list
	 */
	public String printWaitList(String filter);
	
	/**
	 * A string representation of the list of bays in the service area.
	 * @return  String representation of the service area 
	 */
	public String printServiceBays();
}


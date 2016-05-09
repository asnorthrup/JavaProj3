/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.garage.model.util.SimpleIterator;

/**Class for list of vehicles waiting for service
 * @author Andrew Northrup
 *
 */
public class VehicleList {
	/**Instance variable for the head of the linked list of vehicles on the vehicle
	 * waiting service list of type Vehicle	 */
	private Node<Vehicle> head;
	/**Instance variable for the size of the linked list*/
	private int size;
	/**Constructor for vehicle list if a scanner is passed to it, with a file.
	 * Creates a list of vehicles from a Scanner. The Scanner would have been 
	 * initialized by the calling code as a Scanner on an input text file. 
	 * @param s for the scanner passed in by the client
	 */
	public VehicleList(Scanner s){
			head = null;
			//don't need to create file because scanner is init by calling code
			//Scanner fileScanner = new Scanner(new File(filename));
			while(s.hasNextLine()){
				String type = null;
				int tierRead = 0;
				String licPlateRead = null;
				String nameRead = null;
				String car = s.nextLine();
				Scanner lineScanner = new Scanner(car);
				if(lineScanner.hasNext()) { type = lineScanner.next(); type = type.toUpperCase(); } //else { return; }
				if(lineScanner.hasNextInt()) { tierRead = lineScanner.nextInt(); } //else { return; }
				if(lineScanner.hasNext()) { licPlateRead = lineScanner.next(); } //else { return; }
				if(lineScanner.hasNextLine()) { nameRead = lineScanner.nextLine().trim(); } //else { return; }//this will have to be rest of line
				if(type != null && type.equals("R")){
					try{
						RegularCar newVehic = new RegularCar(licPlateRead, nameRead, tierRead);
						add(newVehic);
					} catch (BadVehicleInformationException e){
						System.out.println("Couldn't create Reg Car");
					}
				} else if(type != null && type.equals("E")) {
					try{
						HybridElectricCar newVehic = new HybridElectricCar(licPlateRead, nameRead, tierRead);
						add(newVehic);
					} catch (BadVehicleInformationException e){
						System.out.println("Couldn't create Reg Car");
					}
				//If not E or R, just dont read it in
				}
			}
		s.close();		
	}
	
	/**Null constructor for creating a vehicle list, which creates an
	 * empty vehicle list.
	 */
	public VehicleList(){
		head = null;
	}
	
	/**Gets a vehicle that appears in the filtered list in the given position
	 * @param filtString string for filter criteria
	 * @param posInFiltList for position in the returned filter of the vehicle to remove from waiting list
	 * @return Vehicle that was at that position
	 */
	//Think of the filter parameter as a way of checking to see if a vehicle matches
	//the filter string provided
	//don't need to use the filtered list in the remove (or get) methods
	public Vehicle get(String filtString, int posInFiltList){
		if(posInFiltList < 0) { throw new IndexOutOfBoundsException(); }
		if(posInFiltList >= size) { return null; } //this is new
		Node<Vehicle> tmp = head;
		int k = 0;
		while(k < posInFiltList || (k == posInFiltList && !tmp.vehicData.meetsFilter(filtString))){ 
			if(tmp.vehicData.meetsFilter(filtString)){ k++; }
			if(tmp.next != null){
				tmp = tmp.next;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}
		return tmp.vehicData; //since index starts at zero, should be there, test
	}
	
	/**Adds a vehicle to the end of the waiting list
	 * @param v the vehicle to add to the wait list
	 */
	public void add(Vehicle v){//Adds in sorted order
		Node<Vehicle> tmp = head;
        Node<Vehicle> tmpPrev = null;
        while(tmp != null && (tmp.vehicData.compareToTier(v)) >= 0){
        	tmpPrev = tmp;
        	tmp = tmp.next;
    	}
        if(tmp == head){
        	head = new Node<Vehicle>(v, head);
        }
        else {
        	tmpPrev.next = new Node<Vehicle>(v, tmp);
        }
        size++;
	}
	
	/**Removes the vehicle that appears in the filtered list in the given position
	 * @param filtString string for filter criteria
	 * @param posInFiltList for position in the returned filter of the vehicle to remove from waiting list
	 * @return Vehicle that is removed
	 */
	//see Vehicle.meetsFilter (and Piazza)
	//Think of the filter parameter as a way of checking to see if a vehicle matches
	//the filter string provided
	//don't need to use the filtered list in the remove (or get) methods
	public Vehicle remove(String filtString, int posInFiltList){
		if(posInFiltList < 0) { throw new IndexOutOfBoundsException(); } 
		if(posInFiltList >= size) { return null; } //added pos in filt is greater or = to size
		Node<Vehicle> tmp = head;
		if(posInFiltList == 0 && head.vehicData.meetsFilter(filtString)){ //removes first in list
			tmp = head;
			head = head.next;
			size--;
			return tmp.vehicData;
		}
		int k = 0;
		while(k < posInFiltList || (k == posInFiltList && !tmp.vehicData.meetsFilter(filtString))){ 
			if(tmp.vehicData.meetsFilter(filtString)){ k++; }
			if(tmp.next != null){
				tmp = tmp.next;
			} else {
				return null;
			}
		}
		//set trailing node.next equal to tmp.next (which is going to be removed)
		findTrailingNode(filtString, posInFiltList).next = tmp.next; //since index starts at zero, should be there, test 
		size--;
		return tmp.vehicData;
	}
	
	/** Points to node behind the first pointer, which is pointing to the trailing node.
	 * This method helps in removing the vehicle from the waiting list by traversing the filtered
	 * list with two pointers, with the first pointer looking for a vehicle at a certain position,
	 * and then using the trailing one to help remove that node. (linked list remove) 
	 * @param filtString for the filtered string
	 * @param posInFiltList for the position of the vehicle in the filtered (or not filtered) waiting list
	 * @return Node<Vehicle> for a node representing the trialing node which helps remove from
	 * the vehicle list. 
	 */
	//method is needed to keep list entact, even after something is removed
	private Node<Vehicle> findTrailingNode(String filtString, int posInFiltList){
		if(posInFiltList < 0 ) { throw new IndexOutOfBoundsException(); }
		if(posInFiltList == 0 && head.next.vehicData.meetsFilter(filtString)){ //come back to
			return head;
		}
		Node<Vehicle> tmp = head;
		Node<Vehicle> tmpTrail = null;
		int k = 0;
		while(k < posInFiltList || (k == posInFiltList && !tmp.vehicData.meetsFilter(filtString))){ 
			if(tmp.vehicData.meetsFilter(filtString)){ k++; }
			if(tmp.next != null){
				tmpTrail = tmp;
				tmp = tmp.next;
			} else {
				throw new IndexOutOfBoundsException();
			}
		}
		return tmpTrail; //since index starts at zero, should be there, test; //this stuff is for filtered data, thats why iterator is needed too!
	}
	
	/**String representation of all vehicles that meet the filter. Each substring corresponding 
	 * to a vehicle is terminated by a newline.
	 * @param s for a string that is passed in as the filter criteria
	 * @return string representation of all vehicles that meet the filter criteria
	 */
	public String filteredList(String s){
		/*The string that filtered list returns is a string representation 
		of the list of vehicles that meet whatever filter is passed in.  
		You could just create a new string, and then append each vehicle that 
		meets the filter to the new string you created.  You have methods which 
		will turn the vehicle into a string.*/
		Vehicle temp = null;
		int i = 0;
		String filtListToString = "";
		try{
			temp = get(s, i);
		} catch(IndexOutOfBoundsException e){
			temp = null;
		}
		while(temp != null){
			filtListToString = filtListToString + temp.toString() + '\n';
			i++;
			try{
				temp = get(s, i);
			} catch(IndexOutOfBoundsException e){
				temp = null;
			}
		}
		return filtListToString;
	}
	
	/**Private inner class for creating a linked list of vehicles
	 * @author Andrew Northrup
	 * @param <E> for the generic type of data that could be held in a node
	 */
	private class Node<E> {
		/**Instance variable for the data, which is a vehichle, in the linked list*/
		public Vehicle vehicData;
		/**Instance variable for the next linklink in the linked list*/
		public Node<Vehicle> next;
		
		/**Constructor for the node class that is passed in a vehicle and a link
		 * @param v for the vehicle (or data) of the linked list node
		 * @param next for the next Node<Vehicle> in the linked list
		 */
		public Node(Vehicle v, Node<Vehicle> next){
			this.vehicData = v;
			this.next = next;
		}
	}
	
	/**Private inner class for the simple iterator. This is a cursor that implements the simple
	 * iterator interface.
	 * @author Andrew Northrup
	 *
	 */
	private class Cursor implements SimpleIterator<Vehicle> {
		/**Instance variable that is a node that represents the next node the cursor gets to */
		private Node<Vehicle> cursor = head;

		/* (non-Javadoc)
		 * @see edu.ncsu.csc216.garage.model.util.SimpleIterator#hasNext()
		 */
		/**Determines if what is being iterated over (this case is a linked list) if there is a next node
		 * @return boolean true for if next node exists, false if it does not
		 */
		@Override
		public boolean hasNext() {
			return cursor != null; //true or false if cursor is equal to null (it will move forward)
		}

		/* (non-Javadoc)
		 * @see edu.ncsu.csc216.garage.model.util.SimpleIterator#next()
		 */
		/**Gets the next vehicle in the linked list via the iterator
		 * @return Vehicle that is next in the linked list (i.e. in front of cursor) and moves to next cursor (check on this)
		 */
		@Override
		public Vehicle next() throws NoSuchElementException {
			if( cursor == null) { throw new NoSuchElementException(); }
			Vehicle curVehicle = cursor.vehicData;
			cursor = cursor.next;
			return curVehicle;
		}
	}
	/**Creates a simple iterator that can be used to go through the linked list
	 * of vehicles
	 * @return SimpleIterator<Vehicle> for the iterator to return an iterator over the linked list
	 */
	public SimpleIterator<Vehicle> iterator(){
		return new Cursor(); //gets a new cursor	
	}
	
	
}

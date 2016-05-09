/**
 * 
 */
package edu.ncsu.csc216.garage.model.util;

/**Interface for an iterator that gets a next and determines if has next
 * @author Andrew Northrup
 * @param <E> for general parameter of objects to iterate on
 */
public interface SimpleIterator<E> {
	/**
	 * Does the iterator have more elements to traverse?
	 * @return true if the list iterator has more elements.
	 */ 	
	boolean hasNext();
	/**
	 * Gets the next element in the list and pushes the iterator
	 * down the list.
	 * @return the next element in the list.
	 * @throws Exception which will be no such element exception at runtime if there's no next element
	 */
	E next(); 
	
}

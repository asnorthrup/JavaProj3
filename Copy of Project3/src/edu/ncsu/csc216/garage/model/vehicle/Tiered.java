/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

/** Interface that describes behaviors of objects with tier status 
 * @author Andrew Northrup
 *
 */
public interface Tiered {
	/**Gets the tier represented as an integer
	 * @return int to represent the tier numerically
	 */
	public int getTier();
	/**Compare the tier status of this object with another. 
	 * Returns 0 if the two match, a negative number if the tier status of this object 
	 * is less than the other's, a positive number if the 
	 * tier status of this object is greater. Required for ordering. 
	 * @param t for the tiered object to compare to the one the method is called on
	 * @return integer 0 if they match, negative if the passed in is less than the one compared to
	 * positive if it is more than the tiered object passed in 
	 */
	public int compareToTier(Tiered t);
}

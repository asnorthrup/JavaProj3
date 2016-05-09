/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;

/**Tests the hybrid electric bay class
 * @author Andrew Northrup
 *
 */
public class HybridElectricBayTest {
	private HybridElectricBay hb1;
	private RegularCar rc1;
	private HybridElectricCar hbc1;
	private HybridElectricCar hbc2;
	/**Sets up the testing for the hybrid electric bay test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hb1 = new HybridElectricBay();
		rc1 = new RegularCar("RDC1", "John Bahama", 2);
		hbc1 = new HybridElectricCar("HBEDC1", "Captain Planet", 1);
		System.out.println(hbc1.toString());
		hbc2 = new HybridElectricCar("HBEDC2", "Mother Earth", 2);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.HybridElectricBay#occupy(edu.ncsu.csc216.garage.model.vehicle.Vehicle)}.
	 * Tests occupying a hybrid electric bay with a vehicle.
	 */
	@Test
	public void testOccupy() {
		try {
			hb1.occupy(rc1);
			fail();
		} catch (BayCarMismatchException e) {
			assertFalse(hb1.isOccupied());
		} catch (BayOccupiedException e) {
			fail();
		} //passed throwing error on mismatch of 
		try {
			hb1.occupy(hbc1);
		} catch (BayCarMismatchException e) {
			fail();
		} catch (BayOccupiedException e) {
			fail();
		}
		assertTrue(hb1.isOccupied()); //passed
		try {
			hb1.occupy(hbc2);
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		} catch (BayOccupiedException e) {
			assertTrue(hb1.isOccupied());
		} //passed
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.HybridElectricBay#HybridElectricBay()}. Tests
	 * creating a hybrid electric bay.
	 */
	@Test
	public void testHybridElectricBay() {
		assertFalse(hb1.isOccupied());
	}

}

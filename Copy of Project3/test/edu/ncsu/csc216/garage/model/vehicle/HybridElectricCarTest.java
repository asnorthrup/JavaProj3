/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**Tests the hybrid electric car class
 * @author Andrew Northrup
 *
 */
public class HybridElectricCarTest {
	private HybridElectricCar hbe1;
	private Garage g;
	/**Sets up the hybrid electric car test class
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hbe1 = new HybridElectricCar("hbeDC1", "Jane Doe", 2);
		g = new Garage();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar#pickServiceBay(edu.ncsu.csc216.garage.model.service_garage.Garage)}. Tests
	 * a car picking a service bay.
	 */
	@Test
	public void testPickServiceBay() {
		assertTrue(g.numberOfEmptyBays() == 8);
		try {
			hbe1.pickServiceBay(g);
		} catch (NoAvailableBayException e) {
			e.printStackTrace();
		}
		assertTrue(g.numberOfEmptyBays() == 7);
		assertFalse(g.getBayAt(1).isOccupied());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar#toString()}. Tests the to string of a hybrid
	 * electric vehicle.
	 */
	@Test
	public void testToString() {
		assertEquals("E", hbe1.toString().substring(0, 1));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar#HybridElectricCar(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testHybridElectricCar() {
		assertEquals(hbe1.getLicense(), "hbeDC1");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#meetsFilter(java.lang.String)}. Test to see if hybrid electric vehicle meets the filter
	 * passed in as a string.
	 */
	@Test
	public void testMeetsFilter() {
		assertTrue(hbe1.meetsFilter("d"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#getName()}. Tests getting name of hybrid electric vehicle.
	 */
	@Test
	public void testGetName() {
		assertEquals(hbe1.getName(), "Jane Doe");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#getLicense()}. Tests getting license of a hybrid electric vehicle.
	 */
	@Test
	public void testGetLicense() {
		assertEquals(hbe1.getLicense(), "hbeDC1");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#getTier()}. Tests getting the tier of the hybrid
	 * electric vehicle.
	 */
	@Test
	public void testGetTier() {
		assertEquals(hbe1.getTier(), 2);
	}


}

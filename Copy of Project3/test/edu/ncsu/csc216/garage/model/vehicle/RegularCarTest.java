/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**Tests the regular vehicle test
 * @author Andew Northrup
 *
 */
public class RegularCarTest {
	RegularCar rv1;
	RegularCar rv2;
	RegularCar rv3;
	RegularCar rv4;
	Garage g;
	/**Sets up the regular vehicle test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rv1 = null;
		rv2 = null;
		rv3 = null;
		rv4 = null;
		g = new Garage();
	}
	/**Tests constructor of a regular vehicle
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.RegularCar#RegularCar(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testRegularCar() {
		//test license failures
		try { //test space in license
			rv1 = new RegularCar("DC 1", "Sam Anderson", 2);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv1);
		}
		try { //test too long license
			rv1 = new RegularCar("DC1234567", "Sam Anderson", 2);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv1);
		}
		try { //test empty license
			rv1 = new RegularCar("   ", "Sam Anderson", 2);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv1);
		}
		try { //test valid license
			rv1 = new RegularCar(" DC-12345   ", "Sam Anderson", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertEquals(rv1.getLicense(), "DC-12345"); //passes up to here
		//test name failures
		try { //name is empty
			rv2 = new RegularCar(" DC-12345 ", "   ", 2);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv2);
		}
		try { //test valid name
			rv2 = new RegularCar(" DC-12345   ", "S  ", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertEquals(rv2.getName(), "S"); //passes up to here
		//test tier failures
		try { //less than 0
			rv3 = new RegularCar(" DC-12345 ", " am  ", -1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv3);
		}
		try { //more than 0
			rv3 = new RegularCar(" DC-12345 ", "  pd ", 4);
			fail();
		} catch (BadVehicleInformationException e) {
			assertNull(rv3);
		}
		try { //test valid tier
			rv3 = new RegularCar(" DC-12345   ", "S  ", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertEquals(rv3.getTier(), 2);
		//test type

	}
	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.RegularCar#pickServiceBay(edu.ncsu.csc216.garage.model.service_garage.Garage)}.
	 * Tests a regular vehicle picking a service bay.
	 */
	@Test
	public void testPickServiceBay() {
		try { //test valid license
			rv1 = new RegularCar(" DC-12345   ", "Sam Anderson", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(g.numberOfEmptyBays() == 8);
		try {
			rv1.pickServiceBay(g);
		} catch (NoAvailableBayException e) {

			e.printStackTrace();
		}
		assertTrue(g.numberOfEmptyBays() == 7);
		assertTrue(g.getBayAt(0).isOccupied());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.RegularCar#toString()}. Tests to string of a regular vehicle.
	 */
	@Test
	public void testToString() {
		try { //test valid license
			rv1 = new RegularCar("NC0", "Name", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertEquals("R Gold      NC0       Name", rv1.toString());
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#meetsFilter(java.lang.String)}. Tests whether vehicle meets
	 * the filter passed in as a string.
	 */
	@Test
	public void testMeetsFilter() {
		//use string "AND"
		try { 
			rv1 = new RegularCar(" DC-12345   ", "My Jeep", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try { 
			rv2 = new RegularCar(" DC-12345   ", "My Jeep", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try { 
			rv3 = new RegularCar(" DC-12345   ", "My Jeep", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(rv1.meetsFilter("my j"));
		assertFalse(rv2.meetsFilter("eep"));
		assertTrue(rv3.meetsFilter("jE"));
		try { 
			rv4 = new RegularCar(" MA-1   ", "My Jeep", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(rv4.meetsFilter("my j "));
		rv4 = null;
		try { 
			rv4 = new RegularCar(" MA-1   ", "Jill Anders", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(rv4.meetsFilter("AnD"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#compareToTier(edu.ncsu.csc216.garage.model.vehicle.Tiered)}. Tests
	 * the comparison of tiers of a regular vehicle.
	 */
	@Test
	public void testCompareToTier() {
		try { //
			rv1 = new RegularCar(" DC-12345   ", "Sam Anderson", 0);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try { //test valid name
			rv2 = new RegularCar(" DC-12345   ", "Sa Nd  ", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try { //test valid tier
			rv3 = new RegularCar(" DC-12345   ", "And  ", 0);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		/*
		 * Returns 0 if the two match, a negative number if the tier status of this object 
		 * is less than the other's, a positive number if the 
		 * tier status of this object is greater.
		 */
		assertTrue(rv1.compareToTier(rv2) < 0);
		assertTrue(rv2.compareToTier(rv1) > 0);
		assertTrue(rv3.compareToTier(rv1) == 0);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.Vehicle#setTier(int)}. Tests setting the tier of a regular vehicle.
	 */
	@Test
	public void testSetTier() {
		try { //test valid tier
			rv3 = new RegularCar(" DC-12345   ", "And  ", 0);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(rv3.getTier() == 0);
		try {
			rv3.setTier(4);
		} catch (BadVehicleInformationException e) {
			assertTrue(rv3.getTier() == 0);
		}
		try {
			rv3.setTier(2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		assertTrue(rv3.getTier() == 2);
	}

}

/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;

/**Tests the garage class
 * @author Andrew Northrup
 *
 */
public class GarageTest {
	Garage g;
	RegularCar rc1;
	RegularCar rc2;
	RegularCar rc3;
	RegularCar rc4;
	HybridElectricCar hbe1;
	HybridElectricCar hbe2;
	HybridElectricCar hbe3;
	/**Sets up the testing of the garage class test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		g = new Garage();
		try {
			rc1 = new RegularCar("DC1", "Reg 1", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rc2 = new RegularCar("DC2", "Reg 2", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rc3 = new RegularCar("DC3", "Reg 3", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			hbe1 = new HybridElectricCar("EDC1", "Hy 1", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			hbe2 = new HybridElectricCar("EDC2", "RHy 2", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rc4 = new RegularCar("DC4", "Reg 4", 0);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			hbe3 = new HybridElectricCar("EDC3", "Reg 3", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.Garage#Garage()}. Tests null construction of a
	 * garage.
	 */
	@Test
	public void testGarage() {
		assertEquals(g.getSize(), 8);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.Garage#addRepairBay()}.
	 */
	@Test
	public void testAddRepairBay() {
		g.addRepairBay();
		assertEquals(g.getSize(), 9);
		assertEquals("9", g.getBayAt(0).getBayID().substring(2, 3));
		g.addRepairBay();
		assertEquals("9", g.getBayAt(0).getBayID().substring(2, 3));

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.Garage#numberOfEmptyBays()}. Tests getting the 
	 * number of empty service bays.
	 */
	@Test
	public void testNumberOfEmptyBays() {
		assertEquals(g.numberOfEmptyBays(), 8);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.Garage#getBayAt(int)}. Tests getting a bay
	 * at a specific position by int.
	 */
	@Test
	public void testGetBayAt() {
		assertFalse(g.getBayAt(0).isOccupied());
		RegularCar v = null;
		try {
			v = new RegularCar("DC1", "George Jones", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			g.getBayAt(0).occupy(v);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(0).isOccupied());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.Garage#release()}. Tests releasing a vehicle from a service
	 * bay.
	 */
	@Test
	public void testRelease() {
		g.addRepairBay();
		g.addRepairBay();
		assertEquals(g.numberOfEmptyBays(), 10);
		try { //occupy bay 2
			g.getBayAt(2).occupy(rc1);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(2).isOccupied());
		assertEquals(g.numberOfEmptyBays(), 9);
		try { //should fail at occupying bay 2
			g.getBayAt(2).occupy(rc2);
			fail();
		} catch (BayOccupiedException e) {
			assertTrue(g.getBayAt(2).isOccupied());
		} catch (BayCarMismatchException e) {
			fail();
		}
		try { //occupy bay 3
			g.getBayAt(3).occupy(rc2);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(3).isOccupied());
		assertEquals(g.numberOfEmptyBays(), 8);
		try { //try and occupy hbe bay w/ reg car
			g.getBayAt(8).occupy(rc3);
			fail();
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			assertFalse(g.getBayAt(8).isOccupied());
		}
		try { //occupy bay 4 with a regular vehicle
			g.getBayAt(4).occupy(rc3);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(4).isOccupied());
		assertEquals(g.numberOfEmptyBays(), 7);
		try { //try and occupy reg bay that is already occupied w/ hbe vehic
			g.getBayAt(4).occupy(hbe1);
			fail();
		} catch (BayOccupiedException e) {
			assertTrue(g.getBayAt(4).isOccupied());
		} catch (BayCarMismatchException e) {
			fail();
		}
		try { //occupy hbe bay w/hbe vehic
			g.getBayAt(8).occupy(hbe1);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(8).isOccupied());

		try {  //try and occupy hbe filled bay with hbe vehicle
			g.getBayAt(8).occupy(hbe2);
		} catch (BayOccupiedException e) {
			assertTrue(g.getBayAt(8).isOccupied());
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertEquals(rc3, g.getBayAt(4).release());
		try { //after regular bay has been released add hbe into regular bay
			g.getBayAt(4).occupy(hbe2);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(4).isOccupied());
		assertEquals(rc1, g.getBayAt(2).release());
		assertFalse(g.getBayAt(2).isOccupied());
		try { //after releasing regular bay has been released add regular vehicle
			g.getBayAt(2).occupy(rc4);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(4).isOccupied());
		assertEquals(hbe1, g.getBayAt(8).release());
		assertFalse(g.getBayAt(8).isOccupied());
		try { //after releasing hbe bay add hbe vehicle
			g.getBayAt(8).occupy(hbe3);
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
		assertTrue(g.getBayAt(8).isOccupied());
	}

}

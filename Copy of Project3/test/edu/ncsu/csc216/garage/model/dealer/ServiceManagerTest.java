/**
 * 
 */
package edu.ncsu.csc216.garage.model.dealer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;

/**Test for the ServiceManager class
 * @author Andrew Northrup
 *
 */
public class ServiceManagerTest {
	ServiceManager sm;
	RegularCar rc1;
	RegularCar rc2;
	RegularCar rc3;
	RegularCar rc4;
	HybridElectricCar hbe1;
	HybridElectricCar hbe2;
	HybridElectricCar hbe3;
	/**Sets up the necessary methods for the service manager test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sm = new ServiceManager();
		try {
			rc1 = new RegularCar("L-2", "Owner-2", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rc2 = new RegularCar("L-3", "Owner-3", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rc3 = new RegularCar("L-1", "Owner-1", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			hbe1 = new HybridElectricCar("LE-1", "LEOwner-1", 0);
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
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#ServiceManager()}. Tests the service manager null constructor
	 */
	@Test
	public void testServiceManager() {
		assertTrue(sm.printServiceBays().length() > 0);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#ServiceManager(java.util.Scanner)}. Tests service manager constructor
	 * when passed in a scanner.
	 */
	@Test
	public void testServiceManagerScanner() {
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new File("test-files/cars_clean.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
		}
		if(fileScanner != null){
			sm = new ServiceManager(fileScanner);
		}
		sm.fillServiceBays();
		assertTrue(sm.printServiceBays().length() > 0);
		assertTrue(sm.printWaitList("").length() > 0);
		fileScanner = null;
		try {
			fileScanner = new Scanner(new File("No file exists"));
			fail();
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#fillServiceBays()}. Tests filling service bays
	 * with vehicles on waiting list.
	 */
	@Test
	public void testFillServiceBays() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.fillServiceBays();
		assertEquals(sm.releaseFromService(0), rc1);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#remove(java.lang.String, int)}. Tests removing a vehicle
	 * from the waiting list.
	 */
	@Test
	public void testRemove() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.putOnWaitingList(rc3);
		assertEquals(rc2, sm.remove("OW", 1));
		//TEST REMOVING FROM EMPTY LIST
		sm.fillServiceBays();
		assertNull(sm.remove("", 0));
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#releaseFromService(int)}. Tests releasing a car from
	 * a bay.
	 */
	@Test
	public void testReleaseFromService() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.putOnWaitingList(rc3);
		sm.fillServiceBays();
		assertEquals(sm.releaseFromService(0), rc1);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#addNewBay()}. Tests adding a new bay.
	 */
	@Test
	public void testAddNewBay() {
		sm.addNewBay();
		System.out.println(sm.printServiceBays());
		assertTrue(sm.printServiceBays().startsWith("109"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#printWaitList(java.lang.String)}. Tests printing
	 * the waiting list.
	 */
	@Test
	public void testPrintWaitList() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.putOnWaitingList(rc3);
		sm.putOnWaitingList(hbe1);
		assertTrue(sm.printWaitList("").startsWith("R"));
		System.out.println(sm.printWaitList(""));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#printServiceBays()}. Tests printing the service
	 * bays.
	 */
	@Test
	public void testPrintServiceBays() {
		System.out.println(sm.printServiceBays());
		assertTrue(sm.printServiceBays().startsWith("1"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#putOnWaitingList(java.lang.String, java.lang.String, java.lang.String, int)}.
	 * Tests putting on waiting list by creating a new vehicle from scratch (type, license, name, tier int)
	 */
	@Test
	public void testPutOnWaitingListStringStringStringInt() {
		sm.putOnWaitingList("E", "DC2", "Johnny Rocket", 2);
		sm.putOnWaitingList("R", "DC1", "Johnny Rock", 3);
		sm.putOnWaitingList("R", "DC3", "John Rocket", 1);
		sm.putOnWaitingList("E", "DC4", "Johnny t", 1);
		String waitListString = sm.printWaitList("Ro");
		//System.out.println(waitListString);
		assertTrue(waitListString.startsWith("R"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#putOnWaitingList(edu.ncsu.csc216.garage.model.vehicle.Tiered)}.
	 * Tests putting a vehicle on the waiting list.
	 */
	@Test
	public void testPutOnWaitingListTiered() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.putOnWaitingList(rc3);
		sm.putOnWaitingList(hbe1);
		sm.fillServiceBays();
		assertEquals(sm.printWaitList(""), ""); 
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.dealer.ServiceManager#getWaitingItem(java.lang.String, int)}. Tests getting a waiting
	 * item by filter string and by position in list.
	 */
	@Test
	public void testGetWaitingItem() {
		sm.putOnWaitingList(rc1);
		sm.putOnWaitingList(rc2);
		sm.putOnWaitingList(rc3);
		sm.putOnWaitingList(hbe1);
		assertEquals(sm.getWaitingItem("Ow", 1), rc2);
		assertNull(sm.getWaitingItem(" ", 35));
		assertNull(sm.getWaitingItem(".", -3));
		assertNull(sm.getWaitingItem("ow", -5));
		assertNull(sm.getWaitingItem(null, -5));
		assertEquals(rc2, sm.getWaitingItem(null, 1));
	}

}

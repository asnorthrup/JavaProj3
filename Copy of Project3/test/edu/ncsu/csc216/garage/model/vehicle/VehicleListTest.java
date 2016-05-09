/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.util.SimpleIterator;

/**Tests the vehicle list class
 * @author Andrew Northrup
 *
 */
public class VehicleListTest {
	/*Instance variable for vehicle list*/
	VehicleList vl;
	RegularCar rv1;
	RegularCar rv2;
	RegularCar rv3;
	RegularCar rv4;
	RegularCar rv5;
	/**Sets up the tests for vehicle list test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		vl = new VehicleList();
		rv1 = null;
		rv2 = null;
		rv3 = null;
		rv4 = null;
		rv5 = null;
		try {
			rv1 = new RegularCar("DC1", "Sam Anderson", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv2 = new RegularCar("DC2", "Jill Anders", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv3 = new RegularCar("DC3", "Sam Derson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv4 = new RegularCar("DC4", "Amos Andy", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv5 = new RegularCar("DC5", "James Anderson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		vl.add(rv1);
		vl.add(rv2);
		vl.add(rv3);
		vl.add(rv4);
		vl.add(rv5);
		//From the debugger adds on vehicle list are working
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#VehicleList(java.util.Scanner)}. Test for vehicle
	 * list constructor passed a scanner.
	 */
	@Test
	public void testVehicleListScanner() {
		Scanner fileScanner = null;
		VehicleList vlscan = null;
		try {
			fileScanner = new Scanner(new File("test-files/cars.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
		}
		if (fileScanner != null){
			vlscan = new VehicleList(fileScanner);
		}
		if(vlscan != null){
			assertEquals("MA-0987", vlscan.get("W", 0).getLicense());
			assertEquals("FL-M3456", vlscan.get("m", 1).getLicense());
			assertEquals("NC-5678", vlscan.get("eme", 0).getLicense());
		}
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#VehicleList(java.util.Scanner)}. Tests a second scanned file
	 * for constructor of vehicle list passed in scanner.
	 */
	@Test
	public void testVehicleListScannerTwo() {
		Scanner fileScanner = null;
		VehicleList vlscan = null;
		try {
			fileScanner = new Scanner(new File("test-files/TS_test_scanning.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
		}
		if(fileScanner != null){
			vlscan = new VehicleList(fileScanner);
		}
		if(vlscan != null){
			assertEquals(vlscan.get("", 0).getName(), "Doe, Elec");
		}
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#iterator()}. Tests the iterator of vehicle list
	 */
	@Test
	public void testIterator() {
		SimpleIterator<Vehicle> iter = vl.iterator();
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), rv2);
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), rv1);
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), rv4);
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), rv3);
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), rv5);
		assertFalse(iter.hasNext());
	}

	/**Test removing a vehicle from the vehicle list.
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#remove(java.lang.String, int)}.
	 */
	@Test
	public void testRemove() { 
		assertEquals(vl.remove("And", 1), rv1);
		assertEquals(vl.get("And", 1), rv4);
	}
	
	/**Tests removing vehicle from front of vehicle list
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#remove(java.lang.String, int)}.
	 */
	@Test
	public void testRemoveFront() { 
		assertEquals(vl.remove("And", 0), rv2);
		assertEquals(vl.get("And", 0), rv1);
	}
	/**Tests removing vehicle from end of vehicle list
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#remove(java.lang.String, int)}.
	 */
	@Test
	public void testRemoveEnd() { 
		assertEquals(vl.remove("And", 3), rv5);
		try{
			if(vl != null){
				vl.get("And", 3);
			}
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(vl.get("And", 2), rv4);
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#get(java.lang.String, int)}. Tests getting a vehicle
	 * at a position and by filter passed in to vehicle list.
	 */
	@Test
	public void testGet() {
		assertEquals(vl.get("AnD", 0), rv2);
		assertEquals(vl.get("AnD", 1), rv1);
		assertEquals(vl.get("AND", 2), rv4);
		assertEquals(vl.get("AnD", 3), rv5);
	}
	
	/**Set up second getter test to futher test the getter method.
	 * Test method for {@link edu.ncsu.csc216.garage.model.vehicle.VehicleList#get(java.lang.String, int)}.
	 */
	@Test
	public void testSecondGet() {
		vl = new VehicleList();
		rv1 = null;
		rv2 = null;
		rv3 = null;
		rv4 = null;
		rv5 = null;
		try {
			rv1 = new RegularCar("DC1", "Sam Anderson", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv2 = new RegularCar("DC2", "Jill Anders", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv3 = new RegularCar("DC3", "Sam Dobson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv4 = new RegularCar("DC4", "Amos Andy", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv5 = new RegularCar("DC5", "James Anderson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		vl.add(rv1);
		vl.add(rv2);
		vl.add(rv3);
		vl.add(rv4);
		vl.add(rv5);
		
		assertEquals(vl.get("dob", 0), rv3);
	}
	/**Test printing filtered list
	 */
	@Test
	public void testFilteredList() {
		rv1 = null;
		rv2 = null;
		rv3 = null;
		rv4 = null;
		rv5 = null;
		try {
			rv1 = new RegularCar("DC1", "Sam Anderson", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv2 = new RegularCar("DC2", "Jill Anders", 3);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv3 = new RegularCar("DC3", "Sam Derson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv4 = new RegularCar("DC4", "Amos Andy", 2);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		try {
			rv5 = new RegularCar("DC5", "James Anderson", 1);
		} catch (BadVehicleInformationException e) {
			fail();
		}
		vl.add(rv1);
		vl.add(rv2);
		vl.add(rv3);
		vl.add(rv4);
		vl.add(rv5);
		
		String filtListString = vl.filteredList("AND");
		assertTrue(filtListString.startsWith("R"));
		filtListString = vl.filteredList("JO");
		assertTrue(filtListString.length() == 0);
	}
}

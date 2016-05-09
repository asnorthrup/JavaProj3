/**
 * 
 */
package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;

/**Tests the service bay class, which will be used for both regular and hybrid vehicles
 * @author Andrew Northrup
 *
 */
public class ServiceBayTest {
	private ServiceBay sb1;
	/**Sets up the ability to test the service bay class
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ServiceBay.startBayNumberingAt101();
		sb1 = new ServiceBay();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.ServiceBay#startBayNumberingAt101()}. Tests static
	 * method of starting a bay number at 101.
	 */
	@Test
	public void testStartBayNumberingAt101() {
		ServiceBay.startBayNumberingAt101();
		ServiceBay sb2 = new ServiceBay();
		assertEquals("101", sb2.getBayID());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.ServiceBay#ServiceBay(java.lang.String)}. Tests creating a
	 * service bay by passing in a string.
	 */
	@Test
	public void testServiceBayString() {
		ServiceBay sb2 = new ServiceBay("P");
		assertEquals("P02", sb2.getBayID());
		ServiceBay sb3 = new ServiceBay(" P ");
		assertEquals("P03", sb3.getBayID());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.ServiceBay#isOccupied()}. Test seeing if bay is occupied.
	 */
	@Test
	public void testIsOccupied() {
		assertFalse(sb1.isOccupied());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.ServiceBay#release()}. Tests releasing a vehicle from
	 * a service bay.
	 */
	@Test
	public void testRelease() {
		RegularCar rc = null;
		try{
			rc = new RegularCar("DC-405", "John Dee", 3);
			sb1.occupy(rc);
		} catch (Exception e){
			System.out.println("Caught Exception occupying or creating car");
		}
		assertTrue(sb1.isOccupied());
		assertEquals(rc, sb1.release());
		assertFalse(sb1.isOccupied());
		assertNull(sb1.release());
	}
	
	/**
	 * Test method for occupy service bay.
	 */
	@Test
	public void occupy() {
		RegularCar rc = null;
		HybridElectricCar hbec = null;
		try{
			rc = new RegularCar("DC-405", "John Dee", 3);
			sb1.occupy(rc);
		} catch (Exception e){
			System.out.println("Caught Exception occupying or creating car");
		}
		assertTrue(sb1.isOccupied());
		assertEquals(sb1.release(), rc);
		try{
			hbec = new HybridElectricCar("EDC-405", "John Dow", 2);
			sb1.occupy(hbec);
		} catch (Exception e){
			System.out.println("Caught Exception occupying or creating car");
		}
		assertTrue(sb1.isOccupied());
		assertEquals(hbec, sb1.release());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.garage.model.service_garage.ServiceBay#toString()}. Tests the string method
	 * of a service bay.
	 */
	@Test
	public void testToString() {
		RegularCar rc = null;
		try{
			rc = new RegularCar("LIC-2", "Doe, John", 3);
			sb1.occupy(rc);
		} catch (Exception e){
			System.out.println("Caught Exception occupying or creating car");
		}
		assertEquals(sb1.toString(), "101: LIC-2    Doe, John");
		assertEquals(rc, sb1.release());
		assertEquals("101: EMPTY", sb1.toString());
	}

}

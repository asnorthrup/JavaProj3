package edu.ncsu.csc216.garage.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.garage.model.dealer.ServiceManager;
import edu.ncsu.csc216.garage.model.dealer.Manageable;
import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Simple commmand-line interface for a auto service garage.
 *  (There is no code to correct the placement of the cursor.)
 * The file "cars.txt" can serve as a source of initial data.
 * @author Jo Perry
 *
 */
public class RepairGarageConsole {
	
	private Manageable serviceMgr; // Service Garage backend	
	private String menu =       // Menu of choices
			"1) Add new vehicle\n2) Remove a vehicle\n3) Edit a vehicle tier\n4) Fill the service bays\n5) Complete servicing a vehicle\n" +
			"6) Print the vehicles waiting for service\n7) Print the service bays\n8) Open a service bay\n9) Quit\n"
			+ "\nEnter Selection: ";
	private String filter = ""; // Restricts the waiting list display to vehicles whose owners' names begin with the filter
	private Scanner s = new Scanner(System.in); // Scans user input

	/**
	 * Creates a new console with vehicle wait list drawn from "cars.txt" and 8 service bays.
	 * If "cars.txt" is not available, the patient list is initially empty
	 */
	public RepairGarageConsole() {
		File f = new File("cars.txt");
		try {
			serviceMgr =  new ServiceManager(new Scanner(f));
		} catch (FileNotFoundException e) {
			System.err.println("ServiceManager constructor: Starting with an empty file.");
			serviceMgr = new ServiceManager();
		}
	}
	
	/**
	 * Prints the menu of user choices.
	 * @return the user's menu choice. Errors result in re-calling printMenu().
	 */
	public int printMenu() {
		System.out.print(menu);
		Scanner s1 = new Scanner(System.in);
		try {
			return s1.nextInt();	 
		}
		catch (Exception e)	{
			return printMenu();
		}
	}
	
	/**
	 * Displays the menu and carries out the user's menu choice:<br>
	 *   1. Add new vehicle  <br>
	 *   2. Remove a vehicle <br>
	 *   3. Edit a service tier <br>
	 *   4. Fill the service bays <br>
	 *   5. Complete servicing a vehicle<br>
	 *   6. Print the vehicles waiting for service <br>
	 *   7. Print the service bays<br>
	 *   8. Open a service bay<br>
	 *   9. Quit
	 */
	public void run() {	
		switch (printMenu()) {
		case 1: //	Admit a new vehicle to the wait list
			System.out.print("\nADD VEHICLE\n\nOwner Name: ");
			String name = s.nextLine().trim();
			System.out.print("License: ");
			String license = s.nextLine().trim();
			System.out.print("Customer tier (3 = Platinum, 2 = Gold, 1 = Silver, 0 = None): ");
			int tier = s.nextInt();
			System.out.print("Hybrid/Electric? <y/n>: ");
			String kind = s.next();
			if (kind.equalsIgnoreCase("y"))
				serviceMgr.putOnWaitingList("E", license, name, tier);
			else
				serviceMgr.putOnWaitingList("R", license, name, tier);
			break;
		case 2: //	Remove a vehicle from the wait list. The position is relative to the filter
			System.out.print("\nREMOVE\n\nRemove vehicle at location on filtered list: ");
			int psn = s.nextInt();
			serviceMgr.remove(filter, psn);
			break;
		case 3: // Edit the tier of a vehicle in the wait list
			System.out.print("\nEDIT\n\nEdit tier of vehicle at location on filtered list: ");
			int psnEdit = s.nextInt();
			Vehicle v = (Vehicle)serviceMgr.remove(filter, psnEdit);
			System.out.print("Enter a new tier for vehicle [" + v.getLicense() + " " + v.getName() + "]:");
			int newTier = s.nextInt();
			try{
				v.setTier(newTier);
				serviceMgr.putOnWaitingList(v);
			} catch(BadVehicleInformationException e)
			{
				System.out.print(e.getMessage());
			}			
			break;	
		case 4: //	Fill the service bays with vehicles on the waiting list
			System.out.println("\nFILL SERVICE BAYS \n\n");
			serviceMgr.fillServiceBays();
			break;
		case 5: //	Release a vehicle from the service bays
			System.out.print("\nRELEASE\n\nRelease from bay with numeric index: ");
			psn = s.nextInt();
			serviceMgr.releaseFromService(psn);
			break;
		case 6: //	Print the list of vehicles on the waiting list
			System.out.print("\nPRINT FILTERED VEHICLE LIST\n\nEnter filter (return for no filter): ");
			filter = s.nextLine().trim();
			System.out.println("\n" + serviceMgr.printWaitList(filter));
			break;
		case 7: //	Print the contents of the service bays
			System.out.println("\nPRINT SERVICE BAYS \n\n" + serviceMgr.printServiceBays());
			break;
		case 8: //  Add a new service bay to the garage; 
			System.out.println("\nADD NEW BAY \n\n");
			serviceMgr.addNewBay();
			break;	
		case 9: // Exit the program
			System.out.println("\nEXIT PROGRAM \n\n");
			System.exit(0);
			break;
		default:
			break;
		}
		while(true);
	}
	
	/**
	 * Main method to start program execution.
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("Use this simple application to do preliminary functional testing on the Service Garage project.\n "
				+ "There is no no error checking.\n"
				+ "The cursor location varies according to the menu selection.\n\n");
		(new RepairGarageConsole()).run();
	}
	
}
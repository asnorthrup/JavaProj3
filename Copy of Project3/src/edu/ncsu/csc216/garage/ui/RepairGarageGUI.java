/**
 * 
 */
package edu.ncsu.csc216.garage.ui;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edu.ncsu.csc216.garage.model.dealer.ServiceManager;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;



/**Creates the GUI for the repair garage backend
 * @author AndrewNorthrup
 *
 */
public class RepairGarageGUI extends JFrame implements ActionListener {
		// Window, button, and scrollpane strings
		private final static String TITLE = "Service Garage Manager";
		private final static String VEHICLESAWAITSERV = "Vehicles Awaiting Service";
		private final static String SERVBAYS = "Service Bays";
		private final static String BTN_ADDVEHIC = "Add New Vehicle";
		private final static String BTN_EDITSELECTEDVEHIC = "Edit Selected Vehicle";
		private final static String BTN_REMOVESELECTEDVEHIC = "Remove Selected Vehicle";
		private final static String FILTER = "Display Filter: ";
		private final static String BTN_ADDSERVBAY = "Add Service Bay";
		private final static String BTN_FILLSERVBAY = "Fill Service Bay";
		private final static String BTN_RELEASEBAY = "Finish Repair of Selected Vehicle";
		private final static String QUIT = "Quit";


		// Size constants for the window and scroll panes
		private final static int FRAME_WIDTH = 800;   // Width of main window
		private final static int FRAME_HEIGHT = 700;  // Height of main window
		private static final int SCROLL_LENGTH = 100; // Height of scroll displays
		private static final int PAD = 10;  // Panel padding

		// Panels, Boxes, and Borders
		private TitledBorder bdrVehicles = new TitledBorder(VEHICLESAWAITSERV);
		private TitledBorder bdrServBays = new TitledBorder(SERVBAYS);
		
		private JPanel pnlLeft = new JPanel(); //left side of screen
		private JPanel pnlRight = new JPanel(); //right side of screen
		private JPanel pnlVehicleButtons = new JPanel();  // Shows vehicles
		private JPanel pnlServiceBayButtons = new JPanel(); // Shows service bays
		private JPanel pnlVehicleFilterText = new JPanel(); // Holds new passenger data entry widgets
		//private JPanel pnlBayDisplay= new JPanel(); // Holds new passenger data entry widgets
		//private JPanel pnlQuit= new JPanel(); // Holds new passenger data entry widgets
		//private GridBagLayout gridbag = new GridBagLayout();  // Applied to pnlPassenger
		//private GridBagConstraints c = new GridBagConstraints(); // Constraints on gridbag **Dont want this
		
		// Buttons
		private JButton btnAdd = new JButton(BTN_ADDVEHIC); //To add new vehicle
		private JButton btnEditVehic = new JButton(BTN_EDITSELECTEDVEHIC); //To Edit Selected Vehicle
		private JButton btnRmvVehic = new JButton(BTN_REMOVESELECTEDVEHIC); //To Remove Selected Vehicle
		private JButton btnAddServBay = new JButton(BTN_ADDSERVBAY);  // To add service bay
		private JButton btnFillServBay = new JButton(BTN_FILLSERVBAY);  // To fill service bay
		private JButton btnReleaseBay = new JButton(BTN_RELEASEBAY);  // To finish repair of selected service bay
		private JButton btnQuit = new JButton(QUIT); // To quit the application

		// Labels for JTextField
		private final JLabel lblFilter = new JLabel(FILTER);

		// Text Field for filter
		private JTextField txtFilter = new JTextField(16); 
		// Scrolling list of vehicles on wait list
		private DefaultListModel<String> dlmWaitList = new DefaultListModel<String>(); 
		private JList<String> lstWaitList = new JList<String>(dlmWaitList);
		private JScrollPane scrollWaitList = new JScrollPane(lstWaitList);
		
		// Scrolling list of repair bays
		private DefaultListModel<String> dlmServiceBays = new DefaultListModel<String>(); 
		private JList<String> lstServiceBayList = new JList<String>(dlmServiceBays);
		private JScrollPane scrollServiceBayList = new JScrollPane(lstServiceBayList);

		// Backend model
		private ServiceManager myGarage; // The entity for this reservation system
		
		/**
		 * Constructor. Sets up the user interface and initializes the backend model.
		 * @param s for scanner to load garage with after file is read
		 */
		public RepairGarageGUI(Scanner s) {
			//Create the backend.
			if(s != null){
				myGarage = new ServiceManager(s);
			} else {
				myGarage = new ServiceManager();
			}
			initUI(); 
			this.setVisible(true);
		}
		
		/**
		 * Main method -- begins program execution. Will read in either a console input for a file pathand name
		 * or will open the dialog box if blank is put into console
		 * 
		 * @param args arg[0] is the data file to initialize the garage.
		 */
		public static void main(String[] args) {
		    // create a scanner so we can read the command-line input
		    Scanner scanner = new Scanner(System.in);
		    //  prompt for the file's name
		    System.out.print("Enter file to start from (or blank space for file navigator): ");
		    // get their input as a String
		    String fileName = scanner.nextLine();
		    fileName = fileName.trim();
			if (fileName.length() == 0 || fileName == null){
			//*******Opens File chooser since file name is null or length is zero*********	
				JOptionPane.showMessageDialog(null, "Blank entered. Browse to file through navigator.");
				JFileChooser fileChooser = new JFileChooser();
			    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	// Get the selected file
			    	java.io.File file = fileChooser.getSelectedFile();
			    	// Create a Scanner for the file
			    	Scanner input;
			    	try {
			    		input = new Scanner(file);
			    	} catch (FileNotFoundException e) {
			    		JOptionPane.showMessageDialog(null, "Vehicle file not found.", "Error", JOptionPane.ERROR_MESSAGE);
			    		input = null;
			    	}
			    	new RepairGarageGUI(input);
			    } else {
			      System.out.println("No file selected");
			    }		
//				new RepairGarageGUI(null);
//				System.out.println("Scanner not initialized");
			} else {
				Scanner fileScanner = null;
				try {
					fileScanner = new Scanner(new File(fileName));
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Vehicle file not found.", "Error", JOptionPane.ERROR_MESSAGE);
					fileScanner = null;
				}
				new RepairGarageGUI(fileScanner);
			}
		}
	
		// ------ Private Methods -----------------------------

		/**
		 * Private method - initializes the user interface.
		 */
		private void initUI() {
//			// Set up the panels and the list that make the UI
			setUpPanels();
			setUpLists();

			// Construct the main window and add listeners.
			setTitle(TITLE);
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			Container c = getContentPane();
			//c.setLayout(new FlowLayout());
			c.add(pnlLeft, BorderLayout.WEST); //these sizes will def be wrong
			c.add(pnlRight, BorderLayout.EAST); //these sizes will def be wrong
			setVisible(true);
			addListeners();

			// Make sure the application quits when the window is closed.
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					endExecution();
					System.exit(0);
				}
			});
		}
			
		/**
		 * Private method - Sets up the scrolling list of reservations.
		 */
		private void setUpLists() {
			// Load the data.
			loadBayList();
			loadWaitList("");
			lstWaitList.setFont(new Font("Courier", Font.PLAIN, 12));
			lstWaitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstServiceBayList.setFont(new Font("Courier", Font.PLAIN, 12));
			lstServiceBayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		}

		/**
		 * Private method - Determines the geometry of the main window.
		 */
		private void setUpPanels() {
			/*Panel plan is to have left and right jpanels that are border layouts. Top of left
			 * and right will be jpanels with buttons. Center of left will just be the 
			 * text area where vehicle waiting list is added. South of right will be exit
			 * button. Center of right will be text area where service bays are shown. 
			 */
			//************Panels********/
			// Create the left half of the window panel, as a border layout.
			pnlLeft.setLayout(new BorderLayout());
			pnlLeft.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD, PAD / 2, PAD, PAD));
			
			// Create the right half of the window panel, as a border layout.
			pnlRight.setLayout(new BorderLayout());
			pnlRight.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD, PAD / 2, PAD, PAD));
			
			//Create the panel for the vehicle buttons, will go on north of left panel
			pnlVehicleButtons.setLayout(new BoxLayout(pnlVehicleButtons, BoxLayout.Y_AXIS));
			pnlVehicleButtons.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD, PAD / 2, PAD, PAD));
			
			//Create the panel for the baylist buttons, will go on north of right panel
			pnlServiceBayButtons.setLayout(new BoxLayout(pnlServiceBayButtons, BoxLayout.Y_AXIS));
			pnlServiceBayButtons.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD, PAD / 2, PAD, PAD));
			
			//Create the panel for the filter label and filter textbox, will go in last to vehicle button panel
			pnlVehicleFilterText.setLayout(new FlowLayout());
			//pnlServiceBayButtons.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD, PAD / 2, PAD, PAD));
			
			
			//************Scrolls Lists***********/
			// Initialize the reservation list.
			scrollWaitList.setBorder(bdrVehicles);
			scrollWaitList.setPreferredSize(new Dimension(FRAME_WIDTH / 2 - 4
					* (PAD), SCROLL_LENGTH));
			
			// Initialize the service bay list.
			scrollServiceBayList.setBorder(bdrServBays);
			scrollServiceBayList.setPreferredSize(new Dimension(FRAME_WIDTH / 2 - 4
								* (PAD), SCROLL_LENGTH));

			//********Add Buttons to panels********
			//add vehicle buttons, which will go to North of left panel
			btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlVehicleButtons.add(btnAdd);
			btnEditVehic.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlVehicleButtons.add(btnEditVehic);
			btnRmvVehic.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlVehicleButtons.add(btnRmvVehic);
			//Add another flow layout panel to add the filter text box
			//next to the filter user enter text box
			pnlVehicleFilterText.add(lblFilter);
			pnlVehicleFilterText.add(txtFilter);
			pnlVehicleFilterText.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlVehicleButtons.add(pnlVehicleFilterText); //adds vehicle filter text to vehicle buttons
			
			//add service bay buttons, which will go to North of right panel
			btnAddServBay.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlServiceBayButtons.add(btnAddServBay);
			btnFillServBay.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlServiceBayButtons.add(btnFillServBay);
			btnReleaseBay.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlServiceBayButtons.add(btnReleaseBay);
			
			//add panels to left for vehicles
			pnlLeft.add(pnlVehicleButtons, BorderLayout.NORTH);
			pnlLeft.add(scrollWaitList, BorderLayout.CENTER);
			 
			//add panels to the right for service bays
			pnlRight.add(pnlServiceBayButtons, BorderLayout.NORTH);
			pnlRight.add(scrollServiceBayList, BorderLayout.CENTER);
			pnlRight.add(btnQuit, BorderLayout.SOUTH);
			
		}

		/**
		 * Private Method - Adds listeners to the buttons and groups radio buttons<br/>
		 *    with the default window/aisle choice set to window.
		 */
		private void addListeners() {
			btnQuit.addActionListener(this);
			btnAdd.addActionListener(this);
			btnEditVehic.addActionListener(this);
			btnRmvVehic.addActionListener(this);
			btnAddServBay.addActionListener(this);
			btnFillServBay.addActionListener(this);
			btnReleaseBay.addActionListener(this);
			txtFilter.addActionListener(this); 
		}
//
		/**
		 * Private Method - Loads the reservation list model from a string, with newline tokenizers.
		 */
		//Can pass in a string to load wait list if want to update waitlist
		//with string
		private void loadWaitList(String s) { 
			dlmWaitList.clear();
			StringTokenizer st = new StringTokenizer(
					myGarage.printWaitList(s), "\n");
			while (st.hasMoreTokens()) {
				dlmWaitList.addElement(st.nextToken());
			}
		}
		//call after loading, filling, releasing, etc.
		private void loadBayList() {
			dlmServiceBays.clear();
			StringTokenizer st = new StringTokenizer(
					myGarage.printServiceBays(), "\n");
			while (st.hasMoreTokens()) {
				dlmServiceBays.addElement(st.nextToken());
			}
		}
		
		/**
		 * Private Method - Ends execution of the program.
		 */
		private void endExecution() {
			System.exit(0);
		}
		
		// ------------- End Private Methods -------------------
		
//		 ------ Controller Methods ---------------------------		
		/**
		 * Defines actions to be performed on button clicks
		 * @param e button click (user event)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnQuit)){ // Quit the application
				endExecution();
			}
			if (e.getSource().equals(btnAdd)){ // Add vehicle
				UserDialog lastVehicleGetter = new UserDialog();
				lastVehicleGetter.setVisible(true);
				Vehicle v = lastVehicleGetter.getNewVehicle();
				if (v != null){
					myGarage.putOnWaitingList(v);
				}
				loadWaitList("");
			}
			if (e.getSource().equals(btnEditVehic)){ // Edit Vehicle
				String selectedVehic = myGarage.getWaitingItem(txtFilter.getText(), lstWaitList.getSelectedIndex()).toString();
				Scanner sv = new Scanner(selectedVehic);
				String type = sv.next();
				String tier = sv.next();
				String lic = sv.next();
				String own = sv.nextLine();
				int intTier = 0;
				//"None", "Silver", "Gold", "Platinum"
				if(tier.equalsIgnoreCase("silver")) { intTier = 1; }
				if(tier.equalsIgnoreCase("Gold")) { intTier = 2; }
				if(tier.equalsIgnoreCase("Platinum")) { intTier = 3; }
				EditUserDialog lastVehicleGetterEV = new EditUserDialog(type, lic, own, intTier);
				//Stopped here
				lastVehicleGetterEV.setVisible(true);
				Vehicle ev = lastVehicleGetterEV.getNewVehicle();
				if(ev != null){
					myGarage.remove(txtFilter.getText(), lstWaitList.getSelectedIndex());
					myGarage.putOnWaitingList(ev);
				}
				loadWaitList("");
			}
			if (e.getSource().equals(btnRmvVehic)){ // Remove Vehicle
				myGarage.remove(txtFilter.getText(), lstWaitList.getSelectedIndex()); 
				loadWaitList("");
			}
			if (e.getSource().equals(btnAddServBay)){ // Add service bay
				myGarage.addNewBay();
				loadBayList();
			}
			if (e.getSource().equals(btnFillServBay)){ // Fill Bays
				myGarage.fillServiceBays();
				loadWaitList("");
				loadBayList();
			}
			if (e.getSource().equals(btnReleaseBay)){ // Quit the application
				myGarage.releaseFromService(lstServiceBayList.getSelectedIndex());
				loadBayList();
			}
			if (e.getSource().equals(txtFilter)){ // Gets a filtered list from text box
				loadWaitList(txtFilter.getText());
			}
		}
		// --------End Controller Methods ---------------------

	//}

}

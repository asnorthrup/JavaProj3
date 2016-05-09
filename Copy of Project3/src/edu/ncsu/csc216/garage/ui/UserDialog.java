package edu.ncsu.csc216.garage.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.*;

import edu.ncsu.csc216.garage.model.vehicle.*;

/**
 * Implements a special dialog box to ask for information on a new vehicle.
 * @author Jo Perry
 *
 */
public class UserDialog extends JDialog {

	/** Border text */
	private final static String NEW_VEHICLE_INFO = "New Vehicle Information";
	/** Checkbox text */
	private final static String HYBRID = "Hybrid/Electric";
	/** Customer tier labels */
	private final static String[] TIERS = {"None", "Silver", "Gold", "Platinum"};
	/** User input fields, combo labels */
	private final static String[] INFO_LABELS = {"Customer", "License", "Service Tier"};
	/** Title bar text */
	private final static String TITLE_BAR = "Add New Vehicle";
	/** Cancel button text */
	private final static String CANCEL = "Cancel";
	/** Submit button text */
	private final static String SUBMIT = "Submit";	
	
	/** Panel with Submit & Cancel buttons */
	private JPanel pnlBtn = new JPanel();
	/** Main window panel, contains other panels */
	private JPanel pnlMain = new JPanel();
	/** Panel for laying out user input components */
	private JPanel pnlInfo = new JPanel();
	/** Constraints to lay out user input components */
	GridBagConstraints gbC = new GridBagConstraints();
	
	/** Submit button (when the user wants to complete the Add operation) */
	private JButton btnSubmit = new JButton(SUBMIT);
	/** Cancel button for aborting the operation */
	private JButton btnCancel = new JButton(CANCEL);
	
	/** User input labels */
	private JLabel[] lblInfo = new JLabel[3];
	/** Components for user input (text fields, combo) */
	private JComponent[] compInfo = new JComponent[3];

	/** Check box for Hybrid/Electric */
	private JCheckBox ckbxKind = new JCheckBox(HYBRID);
	/** Combo box for customer tier */
	private JComboBox<String> cboTier = new JComboBox<String>(TIERS);
	
	/** Result returned on successful SUBMIT */
	private Vehicle result = null;
	
	/**
	 * Constructor. Sets up the dialog window
	 */
	public UserDialog() {
		super((java.awt.Frame) null, TITLE_BAR, true);
		setUpGeometry();
		add(pnlMain, BorderLayout.CENTER);
		add(pnlBtn, BorderLayout.SOUTH);
		pack();
	}
	
	/**
	 * Private method -- sets layouts, adds panels and widgets
	 */
	private void setUpGeometry() {
		// The bottom panel will have Submit, Cancel buttons
		pnlBtn.setLayout(new FlowLayout());
		pnlBtn.add(btnSubmit);
		pnlBtn.add(btnCancel);

		// Put together the main panel
		pnlMain.setLayout(new BorderLayout());
		pnlMain.add(pnlBtn, BorderLayout.SOUTH);
		setUpInfo();		
		pnlMain.add(pnlInfo, BorderLayout.CENTER);
		
		// Set defaults for hybrid/electric and tier
		cboTier.setSelectedIndex(0);
		ckbxKind.setSelected(false);
		addActionListeners();
	}
	
	/**
	 * Private method. Creates the grid to lay out the labels and widgets.
	 */
	private void setUpInfo() {
	    // Initialize the layout grid constraints
		pnlInfo.setLayout(new GridBagLayout());
		gbC.weightx = 0.5;
		gbC.gridx = 0;
		gbC.gridy = 0;
		
		// Add the checkbox at the top. 
		pnlInfo.add(ckbxKind, gbC);		
		gbC.fill = GridBagConstraints.HORIZONTAL;
		
		// Add the other labels and widgets for user entry/selection
		for (int k = 0; k < 3; k++) {
			gbC.gridx = 0;
			gbC.gridy = 1 + k;
			lblInfo[k] = new JLabel(INFO_LABELS[k]);
			lblInfo[k].setHorizontalAlignment(SwingConstants.RIGHT);
			pnlInfo.add(lblInfo[k], gbC);
			gbC.gridx = 1;
			if (k < 2)
				compInfo[k] = new JTextField(20);
			else
				compInfo[k] = cboTier;
			pnlInfo.add(compInfo[k], gbC);		
		}
		pnlInfo.setBorder(BorderFactory.createTitledBorder(NEW_VEHICLE_INFO));
	}
	
	/**
	 * Adds internally defined action listeners to Submit and Cancel buttons
	 */
	private void addActionListeners() {
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Try creating a car out of the user input. With success, close the dialog.
				try {
					result = getInput();
					setVisible(false);
					dispose();
				} catch (BadVehicleInformationException e) {
					// Without success, show a message and let the user correct the errors.
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				result = null;
				setVisible(false);
				dispose();
			}
		});
	}

	/**
	 * Private method -- gets input from text fields
	 * @return Car created from the user's input
	 * @throws BadVehicleInformationException if a Car cannot be created from the given information
	 */
	private Vehicle getInput() throws BadVehicleInformationException{
		String name = ((JTextField)compInfo[0]).getText().trim();
		String license = ((JTextField)compInfo[1]).getText().trim();
			if (ckbxKind.isSelected())
				return new HybridElectricCar(license, name, cboTier.getSelectedIndex());
			return new RegularCar(license, name, cboTier.getSelectedIndex());
	}
	
	/**
	 * Returns the most recent result from this dialog box
	 * @return most recently created vehicle (or null if none was created)
	 */
	public Vehicle getNewVehicle() {
		return result;
	}
	
	
}
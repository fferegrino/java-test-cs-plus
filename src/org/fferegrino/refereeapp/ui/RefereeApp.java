package org.fferegrino.refereeapp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.fferegrino.refereeapp.entities.Referee;
import org.fferegrino.refereeapp.io.RefereeReader;
import org.fferegrino.refereeapp.ui.datamodels.RefereesTableModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class RefereeApp implements ActionListener {

	private JFrame frame;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("RefereesIn.txt");
		Scanner scanner = new Scanner(file);
		RefereeReader reader = new RefereeReader();
		ArrayList<Referee> referees = reader.getReferees(scanner);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RefereeApp window = new RefereeApp(referees);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	ArrayList<Referee> referees;
	private JTable refereesTable;

	private JTextPane firstNameSearchText;
	private JTextPane lastNameSearchText;
	
	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textMatches;
	JLabel lblIdValue; 
	
	JButton btnSearch;
	
	private JComboBox comboQualification;
	private JComboBox comboHome;
	private JCheckBox chckbxNorth;
	private JCheckBox chckbxCenter;
	private JCheckBox chckbxSouth;

	/**
	 * Create the application.
	 */
	public RefereeApp(ArrayList<Referee> referees) {
		this.referees = referees;
		initialize();

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		RefereesTableModel model = new RefereesTableModel(referees);
		refereesTable.setModel(model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JSplitPane refereesPanel = new JSplitPane();
		refereesPanel.setResizeWeight(0.9d);
		tabbedPane.addTab("Referees", null, refereesPanel, null);

		JPanel refereeDetailsPanel = new JPanel();
		refereesPanel.setRightComponent(refereeDetailsPanel);
		GridBagLayout gbl_refereeDetailsPanel = new GridBagLayout();
		gbl_refereeDetailsPanel.columnWidths = new int[] { 0, 0 };
		gbl_refereeDetailsPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_refereeDetailsPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_refereeDetailsPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		refereeDetailsPanel.setLayout(gbl_refereeDetailsPanel);

		JPanel searchRefereePanel = new JPanel();
		GridBagConstraints gbc_searchRefereePanel = new GridBagConstraints();
		gbc_searchRefereePanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchRefereePanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_searchRefereePanel.gridx = 0;
		gbc_searchRefereePanel.gridy = 0;
		refereeDetailsPanel.add(searchRefereePanel, gbc_searchRefereePanel);
		GridBagLayout gbl_searchRefereePanel = new GridBagLayout();
		gbl_searchRefereePanel.columnWidths = new int[] { 120, 120, 60 };
		gbl_searchRefereePanel.rowHeights = new int[] { 30, 30 };
		gbl_searchRefereePanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_searchRefereePanel.rowWeights = new double[] { 0.0, 0.0 };
		searchRefereePanel.setLayout(gbl_searchRefereePanel);

		JLabel lblFirstNameSearch = new JLabel("First name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 0;
		searchRefereePanel.add(lblFirstNameSearch, gbc_lblFirstName);

		JLabel lblLastNameSearch = new JLabel("Last name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 1;
		gbc_lblLastName.gridy = 0;
		searchRefereePanel.add(lblLastNameSearch, gbc_lblLastName);

		firstNameSearchText = new JTextPane();
		GridBagConstraints gbc_firstNameTextPane = new GridBagConstraints();
		gbc_firstNameTextPane.insets = new Insets(0, 0, 5, 0);
		gbc_firstNameTextPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstNameTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_firstNameTextPane.gridx = 0;
		gbc_firstNameTextPane.gridy = 1;
		searchRefereePanel.add(firstNameSearchText, gbc_firstNameTextPane);

		lastNameSearchText= new JTextPane();
		GridBagConstraints gbc_lastNameTextPane = new GridBagConstraints();
		gbc_lastNameTextPane.insets = new Insets(0, 0, 5, 0);
		gbc_lastNameTextPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameTextPane.gridx = 1;
		gbc_lastNameTextPane.gridy = 1;
		searchRefereePanel.add(lastNameSearchText, gbc_lastNameTextPane);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 1;
		searchRefereePanel.add(btnSearch, gbc_btnSearch);

		JPanel refereeDetails = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		refereeDetailsPanel.add(refereeDetails, gbc_panel);
		GridBagLayout gbl_refereeDetails = new GridBagLayout();
		gbl_refereeDetails.columnWidths = new int[] { 0, 0, 0 };
		gbl_refereeDetails.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_refereeDetails.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_refereeDetails.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		refereeDetails.setLayout(gbl_refereeDetails);

		JLabel lblId = new JLabel("ID:");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		refereeDetails.add(lblId, gbc_lblId);

		lblIdValue = new JLabel("---");
		lblIdValue.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblXx = new GridBagConstraints();
		gbc_lblXx.anchor = GridBagConstraints.WEST;
		gbc_lblXx.insets = new Insets(0, 0, 5, 0);
		gbc_lblXx.gridx = 1;
		gbc_lblXx.gridy = 0;
		refereeDetails.add(lblIdValue, gbc_lblXx);

		JLabel lblFirstName = new JLabel("First name:");
		GridBagConstraints gbc_lblFirstName_1 = new GridBagConstraints();
		gbc_lblFirstName_1.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName_1.gridx = 0;
		gbc_lblFirstName_1.gridy = 1;
		refereeDetails.add(lblFirstName, gbc_lblFirstName_1);

		textFirstName = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		refereeDetails.add(textFirstName, gbc_textField);
		textFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last name:");
		GridBagConstraints gbc_lblLastName_1 = new GridBagConstraints();
		gbc_lblLastName_1.anchor = GridBagConstraints.EAST;
		gbc_lblLastName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName_1.gridx = 0;
		gbc_lblLastName_1.gridy = 2;
		refereeDetails.add(lblLastName, gbc_lblLastName_1);

		textLastName = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		refereeDetails.add(textLastName, gbc_textField_1);
		textLastName.setColumns(10);

		JLabel lblQualification = new JLabel("Qualification:");
		GridBagConstraints gbc_lblQualification = new GridBagConstraints();
		gbc_lblQualification.anchor = GridBagConstraints.EAST;
		gbc_lblQualification.insets = new Insets(0, 0, 5, 5);
		gbc_lblQualification.gridx = 0;
		gbc_lblQualification.gridy = 3;
		refereeDetails.add(lblQualification, gbc_lblQualification);

		comboQualification = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		refereeDetails.add(comboQualification, gbc_comboBox);

		JLabel lblMatches = new JLabel("Matches:");
		GridBagConstraints gbc_lblMatches = new GridBagConstraints();
		gbc_lblMatches.anchor = GridBagConstraints.EAST;
		gbc_lblMatches.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatches.gridx = 0;
		gbc_lblMatches.gridy = 4;
		refereeDetails.add(lblMatches, gbc_lblMatches);

		textMatches = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 4;
		refereeDetails.add(textMatches, gbc_textField_2);
		textMatches.setColumns(10);

		JLabel lblHome = new JLabel("Home:");
		lblHome.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblHome = new GridBagConstraints();
		gbc_lblHome.anchor = GridBagConstraints.EAST;
		gbc_lblHome.insets = new Insets(0, 0, 5, 5);
		gbc_lblHome.gridx = 0;
		gbc_lblHome.gridy = 5;
		refereeDetails.add(lblHome, gbc_lblHome);

		comboHome = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 5;
		refereeDetails.add(comboHome, gbc_comboBox_1);

		JLabel lblWillingToVisit = new JLabel("Willing to visit:");
		GridBagConstraints gbc_lblWillingToVisit = new GridBagConstraints();
		gbc_lblWillingToVisit.insets = new Insets(0, 0, 5, 5);
		gbc_lblWillingToVisit.gridx = 0;
		gbc_lblWillingToVisit.gridy = 6;
		refereeDetails.add(lblWillingToVisit, gbc_lblWillingToVisit);

		JPanel checksPanel = new JPanel();
		GridBagConstraints gbc_checksPanel = new GridBagConstraints();
		gbc_checksPanel.insets = new Insets(0, 0, 5, 0);
		gbc_checksPanel.fill = GridBagConstraints.BOTH;
		gbc_checksPanel.gridx = 1;
		gbc_checksPanel.gridy = 6;
		refereeDetails.add(checksPanel, gbc_checksPanel);
		checksPanel.setLayout(new GridLayout(0, 3, 0, 0));

		chckbxNorth = new JCheckBox("North");
		checksPanel.add(chckbxNorth);

		chckbxCenter = new JCheckBox("Center");
		checksPanel.add(chckbxCenter);

		chckbxSouth = new JCheckBox("South");
		checksPanel.add(chckbxSouth);

		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 8;
		refereeDetails.add(btnSave, gbc_btnSave);

		JButton btnDelete = new JButton("Delete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 9;
		refereeDetails.add(btnDelete, gbc_btnDelete);

		JButton btnNew = new JButton("New");
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.insets = new Insets(0, 0, 5, 0);
		gbc_btnNew.gridx = 1;
		gbc_btnNew.gridy = 10;
		refereeDetails.add(btnNew, gbc_btnNew);

		refereesTable = new JTable();

		JScrollPane refereesScrollPanel = new JScrollPane(refereesTable);
		refereesPanel.setLeftComponent(refereesScrollPanel);
	}

	protected JTable getRefereesTable() {
		return refereesTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSearch) // Búsqueda
		{
			String firstNameToSearch = firstNameSearchText.getText();
			String lastNameToSearch = lastNameSearchText.getText();
			
			Referee coincidencia = null;
			for(int i = 0; i < referees.size(); i++)
			{
				if(referees.get(i).getFirstName().equals(firstNameToSearch) &&
						referees.get(i).getLastName().equals(lastNameToSearch) )
				{
					coincidencia = referees.get(i);
					break;
				}
			}
			
			if(coincidencia != null)
			{
				lblIdValue.setText(coincidencia.getId());
				textFirstName.setText(coincidencia.getFirstName());
				textLastName.setText(coincidencia.getLastName());
				textMatches.setText(String.valueOf(coincidencia.getAllocatedMatches()));
			}
			else {
				JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");
			}
		}
		
	}

}

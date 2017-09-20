package org.fferegrino.refereeapp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.fferegrino.refereeapp.entities.Area;
import org.fferegrino.refereeapp.entities.AwardingBody;
import org.fferegrino.refereeapp.entities.Referee;
import org.fferegrino.refereeapp.entities.Match;
import org.fferegrino.refereeapp.io.MatchesWriter;
import org.fferegrino.refereeapp.io.RefereeReader;
import org.fferegrino.refereeapp.io.RefereeWriter;
import org.fferegrino.refereeapp.ui.datamodels.*;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;

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
	ArrayList<Match> matches;
	MatchesTableModel matchesTableModel;
	RefereesTableModel refereesTableModel;
	SuitableRefereesListModel suitableRefereesListModel;
	private JTable refereesTable;
	private JList refereeList;

	private JTextPane firstNameSearchText;
	private JTextPane lastNameSearchText;

	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textMatches;
	JLabel lblIdValue;

	JButton btnSearch;
	JButton btnCreateMatch;
	JButton btnClear;
	JButton btnSave;
	JButton btnNew;
	JButton btnDelete;

	private JComboBox comboQualification;
	private JComboBox comboHomeReferee;
	private JComboBox comboAreaAddMatch;
	private JComboBox comboLevelAddMatch;
	private JCheckBox chckbxNorth;
	private JCheckBox chckbxCenter;
	private JCheckBox chckbxSouth;
	private JTable matchesTable;
	private JTextField textWeek;

	JMenuItem mntmExit;
	private JMenuItem mntmViewChart;

	/**
	 * Create the application.
	 */
	public RefereeApp(ArrayList<Referee> referees) {
		this.referees = referees;
		initialize();

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				saveReferees();
				saveMatches();
			}
		});

		refereesTableModel = new RefereesTableModel(referees);
		refereesTable.setModel(refereesTableModel);

		matches = new ArrayList<Match>();
		matchesTableModel = new MatchesTableModel(matches);
		matchesTable.setModel(matchesTableModel);

		suitableRefereesListModel = new SuitableRefereesListModel(referees);
		refereeList.setModel(suitableRefereesListModel);
		SuitableRefereesSelectionModel sm = new SuitableRefereesSelectionModel(refereeList, 2);
		refereeList.setSelectionModel(sm);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mntmExit = new JMenuItem("Exit");
		menuBar.add(mntmExit);
		mntmExit.addActionListener(this);
		
		mntmViewChart = new JMenuItem("View chart");
		menuBar.add(mntmViewChart);
		mntmViewChart.addActionListener(this);
		
		setRefereeCheckboxes();
	}

	private void saveReferees() {
		try {
			PrintWriter pw = new PrintWriter("RefereesOut.txt", "UTF-8");
			RefereeWriter rw = new RefereeWriter();
			rw.writeReferees(pw, referees);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void saveMatches() {
		try {
			PrintWriter pw = new PrintWriter("MatchAllocs.txt", "UTF-8");
			MatchesWriter mw = new MatchesWriter();
			mw.writeMatches(pw, matches);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		lastNameSearchText = new JTextPane();
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
		GridBagConstraints gbc_textFirstName = new GridBagConstraints();
		gbc_textFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_textFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFirstName.gridx = 1;
		gbc_textFirstName.gridy = 1;
		refereeDetails.add(textFirstName, gbc_textFirstName);
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

		String[] qualifications = { "NJB1", "IJB1", "NJB2", "IJB2", "NJB3", "IJB3", "NJB4", "IJB4" };

		comboQualification = new JComboBox(qualifications);
		GridBagConstraints gbc_comboQualification = new GridBagConstraints();
		gbc_comboQualification.insets = new Insets(0, 0, 5, 0);
		gbc_comboQualification.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboQualification.gridx = 1;
		gbc_comboQualification.gridy = 3;
		refereeDetails.add(comboQualification, gbc_comboQualification);

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

		comboHomeReferee = new JComboBox(Area.values());
		comboHomeReferee.addActionListener(this);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 5;
		refereeDetails.add(comboHomeReferee, gbc_comboBox_1);

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

		btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClear.gridwidth = 2;
		gbc_btnClear.insets = new Insets(0, 0, 5, 5);
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 7;
		refereeDetails.add(btnClear, gbc_btnClear);

		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSave.gridwidth = 2;
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 8;
		refereeDetails.add(btnSave, gbc_btnSave);

		btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.RED);
		btnDelete.addActionListener(this);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 10;
		refereeDetails.add(btnDelete, gbc_btnDelete);

		btnNew = new JButton("New");
		btnNew.addActionListener(this);
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.gridwidth = 2;
		gbc_btnNew.insets = new Insets(0, 0, 5, 0);
		gbc_btnNew.gridx = 0;
		gbc_btnNew.gridy = 9;
		refereeDetails.add(btnNew, gbc_btnNew);

		refereesTable = new JTable();

		JScrollPane refereesScrollPanel = new JScrollPane(refereesTable);
		refereesPanel.setLeftComponent(refereesScrollPanel);

		JSplitPane matchesPane = new JSplitPane();
		tabbedPane.addTab("Matches", null, matchesPane, null);

		matchesTable = new JTable();

		JScrollPane matchesScrollPanel = new JScrollPane(matchesTable);
		matchesPane.setLeftComponent(matchesScrollPanel);

		JPanel matchesDetailsPanel = new JPanel();
		matchesPane.setRightComponent(matchesDetailsPanel);
		GridBagLayout gbl_matchesDetailsPanel = new GridBagLayout();
		gbl_matchesDetailsPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_matchesDetailsPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_matchesDetailsPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_matchesDetailsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		matchesDetailsPanel.setLayout(gbl_matchesDetailsPanel);

		JLabel lblWeek = new JLabel("Week:");
		GridBagConstraints gbc_lblWeek = new GridBagConstraints();
		gbc_lblWeek.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeek.anchor = GridBagConstraints.EAST;
		gbc_lblWeek.gridx = 0;
		gbc_lblWeek.gridy = 0;
		matchesDetailsPanel.add(lblWeek, gbc_lblWeek);

		textWeek = new JTextField();
		GridBagConstraints gbc_textWeek = new GridBagConstraints();
		gbc_textWeek.insets = new Insets(0, 0, 5, 0);
		gbc_textWeek.fill = GridBagConstraints.HORIZONTAL;
		gbc_textWeek.gridx = 1;
		gbc_textWeek.gridy = 0;
		matchesDetailsPanel.add(textWeek, gbc_textWeek);
		textWeek.setColumns(10);

		JLabel lblArea = new JLabel("Area:");
		GridBagConstraints gbc_lblArea = new GridBagConstraints();
		gbc_lblArea.anchor = GridBagConstraints.EAST;
		gbc_lblArea.insets = new Insets(0, 0, 5, 5);
		gbc_lblArea.gridx = 0;
		gbc_lblArea.gridy = 1;
		matchesDetailsPanel.add(lblArea, gbc_lblArea);

		comboAreaAddMatch = new JComboBox(Area.values());
		comboAreaAddMatch.addActionListener(this);
		GridBagConstraints gbc_comboArea = new GridBagConstraints();
		gbc_comboArea.insets = new Insets(0, 0, 5, 0);
		gbc_comboArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboArea.gridx = 1;
		gbc_comboArea.gridy = 1;
		matchesDetailsPanel.add(comboAreaAddMatch, gbc_comboArea);

		JLabel lblLevel = new JLabel("Level:");
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.anchor = GridBagConstraints.EAST;
		gbc_lblLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLevel.gridx = 0;
		gbc_lblLevel.gridy = 2;
		matchesDetailsPanel.add(lblLevel, gbc_lblLevel);

		comboLevelAddMatch = new JComboBox(Match.LEVELS);
		comboLevelAddMatch.addActionListener(this);
		GridBagConstraints gbc_comboLevel = new GridBagConstraints();
		gbc_comboLevel.insets = new Insets(0, 0, 5, 0);
		gbc_comboLevel.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLevel.gridx = 1;
		gbc_comboLevel.gridy = 2;
		matchesDetailsPanel.add(comboLevelAddMatch, gbc_comboLevel);

		btnCreateMatch = new JButton("Create match");
		btnCreateMatch.addActionListener(this);

		JLabel lblReferees = new JLabel("Referees:");
		GridBagConstraints gbc_lblReferees = new GridBagConstraints();
		gbc_lblReferees.insets = new Insets(0, 0, 5, 5);
		gbc_lblReferees.gridx = 0;
		gbc_lblReferees.gridy = 3;
		matchesDetailsPanel.add(lblReferees, gbc_lblReferees);

		refereeList = new JList();
		GridBagConstraints gbc_refereeList = new GridBagConstraints();
		gbc_refereeList.insets = new Insets(0, 0, 5, 0);
		gbc_refereeList.fill = GridBagConstraints.HORIZONTAL;
		gbc_refereeList.gridx = 1;
		gbc_refereeList.gridy = 3;
		matchesDetailsPanel.add(refereeList, gbc_refereeList);
		GridBagConstraints gbc_btnCreateMatch = new GridBagConstraints();
		gbc_btnCreateMatch.gridwidth = 2;
		gbc_btnCreateMatch.gridx = 0;
		gbc_btnCreateMatch.gridy = 4;
		matchesDetailsPanel.add(btnCreateMatch, gbc_btnCreateMatch);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) // Búsqueda
		{
			String firstNameToSearch = firstNameSearchText.getText();
			String lastNameToSearch = lastNameSearchText.getText();

			Referee coincidencia = null;
			for (int i = 0; i < referees.size(); i++) {
				if (referees.get(i).getFirstName().equals(firstNameToSearch)
						&& referees.get(i).getLastName().equals(lastNameToSearch)) {
					coincidencia = referees.get(i);
					break;
				}
			}

			if (coincidencia != null) {
				lblIdValue.setText(coincidencia.getId());
				textFirstName.setText(coincidencia.getFirstName());
				textLastName.setText(coincidencia.getLastName());

				textMatches.setText(String.valueOf(coincidencia.getAllocatedMatches()));
				comboQualification.setSelectedItem(coincidencia.getQualification());
				comboHomeReferee.setSelectedItem(coincidencia.getHome());

				chckbxNorth.setSelected(coincidencia.willingToTravelTo(Area.NORTH));
				chckbxCenter.setSelected(coincidencia.willingToTravelTo(Area.CENTRAL));
				chckbxSouth.setSelected(coincidencia.willingToTravelTo(Area.SOUTH));

				chckbxNorth.setEnabled(true);
				chckbxCenter.setEnabled(true);
				chckbxSouth.setEnabled(true);

				if (coincidencia.getHome() == Area.NORTH)
					chckbxNorth.setEnabled(false);
				if (coincidencia.getHome() == Area.CENTRAL)
					chckbxCenter.setEnabled(false);
				if (coincidencia.getHome() == Area.SOUTH)
					chckbxSouth.setEnabled(false);

				textFirstName.setEnabled(false);
				textLastName.setEnabled(false);
				textMatches.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(frame, "Referee not found");
			}
		} else if (e.getSource() == btnClear) {
			lblIdValue.setText("---");

			textFirstName.setText("");
			textLastName.setText("");
			textMatches.setText("");

			chckbxNorth.setEnabled(true);
			chckbxCenter.setEnabled(true);
			chckbxSouth.setEnabled(true);

			chckbxNorth.setSelected(false);
			chckbxCenter.setSelected(false);
			chckbxSouth.setSelected(false);

			textFirstName.setEnabled(true);
			textLastName.setEnabled(true);
			textMatches.setEnabled(true);

		} else if (e.getSource() == btnSave) {

			String id = lblIdValue.getText();
			Referee ref = null;
			int r = -1;
			for (int i = 0; i < referees.size(); i++) {
				ref = referees.get(i);
				if (ref.getId().equals(id)) {
					r = i;
					break;
				}
			}

			if (r >= 0) {
				String qualification = (String) comboQualification.getSelectedItem();
				Area home = (Area) comboHomeReferee.getSelectedItem();

				String north = chckbxNorth.isSelected() ? "Y" : "N";
				String center = chckbxCenter.isSelected() ? "Y" : "N";
				String south = chckbxSouth.isSelected() ? "Y" : "N";

				String awardingBody = qualification.substring(0, 3);

				if (awardingBody.equals("NJB"))
					ref.setQualificationAwardingBody(AwardingBody.NJB);
				else if (awardingBody.equals("IJB"))
					ref.setQualificationAwardingBody(AwardingBody.IJB);
				ref.setQualificationLevel(qualification.charAt(3) - '0');

				ref.setLocalities(north + south + center);
				ref.setHome(home);
				refereesTableModel.fireTableDataChanged();
			}
		} else if (e.getSource() == btnCreateMatch) {
			String error = "";
			Match m = new Match();
			m.area = (Area) comboAreaAddMatch.getSelectedItem();
			m.level = (String) comboLevelAddMatch.getSelectedItem();

			try {
				m.week = Integer.parseInt(textWeek.getText());
			} catch (Exception ex) {
				error += "You must set a valid week\n";
			}

			int[] selectedReferees = refereeList.getSelectedIndices();
			if (selectedReferees.length == 2) {
				m.referee1 = suitableRefereesListModel.getSuitableReferees()[selectedReferees[0]];
				m.referee2 = suitableRefereesListModel.getSuitableReferees()[selectedReferees[1]];

				m.referee1.setAllocatedMatches(m.referee1.getAllocatedMatches() + 1);
				m.referee2.setAllocatedMatches(m.referee2.getAllocatedMatches() + 1);

				refereesTableModel.fireTableDataChanged();
			} else {
				error += "You must select 2 referees\n";
			}

			if (error.equals("")) {
				matches.add(m);
				matchesTableModel.fireTableRowsInserted(0, matches.size());
			} else {
				JOptionPane.showMessageDialog(frame, error);
			}

		} else if (e.getSource() == btnDelete) {
			String id = lblIdValue.getText();
			Referee ref = null;
			int r = -1;
			for (int i = 0; i < referees.size(); i++) {
				ref = referees.get(i);
				if (ref.getId().equals(id)) {
					r = i;
					break;
				}
			}
			if (r >= 0) {
				referees.remove(r);
				refereesTableModel.fireTableRowsDeleted(0, referees.size());
			}
		} else if (e.getSource() == comboAreaAddMatch || e.getSource() == comboLevelAddMatch) {
			suitableRefereesListModel.setConditions((String) comboLevelAddMatch.getSelectedItem(),
					(Area) comboAreaAddMatch.getSelectedItem());

			if (suitableRefereesListModel.getSize() >= 2) {
				int[] indices = { 0, 1 };
				refereeList.setSelectedIndices(indices);
			} else {
				JOptionPane.showMessageDialog(frame, "No suitable referees matched the selected criteria");
			}
		} else if (e.getSource() == btnNew) {

			String error = "";
			Referee newref = new Referee();

			String firstName = textFirstName.getText();
			String lastName = textLastName.getText();
			String allocatedMatches = textMatches.getText();
			String qualification = (String) comboQualification.getSelectedItem();
			Area home = (Area) comboHomeReferee.getSelectedItem();
			String north = chckbxNorth.isSelected() ? "Y" : "N";
			String center = chckbxCenter.isSelected() ? "Y" : "N";
			String south = chckbxSouth.isSelected() ? "Y" : "N";
			String awardingBody = qualification.substring(0, 3);

			if (firstName.equals("") || lastName.equals("")) {
				error += "A referee must have a full name\n";
			} else {
				newref.setFirstName(firstName);
				newref.setLastName(lastName);
			}

			if (allocatedMatches.equals(""))
				allocatedMatches = "0";

			try {

				newref.setAllocatedMatches(Integer.parseInt(allocatedMatches));
				if (newref.getAllocatedMatches() < 0) {
					error += "Please provide positive number for the allocated matches\n";
				}
			} catch (Exception ee) {
				error += "Please provide a number for the allocated matches\n";
			}

			newref.setQualificationLevel(qualification.charAt(3) - '0');

			if (awardingBody.equals("NJB"))
				newref.setQualificationAwardingBody(AwardingBody.NJB);
			else if (awardingBody.equals("IJB"))
				newref.setQualificationAwardingBody(AwardingBody.IJB);

			newref.setLocalities(north + south + center);
			newref.setHome(home);

			if (referees.size() >= 12) {
				error += "Cannot add a new referee. The limit of 12 has been reached.\n";
			}

			if (error.equals("")) {
				String myNewRefId = newref.getId().substring(0, 2);
				int newId = referees.stream()
						.filter(r -> r.getId().startsWith(myNewRefId))
						.map(r -> Integer.parseInt(r.getId().substring(2)))
						.max((i1, i2) -> i1 - i2).get() + 1;
				newref.setSequenceNumber(newId);
				referees.add(newref);
				refereesTableModel.fireTableRowsInserted(0, referees.size());
			} else {
				JOptionPane.showMessageDialog(frame, error);
			}
		} else if (e.getSource() == mntmExit) {
			saveReferees();
			saveMatches();
			System.exit(0);
		} else if (e.getSource() == comboHomeReferee) {

			setRefereeCheckboxes();
		} else if (e.getSource() == mntmExit) {
			saveReferees();
			saveMatches();
			System.exit(0);
		} else if (e.getSource() == mntmViewChart) {

			ChartPanel frame = new ChartPanel(referees);
			frame.setVisible(true);
		}
	}

	private void setRefereeCheckboxes() {
		Area selected = (Area) comboHomeReferee.getSelectedItem();

		chckbxNorth.setEnabled(true);
		chckbxCenter.setEnabled(true);
		chckbxSouth.setEnabled(true);

		if (selected == Area.NORTH) {
			chckbxNorth.setEnabled(false);
			chckbxNorth.setSelected(true);
		} else if (selected == Area.CENTRAL) {
			chckbxCenter.setEnabled(false);
			chckbxCenter.setSelected(true);
		} else if (selected == Area.SOUTH) {
			chckbxSouth.setEnabled(false);
			chckbxSouth.setSelected(true);
		}
	}

}

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

public class RefereeApp {

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

	/**
	 * Create the application.
	 */
	public RefereeApp(ArrayList<Referee> referees) {
		this.referees = referees;
		initialize();

		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		RefereesTableModel model = new RefereesTableModel(referees);
		refereesTable.setModel(model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JSplitPane refereesPanel = new JSplitPane();
		refereesPanel.setResizeWeight(0.5d);
		tabbedPane.addTab("Referees", null, refereesPanel, null);

		JPanel panel = new JPanel();
		refereesPanel.setRightComponent(panel);

		refereesTable = new JTable();
		tabbedPane.addTab("New tab", null, refereesTable, null);

		JScrollPane refereesScrollPanel = new JScrollPane(refereesTable);
		refereesPanel.setLeftComponent(refereesScrollPanel);
	}

	protected JTable getRefereesTable() {
		return refereesTable;
	}

}

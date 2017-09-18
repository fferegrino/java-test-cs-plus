package org.fferegrino.refereeapp.ui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import org.fferegrino.refereeapp.entities.Referee;
import org.fferegrino.refereeapp.io.RefereeReader;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class RefereeApp {

	private JFrame frame;
	private JTextArea refereeList;

	/**
	 * Launch the application.
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

	/**
	 * Create the application.
	 */
	public RefereeApp(ArrayList<Referee> referees) {
		this.referees = referees;
		initialize();
		
		 
		   for(int i = 0; i < referees.size(); i++)
		   {
			   Referee r = referees.get(i);
			   this.refereeList.append(r.toString() + "\n");
		   }
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
		
		JPanel refereesPanel = new JPanel();
		tabbedPane.addTab("Referees", null, refereesPanel, null);
		
		refereeList = new JTextArea();
		refereeList.setEditable(false);
		refereesPanel.add(refereeList);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}

	protected JTextArea getRefereeList() {
		return refereeList;
	}
}

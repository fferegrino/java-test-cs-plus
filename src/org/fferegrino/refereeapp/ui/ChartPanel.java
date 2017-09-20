package org.fferegrino.refereeapp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fferegrino.refereeapp.entities.Referee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ChartPanel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChartPanel frame = new ChartPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	

	public static final int PANEL_WIDTH = 700;
	public static final int PANEL_PADDING = 30;
	public static final int PANEL_HEIGHT = 350;
	public static final int BARS_PADDING = 7;
	
	private ArrayList<Referee> refs;

	/**
	 * Create the frame.
	 */
	public ChartPanel(ArrayList<Referee> referees) {
		this.refs = referees;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, PANEL_WIDTH, PANEL_HEIGHT + 40);
		contentPane = new DrawablePanel();
		setContentPane(contentPane);
	}
	

	class DrawablePanel extends JPanel {
		public void paintComponent(Graphics g) {
			int chartWidth = PANEL_WIDTH - PANEL_PADDING * 2;
			int chartHeight = PANEL_HEIGHT - PANEL_PADDING * 2;
			int lowerBorder = PANEL_HEIGHT - PANEL_PADDING;
			int rightmostBorder = PANEL_WIDTH - PANEL_PADDING;
			
			int[] allocations = new int[refs.size()];
			for (int i = 0; i < refs.size(); i++)
			{
				allocations[i] = refs.get(i).getAllocatedMatches();
			}
			
			
			double max = (double)Arrays.stream(allocations).max().getAsInt();
			
			double unitSize = chartHeight / (max + 2);
			
			double barWidth =  (chartWidth / (double)allocations.length) - BARS_PADDING;
			
			// Draw axis:
			g.drawLine(PANEL_PADDING, chartHeight, chartWidth, chartHeight); // X axis
			g.drawLine(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, chartHeight); // Y axis
			

			int currentX = PANEL_PADDING;
			
			for (Referee re : refs) {
				int i =  re.getAllocatedMatches();
				int barX = currentX;
				int barY = chartHeight - (int)(unitSize * i);
				g.setColor(new Color((i*3 * i) % 255, (i*2* i *4) % 255,(i*i) % 225));
				g.fillRect(currentX, barY, (int)barWidth, (int)(unitSize * i));

				g.setColor(Color.black);
				g.drawString(String.valueOf(i), (int)(currentX + (barWidth / 2)) - 10, barY - 5);
				g.drawString(re.getId(), currentX, chartHeight + 15);
				currentX += (int)barWidth + BARS_PADDING;
				
			}
			
		}
	}

}

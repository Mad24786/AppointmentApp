package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nrw.hspv.util.HspvColor;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {
	
	public DashboardPanel() {
		initComponents();
		createEvents();
	}
	
	public void initComponents(){
		setLayout(new BorderLayout());
		
		
		JPanel centerPanel = new JPanel();		
		centerPanel.setLayout(new GridLayout(5, 7, 5, 5));
		centerPanel.setBackground(Color.WHITE);
		for (int i = 0; i < 35; i++) {
			JPanel panel = new CalendarPanel(i);
			centerPanel.add(panel);
		}	

		
		JPanel eastPanel = new JPanel();
		JLabel lblEast = new JLabel("Termine heute");
		eastPanel.setBackground(Color.WHITE);
		eastPanel.add(lblEast);
		eastPanel.setPreferredSize(new Dimension(250, 0));
//		eastPanel.setMinimumSize(new Dimension(500, 0));
		add(centerPanel, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
	}
	
	public void createEvents() {
		
	}
	
	public class CalendarPanel extends JPanel {

		int i;
		
		public CalendarPanel(int i) {
			
			this.i = i;
			
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			
			setBackground(HspvColor.SEC_BROWN);
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString(String.valueOf(i), 5, 15);
		}

	}

	public static void main(String[] args) {
		
		new DashboardPanel();

	}

}

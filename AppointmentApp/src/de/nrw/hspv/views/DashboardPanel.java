package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nrw.hspv.database.Get;
import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {
	
	public DashboardPanel() {
		initComponents();
		createEvents();
	}
	
	public void initComponents(){
		
		/** Initialize calendar vars */
		int selectedMonth, selectedYear;
		Calendar cal = new GregorianCalendar();
		int cDay = cal.get(Calendar.DATE);
		int cMonth = selectedMonth = cal.get(Calendar.MONTH);
		int cYear = selectedYear = cal.get(Calendar.YEAR);
		
		/** */
		setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.setBackground(Color.WHITE);
		JLabel lblDate = new JLabel(monthToString(cMonth) + " " + cYear);
		northPanel.add(lblDate);
		add(northPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();		
		centerPanel.setLayout(new GridLayout(5, 7, 5, 5));
		centerPanel.setBackground(Color.WHITE);
		
		/** TEST BEGINN **/
		GregorianCalendar gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int days =gCal.getActualMaximum(Calendar.DATE);
		int startInWeek = gCal.get(Calendar.DAY_OF_WEEK);
		
		gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int totalweeks = gCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		int count = 1; // count the days
		for (int i = 1; i <= totalweeks; i++) {
			for(int j = 1; j <= 7; j++) {
				JPanel panel;
				if (count < startInWeek || (count - startInWeek + 1) > days) {
					// no day here
					panel = new CalendarPanel(0, Color.WHITE);
					centerPanel.add(panel);
				}
				else {
					if(cDay == (count - startInWeek + 1) && cMonth == selectedMonth && cYear == selectedYear) {
						// marks the current day
						panel = new CalendarPanel((count - startInWeek + 1), HspvColor.ORANGE);
						centerPanel.add(panel);
					}
					else {
						// every other day
						panel = new CalendarPanel((count - startInWeek + 1), HspvColor.SEC_BROWN);
						centerPanel.add(panel);
					}
					panel.addMouseListener(new MyMouseListener());
				}
				count++;
			}
			
		}
		
		/** TEST ENDE **/

		
		JPanel eastPanel = new JPanel(new FlowLayout());
		JLabel lblEast = new JLabel("Termine heute");
		eastPanel.setBackground(Color.WHITE);
		eastPanel.add(lblEast);
		
		
		ArrayList<Appointment> allAppointments = Get.appointments.getAllAsArrayList();
		Date dt = new Date();
		dt.setHours(0);
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		Date tomorrow = c.getTime();
		
		Collections.sort(allAppointments);
		for(Appointment i : allAppointments) {
			
			if(i.getDateAndTime().after(dt) && i.getDateAndTime().before(tomorrow)) {
				JLabel lblTestEast = new JLabel(i.getDateAndTime().toString());
				eastPanel.add(lblTestEast);
			}
				
		}
		eastPanel.setPreferredSize(new Dimension(250, 0));
//		eastPanel.setMinimumSize(new Dimension(500, 0));
		add(centerPanel, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
	}
	
	public void createEvents() {
		
	}
	
	public String monthToString(int m) {
		switch (m) {
		case 0: {
			return "Januar";
		}
		case 1: {
			return "Feburar";
		}
		case 2: {
			return "März";
		}
		case 3: {
			return "April";
		}
		default:
			return "ein anderer Monat";
		}
	}
	
	public class MyMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(((CalendarPanel) e.getComponent()).getDay());
		}
	}
	
	public class CalendarPanel extends JPanel {

		int day;
		
		public CalendarPanel(int day, Color c) {
			
			this.day = day;
			
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			
			setBackground(c);
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(day != 0) {
				g.drawString(String.valueOf(day), 5, 15);
			}
		}

		public int getDay() {
			return day;
		}

	}

	public static void main(String[] args) {
		
		new DashboardPanel();

	}

}

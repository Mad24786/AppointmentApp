package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;

@SuppressWarnings("serial")
public class DashboardPanel extends JPanel {
	
	/*
	 * DashboardPanel Layout
	 */
//	public static CardLayout layout = new CardLayout();
	
	/*
	 * Get instance of Calendar
	 */
	public static Calendar c = Calendar.getInstance();
	
	/*
	 * Components
	 */
	public static JPanel mainPanel = new JPanel();
	public static JPanel centerPanel;
	public static JPanel eastPanel;
	public static CalendarPanel[] panel = new CalendarPanel[32];
	
	public static JLabel lblEast;
	
	/*
	 *  Initialize calendar vars 
	 */
	public static int selectedMonth, selectedYear;
	public static Calendar cal = new GregorianCalendar();
	public static int cDay = cal.get(Calendar.DATE);
	public static int cMonth = selectedMonth = cal.get(Calendar.MONTH);
	public static int cYear = selectedYear = cal.get(Calendar.YEAR);
	// format pattern
	public static SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
	public static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	
	/* 
	 * Fetch appointments data 
	 */
	public static ArrayList<Appointment> allAppointments;		
	
	/*
	 * settings
	 */
	public static boolean showUserAppointments = false;
	public static boolean logEvents = true;
	
	public DashboardPanel() {
		allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		initComponents();
		createEvents();
	}
	
	public void initComponents(){	
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		
		/** */
		mainPanel.setLayout(new BorderLayout(0,0));
		mainPanel.setBackground(Color.WHITE);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.setBackground(Color.WHITE);
		JLabel lblDate = new JLabel(monthToString(cMonth) + " " + cYear);
		northPanel.add(lblDate);		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		centerPanel = new JPanel(new GridLayout(0, 7, 5, 5));		
		centerPanel.setBackground(Color.WHITE);
		
		for (int i = 0; i < 7; i++) {
			centerPanel.add(new WeekdayLabel(i));
		}
		
		/** TEST BEGINN **/
//		buildDashboardCalendar();
		allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		GregorianCalendar gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int days =gCal.getActualMaximum(Calendar.DATE);
		int startInWeek = gCal.get(Calendar.DAY_OF_WEEK);

		gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int totalweeks = gCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		/* Build dashboard calendar */
		if ((startInWeek == 7 && days > 29) || (startInWeek == 6 && days > 30)) {
			totalweeks +=1;
		}
		
		int count = 1; // count the days
		for (int i = 1; i <= totalweeks; i++) {
			for(int j = 1; j <= 7; j++) {
				
				if (count < startInWeek || (count - startInWeek + 1) > days) {
					// no day here
					panel[0] = new CalendarPanel(0, Color.WHITE);
					centerPanel.add(panel[0]);
				}
				else {
					if(cDay == (count - startInWeek + 1) && cMonth == selectedMonth && cYear == selectedYear) {
						// marks the current day
						panel[(count - startInWeek + 1)] = new CalendarPanel((count - startInWeek + 1), HspvColor.ORANGE);
						centerPanel.add(panel[(count - startInWeek + 1)]);
					}
					else {
						// every other day
						panel[(count - startInWeek + 1)] = new CalendarPanel((count - startInWeek + 1), HspvColor.SEC_BROWN);
						centerPanel.add(panel[(count - startInWeek + 1)]);
					}
					panel[(count - startInWeek + 1)].addMouseListener(new MyMouseListener());
				}
				count++;
			}
			
		}
		/** TEST ENDE **/

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		//Create the panel that contains the "cards".
//        cards = new JPanel(new CardLayout());
//        JPanel defaultCard = new AppointmentsByDate(cDay);
//        
//        
//        cards.add(defaultCard, Integer.toString(cDay));
//        for (int i = 1; i <= days; i++) {
//        	if (i != cDay) {
//        		JPanel anotherCard = new AppointmentsByDate(i);
//            	cards.add(anotherCard, Integer.toString(i));
//        	}
//        }
//        
//		mainPanel.add(cards, BorderLayout.EAST);
			
		add(mainPanel);
		
	}
	
	public static void buildDashboardCalendar() {
		centerPanel.removeAll();
		allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		GregorianCalendar gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int days =gCal.getActualMaximum(Calendar.DATE);
		int startInWeek = gCal.get(Calendar.DAY_OF_WEEK);

		gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		int totalweeks = gCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		/* Build dashboard calendar */
		if ((startInWeek == 7 && days > 29) || (startInWeek == 6 && days > 30)) {
			totalweeks +=1;
		}
		
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
//					panel.addMouseListener(new MyMouseListener());
				}
				count++;
			}
			
		}
		AppointmentApp.mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Events for calendar panels in dashboard panel will be added here.
	 */
	public void createEvents() {
		
		for (JPanel p : panel) {
			p.addMouseListener(new MyMouseListener());
		}
		
	}
	
	public static class AppointmentsByDate extends JPanel {
				
		public AppointmentsByDate(int d, boolean singleUserAppointments) {
			AppointmentApp.appointmentListDay = d;
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			setLayout(new FlowLayout());
			setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(250, 10));
			setName(Integer.toString(d));
			lblEast = new JLabel("Termine " + d + ". " + monthToString(cMonth) + " " + cYear);
			
			JCheckBox chbAppointments = new JCheckBox("nur meine Termine anzeigen");
			chbAppointments.setSelected(showUserAppointments);
			chbAppointments.setBackground(Color.WHITE);
			chbAppointments.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(chbAppointments.isSelected()) {
						showUserAppointments = true;
						AppointmentApp.mainPanel.remove(AppointmentApp.mainLayout.getLayoutComponent(BorderLayout.EAST));
						AppointmentApp.mainPanel.add(new AppointmentsByDate(AppointmentApp.sDay, showUserAppointments), BorderLayout.EAST);
						AppointmentApp.mainPanel.validate();
					}
					else {
						showUserAppointments = false;
						AppointmentApp.mainPanel.remove(AppointmentApp.mainLayout.getLayoutComponent(BorderLayout.EAST));
						AppointmentApp.mainPanel.add(new AppointmentsByDate(AppointmentApp.sDay, showUserAppointments), BorderLayout.EAST);
						AppointmentApp.mainPanel.validate();
					}
						
				}
			});
			add(lblEast);
			
			/* set time to given day 0:00 */
			c.setTime(new Date());
			c.set(Calendar.DATE, d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today = c.getTime();
			/* set time to next day 0:00 */
			c.add(Calendar.DATE, 1);
			Date tomorrow = c.getTime();
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			Collections.sort(allAppointments);
			for(Appointment i : allAppointments) {
				
				if (singleUserAppointments) {
					if(i.getStart().after(today) && i.getStart().before(tomorrow) && AppointmentApp.user.getId() == i.getEmployee().getId()) {
						/* if appointment is after today 0:00 and tomorrow 0:00 put it out */
						JPanel lblTestEast = new SmallAppointmentPanel(i);
						lblTestEast.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								new AppointmentFrame(i);
							}
							
						});
						add(lblTestEast);
					}
				}
				else {
					if(i.getStart().after(today) && i.getStart().before(tomorrow)) {
						/* if appointment is after today 0:00 and tomorrow 0:00 put it out */
						JPanel lblTestEast = new SmallAppointmentPanel(i);
						lblTestEast.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								new AppointmentFrame(i);
							}
							
						});
						add(lblTestEast);
					}
				}
				
				
					
			}
			add(chbAppointments);
		
		}
		
		public class SmallAppointmentPanel extends JPanel {
			
			Appointment a;

			public SmallAppointmentPanel(Appointment a) {
				this.a = a;
				setPreferredSize(new Dimension(200, 30));
				setBackground(HspvColor.SEC_BROWN);
				setToolTipText("<html>#" + a.getId() + ": " + ((a.getMessage() == "") ? "keine weiteren Angaben" : a.getMessage()) +
						"<br/>Eingetragen von: " + a.getAuthor().getLastName() + ", " + a.getAuthor().getFirstName() + "</html>");
			}
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				String end = null;
				if(a.getEnd() != null ) {
					end = sdfTime.format(a.getEnd());
				}
				
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(sdfTime.format(a.getStart()) + " - " + end + ": " + a.getIssue().getName(), 5, 12);					
				g.drawString(a.getEmployee().getLastName() + ", " + a.getEmployee().getFirstName(), 5, 26);					
	
			}
			
			/**
			 * 
			 */
			@Override
			public JToolTip createToolTip() {
			    JToolTip tooltip = super.createToolTip();
			    tooltip.setBorder(BorderFactory.createLineBorder(HspvColor.GRAY));
			    tooltip.setBackground(HspvColor.ORANGE);  
			    tooltip.setForeground(Color.BLACK);
			    return tooltip;
			}

		}
		
		public static int getCount(int d) {
			int appointmentCount = 0;
					
			/* set time to given day 0:00 */
			c.setTime(new Date());
			c.set(Calendar.DATE, d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today = c.getTime();
			/* set time to next day 0:00 */
			c.add(Calendar.DATE, 1);
			Date tomorrow = c.getTime();
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			for(Appointment i : allAppointments) {
				
				if(i.getStart().after(today) && i.getStart().before(tomorrow)) {
					/* if appointment is after today 0:00 and tomorrow 0:00 put it out */
					appointmentCount++;
				}
					
			}
			return appointmentCount;
		}
		
	}
	
	public static String monthToString(int m) {
		switch (m) {
	        case 0:  return "Januar";
	        case 1:  return "Februar";
	        case 2:  return "M�rz";
	        case 3:  return "April";
	        case 4:  return "Mai";
	        case 5:  return "Juni";
	        case 6:  return "Juli";
	        case 7:  return "August";
	        case 8:  return "September";
	        case 9:  return "Oktober";
	        case 10: return "November";
	        case 11: return "Dezember";
	        default: return "Invalid month";
		}
	}
	
	public static class MyMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {		
			refreshAppointmentList(((CalendarPanel) e.getComponent()).getDay());
		}
	}
	public static void refreshAppointmentList(int day) {
		AppointmentApp.mainPanel.remove(AppointmentApp.mainLayout.getLayoutComponent(BorderLayout.EAST));
		AppointmentApp.sDay = (day);
		AppointmentApp.mainPanel.add(new AppointmentsByDate(AppointmentApp.sDay, showUserAppointments), BorderLayout.EAST);
		AppointmentApp.mainPanel.validate();
	}
	public static class CalendarPanel extends JPanel {

		int day;
		public JLabel lblAppCount;
		
		public CalendarPanel(int day, Color c) {
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			this.day = day;
			
			
			
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			setLayout(new BorderLayout(2,2));
			setBackground(c);
			
			/*
			 * add label only if it is a colored panel
			 */
			if(c.equals(Color.WHITE) == false) {
				/* label for date */
				JLabel lblDate = new JLabel(" " + Integer.toString(day));
				add(lblDate, BorderLayout.NORTH);
				
				/* label for appointment count */
				lblAppCount = new JLabel("Termine: " + Integer.toString(AppointmentsByDate.getCount(day)) + " ");
				lblAppCount.setHorizontalAlignment(SwingConstants.RIGHT);
				lblAppCount.setFont(new Font(lblDate.getFont().toString(), Font.PLAIN, 11));
				add(lblAppCount, BorderLayout.SOUTH);
			}
			
		}

		public int getDay() {
			return day;
		}

	}
	
	
	
	public class WeekdayLabel extends JLabel {

		int index;
		String[] weekdays = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
		
		public WeekdayLabel(int i) {
			this.index = i;
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.BOTTOM);
			setText(weekdays[index]);
			
		}
	}

	public static void main(String[] args) {
		
		//new DashboardPanel();

	}

}

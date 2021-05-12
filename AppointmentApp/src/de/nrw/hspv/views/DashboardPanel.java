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
import java.io.Serializable;
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

/**
 * In this class the dashboard panel (as an instance of JPanel) will be created.
 * This object is instanced by default and will be instanced, if the user clicks 
 * on the dashboard icon in main navigation. No special rights provided.
 * 
 * From this panel the user is able to see coming appointments by month.
 * 
 * @author Mathias Fernahl
 * @version 17 May 2021
 */
public class DashboardPanel extends JPanel {

	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Components
	 */
	private static JPanel mainPanel = new JPanel();
	private static JPanel centerPanel;
	static CalendarPanel[] panel = new CalendarPanel[32];
	
	public static JLabel lblEast;
	
	/*
	 * Get instance of Calendar
	 */
	public static Calendar c = Calendar.getInstance();
	
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
	
	/**
	 * Constructor to initialize components and create events
	 * in this panel. 
	 */
	public DashboardPanel() {
		initComponents();
		createEvents();
	}
	
	/**
	 * Initializes all needed components in this panel
	 * and feeds them with data if needed.
	 */
	public void initComponents(){	
		/*
		 * set default layout values
		 */
		setBackground(Color.WHITE);	
		
		/*
		 * set default layout values for main panel
		 */
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		
		/*
		 * the upper panel just shows a label with current month and year
		 */
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.setBackground(Color.WHITE);
		JLabel lblDate = new JLabel(monthToString(cMonth) + " " + cYear);
		lblDate.setFont(new Font(lblDate.getFont().toString(), Font.PLAIN, 24));
		northPanel.add(lblDate);		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		/*
		 * calendar in center panel is going to be created
		 * with a GridLayout
		 */
		centerPanel = new JPanel(new GridLayout(0, 7, 5, 5));		
		centerPanel.setBackground(Color.WHITE);
		// add labels with weekdays string to the top
		for (int i = 0; i < 7; i++) {
			centerPanel.add(new WeekdayLabel(i));
		}
		
		allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		// a new calendar from the 1st of current month in current year
		GregorianCalendar gCal = new GregorianCalendar(selectedYear, selectedMonth, 1);
		// count of days in this month
		int days = gCal.getActualMaximum(Calendar.DATE);
		// the day of week of 1st, e.g. sunday equals 0
		int startInWeek = gCal.get(Calendar.DAY_OF_WEEK);

		// the count of weeks this month strechtes
		int totalweeks = gCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		// in this defined cases a month is going to be stretched over 6 weeks, so we have to add 1
		if ((startInWeek == 7 && days > 29) || (startInWeek == 6 && days > 30)) {
			totalweeks +=1;
		}
		// start counting the days
		int count = 1;
		// outer for-loop is setting the rows
		for (int i = 1; i <= totalweeks; i++) {
			// inner for-loop is setting the columns
			for(int j = 1; j <= 7; j++) {
				// under this following conditions there is no day to show
				// this panels will be set before the 1st and after the last day of the month
				if (count < startInWeek || (count - startInWeek + 1) > days) {
					// no day here
					panel[0] = new CalendarPanel(0, Color.WHITE);
					centerPanel.add(panel[0]);
				}
				else {
					// now we have a day to show...
					// ...maybe today?
					if(cDay == (count - startInWeek + 1) && cMonth == selectedMonth && cYear == selectedYear) {
						// marks the current day in an eyecatching orange
						panel[(count - startInWeek + 1)] = new CalendarPanel((count - startInWeek + 1), HspvColor.ORANGE);
					}
					else {
						// every other day to be shown in brown
						panel[(count - startInWeek + 1)] = new CalendarPanel((count - startInWeek + 1), HspvColor.SEC_BROWN);
					}
					centerPanel.add(panel[(count - startInWeek + 1)]);
				}
				count++;
			}
			
		}
		// ready! add it to the center of main panel
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		add(mainPanel);
		
	}
	
	/**
	 * Events for calendar panels in dashboard panel are added here.
	 */
	public void createEvents() {
		// calendar panels get a mouse listener to show all appointments of a day in right panel by click
		for (JPanel p : panel) {
			p.addMouseListener(new MyMouseListener());
		}
		
	}
	
	/**
	 * This class creates an object to show a list of all appointments of one day
	 * in right panel of the application. By default it shows the list of
	 * appointments for today. Users can select another day from dashboard panel.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 *
	 */
	public static class AppointmentsByDate extends JPanel {
				
		/**
		 * A unique serial version identifier.
		 * 
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * This object is completly builded here.
		 * 
		 * @param d								The day to show appoiontments for.
		 * @param singleUserAppointments		True if user only wants to see own appointments.
		 * 										False shows appoiontments of all emplpoyees.
		 */
		public AppointmentsByDate(int d, boolean singleUserAppointments) {
			// remember the day to show
			AppointmentApp.appointmentListDay = d;
			
			// set default layout values with FlowLayout
			setLayout(new FlowLayout());
			setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(250, 10));
			setName(Integer.toString(d));
			
			// label with selected date as header
			lblEast = new JLabel("Termine " + d + ". " + monthToString(cMonth) + " " + cYear);
			add(lblEast);
			
			// set time to given day 0:00 - 'today'
			c.setTime(new Date());
			c.set(Calendar.DATE, d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today = c.getTime();
			// set time to next day 0:00 - 'tomorrow'
			c.add(Calendar.DATE, 1);
			Date tomorrow = c.getTime();
			
			// get a fresh list of all appointments and sort them
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			Collections.sort(allAppointments);
			// ruin through appointments
			for(Appointment i : allAppointments) {
				// check if user wants to see only own appointments
				if (singleUserAppointments) {
					if(i.getStart().after(today) && i.getStart().before(tomorrow) && AppointmentApp.user.getId() == i.getEmployee().getId()) {
						// if appointment is between today 0:00 and tomorrow 0:00 and current user is the user to
						// do this appointment, create a SmallAppointmentPanel and add it
						JPanel lblTestEast = new SmallAppointmentPanel(i);
						add(lblTestEast);
					}
				}
				// user wants to see all appointments
				else {
					if(i.getStart().after(today) && i.getStart().before(tomorrow)) {
						// if appointment is between today 0:00 and tomorrow 0:00, create a SmallAppointmentPanel and add it
						JPanel lblTestEast = new SmallAppointmentPanel(i);
						add(lblTestEast);
					}
				}		
			}
			
			// the check box to select individiual view
			JCheckBox chbAppointments = new JCheckBox("nur meine Termine anzeigen");
			chbAppointments.setSelected(AppointmentApp.showUserAppointments);
			chbAppointments.setBackground(Color.WHITE);
			chbAppointments.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					// on change switch settings
					if(chbAppointments.isSelected()) {
						AppointmentApp.showUserAppointments = true;
					}
					else {
						AppointmentApp.showUserAppointments = false;
					}
					// change the panel
					AppointmentApp.mainPanel.remove(AppointmentApp.mainLayout.getLayoutComponent(BorderLayout.EAST));
					AppointmentApp.mainPanel.add(new AppointmentsByDate(AppointmentApp.sDay, AppointmentApp.showUserAppointments), BorderLayout.EAST);
					AppointmentApp.mainPanel.validate();
						
				}
			});
			add(chbAppointments);
		
		}
		
		/**
		 * A small <code>JPanel</code> for a single appointment is created here.
		 * 
		 * @author Mathias Fernahl
		 * @version 17 May 2021
		 *
		 */
		public class SmallAppointmentPanel extends JPanel {
			
			/**
			 * A unique serial version identifier.
			 * 
			 * @see Serializable#serialVersionUID
			 */
			private static final long serialVersionUID = 1L;
			
			private Appointment a;
			
			/**
			 * This panel is build here.
			 * 
			 * @param a 		The appointment to draw in this panel
			 */
			public SmallAppointmentPanel(Appointment a) {
				this.a = a;
				setPreferredSize(new Dimension(200, 30));
				setBackground(HspvColor.SEC_BROWN);
				// a short ToolTip to show further information
				setToolTipText("<html>#" + a.getId() + ": " + ((a.getMessage() == "") ? "keine weiteren Angaben" : a.getMessage()) +
						"<br/>Eingetragen von: " + a.getAuthor().getLastName() + ", " + a.getAuthor().getFirstName() + "</html>");
			}
			
			/**
			 * Draws some appointment information on this panel.
			 */
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString(sdfTime.format(a.getStart()) + " - " + sdfTime.format(a.getEnd()) + ": " + a.getIssue().getName(), 5, 12);					
				g.drawString(a.getEmployee().getLastName() + ", " + a.getEmployee().getFirstName(), 5, 26);					
	
			}
			
			/**
			 * Customizes the <code>JToolTip</code>.
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
		
		/**
		 * Gets the count of appointments for a specific day.
		 * 
		 * @param d		The day to count appointments for.
		 * @return		The count of appointments for <code>d</code>s appointments.
		 */
		public static int getCount(int d) {
			int appointmentCount = 0;
					
			// set time to given day 0:00 - 'today'
			c.setTime(new Date());
			c.set(Calendar.DATE, d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today = c.getTime();
			// set time to next day 0:00 - 'tomorrow'
			c.add(Calendar.DATE, 1);
			Date tomorrow = c.getTime();
			// get a fresh list of all appointments
			allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
			for(Appointment i : allAppointments) {
				if(i.getStart().after(today) && i.getStart().before(tomorrow)) {
					// if appointment is between today 0:00 and tomorrow 0:00, count */
					appointmentCount++;
				}				
			}
			return appointmentCount;
		}
		
	}
	
	/**
	 * Changes a month from Integer to String.
	 * 
	 * @param m 	A month as Integer.
	 * @return		The month as a german String.
	 */
	public static String monthToString(int m) {
		switch (m) {
	        case 0:  return "Januar";
	        case 1:  return "Februar";
	        case 2:  return "März";
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
	
	/**
	 * The MouseListener for <code>CalendarPanel</code>s to show appointments by day in
	 * the right panel.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 *
	 */
	public static class MyMouseListener extends MouseAdapter{
		/**
		 * Calls <code>refreshAppointmentList</code> for selected day.
		 * 
		 * @see DashboardPanel
		 */
		@Override
		public void mouseClicked(MouseEvent e) {		
			refreshAppointmentList(((CalendarPanel) e.getComponent()).getDay());
		}
	}
	
	/**
	 * Refreshes the list of daily appointments in right dashboard panel.
	 *  
	 * @param day 	The day that is currently shown by users selection.
	 */
	public static void refreshAppointmentList(int day) {
		AppointmentApp.mainPanel.remove(AppointmentApp.mainLayout.getLayoutComponent(BorderLayout.EAST));
		AppointmentApp.sDay = day;
		AppointmentApp.mainPanel.add(new AppointmentsByDate(AppointmentApp.sDay, AppointmentApp.showUserAppointments), BorderLayout.EAST);
		AppointmentApp.mainPanel.validate();
	}
	
	/**
	 * Objects from <code>CalendarPanel</code> are squared panels
	 * to look in sum like a big calendar labeld with day of month and
	 * count of appointments at each day.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 */
	public static class CalendarPanel extends JPanel {

		/**
		 * A unique serial version identifier.
		 * 
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/*
		 * the day of month
		 */
		int day;
		
		/*
		 * label for appoiontment count
		 */
		public JLabel lblAppCount;
		
		/**
		 * Constructor creates a new squared calendar panel.
		 * 
		 * @param day   	The day you want to create this panel for.
		 * @param c			The background color of this panel.
		 */
		public CalendarPanel(int day, Color c) {
			
			// set object variable
			this.day = day;
			
			// set default layout values
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			setLayout(new BorderLayout(2,2));
			setBackground(c);
			
			// add label only if it is a colored panel
			if((c.equals(Color.WHITE)) == false) {
				// label for the date
				JLabel lblDate = new JLabel(" " + Integer.toString(day));
				add(lblDate, BorderLayout.NORTH);
				
				// label for appointment count
				lblAppCount = new JLabel("Termine: " + Integer.toString(AppointmentsByDate.getCount(day)) + " ");
				lblAppCount.setHorizontalAlignment(SwingConstants.RIGHT);
				lblAppCount.setFont(new Font(lblDate.getFont().toString(), Font.PLAIN, 11));
				add(lblAppCount, BorderLayout.SOUTH);
			}
			
		}
		/**
		 * @return the day of this calendar panel
		 */
		public int getDay() {
			return day;
		}

	}
	
	
	/**
	 * This class creates weekday labels for dashboard calendar.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 *
	 */
	public class WeekdayLabel extends JLabel {

		/**
		 * A unique serial version identifier.
		 * 
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		String[] weekdays = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
		
		/**
		 * Constructor to set up the label by getting the day of week.
		 * 
		 * @param i		The day of week. Only 0 to 6.
		 */
		public WeekdayLabel(int i) {
			// if given parameter is wrong, return and do nothing
			if (i < 0 || i > 6)
				return;
			// else build the label
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.BOTTOM);
			setText(weekdays[i]);
		}
		
	}
	
}

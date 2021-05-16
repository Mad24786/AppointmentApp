package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;
import de.nrw.hspv.views.DashboardPanel.AppointmentsByDate;
import de.nrw.hspv.views.DashboardPanel.CalendarPanel;

/**
 * This class creates an AppointmentFrame as an instance of JFrame for
 * inserting new appointment.
 * 
 * @author Mathias Fernahl
 * @version 17 May 2021
 * @see javax.swing.JFrame
 *
 */
public class AppointmentFrame extends JFrame {
	
	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * The layout components of this Frame
	 */
	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel mainPanel = new JPanel(mainLayout);
	public static JPanel centerPanel = new JPanel();
	public static JPanel southPanel;
	
	/*
	 * Components for input and output
	 */
	public static JComboBox<Issue> cbIssue;
	public static JComboBox<User> cbUser;
	public static JSpinner spinner;
	public static JTextArea txtText;
	public static JLabel errMsg = new JLabel();
	
	/*
	 * Icons in this Frame
	 */
	public static JPanel addIcon;
	public static JPanel editIcon;
	public static JPanel deleteIcon;
	
	/*
	 * Buttons in this Frame
	 */	
	public static JButton btnOk = new JButton("OK");
	public static JButton btnCancel = new JButton("Abbrechen");
	
	/*
	 * Vectors for JComboBox
	 */
	public static Vector<Issue> vecIssues = new Vector<Issue>();
	public static Vector<User> vecUsers = new Vector<User>();
	
	/**
	 * Instantiate the appointment frame with components and events 
	 */
	public AppointmentFrame(){
		initComponents();
		createEvents();
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		/*
		 * set default layout values for the frame
		 */
		setSize(new Dimension(640,240));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int x = (AppointmentApp.screenSize.width - this.getSize().width) / 2;
	    int y = (AppointmentApp.screenSize.height - this.getSize().height) / 2;
	    setLocation(x, y);
	   
		/* 
		 * set default values for main panel
		 */
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(mainLayout);
				
		/*
		 * set default values for lower panel
		 * buttons and label for messages are going to be added here
		 */
		southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		southPanel.add(btnOk);
		southPanel.add(btnCancel);
		errMsg.setForeground(HspvColor.ORANGE);
		southPanel.add(errMsg);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		/*
		 * set up the form for creating an appointment    
		 * in center panel with GridBagLayout
		 */
		GridBagLayout gb = new GridBagLayout();
		centerPanel.setLayout(gb);
		centerPanel.setBackground(Color.WHITE);

		/*
		 * JComboBox for issue selection
		 */
		JLabel lblIssue = new JLabel("Anliegen:");
		addComp(gb, lblIssue, 0, 0, 1, 1);
		
		ArrayList<Issue> allIssues = AppointmentApp.ISSUES.getAllAsArrayList();
		Collections.sort(allIssues);
		for(Issue i : allIssues) {
			vecIssues.add(i);
		}
		
		// fill JComboBox with data
		cbIssue = new JComboBox<Issue>(vecIssues);
        cbIssue.addFocusListener(new MyFocusListener());
        addComp(gb, cbIssue, 1, 0, 1, 1);
		
        /*
         * set up a spinner to choose date and time of new appointment
         */
		JLabel lblDate = new JLabel("Datum:");
		addComp(gb, lblDate, 0, 1, 1, 1);

        Calendar calendar = Calendar.getInstance();

        Date initDate = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 8, 0).getTime();
        calendar.add(Calendar.YEAR, -1);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
        
        spinner = new JSpinner(dateModel);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().addFocusListener(new MyFocusListener());
        spinner.addChangeListener(new ChangeListener() {
        	// check the date for conflicts while changing
			@Override
			public void stateChanged(ChangeEvent e) {
				checkDate();
			}
		});
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd.MM.yyyy, HH:mm 'Uhr'"));
		addComp(gb, spinner, 1, 1, 1, 1);
		
		/*
		 * JComboBox for employee selection
		 */
		JLabel lblEmployee = new JLabel("Mitarbeiter:");
		addComp(gb, lblEmployee, 0, 2, 1, 1);

		ArrayList<User> allUsers = AppointmentApp.USERS.getAllAsArrayList();
		for(User u : allUsers) {
			vecUsers.add(u);
		}
		
		cbUser = new JComboBox<User>(vecUsers);
		cbUser.addItemListener(new ItemListener() {
			// check the date for conflicts while changing
			@Override
			public void itemStateChanged(ItemEvent e) {
				checkDate();
			}
		});
        cbUser.addFocusListener(new MyFocusListener());
        addComp(gb, cbUser, 1, 2, 1, 1);
        
        /*
         * JTextArea for a custom message
         */
		JLabel lblText = new JLabel("Bemerkung:");
		addComp(gb, lblText, 0, 3, 1, 1);
		
		txtText = new JTextArea();
		txtText.setPreferredSize(new Dimension(250,150));
		txtText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txtText.addFocusListener(new MyFocusListener());
		addComp(gb, txtText, 1, 3, 1, 1);
		
		/*
		 * add the center panel to main panel
		 */
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		/*
		 * add main panel to this frame
		 */
		add(mainPanel);
		
		/*
		 * add own window listener to this frame
		 */
		addWindowListener(new MyWindowListener());
		
//		pack();
				
	}
	
	/**
	 * Events for this <code>Frame</code> are created here.
	 */
	public static void createEvents() {
		
		btnOk.addActionListener(new ActionListener() {

			// this is what happens, if user clicks the ok button
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// get selected issue
				Issue issue = (Issue) cbIssue.getSelectedItem();
				// get start of this appointment
				Date start = (Date) spinner.getValue();
				// calculate the end date in dependency of selected issue
				DashboardPanel.c.setTime(start);
				DashboardPanel.c.add(Calendar.MINUTE, issue.getScheduledTime());
				Date end = DashboardPanel.c.getTime();	
				// get selected user to do this appointment
				User user = (User) cbUser.getSelectedItem();
				
				// create new appointment object
				Appointment a = new Appointment(user, AppointmentApp.user, issue, start, end, txtText.getText());
				try {
					// try to store in file
					AppointmentApp.APPOINTMENTS.store(a);
					// give feedback to the user in case of success
					errMsg.setText("Termin wurde gespeichert.");
					// clear fields
					clearFields();
					// update concerning dashboard component by extracing the day of month
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(start);
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					CalendarPanel cp = DashboardPanel.panel[day]; 
					cp.lblAppCount.setText("Termine: " + Integer.toString(AppointmentsByDate.getCount(day)) + " ");
					// update JList in AppointmentPanel
					AppointmentPanel.fillAppointmentList();
					// update appointment list in DashboardPanel
					DashboardPanel.refreshAppointmentList(AppointmentApp.appointmentListDay);
				} catch (Exception exception) {
					exception.printStackTrace();
					// show error to the user in case of problems
					errMsg.setText("Es ist ein Fehler aufgetreten.");
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			// this is what happens, if user clicks cancel button
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		
	}
	
	/**
	 * Simply clears all fields.
	 */
	public static void clearFields() {
		cbIssue.setSelectedIndex(0);
		cbUser.setSelectedIndex(0);
		txtText.setText("");
		checkDate();
	}
	
	/**
	 * Checks the current selected date for conflicts with other appointments.
	 */
	public static void checkDate() {
		
		// get current selected date from spinner
		Date start = (Date) spinner.getValue();
		
		// get selected issue 
		Issue selectedIssue = (Issue) cbIssue.getSelectedItem();
		// get end date in dependency of selected issue
		// TODO calendar vars!!
		DashboardPanel.c.setTime(start);
		DashboardPanel.c.add(Calendar.MINUTE, selectedIssue.getScheduledTime());
		Date end = DashboardPanel.c.getTime();
		
		// get selected user
		User user = (User) cbUser.getSelectedItem();
		
		// variables for appointments to compare, before and after selected time
		Appointment theAppointmentBefore = null;
		Appointment theAppointmentAfter = null;
		
		// all data available, now it is time to check the date:
		// get all appointments first
		ArrayList<Appointment> allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		// sort appointments chronologically
		Collections.sort(allAppointments);
		// now run chronologically through all appointments
		for(Appointment i : allAppointments) {
			if(i.getStart().before(start) || i.getStart().equals(start)) {
				// if appointment is before selected date, overwrite variable until last appointment
				theAppointmentBefore = i;
			}
			else {
				// if appointment is after selected date
				theAppointmentAfter = i;
				break;
			}
		}
		
		String strErrMsg = "";
		
		// selected date is in the past
		if(start.before(new Date())) {
			// error message for this conflict
			strErrMsg = "Termin liegt in der Vergangenheit.";
			// set button disabled
			btnOk.setEnabled(false);
		}
		// start of new appointment collides with the end of the appointment before and the same user is selected to do overlapping appointments or
		// end of new appointment collides with the start of the appointment after and the same user is selected to do overlapping appointments
		else if((theAppointmentBefore != null && start.compareTo(theAppointmentBefore.getEnd()) < 0 && user.getId() == theAppointmentBefore.getEmployee().getId()) ||
				(theAppointmentAfter != null && end.compareTo(theAppointmentAfter.getStart()) > 0 && user.getId() == theAppointmentAfter.getEmployee().getId())) {
			strErrMsg = "Terminkonflikt";
			btnOk.setEnabled(false);
		}
		else {
			// everything is fine
			btnOk.setEnabled(true);
		}
		// show the error message to the user
		errMsg.setText(strErrMsg);
		
	}
	
	/**
	 * WindowListener to set window invisible by clicking the 'X'.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 */
	private class MyWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			clearFields();
			setVisible(false);		
			if (AppointmentApp.logEvents)
				AppointmentApp.log.log(Level.INFO, "Appointment window set invisible");
		}
	}
	
	/**
	 * Own FocusListener to check selected date for conflicts when user leaves or enters a field.
	 * 
	 * @author Mathias Fernahl
	 * @version 17 May 2021
	 */
	private class MyFocusListener extends FocusAdapter{			
		@Override
		public void focusGained(FocusEvent e) {
			checkDate();
		}
		@Override
		public void focusLost(FocusEvent e) {
			checkDate();
		}
	}
	
	/**
	 * Adds component <code>c</code> to GridBagLayout with given constraints.
	 * 
	 * @param gb	The GridbagLayout to take the component.
	 * @param c		The component to be added.
	 * @param x		Number of the colummn.
	 * @param y		Number of the row.
	 * @param w		Width of the cell.
	 * @param h		Height of the cell.
	 */
	public void addComp(GridBagLayout gb, Component c, int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1;
		gb.setConstraints(c, gbc);
		centerPanel.add(c);				
	}

}

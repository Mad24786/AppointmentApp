package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * AppointmentApp is a light and simple application for managing appointments in an organization.
 * Users can be created and given different permissions via the user administration. 
 * Issues can be adapted to the organization and created individually. These are given a fixed 
 * processing time in order to avoid overlapping. 
 * 
 * <code>AppointmentApp</code> is the starting point for the entire application. A frame is 
 * generated here and navigation and <code>DashboardPanel</code> are loaded as the main screen.
 * 
 * In addition, various system components are made available here for application-wide use.
 * 
 * @author Mathias Fernahl
 * @version 17 May 2021
 *
 */
public class AppointmentApp extends JFrame{
		
	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Logger
	 */
	public static final Logger log = Logger.getLogger(AppointmentApp.class.getName());

	/*
	 * the user, who successfully logged in
	 */
	public static User user;
	
	/*
	 * user session settings
	 */
	public static boolean showUserAppointments = false;
	public static boolean logEvents = true;
	
	/*
	 * FileDatabase for global use
	 */
	public static FileDatabase<Appointment> APPOINTMENTS;
	public static FileDatabase<Issue> ISSUES;
	public static FileDatabase<User> USERS;
	
	/*
	 * calendar variables
	 */
	public static Calendar cal = new GregorianCalendar();
	public static int sDay, appointmentListDay;
	public static int cDay = sDay = appointmentListDay = cal.get(Calendar.DATE);
	public static int cMonth = cal.get(Calendar.MONTH);
	public static int cYear = cal.get(Calendar.YEAR);

	/*
	 * put other windows than this here
	 */
	public static AppointmentFrame appFrame;
	
	/*
	 * get user screen size
	 */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/*
	 * set window size here
	 */
	public static final Dimension windowSize = new Dimension(1024, 768);
	
	/*
	 * border layout for main panel
	 */
	public static BorderLayout mainLayout = new BorderLayout();
	
	/*
	 * components of this window for use in this class 
	 */
	public static JPanel mainPanel = new JPanel(mainLayout);
	private static JPanel centerPanel;
	public static JPanel eastPanel;
	private static JLabel lblNorth = new JLabel(); 
	public static JLabel lblLogging;
	
	/*
	 * content of components 
	 */
	public static DashboardPanel dashboardPanel;
	public static AppointmentPanel appointmentPanel;
	
	// navigation panels
	private static JPanel iconDashboard;
	private static JPanel iconAppointment;
	private static JPanel iconUser;
	private static JPanel iconIssue;
	private static JPanel iconInfo;
	private static JPanel iconExit;
	//JPanel[] icons;
	
	/**
	 * The actual start of the application is when the Constructor of
	 * <code>AppointmentApp</code> is called. This happens after a user
	 * has logged in.
	 * 
	 * @param userId 	ID of the user, who logged in before calling this class
	 */
	AppointmentApp(int userId){	
		super("AppointmentApp v0.1");
		//start logging
		try {
			log.addHandler(new FileHandler("src/de/nrw/hspv/database/log.txt"));
		} catch (SecurityException | IOException e) {}
		
		// load database
		try {
			ISSUES = new FileDatabase<Issue>(new File("src/de/nrw/hspv/database/issues.dat"));
			USERS = new FileDatabase<User>(new File("src/de/nrw/hspv/database/users.dat"));
			APPOINTMENTS = new FileDatabase<Appointment>(new File("src/de/nrw/hspv/database/appointments.dat"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// set user
		AppointmentApp.user = AppointmentApp.USERS.get(userId);
			
		// initialize components of this frame
		initComponents();
		
		// create events for this frame
		createEvents();

		// instance of other windows
		appFrame = new AppointmentFrame();
	}
	
	/**
	 * Initializes all needed components in this frame
	 * and feeds them with data if needed.
	 */
	private void initComponents() {
		/*
		 * set default values of this window
		 */
		setSize(windowSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		mainPanel.setBackground(Color.WHITE);
		int x = (screenSize.width - this.getSize().width) / 2;
	    int y = (screenSize.height - this.getSize().height) / 2;
	    setLocation(x, y);
		
	    /* 
	     * build navigation panel on the left
	     */
	    // set GridLayout with 6 rows in 1 column
		JPanel navigation = new JPanel(new GridLayout(6,1,5,5));
		// set default values for this panel
		navigation.setBackground(Color.WHITE);
		navigation.setPreferredSize(new Dimension(125,0));
		navigation.setBorder(new EmptyBorder(0, 15, 0, 15));
		
		// create and add icons to navigation panel
		iconDashboard = new IconPanel("dashboard", true);
		navigation.add(iconDashboard);
		iconAppointment = new IconPanel("calendar", AppointmentApp.user.isCanWriteAppointments());
		navigation.add(iconAppointment);
		iconUser = new IconPanel("user", AppointmentApp.user.isCanWriteUsers());
		navigation.add(iconUser);
		iconIssue = new IconPanel("issue", AppointmentApp.user.isCanWriteIssues());
		navigation.add(iconIssue);
		iconInfo = new IconPanel("info", true);
		navigation.add(iconInfo);
		iconExit = new IconPanel("exit", true);		
		navigation.add(iconExit);
		
		// add navigation to main panel
		mainPanel.add(navigation, BorderLayout.WEST);
		
		/*
		 * build upper panel, just a labeled icon to show where the user is moving around
		 */
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setPreferredSize(new Dimension(0,50));
		lblNorth.setText("Dashboard");
		lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/dashboard_small.png")));
        northPanel.add(lblNorth);
        northPanel.setAlignmentY(LEFT_ALIGNMENT);
        // add this to main panel
        mainPanel.add(northPanel, BorderLayout.NORTH);
        
        /*
         * center panel is an instance of DashboardPanel() by default
         */
		centerPanel = new DashboardPanel();
		// add center panel to main panel
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		/*
		 * east panel with today appointments
		 */
		eastPanel = new DashboardPanel.AppointmentsByDate(cDay, showUserAppointments);
		// add this panel to main panel
		mainPanel.add(eastPanel, BorderLayout.EAST);
		
		/*
		 * south panel for some system informations about user and logging
		 */
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		southPanel.setBackground(Color.WHITE);
		JLabel lblUser = new JLabel("Benutzer: " + user.getLastName() + ", " + user.getFirstName());
		southPanel.add(lblUser);
		lblLogging = new JLabel("| Logging: " + (logEvents ? "aktiviert" : "deaktiviert"));
		southPanel.add(lblLogging);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		/*
		 * finally add main panel to this frame, pack and make it visible
		 */
		add(mainPanel);
		pack();
		setVisible(true);
	}
	
	/**
	 * This method creates all events for the main window. It is
	 * only controlling the navigation icons so far.
	 */
	private void createEvents() {
		
		iconDashboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				revalidateAndRepaint();
				lblNorth.setText("Dashboard");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/dashboard_small.png")));
				mainPanel.add(new DashboardPanel(), BorderLayout.CENTER);
				validate();
			}
		});
		
		/* check for permission before adding MouseListener */
		if(AppointmentApp.user.isCanWriteAppointments()) {
			iconAppointment.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					revalidateAndRepaint();
					lblNorth.setText("Terminverwaltung");
					lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/calendar_small.png")));
					mainPanel.add(new AppointmentPanel(), BorderLayout.CENTER);
					validate();
				}
			});
		}
		
		if(AppointmentApp.user.isCanWriteIssues()) {
			iconIssue.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					revalidateAndRepaint();
					lblNorth.setText("Anliegen");
					lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/issue_small.png")));
					mainPanel.add(new IssuePanel(), BorderLayout.CENTER);
					validate();
				}
			});
		}
		
		if(AppointmentApp.user.isCanWriteUsers()) {
			iconUser.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					revalidateAndRepaint();
					lblNorth.setText("User");
					lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/user_small.png")));
					mainPanel.add(new UserPanel(), BorderLayout.CENTER);
					validate();
				}
			});
		}
		
		iconInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				revalidateAndRepaint();
				lblNorth.setText("Information");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/info_small.png")));
				mainPanel.add(new InfoPanel(), BorderLayout.CENTER);
				validate();
			}
		});
		
		iconExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich beenden?", "Beenden", JOptionPane.YES_NO_OPTION) == 0)
					System.exit(0);
			}
		});
		
	}
	
	/**
	 * Removes all components from centerPanel to make sure it is ready for a new panel. 
	 */
	public static void revalidateAndRepaint() {
		centerPanel.removeAll();
		centerPanel.revalidate();
		centerPanel.repaint();
		mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));		
	}
	/**
	 *  Inner class IconPanel can create icons for main navigation easily.
	 *  
	 *  @author Mathias Fernahl
	 *  @version 17 May 2021
	 */
	public class IconPanel extends JPanel {
		
		/**
		 * A unique serial version identifier.
		 * 
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		private String s; 
			
		/**
		 * The constructor does all the work by getting a String for the icon 
		 * and a boolean value to manage access of different users.
		 * 
		 * @param s			The String for getting the correct icon. 
		 * @param access	A boolean value from user class to make sure a user
		 * 					is allowed to get access to this menu item
		 */
		public IconPanel(String s, boolean access) {
			
			this.s = s;
			// set default layout
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			setToolTipText(s);
			// change color in dependency of user permission
			if(access) {
				setBackground(HspvColor.ORANGE);
			}
			else {
				setBackground(HspvColor.SEC_GRAY);
			}
		}
		
		/**
		 * Draws the icon on this panel by reading the given string.
		 * Make sure the image file exists!
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2), ((this.getHeight() - icon.getIconHeight()) / 2));
		}
		
		/**
		 * Customizes the <code>JToolTip</code>.
		 */
		@Override
		public JToolTip createToolTip() {
		    JToolTip tooltip = super.createToolTip();
		    tooltip.setBorder(BorderFactory.createLineBorder(HspvColor.GRAY));
		    tooltip.setBackground(HspvColor.SEC_BROWN);  
		    tooltip.setForeground(Color.BLACK);
		    return tooltip;
		}

	}
	
	/**
	 * Starts the application!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
			
		new AppointmentApp(2);
		
	}

}

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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class AppointmentApp extends JFrame{
	
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
	public static int cDay = cal.get(Calendar.DATE);
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
	 * Logger
	 */
	public static final Logger log = Logger.getLogger(AppointmentApp.class.getName());
	
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
	
	// navigation panels
	private static JPanel iconDashboard;
	private static JPanel iconAppointment;
	private static JPanel iconUser;
	private static JPanel iconIssue;
	private static JPanel iconInfo;
	private static JPanel iconExit;
	//JPanel[] icons;
	
	/**
	 * getting started with AppointmentApp
	 */
	AppointmentApp(){	
		super("AppointmentApp v0.1");
		/* load database */
		try {
			ISSUES = new FileDatabase<Issue>(new File("src/de/nrw/hspv/database/issues.dat"));
			USERS = new FileDatabase<User>(new File("src/de/nrw/hspv/database/users.dat"));
			APPOINTMENTS = new FileDatabase<Appointment>(new File("src/de/nrw/hspv/database/appointments.dat"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * initialize components of this frame
		 */
		initComponents();
		
		/*
		 * create events for this frame
		 */
		createEvents();

		/* instance of other windows */
		// TODO find another place for this
		appFrame = new AppointmentFrame();
	}
	
	private void initComponents() {
		/*
		 * set default values of this window
		 */
		setSize(windowSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		int x = (screenSize.width - this.getSize().width) / 2;
	    int y = (screenSize.height - this.getSize().height) / 2;
	    setLocation(x, y);
		
	    /* 
	     * build up navigation panel in the west
	     */
	    
	    // set grid layout with 6 rows in 1 column
		JPanel navigation = new JPanel(new GridLayout(6,1,5,5));
		// set default values for this panel
		navigation.setBackground(Color.WHITE);
		navigation.setPreferredSize(new Dimension(125,0));
		navigation.setBorder(new EmptyBorder(0, 15, 0, 15));
		
		// create and add icons to navigation panel
		iconDashboard = new IconPanel("dashboard", true);
		navigation.add(iconDashboard);
		iconAppointment = new IconPanel("calendar", true);
		navigation.add(iconAppointment);
		iconUser = new IconPanel("user", true);
		navigation.add(iconUser);
		iconIssue = new IconPanel("issue", true);
		navigation.add(iconIssue);
		iconInfo = new IconPanel("info", false);
		navigation.add(iconInfo);
		iconExit = new IconPanel("exit", true);		
		navigation.add(iconExit);
		
		// add navigation in west to main panel
		mainPanel.add(navigation, BorderLayout.WEST);
		
		/*
		 * build up northern panel, just a label to show where the user is moving
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
		eastPanel = new DashboardPanel.AppointmentsByDate(cDay);
		// TODO add scroll pane
		// add center panel to main panel
		mainPanel.add(eastPanel, BorderLayout.EAST);
		
		/*
		 * south panel is never used so long, but there could be cool things :)
		 */
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(SwingConstants.RIGHT));
		southPanel.setBackground(Color.WHITE);
		southPanel.setPreferredSize(new Dimension(0,50));
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		/*
		 * finally add main panel to this frame, pack and make it visible
		 */
		add(mainPanel);
		pack();
		setVisible(true);
	}
	
	/**
	 * This method creates all events for the main window. Until now it is
	 * only controlling the navigation icons.
	 */
	private void createEvents() {
		
		// TODO eigenen MouseListener schreiben
		
		/* Aktion für Klick auf Icon festlegen */ 
		/* 1. MouseListener zum Icon adden */
		iconDashboard.addMouseListener(new MouseAdapter() {
			/* 2. MouseClicked-Methode überschreiben */
			@Override
			public void mouseClicked(MouseEvent e) {
				/* 3. Das aktuelle Panel aus dem Center löschen 
				 * mainLayout.getLayoutComponent(BorderLayout.CENTER) -> zieht sich immer die aktuelle Komponente */
				mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
				/* 4. Das obere Panel mit dem ausgewählten Bereich aktualiesiren und das passende Icon wählen */
				lblNorth.setText("Dashboard");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/dashboard_small.png")));
				/* 5. Neues Panel-Objekt erzeugen und der Variable centerPanel zuweisen
				 * new DashboardPanel(); -> dort steht dann der Name der Klasse, z. B. UserPanel() */
				centerPanel = new DashboardPanel();
				/* 6. CenterPanel im Center des mainPanel hinzufügen */
				mainPanel.add(centerPanel, BorderLayout.CENTER);
				
				log.log(Level.INFO, "Dashboard called");
			}
		});
		
		iconAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				appFrame.setVisible(true);
				log.log(Level.INFO, "Appointment window set visible");
			}
		});
				
		iconIssue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
				lblNorth.setText("Anliegen");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/issue_small.png")));
				centerPanel = new IssuePanel();
				mainPanel.add(centerPanel, BorderLayout.CENTER);
// 				iconIssue.setBackground(Color.RED);
			}
		});
		iconUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
				lblNorth.setText("User");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/user_small.png")));
				centerPanel = new UserPanel();
				mainPanel.add(centerPanel, BorderLayout.CENTER);
			}
		});
		
		iconExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich beenden?") == 0)
					System.exit(0);
			}
		});
		
	}
	
	public static void refreshDashboard() {
		mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
		centerPanel = new DashboardPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.repaint();
		log.log(Level.INFO, "Dashboard refreshed");
	}
	
	/**
	 *  Inner class IconPanel can create icons for main navigation easily.
	 *  
	 *  @author Mathias Fernahl
	 *  @version 0.1
	 */
	public class IconPanel extends JPanel {
		
		private String s; 
			
		/**
		 * The constructor does all the work by getting a String and a boolean value
		 * to manage access of different users
		 * 
		 * @param s			The String for getting the correct icon. 
		 * @param access	A boolean value from user class to make sure a user
		 * 					is allowed to get access to this menu item
		 */
		public IconPanel(String s, boolean access) {
			
			this.s = s;
			
			setPreferredSize(new Dimension(75,75));
			setMaximumSize(new Dimension(75,75));
			
			if(access) {
				setBackground(HspvColor.ORANGE);
			}
			else {
				setBackground(HspvColor.SEC_GRAY);
			}
		}
		
		/*
		 * draws the icon on this panel by reading the given string
		 * make sure that the image file exists!
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2), ((this.getHeight() - icon.getIconHeight()) / 2));
		}

	}
	
	/**
	 * Starts the program!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			log.addHandler(new FileHandler("log.txt"));
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new AppointmentApp();
	}

}

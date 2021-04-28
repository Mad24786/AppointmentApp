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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.nrw.hspv.database.Get;
import de.nrw.hspv.util.HspvColor;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class AppointmentApp extends JFrame{

	/** 
	 * Alle Datenbanken laden und die benötigte Datenbank holen
	 */
	public static Get get = new Get();	
	
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(AppointmentApp.class.getName());
	
	public static final Dimension windowSize = new Dimension(1024, 768); 
	
	public static JPanel centerPanel;
	JLabel lblNorth = new JLabel(); 
	
	
	BorderLayout mainLayout = new BorderLayout();
	JPanel mainPanel = new JPanel(mainLayout);
	
	JPanel iconDashboard;
	JPanel iconAppointment;
	JPanel iconUser;
	JPanel iconIssue;
	JPanel iconInfo;
	JPanel iconExit;
	
	JPanel[] icons;
	
	AppointmentApp(){
		
		super("AppointmentApp v0.1");
		initComponents();
		createEvents();
	}
	
	private void initComponents() {
		setSize(windowSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		int x = (screenSize.width - this.getSize().width) / 2;
	    int y = (screenSize.height - this.getSize().height) / 2;
	    setLocation(x, y);
		

		add(mainPanel);
		
		JPanel navigation = new JPanel(new GridLayout(6,1,5,5));
		
		navigation.setBackground(Color.WHITE);
		navigation.setPreferredSize(new Dimension(125,0));
		navigation.setBorder(new EmptyBorder(0, 15, 0, 15));
		
		iconDashboard = new IconPanel("dashboard", true);
		navigation.add(iconDashboard);
		iconAppointment = new IconPanel("calendar", true);
		navigation.add(iconAppointment);
		iconUser = new IconPanel("user", false);
		navigation.add(iconUser);
		iconIssue = new IconPanel("issue", true);
		navigation.add(iconIssue);
		iconInfo = new IconPanel("info", false);
		navigation.add(iconInfo);
		iconExit = new IconPanel("exit", true);		
		navigation.add(iconExit);
		
		mainPanel.add(navigation, BorderLayout.WEST);
		
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setPreferredSize(new Dimension(0,50));
		lblNorth.setText("Dashboard");
		lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/dashboard_small.png")));
        northPanel.add(lblNorth);
        northPanel.setAlignmentY(LEFT_ALIGNMENT);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        
        
		centerPanel = new DashboardPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(SwingConstants.RIGHT));
		southPanel.setBackground(Color.WHITE);
		southPanel.setPreferredSize(new Dimension(0,50));
		mainPanel.add(southPanel, BorderLayout.SOUTH);
						
		pack();
		
		setVisible(true);
	}
	
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
			}
		});
		
		iconAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
				lblNorth.setText("Terminverwaltung");
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/calendar_small.png")));
				centerPanel = new AppointmentPanel();
				mainPanel.add(centerPanel, BorderLayout.CENTER);
// 				iconIssue.setBackground(Color.RED);
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
				lblNorth.setIcon(new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/user.png")));
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
	
	public class IconPanel extends JPanel {
		
		/** Erzeugt Hauptmenü-Icons */
		
		private String s; 
			
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
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2), ((this.getHeight() - icon.getIconHeight()) / 2));
		}

	}
	
	public static void main(String[] args) {
		new AppointmentApp();
	}

}

package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;
import de.nrw.hspv.views.DashboardPanel.AppointmentsByDate;
import de.nrw.hspv.views.DashboardPanel.CalendarPanel;

/**
 * 
 * In this class the appointment panel (as an instance of JPanel) will be created.
 * This object will be instanced, if the user clicks on the appointment icon in main
 * navigation. Existing rights provided.
 * 
 * From this panel the user is able to set new appointments or delete existing
 * appointments.
 * 
 * 
 * @author Mathias Fernahl
 * 
 */
public class AppointmentPanel extends JPanel {
	
	/*
	 * some layout components for class-wide use
	 */
	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel centerPanel = new JPanel();
	
	/*
	 * components for user input and system output
	 */
	public static JTextField txtId;
	public static JComboBox<Issue> cbIssue;
	public static JSpinner spinner;
	public static JTextArea txtText;
	public static JList<Appointment> list; 
	public static DefaultListModel<Appointment> listModel;
	public static JLabel errMsg = new JLabel();
	
	/*
	 * sub navigation icons
	 */
	public static JPanel addIcon;
	public static JPanel deleteIcon;
	
	/**
	 * Constructor to initialize components and create events
	 * in this panel. 
	 */
	public AppointmentPanel(){
		initComponents();
		createEvents();
	}
	
	/**
	 * Initializes all needed components in this panel
	 * and feeds them with data if needed.
	 */
	private void initComponents() {
		/* first remove all to reset if repeating call */
		centerPanel.removeAll();

		/* general settings for  this Panel */
		setBackground(Color.WHITE);
		setLayout(mainLayout);
				
		
		/* 
		 * Build upper panel with FlowLayout for sub navigation 
		 */
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		/* create sub navigation icons and add to panel */
		addIcon = new CreateIcon("add", AppointmentApp.user.isCanWriteAppointments());
		northPanel.add(addIcon);
		deleteIcon = new CreateIcon("delete", AppointmentApp.user.isCanWriteAppointments());
		northPanel.add(deleteIcon);
		
		/* add error message label with noticeable orange font color */
		errMsg.setForeground(HspvColor.ORANGE);
		northPanel.add(errMsg);
		/* add whole upper panel */
		add(northPanel, BorderLayout.NORTH);
		
		/* 
		 * Build center panel with a JList of all available appointments 
		 */        
		centerPanel.setBackground(Color.WHITE);
		listModel = new DefaultListModel<Appointment>();
		/* fill the list */
		fillAppointmentList();
		/* instantiate list with data */ 
		list = new JList<Appointment>(listModel);
		/* add list to a JScrollPane and remove border */
		JScrollPane scrPane = new JScrollPane(list);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPane.setPreferredSize(new Dimension(548,445));
		scrPane.setBorder(BorderFactory.createEmptyBorder());
		/* add customized list to center panel */
		centerPanel.add(scrPane);
		/* add whole center panel */
		add(centerPanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * This methods builds respectively <b>re</b>builds 
	 * the existing JList with all available appointments.
	 */
	public static void fillAppointmentList() {
		// if content already exists, remove it
		if(listModel.getSize() != 0)
			listModel.removeAllElements();
		
		// get the freshest data of appointments and sort
		// TODO source out...
		DashboardPanel.allAppointments = AppointmentApp.APPOINTMENTS.getAllAsArrayList();
		Collections.sort(DashboardPanel.allAppointments);
		
		// add all appointments to JList related listModel
		for (Appointment e : DashboardPanel.allAppointments) {
			listModel.addElement((Appointment) e);
		}
	}
	
	/**
	 * Events for sub navigation in appointment panel will be created here.
	 */
	public static void createEvents() {
		
		/* adds action for clicking the add button */
		addIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AppointmentApp.appFrame.setVisible(true);
				AppointmentFrame.checkDate();
			}
			
		});
		
		/* adds action for clicking the delete button */
		deleteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Appointment a = list.getSelectedValue();
				/* first check if an appointment is selected... */
				if(a != null) {
					/* if user has selected an appointment and really wants to delete it */
					if (JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin #" + a.getId() + " löschen?", "Termin löschen", JOptionPane.YES_NO_OPTION) == 0) {
						try {
							AppointmentApp.APPOINTMENTS.remove(a.getId());
							AppointmentApp.log.log(Level.INFO, "Appointment deleted");
						} catch (IOException ioe) {
							errMsg.setText("Löschen nicht möglich."); 
						}
						int day = a.getStart().getDate();
						CalendarPanel cp = DashboardPanel.panel[day]; 
						cp.lblAppCount.setText("Termine: " + Integer.toString(AppointmentsByDate.getCount(day)) + " ");
						/* refresh JList if everything is fine */
						AppointmentPanel.fillAppointmentList();
						DashboardPanel.refreshAppointmentList(AppointmentApp.appointmentListDay);
					}
				}
				/* ...else give a notice to the user */
				else {
					errMsg.setText("Bitte wählen Sie den zu löschenden Termin aus.");
				}
			}
		});
		
	}
	
	/**
	 * As <code>CreateIcon</code> suggests, this class creates the icons for sub 
	 * navigation in <code>AppointmentPanel</code> to control appointment operations. 
	 * 
	 * @author Mathias Fernahl
	 *
	 */
	public class CreateIcon extends JPanel {
		
		/**
		 * A unique serial version identifier.
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/* String for icon resource */
		private String s; 
			
		/**
		 * Constructor for creating an icon. 
		 * 
		 * @param s			A string for icon resource. Has to be the filename of the icon image. 
		 * @param access	A boolean value. Should be given from <code>User</code> object of 
		 * 					user, who is currently logged in.
		 * 
		 */
		public CreateIcon(String s, boolean access) {
			
			/* put given String to object variable */ 
			this.s = s;
			
			/* setting the size */
			Dimension size = new Dimension(50,50);
			
			// TODO maybe useless??
			setPreferredSize(size);
			setMaximumSize(size);
			
			/* if access is granted, show icon in orange, else gray */
			if(access) 
				setBackground(HspvColor.ORANGE);
			else 
				setBackground(HspvColor.SEC_GRAY);
			
		}
	
		/**
		 * Paints this component with icon image. 
		 * 
		 * @param g			Object of <code>Graphics</code> 
		 * 
		 */
		@Override		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			/* getting the icon */
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			/* paint in center */
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2), ((this.getHeight() - icon.getIconHeight()) / 2));
		}

	}

	
	public static void main(String[] args) {

		new AppointmentPanel();

	}

}

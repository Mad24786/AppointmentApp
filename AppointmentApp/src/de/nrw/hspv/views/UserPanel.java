package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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

import de.nrw.hspv.views.AppointmentApp;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.views.UserFrame;
import de.nrw.hspv.views.DashboardPanel.AppointmentsByDate;
import de.nrw.hspv.views.DashboardPanel.CalendarPanel;
import de.nrw.hspv.views.IssuePanel.CreateIcon;
import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.User;

/**
 * In der Klasse UserPanel wird das Panel erstellt welches die Möglichkeit
 * bietet einen User zu bearbeiten oder zu löschen. Außerdem kann man sich eine
 * Liste der erstellten User angucken
 * 
 * @author Dennis Herrndörfer
 *
 */
public class UserPanel extends JPanel {
	/**
	 * Layout
	 */
	BorderLayout layout = new BorderLayout();
	/**
	 * Panel
	 */
	JPanel centerPanel = new JPanel();
	JPanel addIcon;
	JPanel deleteIcon;
	/**
	 * Buttons
	 */
	JButton deleteButton = new JButton();
	JButton createButton = new JButton();

	JList<User> list;
	static DefaultListModel<User> listModel;
	// static ArrayList<User> allUsers;
	public static FileDatabase<User> USERS;
	public static ArrayList<User> allUsers;
	public static JLabel errMsg = new JLabel();

	/**
	 * Konstruktor
	 */
	public UserPanel() {
		initUi();
		createEvents();
		try {

			USERS = new FileDatabase<User>(new File("src/de/nrw/hspv/database/users.dat"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Panel wird erstellt
	 */
	private void initUi() {

		/**
		 * Layout setzen
		 */
		setLayout(layout);
		/**
		 * Panel oben erstellen mit den dazugehörigen Icons
		 */
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		/**
		 * Aufruf der Methode CreateIcon
		 */
		addIcon = new CreateIcon("add", true);
		northPanel.add(addIcon);

		deleteIcon = new CreateIcon("delete", true);
		northPanel.add(deleteIcon);
		/**
		 * northPanel dem Panell hinzufügen
		 */
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();

		centerPanel.setBackground(Color.WHITE);
		listModel = new DefaultListModel<User>();
		/* fill the list */
		fillAppointmentList();
		/* instantiate list with data */
		list = new JList<User>(listModel);
		/* add list to a JScrollPane and remove border */
		JScrollPane scrPane = new JScrollPane(list);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPane.setPreferredSize(new Dimension(548, 445));
		scrPane.setBorder(BorderFactory.createEmptyBorder());
		/* add customized list to center panel */
		centerPanel.add(scrPane);
		/* add whole center panel */
		add(centerPanel, BorderLayout.CENTER);

	}

	/**
	 * Event Handling
	 */
	public void createEvents() {
		/**
		 * Aufruf der Klasse UserFrame bei Klicken auf das addIcon
		 */
		addIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new UserFrame();
			}

		});
		/* adds action for clicking the delete button */
		deleteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				User a = list.getSelectedValue();
				/* first check if an appointment is selected... */
				if (a != null) {
					/* if user has selected an appointment and really wants to delete it */
					if (JOptionPane.showConfirmDialog(null, "Wollen Sie den User #" + a.getId() + " lï¿½schen?",
							"User löschen", JOptionPane.YES_NO_OPTION) == 0) {
						try {
							AppointmentApp.USERS.remove(a.getId());
							AppointmentApp.log.log(Level.INFO, "User deleted");
						} catch (IOException ioe) {
							errMsg.setText("Lï¿½schen nicht mï¿½glich.");
						}
//						int day = a.getStart().getDate();
//						CalendarPanel cp = DashboardPanel.panel[day]; 
//						cp.lblAppCount.setText("Termine: " + Integer.toString(AppointmentsByDate.getCount(day)) + " ");
//						/* refresh JList if everything is fine */
						UserPanel.fillAppointmentList();
//						DashboardPanel.refreshAppointmentList(AppointmentApp.appointmentListDay);
					}
				}
				/* ...else give a notice to the user */
				else {
					errMsg.setText("Bitte wï¿½hlen Sie den zu lï¿½schenden Termin aus.");
				}
			}
		});
	}

	public static void fillAppointmentList() {
		// if content already exists, remove it
		if (listModel.getSize() != 0)
			listModel.removeAllElements();

		// get the freshest data of appointments and sort
		// TODO source out...
		allUsers = AppointmentApp.USERS.getAllAsArrayList();
		// Collections.sort(allUsers);

		// add all appointments to JList related listModel
		for (User e : allUsers) {
			listModel.addElement((User) e);
		}
	}

	/**
	 * Methode CreateIcon welche die beiden Icons erstellt
	 * 
	 * @author übernommen von Matthias Fernahl
	 * 
	 *
	 */
	public class CreateIcon extends JPanel {

		private String s;

		public CreateIcon(String s, boolean access) {

			this.s = s;

			Dimension size = new Dimension(50, 50);

			setPreferredSize(size);
			setMaximumSize(size);

			if (access) {
				setBackground(HspvColor.ORANGE);
			} else {
				setBackground(HspvColor.SEC_GRAY);
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2),
					((this.getHeight() - icon.getIconHeight()) / 2));
		}
	}

}

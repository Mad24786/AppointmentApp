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
import java.io.Serializable;
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
 * This class creates an IssuePanel as an instance of JPanel. You can use this
 * class to see the issues and open the windows to edit them and create new
 * issues.
 * 
 * @author Luis Duhme
 * @version 17 May 2021
 * @see javax.swing.JPanel
 *
 */

@SuppressWarnings("serial")
public class IssuePanel extends JPanel {

	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel mainPanel = new JPanel(mainLayout);

	public static JPanel centerPanel;
	public static JPanel cards;
	public static JLabel errMsg = new JLabel();

	public static JTextField txtId;
	public static JComboBox<Issue> cbIssue;
	public static JSpinner spinner;
	public static JTextArea txtText;

	public static JList<Appointment> list;

	public static JPanel addIcon;
	public static JPanel editIcon;
	public static JPanel deleteIcon;

	public static JButton btnOk = new JButton("OK");
	public static JButton btnCancel = new JButton("Abbrechen");

	public static Vector<Issue> vecIssues = new Vector<Issue>();

	public IssuePanel() {
		initComponents();
		createEvents();
	}

	private void initComponents() {

//all panels are saved
		HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
		panels.put("main", new JPanel());

		/* this Panel */
		setBackground(Color.WHITE);
		setLayout(mainLayout);

//the north panel
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

//the two buttons are created
		addIcon = new CreateIcon("add", true);
		northPanel.add(addIcon);
		deleteIcon = new CreateIcon("delete", true);
		northPanel.add(deleteIcon);

//the label which is used for an error message is described
		errMsg.setForeground(HspvColor.ORANGE);
		northPanel.add(errMsg);
		add(northPanel, BorderLayout.NORTH);

//the center panel is created and described
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		DefaultListModel<Appointment> listModel = new DefaultListModel<Appointment>();
		Collections.sort(DashboardPanel.allAppointments);
		for (Appointment e : DashboardPanel.allAppointments) {
			listModel.addElement(e);
		}
//the list in the center with all the appointments is created
		list = new JList<Appointment>(listModel);
		JScrollPane scrPane = new JScrollPane(list);
		scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrPane.setPreferredSize(new Dimension(548, 445));
		scrPane.setBorder(BorderFactory.createEmptyBorder());
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Appointment a = list.getSelectedValue();
				System.out.println(a.getId());
			}
		});
		centerPanel.add(scrPane);
		add(centerPanel, BorderLayout.CENTER);

//		add(mainPanel);

	}

	public static void createEvents() {
//Set the IssueFrame to visible (opens it), if you click on the add icon
		addIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				IssueFrame obj = new IssueFrame();
				obj.setVisible(true);

			}

		});

		/* adds action for clicking the delete button */
		deleteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Appointment a = list.getSelectedValue();
				/* first check if an appointment is selected... */
				if (a != null) {
					/* if user has selected an appointment and really wants to delete it */
					if (JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin #" + a.getId() + " löschen?",
							"Termin löschen", JOptionPane.YES_NO_OPTION) == 0) {
						try {
							AppointmentApp.APPOINTMENTS.remove(a.getId());
							AppointmentApp.log.log(Level.INFO, "Appointment deleted");
						} catch (IOException ioe) {
							errMsg.setText("L�schen nicht m�glich.");
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
					errMsg.setText("Bitte w�hlen Sie den zu l�schenden Termin aus.");
				}
			}
		});

	}

	/**
	 * As <code>CreateIcon</code> suggests, this class creates the icons for sub
	 * navigation in <code>AppointmentPanel</code> to control appointment
	 * operations.
	 * 
	 * @author Mathias Fernahl
	 *
	 */
	public class CreateIcon extends JPanel {

		/**
		 * A unique serial version identifier.
		 * 
		 * @see Serializable#serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		/* String for icon resource */
		private String s;

		/**
		 * Constructor for creating an icon.
		 * 
		 * @param s      A string for icon resource. Has to be the filename of the icon
		 *               image.
		 * @param access A boolean value. Should be given from <code>User</code> object
		 *               of user, who is currently logged in.
		 * 
		 */
		public CreateIcon(String s, boolean access) {

			/* put given String to object variable */
			this.s = s;

			/* setting the size */
			Dimension size = new Dimension(50, 50);

			// TODO maybe useless??
			setPreferredSize(size);
			setMaximumSize(size);

			/* if access is granted, show icon in orange, else gray */
			if (access)
				setBackground(HspvColor.ORANGE);
			else
				setBackground(HspvColor.SEC_GRAY);

		}

		/**
		 * Paints this component with icon image.
		 * 
		 * @param g Object of <code>Graphics</code>
		 * 
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			/* getting the icon */
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			/* paint in center */
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2),
					((this.getHeight() - icon.getIconHeight()) / 2));
		}

	}

	// Starts the issuePanel

	public static void main(String[] args) {

		new IssuePanel();

	}

}

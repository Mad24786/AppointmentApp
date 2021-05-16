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

/**
 * This class creates an IssuePanel as an instance of JPanel. You can use this
 * class to see the issues and open the windows to edit them and create new
 * issues.
 * 
 * @author Luis Duhme
 * @version 17 May 2021
 * @see javax.swing.JFrame
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

//the three buttons are created
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

//Deletes item from the list, if its clicked and selected

		deleteIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Appointment a = list.getSelectedValue();
				if (a != null) {
					if (JOptionPane.showConfirmDialog(null, "Wollen Sie den Termin #" + a.getId() + " l�schen?") == 0) {
						try {
							AppointmentApp.APPOINTMENTS.remove(a.getId());
							AppointmentApp.log.log(Level.INFO, "Appointment deleted");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					errMsg.setText("Bitte w�hlen Sie den zu l�schenden Termin aus.");
				}
			}
		});

		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Date start = (Date) spinner.getValue();
				Date end = null;
				User user = null;
				Issue issue = (Issue) cbIssue.getSelectedItem();

//				Appointment a = new Appointment(user, issue, start, end, txtText.getText());
				try {
//					AppointmentApp.APPOINTMENTS.store(a);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});

	}

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

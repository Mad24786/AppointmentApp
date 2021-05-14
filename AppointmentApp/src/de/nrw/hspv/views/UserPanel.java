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
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.nrw.hspv.views.AppointmentApp;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.views.UserFrame;
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


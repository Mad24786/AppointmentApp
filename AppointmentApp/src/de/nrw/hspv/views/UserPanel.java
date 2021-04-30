package de.nrw.hspv.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import de.nrw.hspv.database.Get;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;

import java.util.ArrayList;
import java.util.Vector;

import java.util.logging.Logger;

public class UserPanel extends JPanel {

	private static final Logger log = Logger.getLogger(UserPanel.class.getName());

	JButton buttonOk;
	JButton buttonCancel;

	//JLabel labelId;
	JLabel labelFirstName;
	JLabel labelLastName;
	JLabel labelAge;
	JLabel labelPhoneNumber;
	JLabel labelEmail;
	JLabel labelPassword;
	JLabel labelCanReadAppointments;
	JLabel labelCanWriteAppointments;
	JLabel labelCanReadIssues;
	JLabel labelCanWriteIssues;
	JLabel labelCanReadUsers;
	JLabel labelCanWriteUsers;

	//JTextField textId;
	JTextField textFirstName;
	JTextField textLastName;
	JTextField textAge;
	JTextField textPhoneNumber;
	JTextField textEmail;
	JTextField textPassword;
	JCheckBox CheckCanReadAppointments;
	JCheckBox CheckCanWriteAppointments;
	JCheckBox CheckCanReadIssues;
	JCheckBox CheckCanWriteIssues;
	JCheckBox CheckCanReadUsers;
	JCheckBox CheckCanWriteUsers;

	public UserPanel() {
		initComponents();
		createEvents();
	}

	public void initComponents() {
		setLayout(new GridLayout(0, 2));
		setBackground(Color.WHITE);

		Vector<User> vec = new Vector<User>();

		ArrayList<User> allUsers = Get.users.getAllAsArrayList();
		for (User i : allUsers) {
			vec.add(i);
//		}
//		JComboBox<User> highscoreCombo = new JComboBox<User>(vec);
//		highscoreCombo.addItemListener(new ItemListener() {

//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				User i = (User) e.getItem();
//				textId.setText(String.valueOf(i.getId()));
//			}
		}

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		GridLayout centerLayout = new GridLayout(2, 0);
		centerPanel.setLayout(centerLayout);

		//labelId = new JLabel("ID:");
		// labelId.setPreferredSize(new Dimension(50, 24));
		//add(labelId);
		//textId = new JTextField();
		//add(textId);
		labelFirstName = new JLabel("Vorname:");
		add(labelFirstName);
		textFirstName = new JTextField();
		add(textFirstName);
		labelLastName = new JLabel("Nachname:");
		add(labelLastName);
		textLastName = new JTextField();
		add(textLastName);
		labelAge = new JLabel("Alter:");
		add(labelAge);
		textAge = new JTextField();
		add(textAge);
		labelPhoneNumber = new JLabel("Telefonnummer:");
		add(labelPhoneNumber);
		textPhoneNumber = new JTextField();
		add(textPhoneNumber);
		labelEmail = new JLabel("Email:");
		add(labelEmail);
		textEmail = new JTextField();
		add(textEmail);
		labelPassword = new JLabel("Passwort:");
		add(labelPassword);
		textPassword = new JTextField();
		add(textPassword);
		labelCanReadAppointments = new JLabel("Berechtigung Termine lesen");
		add(labelCanReadAppointments);
		CheckCanReadAppointments = new JCheckBox();
		add(CheckCanReadAppointments);
		labelCanWriteAppointments = new JLabel("Berechtigung Bearbeiten von Terminen");
		add(labelCanWriteAppointments);
		CheckCanWriteAppointments = new JCheckBox();
		add(CheckCanWriteAppointments);
		labelCanReadIssues = new JLabel("Berechtigung Anliegen lesen");
		add(labelCanReadIssues);
		CheckCanReadIssues = new JCheckBox();
		add(CheckCanReadIssues);
		labelCanWriteIssues = new JLabel("Berechtigung Bearbeiten von Anliegen");
		add(labelCanWriteIssues);
		CheckCanWriteIssues = new JCheckBox();
		add(CheckCanWriteIssues);
		labelCanReadUsers = new JLabel("Berechtigung Lesen von User");
		add(labelCanReadUsers);
		CheckCanReadUsers = new JCheckBox();
		add(CheckCanReadUsers);
		labelCanWriteUsers = new JLabel("Berechtigung Bearbeiten von User");
		add(labelCanWriteUsers);
		CheckCanWriteUsers = new JCheckBox();
		add(CheckCanWriteUsers);
		

		buttonCancel = new JButton("Abbruch");
		buttonCancel.setSize(20, 30);
		add(buttonCancel);
		buttonOk = new JButton("Ok");
		add(buttonOk);

	}

	public void createEvents() {

		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("Aktion abgebrochen.");

			}
		});

		buttonOk.addActionListener(new ActionListener() {
			int  age, number;
			boolean readA, writeA, readI, writeI;

			@Override

			public void actionPerformed(ActionEvent e) {

				// User user = new User();

//				try {
//					id = Integer.parseInt(textId.getText());
//				} catch (NumberFormatException e1) {
//					textId.setText("Fehlerhafte Eingabe: [ID]");
//					log.warning("Fehlerhafte Eingabe: [ID]");
//					textId.setForeground(Color.RED);
//					return;
//				}
//				if (textId.getText().trim().equals("")) {
//					textId.setForeground(Color.RED);
//					textId.setText("Pflichteingabe");
//
//					return;
//				}

				if (textFirstName.getText().trim().equals("")) {
					textFirstName.setForeground(Color.RED);
					textFirstName.setText("Pflichteingabe");

					return;
				}
				if (textLastName.getText().trim().equals("")) {
					textLastName.setForeground(Color.RED);
					textLastName.setText("Pflichteingabe");

					return;
				}
				try {
					age = Integer.parseInt(textAge.getText());
				} catch (NumberFormatException e1) {
					textAge.setText("Fehlerhafte Eingabe: [Age]");
					log.warning("Fehlerhafte Eingabe: [Age]");
					textAge.setForeground(Color.RED);
					return;
				}
				if (textAge.getText().trim().equals("")) {
					textAge.setForeground(Color.RED);
					textAge.setText("Pflichteingabe");

					return;
				}
				try {
					number = Integer.parseInt(textPhoneNumber.getText());
				} catch (NumberFormatException e1) {
					textPhoneNumber.setText("Fehlerhafte Eingabe: [Number]");
					log.warning("Fehlerhafte Eingabe: [Number]");
					textPhoneNumber.setForeground(Color.RED);
					return;
				}
				if (textPhoneNumber.getText().trim().equals("")) {
					textPhoneNumber.setForeground(Color.RED);
					textPhoneNumber.setText("Pflichteingabe");

					return;
				}
				if (textEmail.getText().trim().equals("")) {
					textEmail.setForeground(Color.RED);
					textEmail.setText("Pflichteingabe");

					return;
				}
				if(textPassword.getText().trim().equals("")) {
					textPassword.setForeground(Color.RED);
					textPassword.setText("Pflichteingabe");
				}

				// User user = new User(Integer.parseInt(textId.getText()),
				// textFirstName.getText(),textLastName.getText(),
				// Integer.parseInt(textAge.getText()),Integer.parseInt(textPhoneNumber.getText()),
				// textEmail.getText(), readA, writeA,readI,writeI);
				//
				User user = new User(textFirstName.getText(), textLastName.getText(),
						Integer.parseInt(textAge.getText()), Integer.parseInt(textPhoneNumber.getText()),
						textEmail.getText(),textPassword.getText(), CheckCanReadAppointments.isSelected(),
						CheckCanWriteAppointments.isSelected(), CheckCanReadIssues.isSelected(),
						CheckCanWriteIssues.isSelected(),CheckCanReadUsers.isSelected(),CheckCanWriteUsers.isSelected());
				try {
					Get.users.store(user);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//labelId.setForeground(Color.BLACK);
				textFirstName.setForeground(Color.BLACK);
				textLastName.setForeground(Color.BLACK);
				textAge.setForeground(Color.BLACK);
				textPhoneNumber.setForeground(Color.BLACK);
				textEmail.setForeground(Color.BLACK);
				
				System.out.println("Sie haben einen neuen User angelegt. Ihr neuer Username ist: username"+user.getId());

			}
		});

	}

	public static void main(String[] args) {

		new UserPanel();

	}

}

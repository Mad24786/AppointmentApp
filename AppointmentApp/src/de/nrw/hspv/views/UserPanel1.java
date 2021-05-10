package de.nrw.hspv.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

public class UserPanel1 extends JFrame {
	private JFrame jFrame;
	private Container contentPane;
	JButton buttonOk;
	JButton buttonCancel;
	private static final Logger log = Logger.getLogger(UserPanel.class.getName());
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

	public UserPanel1() {
		initUI();
		createEvents();
	}
	private void initUI() {
		jFrame = new JFrame("User");
		jFrame.setSize(800, 600);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		setLayout(new GridLayout(0, 2));
		setBackground(Color.WHITE);

		

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		GridLayout centerLayout = new GridLayout(2, 0);
		centerPanel.setLayout(centerLayout);

		
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
		add(centerPanel);
		pack();
		setVisible(true);

	}

	public void createEvents() {

		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("Aktion abgebrochen.");
				textFirstName.setText("");
				textLastName.setText("");
				textAge.setText("");
				textPhoneNumber.setText("");
				textEmail.setText("");
				textPassword.setText("");
				
			}
		});

		buttonOk.addActionListener(new ActionListener() {
			int  age, number;
			
			@Override

			public void actionPerformed(ActionEvent e) {

				

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
				if(age >120) {
					textAge.setForeground(Color.RED);
					textAge.setText("Geben sie ein richtiges Alter ein");
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

				
				User user = new User(textFirstName.getText(), textLastName.getText(),
						Integer.parseInt(textAge.getText()), Integer.parseInt(textPhoneNumber.getText()),
						textEmail.getText(),textPassword.getText(), CheckCanReadAppointments.isSelected(),
						CheckCanWriteAppointments.isSelected(), CheckCanReadIssues.isSelected(),
						CheckCanWriteIssues.isSelected(),CheckCanReadUsers.isSelected(),CheckCanWriteUsers.isSelected());
				try {
					AppointmentApp.USERS.store(user);
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
				JOptionPane.showMessageDialog(null,"Sie haben einen neuen User angelegt. Ihr neuer Username ist: username"+user.getId());
				textFirstName.setText("");
				textLastName.setText("");
				textAge.setText("");
				textPhoneNumber.setText("");
				textEmail.setText("");
				textPassword.setText("");
			}
		});

	}

	public static void main(String[] args) {

		new UserPanel1();

	}



	}



package de.nrw.hspv.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import de.nrw.hspv.util.User;

/**
 * In der Klasse UserFrame wird das Fenster für das erstellen eines neuen Users
 * erstellt. In dieser Klasse können Name, Alter, Email, Telefonnummer und
 * Berechtigungen festgelegt werden, die später wichtig sind für die Erstellung
 * eines Termins.
 * 
 * @author Dennis Herrndörfer
 *
 */
public class UserFrame extends JFrame {
	/**
	 * Fenster
	 */
	private JFrame jFrame;
	private Container contentPane;
	/**
	 * Buttons
	 */
	JButton buttonOk;
	JButton buttonCancel;
	private static final Logger log = Logger.getLogger(UserPanel.class.getName());
	/**
	 * Labels
	 */
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

	/**
	 * Textfields
	 */
	JTextField textFirstName;
	JTextField textLastName;
	JTextField textAge;
	JTextField textPhoneNumber;
	JTextField textEmail;
	JTextField textPassword;
	/**
	 * CheckBoxen
	 */
	JCheckBox CheckCanReadAppointments;
	JCheckBox CheckCanWriteAppointments;
	JCheckBox CheckCanReadIssues;
	JCheckBox CheckCanWriteIssues;
	JCheckBox CheckCanReadUsers;
	JCheckBox CheckCanWriteUsers;

	/**
	 * Konstruktor
	 */
	public UserFrame() {
		initUI();
		createEvents();
	}

	/**
	 * Fenster wird erstellt
	 */
	private void initUI() {
		jFrame = new JFrame("User");
		jFrame.setSize(new Dimension(640, 480));

		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		/**
		 * GridLayout
		 */
		setLayout(new GridLayout(0, 2));
		setBackground(Color.WHITE);
		/**
		 * Position des Fensters
		 */
		int x = (AppointmentApp.screenSize.width - this.getSize().width) / 3;
		int y = (AppointmentApp.screenSize.height - this.getSize().height) / 3;
		setLocation(x, y);

		/**
		 * CenterPanel ebenfalls mit GridLayout
		 */
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		GridLayout centerLayout = new GridLayout(2, 0);
		centerPanel.setLayout(centerLayout);

		/**
		 * Befüllung der Labels und CheckBoxen und hinzufügen zum centerPanel
		 */
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
		/**
		 * Center Panel zum Frame hinzufügen
		 */
		add(centerPanel);
		pack();
		/**
		 * Frame sichtbar machen
		 */
		setVisible(true);

	}

	/**
	 * Event Handling
	 */
	public void createEvents() {
		/**
		 * Bei betätigen des Abbruch Buttons werden die Textfelder gelöscht und das
		 * Programm schließt sich
		 */
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

				dispose();

			}
		});
		/**
		 * Bei betätigen des Ok Buttons werden die Eingaben geprüft und der User am Ende
		 * gespeichert
		 */
		buttonOk.addActionListener(new ActionListener() {
			int age, number;

			@Override

			public void actionPerformed(ActionEvent e) {

				/**
				 * Prüfung ob Vorname eingeben wurde
				 */
				if (textFirstName.getText().trim().equals("")) {
					textFirstName.setForeground(Color.RED);
					textFirstName.setText("Pflichteingabe");

					return;
				}
				/**
				 * Prüfung ob Nachname eingeben wurde
				 */
				if (textLastName.getText().trim().equals("")) {
					textLastName.setForeground(Color.RED);
					textLastName.setText("Pflichteingabe");

					return;
				}
				/**
				 * Prüfung ob eine Zahl beim Alter eingeben wurde
				 */
				try {
					age = Integer.parseInt(textAge.getText());
				} catch (NumberFormatException e1) {
					textAge.setText("Fehlerhafte Eingabe: [Age]");
					log.warning("Fehlerhafte Eingabe: [Age]");
					textAge.setForeground(Color.RED);
					return;
				}
				/**
				 * Prüfung ob überhaupt etwas beim Alter eingegeben wurde
				 */
				if (textAge.getText().trim().equals("")) {
					textAge.setForeground(Color.RED);
					textAge.setText("Pflichteingabe");

					return;
				}
				/**
				 * Prüfung ob das eingegebene Alter unter 120 ist
				 */
				if (age > 120) {
					textAge.setForeground(Color.RED);
					textAge.setText("Geben sie ein richtiges Alter ein");
					return;
				}
				/**
				 * Prüfung ob die Telefonnummer eine Zahl ist
				 */
				try {
					number = Integer.parseInt(textPhoneNumber.getText());
				} catch (NumberFormatException e1) {
					textPhoneNumber.setText("Fehlerhafte Eingabe: [Number]");
					log.warning("Fehlerhafte Eingabe: [Number]");
					textPhoneNumber.setForeground(Color.RED);
					return;
				}
				/**
				 * Prüfung ob überhaupt eine Telefonnummer eingeben wurde
				 */
				if (textPhoneNumber.getText().trim().equals("")) {
					textPhoneNumber.setForeground(Color.RED);
					textPhoneNumber.setText("Pflichteingabe");

					return;
				}
				/**
				 * Prüfung ob Email eingegeben wurde
				 */
				if (textEmail.getText().trim().equals("")) {
					textEmail.setForeground(Color.RED);
					textEmail.setText("Pflichteingabe");

					return;
				}
				/**
				 * Prüfung ob Passwort eingeben wurde
				 */
				if (textPassword.getText().trim().equals("")) {
					textPassword.setForeground(Color.RED);
					textPassword.setText("Pflichteingabe");
				}

				/**
				 * Bei keinem Fehler wird Konstruktor aus Klasse User aufgerufen und befüllt
				 */
				User user = new User(textFirstName.getText(), textLastName.getText(),
						Integer.parseInt(textAge.getText()), Integer.parseInt(textPhoneNumber.getText()),
						textEmail.getText(), textPassword.getText(), CheckCanReadAppointments.isSelected(),
						CheckCanWriteAppointments.isSelected(), CheckCanReadIssues.isSelected(),
						CheckCanWriteIssues.isSelected(), CheckCanReadUsers.isSelected(),
						CheckCanWriteUsers.isSelected());
				/**
				 * User wird gespeichert
				 */
				try {
					AppointmentApp.USERS.store(user);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UserPanel.fillAppointmentList();
				/**
				 * Schriftfarbe wird wieder auf Schwarz gesetzt
				 */
				textFirstName.setForeground(Color.BLACK);
				textLastName.setForeground(Color.BLACK);
				textAge.setForeground(Color.BLACK);
				textPhoneNumber.setForeground(Color.BLACK);
				textEmail.setForeground(Color.BLACK);

				/**
				 * Id wird als neuer Username ausgegeben
				 */
				System.out.println("Sie haben einen neuen User angelegt. Ihr neuer Username ist:" + user.getId());
				JOptionPane.showMessageDialog(null,
						"Sie haben einen neuen User angelegt. Ihr neuer Username ist: username" + user.getId());
				/**
				 * Der Inhalt der Textfelder wird gelöscht
				 */
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
		/**
		 * Aufruf des Konstruktors
		 */
		new UserFrame();

	}

}

package de.nrw.hspv.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.User;
import de.nrw.hspv.util.HspvColor;

/**
 * In der Klasse LogInPanel wird das LogInFenster erstellt. Über diese Klasse
 * kann man sich anmelden um in die Terminverwaltung zu kommen. Es ist möglich
 * sich mit unterschiedlichen Usern mit unterschiedlichen Rechten anzumelden.
 * Beim LogIn erkennt das Programm um welchen User es sich gerade handelt und
 * prüft ob die Id mit dem dazugehörigen Passwort übereinstimmt.
 * 
 * @author Dennis Herrndörfer
 *
 */
public class LogInPanel implements ActionListener, KeyListener {
	/**
	 * Fenster
	 */
	private JFrame jFrame;
	private Container contentPane;

	/**
	 * Komponenten werden erstellt
	 */
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JTextField userJTextField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton resetJButton;
	private JCheckBox showPasswordBox;
	private JCheckBox hidePasswordBox;

	/**
	 * Objekte für den Log In
	 */
	private String PASSWORDString;
	private int id;
	public static FileDatabase<User> USERS;

	/**
	 * Konstruktor
	 */
	public LogInPanel() {
		/**
		 * Fenster erstellt
		 */
		initUI();
		/**
		 * Menü erstellt
		 */
		createMenu();
		/**
		 * Komponenten erstellt
		 */
		createComponent();
		/**
		 * Komponenten hinzufügen
		 */
		addComponentsToContentPane();
		/**
		 * setze das Layout für den Container
		 */
		setLayoutManager();
		setLocationAndSizeOfComponent();

		/**
		 * Try/Catch für die FileDatabase USERS
		 */
		try {

			USERS = new FileDatabase<User>(new File("src/de/nrw/hspv/database/users.dat"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fenster erstellen
	 */

	private void initUI() {
		jFrame = new JFrame("Login");
		jFrame.setSize(400, 400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		jFrame.setBackground(Color.ORANGE);

		contentPane = jFrame.getContentPane();
		contentPane.setBackground(HspvColor.SEC_GRAY);
		jFrame.setResizable(false);

		jFrame.setVisible(true);

	}

	/**
	 * Menü erstellen
	 */

	private void createMenu() {

		JMenuBar jMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu sourceMenu = new JMenu("Source");

		jMenuBar.add(fileMenu);
		jMenuBar.add(editMenu);
		jMenuBar.add(sourceMenu);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			/**
			 * Event Handling für Exit
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		/**
		 * Shortcut für schließen des Programms
		 */
		exitItem.setMnemonic(KeyEvent.VK_E); // Shortcut erstellen
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

		fileMenu.add(exitItem);

		jFrame.setJMenuBar(jMenuBar);

	}

	/**
	 * Komponenten erstellen
	 */
	private void createComponent() {
		/**
		 * Labels werden erstellt
		 */
		userLabel = new JLabel("Username: ");
		userLabel.setForeground(HspvColor.ORANGE);
		passwordLabel = new JLabel("Passwort: ");
		passwordLabel.setForeground(HspvColor.ORANGE);

		userJTextField = new JTextField();

		userJTextField.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent Input) {

				/**
				 * ID wird eingelesen und mit Password verglichen
				 */
				id = Integer.parseInt(userJTextField.getText());

				System.out.println(id);
				if (USERS.get(id) == null) {
					System.out.println("Falsch");
					return false;
				} else {
					User user = (User) USERS.get(id);
					PASSWORDString = user.getPassword();

					return true;

				}
			}
		});
		passwordField = new JPasswordField();
		passwordField.addKeyListener(this);

		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		resetJButton = new JButton("Reset");
		resetJButton.addActionListener(this);

		showPasswordBox = new JCheckBox("Passwort anzeigen");
		showPasswordBox.setForeground(HspvColor.ORANGE);
		showPasswordBox.setBackground(HspvColor.SEC_GRAY);
		showPasswordBox.addActionListener(this);
		hidePasswordBox = new JCheckBox("Passwort verbergen");

	}

	/**
	 * Komponenten hinzufügen
	 */
	private void addComponentsToContentPane() {
		contentPane.add(userLabel);
		contentPane.add(passwordLabel);
		contentPane.add(userJTextField);
		contentPane.add(passwordField);
		contentPane.add(loginButton);
		contentPane.add(resetJButton);
		contentPane.add(hidePasswordBox);
		contentPane.add(showPasswordBox);

	}

	/**
	 * Position der Komponenten festlegen
	 */
	private void setLocationAndSizeOfComponent() {
		userLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setBounds(50, 220, 100, 30);
		userJTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		showPasswordBox.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 300, 100, 30);
		resetJButton.setBounds(200, 300, 100, 30);
	}

	/**
	 * Mögliches Layout setzen
	 */

	private void setLayoutManager() {

		contentPane.setLayout(null);
	}

	public static void main(String[] args) {
		/**
		 * Aufruf des Konstruktors
		 */
		new LogInPanel();
	}

	/**
	 * Dialog erstellen
	 */

	private void createMessage(String message) {
		JOptionPane.showMessageDialog(jFrame, message);
	}

	/**
	 * Event Handling
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String userString = userJTextField.getText();
			char[] chars = passwordField.getPassword();
			String userPasswordString = new String(chars);

			if (PASSWORDString.equalsIgnoreCase(userPasswordString)) {
				System.out.println("Login erfolgreich");
				createMessage("Login erfolgreich");
				/**
				 * Bei richtigem Passwort: AppointmentApp wird passend zum Account geöffnet
				 */
				new AppointmentApp(id);
				/**
				 * Das LogInPanel verschwindet
				 */
				jFrame.dispose();

			} else {
				/**
				 * Bei falschem Passwort wird Fehler ausgegeben
				 */
				System.out.println("Username oder Password falsch");
				createMessage("Username oder Password falsch");
			}
		}
		/**
		 * Bei Drücken des ResetButtons werden die Textfelder geleert
		 */
		if (e.getSource() == resetJButton) {
			userJTextField.setText("");
			passwordField.setText("");
		}
		/**
		 * Bei Drücken des showPasswordBox Buttons wird das Passwortfeld aufgedeckt
		 */
		if (e.getSource() == showPasswordBox) {
			if (showPasswordBox.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			System.out.println("Zum Login");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

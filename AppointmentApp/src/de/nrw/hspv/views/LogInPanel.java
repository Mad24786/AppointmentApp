package de.nrw.hspv.views;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import de.nrw.hspv.views.AppointmentApp;

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
import de.nrw.hspv.util.User;

public class LogInPanel implements ActionListener, KeyListener{
	//Fenster
	private JFrame jFrame;
	private Container contentPane;
	
	// Komponenten
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JTextField userJTextField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton resetJButton;
	private JCheckBox showPasswordBox;
	private JCheckBox hidePasswordBox;
	
	private final String USERNAMEString = "user123";
	private String PASSWORDString	= "123456";
	private int id;
	
	
	public LogInPanel() {
		initUI(); //Fester erstellt
		createMenu(); //Menü erstellt
		createComponent(); //Komponenten erstellt
		addComponentsToContentPane(); //Komponenten hinzufügen
		setLayoutManager(); // setze das Layout für den Container
		setLocationAndSizeOfComponent(); 
	
	}
	
	//Fenster erstellen
	
	private void initUI() {
		jFrame = new JFrame("Login");
		jFrame.setSize(400, 400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		
		contentPane = jFrame.getContentPane();
		jFrame.setResizable(false); //Nutzer kann Fenster nicht mehr kleiner oder größer machen
		jFrame.setVisible(true);
	}
	
	
	// Menü erstellen
	
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		//oder
		
		exitItem.addActionListener(e ->{
			System.exit(0);
		});
		
		exitItem.setMnemonic(KeyEvent.VK_E);		//Shortcut erstellen
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));  
		
		
		fileMenu.add(exitItem);
		
		jFrame.setJMenuBar(jMenuBar);
		
	}
	
	//Komponenten erstellen
	private void createComponent() {
		userLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Passwort: ");
		
		userJTextField = new JTextField();
		userJTextField.setInputVerifier(new InputVerifier() {
				@Override
				public boolean verify(JComponent Input) {
//					int id;
					
					 id = Integer.parseInt(userJTextField.getText());
				
					System.out.println(id);
					if(AppointmentApp.USERS.get(id)== null) {
						System.out.println("Falsch");
						return false;
					}else {
						User user = (User) AppointmentApp.USERS.get(id);
						PASSWORDString = user.getPassword();
						
						return true;
						
					}
				}});
		passwordField = new JPasswordField();
		passwordField.addKeyListener(this);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		resetJButton = new JButton("Reset");
		resetJButton.addActionListener(this);
		
		showPasswordBox = new JCheckBox("Passwort anzeigen");
		showPasswordBox.addActionListener(this);
		hidePasswordBox = new JCheckBox("Passwort verbergen");
		
	}
	
	//Komponenten hinzufügen
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
	private void setLocationAndSizeOfComponent() {
		userLabel.setBounds(50, 150, 100, 30); 
		passwordLabel.setBounds(50,220, 100, 30);
		userJTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		//hidePasswordBox.setBounds(50, 250, 150, 30);
		showPasswordBox.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 300, 100, 30);
		resetJButton.setBounds(200, 300, 100, 30);
	}
	
	//Layout setzen
	
	private void setLayoutManager() {
		
		contentPane.setLayout(null);
	}
	public static void main(String[] args) {

		new LogInPanel();
	}
	
	// Dialog erstellen
	
	private void createMessage(String message) {
		JOptionPane.showMessageDialog(jFrame, message);
	}
	// Event Handling
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== loginButton) {
			String userString = userJTextField.getText();
			char[] chars = passwordField.getPassword();
			String userPasswordString = new String(chars);
			
			if(PASSWORDString.equalsIgnoreCase(userPasswordString)) {
				System.out.println("Login erfolgreich");
				createMessage("Login erfolgreich");
				
				new AppointmentApp(id);
				jFrame.dispose();
				
			}else {
				System.out.println("Username oder Password falsch");
				createMessage("Username oder Password falsch");
			}
		}
		if (e.getSource() == resetJButton) {
			userJTextField.setText("");
			passwordField.setText("");
		}
		
		if (e.getSource()== showPasswordBox) {
			if(showPasswordBox.isSelected()) {
				passwordField.setEchoChar((char) 0);
			}else {
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
		if(e.getKeyChar()== KeyEvent.VK_ENTER) {
			System.out.println("Zum Login");
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

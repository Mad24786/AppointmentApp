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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;

@SuppressWarnings("serial")
public class AppointmentFrame extends JFrame {
	
	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel mainPanel = new JPanel(mainLayout);
		
	public static JPanel centerPanel = new JPanel();
	public static JPanel cards;
	
	public static JTextField txtId;
	public static JComboBox<Issue> cbIssue;
	public static JSpinner spinner;
	public static JTextArea txtText;
	
	public static JPanel addIcon;
	public static JPanel editIcon;
	public static JPanel deleteIcon;
	
	public static JButton btnOk = new JButton("OK");
	public static JButton btnCancel = new JButton("Abbrechen");
	
	public static Vector<Issue> vecIssues = new Vector<Issue>();
	
	public AppointmentFrame(){
//		new Get();
		initComponents();
		createEvents();
	}
	
	private void initComponents() {
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int x = (AppointmentApp.screenSize.width - this.getSize().width) / 2;
	    int y = (AppointmentApp.screenSize.height - this.getSize().height) / 2;
	    setLocation(x, y);
	    
		// TODO Speicher f�r alle Panels, kann noch n�tzlich sein
		HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
		panels.put("main", new JPanel());
				
		/* this Panel */
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(mainLayout);
				
		/* oberes Panel (Men�leiste) */
//		JPanel northPanel = new JPanel();
//		northPanel.setBackground(Color.WHITE);
//		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		
//		/* Buttons generieren */
//		addIcon = new CreateIcon("add", true);
//		northPanel.add(addIcon);
//		editIcon = new CreateIcon("edit", false);
//		northPanel.add(editIcon);
//		deleteIcon = new CreateIcon("delete", false);
//		northPanel.add(deleteIcon);
//		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		/* rechtes Panel */
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.WHITE);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		
		/* unteres Panel */
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(btnCancel);
		southPanel.add(btnOk);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		/* linkes Panel (derzeit nicht ben�tigt) */
//		JPanel westPanel = new JPanel();
//		westPanel.setBackground(Color.WHITE);
//		add(westPanel, BorderLayout.WEST);
		
		/* mittleres Panel */        
		
		GridBagLayout gb = new GridBagLayout();
		centerPanel.setLayout(gb);
		centerPanel.setBackground(Color.WHITE);
		
		JLabel lblId = new JLabel("ID:");
		addComp(gb, lblId, 0, 0, 1, 1);
		txtId = new JTextField();
		txtId.setEnabled(false);
		addComp(gb, txtId, 1, 0, 1, 1);
		
		JLabel lblIssue = new JLabel("Anliegen:");
		addComp(gb, lblIssue, 0, 1, 1, 1);
		
		@SuppressWarnings("static-access")
		ArrayList<Issue> allIssues = AppointmentApp.ISSUES.getAllAsArrayList();
		
		for(Issue i : allIssues) {
			vecIssues.add(i);
		}
		
		
		cbIssue = new JComboBox<Issue>(vecIssues);
        cbIssue.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Issue i = (Issue) e.getItem();
				txtId.setText(String.valueOf(i.getId()));
			}
		});
        addComp(gb, cbIssue, 1, 1, 1, 1);
		
		JLabel lblDate = new JLabel("Datum:");
		addComp(gb, lblDate, 0, 2, 1, 1);

        Calendar calendar = Calendar.getInstance();

        //Add the third label-spinner pair.
//        Date initDate = calendar.getTime();
        Date initDate = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 8, 0).getTime();
        calendar.add(Calendar.YEAR, -1);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
        
        spinner = new JSpinner(dateModel);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd.MM.yyyy, HH:mm 'Uhr'"));
		addComp(gb, spinner, 1, 2, 1, 1);
		
		JLabel lblTime = new JLabel("Uhrzeit:");
		addComp(gb, lblTime, 0, 3, 1, 1);
		JTextField txtTime = new JTextField();
		txtTime.setEnabled(false);
		addComp(gb, txtTime, 1, 3, 1, 1);
		
		JLabel lblEmployee = new JLabel("Mitarbeiter:");
		addComp(gb, lblEmployee, 0, 4, 1, 1);
		JTextField txtEmployee = new JTextField();
		addComp(gb, txtEmployee, 1, 4, 1, 1);
		
		JLabel lblCustomer = new JLabel("Kunde:");
		addComp(gb, lblCustomer, 0, 5, 1, 1);
		JTextField txtCustomer = new JTextField();
		addComp(gb, txtCustomer, 1, 5, 1, 1);
		
		JLabel lblText = new JLabel("Bemerkung:");
		addComp(gb, lblText, 0, 6, 1, 1);
		txtText = new JTextArea();
		txtText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addComp(gb, txtText, 1, 6, 1, 1);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		add(mainPanel);
		
		addWindowListener(new MyWindowListener());
		
//		pack();
		setVisible(false);
				
	}
	
	public static void createEvents() {
		
//		editIcon.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println("Clicked");
//				
//			}
//			
//		});
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Date start = (Date) spinner.getValue();
				Date end = null;
				User user = null;
				Issue issue = (Issue) cbIssue.getSelectedItem();
				
				Appointment a = new Appointment(user, issue, start, end, txtText.getText());
				try {
					AppointmentApp.APPOINTMENTS.store(a);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
	}
	private class MyWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			setVisible(false);		
			AppointmentApp.refreshDashboard();
			AppointmentApp.log.log(Level.INFO, "Appointment window set invisible");
		}
	}
	
	public class CreateIcon extends JPanel {
		
		private String s; 
			
		public CreateIcon(String s, boolean access) {
			
			this.s = s;
			
			Dimension size = new Dimension(50,50);
			
			setPreferredSize(size);
			setMaximumSize(size);
			
			if(access) {
				setBackground(HspvColor.ORANGE);
			}
			else {
				setBackground(HspvColor.SEC_GRAY);
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon(AppointmentApp.class.getResource("/de/nrw/hspv/ressources/" + s + ".png"));
			icon.paintIcon(this, g, ((this.getWidth() - icon.getIconWidth()) / 2), ((this.getHeight() - icon.getIconHeight()) / 2));
		}

	}
	
	public void addComp(GridBagLayout gb, Component c, int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1;
		gb.setConstraints(c, gbc);
		centerPanel.add(c);				
	}
	
	public static void main(String[] args) {

		new AppointmentFrame();

	}

}

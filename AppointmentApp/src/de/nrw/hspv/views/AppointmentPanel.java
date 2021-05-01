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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import de.nrw.hspv.database.Get;
import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;

@SuppressWarnings("serial")
public class AppointmentPanel extends JPanel {
	
	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel mainPanel = new JPanel(mainLayout);
	
	
	
	public static JPanel centerPanel;
	
	public static JTextField txtId;
	public static JComboBox<Issue> cbIssue;
	public static JSpinner spinner;
	public static JTextArea txtText;
	
	public static JButton btnOk = new JButton("OK");
	public static JButton btnCancel = new JButton("Abbrechen");
	
	public static Vector<Issue> vecIssues = new Vector<Issue>();
	
	public AppointmentPanel(){
		initComponents();
		createEvents();
	}
	
	private void initComponents() {
	
		// TODO Speicher für alle Panels, kann noch nützlich sein
		HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
		panels.put("main", new JPanel());
				
		/* this Panel */
		setBackground(Color.WHITE);
		setLayout(mainLayout);
				
		/* oberes Panel (Menüleiste) */
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		/* Buttons generieren */
		JPanel addIcon = new CreateIcon("add", true);
		northPanel.add(addIcon);
		JPanel editIcon = new CreateIcon("edit", false);
		northPanel.add(editIcon);
		JPanel deleteIcon = new CreateIcon("delete", false);
		northPanel.add(deleteIcon);
		add(northPanel, BorderLayout.NORTH);
		
		/* rechtes Panel */
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.WHITE);
		add(eastPanel, BorderLayout.EAST);
		
		/* unteres Panel */
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(btnCancel);
		southPanel.add(btnOk);
		add(southPanel, BorderLayout.SOUTH);
		
		/* linkes Panel (derzeit nicht benötigt) */
//		JPanel westPanel = new JPanel();
//		westPanel.setBackground(Color.WHITE);
//		add(westPanel, BorderLayout.WEST);
		
		/* mittleres Panel */
		centerPanel = new CreateCenterAdd();
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);
		
//		add(mainPanel);
				
	}
	
	public static void createEvents() {
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Appointment a = new Appointment(Integer.parseInt(txtId.getText()), ((Issue) cbIssue.getSelectedItem()), ((Date) spinner.getValue()), txtText.getText());
				try {
					Get.appointments.store(a);
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
	
	public static class CreateCenterAdd extends JPanel{
		
		public CreateCenterAdd() {

			GridBagLayout gb = new GridBagLayout();
			setLayout(gb);
//			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			JLabel lblId = new JLabel("ID:");
			addComp(gb, lblId, 0, 0, 1, 1);
			txtId = new JTextField();
			txtId.setEnabled(false);
			addComp(gb, txtId, 1, 0, 1, 1);
			
			JLabel lblIssue = new JLabel("Anliegen:");
			addComp(gb, lblIssue, 0, 1, 1, 1);
			
			@SuppressWarnings("static-access")
			ArrayList<Issue> allIssues = AppointmentApp.get.issues.getAllAsArrayList();
			//Collections.sort(allIssues);
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
//	        Date initDate = calendar.getTime();
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
		
		}
		
		private void addComp(GridBagLayout gb, Component c, int x, int y, int w, int h) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2,2,2,2);
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.gridwidth = w;
			gbc.gridheight = h;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = gbc.weighty = 1;
			gb.setConstraints(c, gbc);
			add(c);				
		}
		
	}

	public static void main(String[] args) {

		new AppointmentPanel();

	}

}

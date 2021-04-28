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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.nrw.hspv.util.HspvColor;
import de.nrw.hspv.util.Issue;

@SuppressWarnings("serial")
public class AppointmentPanel extends JPanel {
	
	public static BorderLayout mainLayout = new BorderLayout();
	public static JPanel mainPanel = new JPanel(mainLayout);
	
	
	
	public static JPanel centerPanel;
	
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
		mainPanel.setBackground(Color.WHITE);
				
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
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		/* rechtes Panel */
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.WHITE);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		
		/* unteres Panel */
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new FlowLayout(SwingConstants.RIGHT));
		southPanel.add(btnCancel);
		southPanel.add(btnOk);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		/* linkes Panel (derzeit nicht benötigt) */
//		JPanel westPanel = new JPanel();
//		westPanel.setBackground(Color.WHITE);
//		add(westPanel, BorderLayout.WEST);
		
		/* mittleres Panel */
		centerPanel = new CreateCenterAdd();
		centerPanel.setBackground(Color.WHITE);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		add(mainPanel);
				
	}
	
	public static void createEvents() {
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel");
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
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			JLabel lblId = new JLabel("ID:");
			addComp(gb, lblId, 0, 0, 1, 1);
			JTextField txtId = new JTextField();
			addComp(gb, txtId, 1, 0, 1, 1);
			
			JLabel lblIssue = new JLabel("Anliegen:");
			addComp(gb, lblIssue, 0, 1, 1, 1);
			
			@SuppressWarnings("static-access")
			ArrayList<Issue> allIssues = AppointmentApp.get.issues.getAllAsArrayList();
			for(Issue i : allIssues) {
				vecIssues.add(i);
			}
	        JComboBox<Issue> cbIssue = new JComboBox<Issue>(vecIssues);
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
			JTextField txtDate = new JTextField();
			addComp(gb, txtDate, 1, 2, 1, 1);
			
			JLabel lblTime = new JLabel("Uhrzeit:");
			addComp(gb, lblTime, 0, 3, 1, 1);
			JTextField txtTime = new JTextField();
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
			JTextArea txtText = new JTextArea();
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

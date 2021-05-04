package de.nrw.hspv.views;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nrw.hspv.util.Issue;

import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("serial")
public class IssuePanel extends JPanel{
		
	JButton btnOk;
	JButton btnCancel;
	
	JLabel lblId;
	JLabel lblName;
	JLabel lblTime;
	JLabel lblMsg;
	JTextField txtId;
	JTextField txtName;
	JTextField txtDesc;
	JTextField txtTime;
	
	JPanel icon;
	
	GridBagConstraints constr;
	
	public IssuePanel(){
		initComponents();
		createEvents();
	}
	
	public void initComponents() {
		setLayout(new GridLayout(0,2));
		setBackground(Color.WHITE);

        Vector<Issue> vec = new Vector<Issue>();
        /** Holt alle Objekte aus der DB ab */
		@SuppressWarnings("static-access")
		ArrayList<Issue> allIssues = AppointmentApp.ISSUES.getAllAsArrayList();
		for(Issue i : allIssues) {
			vec.add(i);
		}
        JComboBox<Issue> highscoreCombo = new JComboBox<Issue>(vec);
        highscoreCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Issue i = (Issue) e.getItem();
				txtId.setText(String.valueOf(i.getId()));
			}
		});

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		GridLayout centerLayout = new GridLayout(2,0);
		centerPanel.setLayout(centerLayout);

		lblId = new JLabel("ID:");
		lblId.setPreferredSize(new Dimension(50, 24));
        add(lblId);
        txtId = new JTextField();
        add(txtId);
        lblName = new JLabel("Bezeichnung:");
        add(lblName); 
        txtName = new JTextField();
        add(txtName);
        JLabel lblDesc = new JLabel("Beschreibung:");
        add(lblDesc);      
        txtDesc = new JTextField();
        add(txtDesc);
		lblTime = new JLabel("Dauer in Minuten:");
        add(lblTime);      
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 120, 20);
        slider.setBackground(Color.WHITE);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension(400,100));
        add(slider);
        JLabel label = new JLabel("Alle Anliegen: ");
        add(label);
        add(highscoreCombo);
        
        slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println(slider.getValue());
			}
		});
        
	}
	
	public void createEvents() {
		
//		btnCancel.addActionListener(new ActionListener() {	
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				log.info("Aktion abgebrochen.");
////				dispose();
//			}
//		});
		
//		btnOk.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				lblId.setForeground(Color.BLACK);
//				lblName.setForeground(Color.BLACK);
//				lblTime.setForeground(Color.BLACK);
//				lblMsg.setText("");
//				
//				int id, time;
//				
//				try {
//					id = Integer.parseInt(txtId.getText());
//				} catch (NumberFormatException nfe) {
//					lblMsg.setText("Fehlerhafte Eingabe: {ID}");
//					log.warning("Fehlerhafte Eingabe: {ID}");
//					lblId.setForeground(Color.RED);
//					return;
//				}
//				
//				if(txtName.getText().trim().equals("")) {
//					lblName.setForeground(Color.RED);
//					lblMsg.setText("Fehlerhafte Eingabe. Das Feld {Name} ist ein Pflichtfeld.");
//					log.warning("Das Feld {Name} ist ein Pflichtfeld.");
//					return;
//				}
//				
//				try {
//					time = Integer.parseInt(txtTime.getText());
//				} catch (NumberFormatException nfe) {
//					lblMsg.setText("Fehlerhafte Eingabe: {Zeit}");
//					log.warning("Fehlerhafte Eingabe: {Zeit}");
//					lblTime.setForeground(Color.RED);
//					return;
//				}
//			
//				Issue issue = new Issue(id, txtName.getText(), (1000*60*time));
//				try {
//					Load.getIssues().store(issue);
//					lblMsg.setText("Anliegen erfolgreich erstellt.");
//					txtId.setText("");
//					txtName.setText("");
//					txtDesc.setText("");
//					txtTime.setText("");
//				} catch (IOException e1) {
//					lblMsg.setText("Fehler beim Schreiben des Objekts.");
//					log.warning("Fehler beim Schreiben des Objekts.");
//				}
//
//			}
//		});
		
	}
	
	public static void main(String[] args) {
		
		new IssuePanel();
		
	}

}

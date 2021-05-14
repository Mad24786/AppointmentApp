package de.nrw.hspv.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nrw.hspv.util.Issue;

import java.util.ArrayList;
import java.util.Vector;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class creates an IssueFrame as an instance of JFrame. You can use this
 * class to describe the issues.
 * 
 * @author Luis Duhme
 * @version 17 May 2021
 * @see javax.swing.JFrame
 *
 */

@SuppressWarnings("serial")
public class IssueFrame extends JFrame {
	// Create instance of JFrame

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

//IssueFrame with events and its components 
	public IssueFrame() {
		getContentPane().setBackground(new Color(255, 102, 51));
		initComponents();
		createEvents();
	}

//Here the dropdown list at the bottom of the frame is set
	public void initComponents() {
		setBackground(new Color(255, 102, 51));
		setSize(new Dimension(640, 480));
		Vector<Issue> vec = new Vector<Issue>();
		/** Holt alle Objekte aus der DB ab */
		@SuppressWarnings("static-access")
		ArrayList<Issue> allIssues = AppointmentApp.ISSUES.getAllAsArrayList();
		for (Issue i : allIssues) {
			vec.add(i);
		}
		JComboBox<Issue> highscoreCombo = new JComboBox<Issue>(vec);
		highscoreCombo.setBounds(201, 305, 255, 33);
		highscoreCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Issue i = (Issue) e.getItem();
				txtId.setText(String.valueOf(i.getId()));
			}
		});
//The JPanel which is located in the Center is created and described here
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		GridLayout centerLayout = new GridLayout(2, 0);
		centerPanel.setLayout(centerLayout);
		getContentPane().setLayout(null);
//The components are described with e.g. their size
		lblId = new JLabel("ID:");
		lblId.setForeground(new Color(255, 255, 204));
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setBounds(71, 54, 66, 46);
		lblId.setPreferredSize(new Dimension(50, 24));
		getContentPane().add(lblId);
		txtId = new JTextField();
		txtId.setBounds(201, 67, 255, 25);
		getContentPane().add(txtId);
		lblName = new JLabel("Bezeichnung:");
		lblName.setForeground(new Color(255, 255, 204));
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(60, 115, 106, 39);
		getContentPane().add(lblName);
		txtName = new JTextField();
		txtName.setBounds(201, 125, 255, 25);
		getContentPane().add(txtName);
		JLabel lblDesc = new JLabel("Beschreibung:");
		lblDesc.setForeground(new Color(255, 255, 204));
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDesc.setBounds(60, 176, 135, 39);
		getContentPane().add(lblDesc);
		txtDesc = new JTextField();
		txtDesc.setBounds(201, 185, 255, 25);
		getContentPane().add(txtDesc);
		lblTime = new JLabel("Dauer in Minuten:");
		lblTime.setForeground(new Color(255, 255, 204));
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTime.setBounds(60, 228, 120, 39);
		getContentPane().add(lblTime);
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 120, 20);
		slider.setForeground(new Color(255, 255, 255));
		slider.setFont(new Font("Tahoma", Font.PLAIN, 12));
		slider.setBounds(201, 227, 255, 61);
		slider.setBackground(new Color(255, 102, 51));
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(400, 100));
		getContentPane().add(slider);
		JLabel label = new JLabel("Alle Anliegen: ");
		label.setForeground(new Color(255, 255, 204));
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(60, 296, 90, 46);
		getContentPane().add(label);
		getContentPane().add(highscoreCombo);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println(slider.getValue());
			}
		});

	}

	public void createEvents() {

	}

	public static void main(String[] args) {

		new IssueFrame();

	}
}

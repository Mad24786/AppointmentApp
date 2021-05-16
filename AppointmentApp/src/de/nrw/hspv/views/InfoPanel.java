package de.nrw.hspv.views;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.nrw.hspv.views.DashboardPanel.AppointmentsByDate;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;

import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * In this class InfoPanel.java an instance of JPanel is created. Here you can
 * see the logging and toggle it. If you Click on the i in the navigtion bar on
 * the left, you open this panel.
 * 
 * @author Luis Duhme
 * @version 17 May 2021
 */
public class InfoPanel extends JPanel {

//Creating the panel as in instance of JPanel.

	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	JTextArea txtrThisIsInfo;

//Creating the JTextArea, where the logging is displayed, if enabled.
	public InfoPanel() {
		setBackground(Color.WHITE);
		setLayout(null);

		txtrThisIsInfo = new JTextArea();
		txtrThisIsInfo.setWrapStyleWord(true);
		txtrThisIsInfo.setText("This is info text Area");
//Creating the scrolling function to look through the logged text.		
		JScrollPane scroll = new JScrollPane(txtrThisIsInfo);
		scroll.setBounds(0, 55, 420, 494);
		add(scroll);
		/**
		 * Creating the CheckBox, where you can enable and disable the logging. The
		 * ItemListener is created and bound to to events: toggle the logEvents and show
		 * in the south panel, if the logging is activated.
		 *
		 */
		JCheckBox chckbxNewCheckBox = new JCheckBox("Logging-Funktion");
		chckbxNewCheckBox.setBounds(0, 6, 142, 30);
		chckbxNewCheckBox.setSelected(AppointmentApp.logEvents);
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// on change switch settings
				if (chckbxNewCheckBox.isSelected()) {
					AppointmentApp.logEvents = true;
					AppointmentApp.lblLogging
							.setText("| Logging: " + (AppointmentApp.logEvents ? "aktiviert" : "deaktiviert"));
				} else {
					AppointmentApp.logEvents = false;
					AppointmentApp.lblLogging
							.setText("| Logging: " + (AppointmentApp.logEvents ? "aktiviert" : "deaktiviert"));
				}

			}
		});
		/**
		 * Fill textarea with content: the log.txt file from the database folder, which
		 * contains event event that as been logged. This happens not, if the CheckBox
		 * is not checked.
		 */
		add(chckbxNewCheckBox);
		String filePath = "src/de/nrw/hspv/database/log.txt";
		getDataFromFile(filePath);

	}

	void getDataFromFile(String table) {
		txtrThisIsInfo.setText("");
		try {
			BufferedReader br = new BufferedReader(new FileReader(table));
			String s = "";
			while ((s = br.readLine()) != null) {

				txtrThisIsInfo.append(s + "\n");

			}
			br.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
}

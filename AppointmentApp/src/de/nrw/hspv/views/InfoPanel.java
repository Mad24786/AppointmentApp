package de.nrw.hspv.views;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.nrw.hspv.views.DashboardPanel.AppointmentsByDate;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InfoPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	JTextArea txtrThisIsInfo;

	public InfoPanel() {
		setBackground(Color.WHITE);
		setLayout(null);

		txtrThisIsInfo = new JTextArea();
		txtrThisIsInfo.setWrapStyleWord(true);
		txtrThisIsInfo.setText("This is info text Area");
		JScrollPane scroll = new JScrollPane(txtrThisIsInfo);
		scroll.setBounds(0, 55, 420, 494);
		add(scroll);

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

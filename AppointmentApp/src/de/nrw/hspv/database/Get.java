package de.nrw.hspv.database;

import java.io.IOException;
import java.io.Serializable;

import de.nrw.hspv.util.Appointment;
import de.nrw.hspv.util.FileDatabase;
import de.nrw.hspv.util.Issue;
import de.nrw.hspv.util.User;

@SuppressWarnings("serial")
public class Get implements Serializable {
	
	public static FileDatabase<Appointment> appointments;
	public static FileDatabase<Issue> issues;
	public static FileDatabase<User> users;
	
	public Get() {	
		try {
			issues = new FileDatabase<Issue>("src\\de\\nrw\\hspv\\database\\issues.dat");
			users = new FileDatabase<User>("src\\de\\nrw\\hspv\\database\\users.dat");
			appointments = new FileDatabase<Appointment>("src\\de\\nrw\\hspv\\database\\appointments.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileDatabase<User> getUsers() { return users; }
	public static FileDatabase<Issue> getIssues() { return issues; }
	public static FileDatabase<Appointment> getAppointments() { return appointments; }

}

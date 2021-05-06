package de.nrw.hspv.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import de.nrw.hspv.views.AppointmentApp;
import de.nrw.hspv.views.DashboardPanel;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4763035350427944677L;

	// Ale Datenbanken laden, und benötigte Datenbank holen

	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private int phoneNumber;
	// private boolean sachbearbeiter;
	private String email;
	private boolean canReadAppointments = false;
	private boolean canWriteAppointments = false;
	private boolean canReadIssues = false;
	private boolean canWriteIssues = false;
	private boolean canReadUsers = false;
	private boolean canWriteUsers = false;
	private String password;
	private String username;

	// Konstruktor
	public User() {

	}

	public User(String firstName, String lastName, int age, int phoneNumber, String email, String password,
			boolean canReadAppointments, boolean canWriteAppointments, boolean canReadIssues, boolean canWriteIssues,
			boolean canReadUsers, boolean canWriteUsers) {
		this.id = AppointmentApp.USERS.getNextId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.canReadAppointments = canReadAppointments;
		this.canWriteAppointments = canWriteAppointments;
		this.canReadIssues = canReadIssues;
		this.canWriteIssues = canWriteIssues;
		this.canReadUsers = canReadUsers;
		this.canWriteUsers = canWriteUsers;
		this.username = "username"+getId();
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s (%d)", getLastName(), getFirstName(), getId());
	}

	// Getter und Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public boolean getSachbearbeiter() {
//		return sachbearbeiter;
//	}
//
//	public void setSachbearbeiter(boolean sachbearbeiter) {
//		sachbearbeiter = sachbearbeiter;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCanReadAppointments() {
		return canReadAppointments;
	}

	public void setCanReadAppointments(boolean canRead) {
		this.canReadAppointments = canReadAppointments;
	}

	public boolean isCanWriteAppointments() {
		return canWriteAppointments;
	}

	public void setCanWriteAppointments(boolean canWrite) {
		this.canWriteAppointments = canWriteAppointments;
	}

	public boolean isCanReadIssues() {
		return canReadIssues;
	}

	public void setCanReadIssues(boolean canReadIssues) {
		this.canReadIssues = canReadIssues;
	}

	public boolean isCanWriteIssues() {
		return canWriteIssues;
	}

	public void setCanWriteIssues(boolean canWriteIssues) {
		this.canWriteIssues = canWriteIssues;
	}

	public boolean isCanReadUsers() {
		return canReadUsers;
	}

	public void setCanReadUsers(boolean canReadUsers) {
		this.canReadUsers = canReadUsers;
	}

	public boolean isCanWriteUsers() {
		return canWriteUsers;
	}

	public void setCanWriteUsers(boolean canWriteUsers) {
		this.canWriteUsers = canWriteUsers;
	}

	public static void main(String[] args) {
		User user = new User();
	}

}

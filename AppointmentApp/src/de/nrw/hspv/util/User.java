package de.nrw.hspv.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	// Ale Datenbanken laden, und benötigte Datenbank holen

	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private int phoneNumber;
	// private boolean sachbearbeiter;
	private String email;
	private boolean canRead = false;
	private boolean canWrite = true;

	// Konstruktor
	public User() {

	}

	public User(int id, String firstName, String lastName, int age, int phoneNumber, String email, boolean canRead, boolean canWrite) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.canRead = canRead;
		this.canWrite = canWrite;
	}

	// Getter und Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public static void main(String[] args) {
		User user = new User();
	}

	public boolean isCanRead() {
		return canRead;
	}

	public void setCanRead(boolean canRead) {
		this.canRead = canRead;
	}

	public boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

}

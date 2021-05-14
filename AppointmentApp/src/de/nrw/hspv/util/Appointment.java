package de.nrw.hspv.util;

import java.io.Serializable;
import java.util.Date;

import de.nrw.hspv.views.AppointmentApp;
import de.nrw.hspv.views.DashboardPanel;

// TODO find another place for sdf

/**
 * 
 * If a new appointment is created, an object of this class will be generated.
 * An appointment contains a bunch of informations, which are partially given by
 * user input. Other informations will be created by the system.
 * 
 * 
 * @author Mathias Fernahl
 * @version 17 May 2021
 * @see java.io.Serializable
 * @see java.lang.Comparable
 */
public class Appointment implements Serializable, Comparable<Appointment> {

	/**
	 * A unique serial version identifier.
	 * 
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 7959084599528377433L;

	int id, employeeId, customerId;
	User employee;
	User author;
	Issue issue;
	Date start;
	Date end;
	String message;

	/**
	 * The appointment object is going to be created here.
	 * 
	 * @param user   A user object of the employee who's (hopefully) going to do
	 *               this appointment. This object is chosen by a user in
	 *               <code>AppointmentFrame</code>.
	 * @param author Author is the user, who is currently logged in and creates this
	 *               appointment. System will give this param automatically.
	 * @param issue  The concrete issue to do as its object. This object is chosen
	 *               by a user in <code>AppointmentFrame</code>.
	 * @param start  A date object of the start of this appointment. This object is
	 *               build by a user choice in <code>AppointmentFrame</code>.
	 * @param end    The end of the appointment. This date object will be
	 *               automacially generated due to chosen issue object and its
	 *               scheduled time.
	 * @param cMsg   A custom message to give useful hints for processing employee.
	 */
	public Appointment(User user, User author, Issue issue, Date start, Date end, String cMsg) {
		/*
		 * generate a new ID from appointments database
		 */
		setId(AppointmentApp.APPOINTMENTS.getNextId());
		setEmployee(user);
		setAuthor(author);
		setIssue(issue);
		setStart(start);
		setEnd(end);
		setMessage(cMsg);
	}

	/**
	 * An appointment is a comparable object, so this methods defines the regulations,
	 * if a sorted list of all appointments is needed by the system.
	 * 
	 * Here it is the chronologically order between the appointments to compare.
	 * 
	 * @param a The appointment object to compare.
	 */
	@Override
	public int compareTo(Appointment a) {
		if (this.getStart().after(a.getStart())) {
			return 1;
		} else if (this.getStart().before(a.getStart())) {
			return -1;
		} else
			return 0;
	}

	/**
	 * @return A more or less nice String. Useful for overview in <code>AppointentPanel</code>.
	 */
	@Override
	public String toString() {
		return String.format("%s, %s - %s: %s", DashboardPanel.sdfDate.format(getStart()), DashboardPanel.sdfTime.format(getStart()),
												DashboardPanel.sdfTime.format(getEnd()), getIssue().getName());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date d) {
		this.start = d;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

}

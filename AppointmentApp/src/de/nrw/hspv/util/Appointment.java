package de.nrw.hspv.util;

import java.io.Serializable;
import java.util.Date;

import de.nrw.hspv.views.AppointmentApp;
import de.nrw.hspv.views.DashboardPanel;

/**
 * 
 * @author mfern
 *
 */

public class Appointment implements Serializable, Comparable<Appointment> {
		
	/**
	 * 
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
	 * 
	 * Constructor creates an appointment object with 
	 * 
	 * @param user		ID of employee object who is going to handle this appointment
	 * @param issue		Issue object for this appointment
	 * @param start		Date object that marks the beginning of this appointment
	 * @param end		The end of this appointment
	 * @param cMsg		A custom message 
	 */
	public Appointment(User user, User author, Issue issue, Date start, Date end, String cMsg){
		setId(AppointmentApp.APPOINTMENTS.getNextId());
		setEmployee(user);
		setAuthor(author);
		setIssue(issue);
		setStart(start);
		setEnd(end);
		setMessage(cMsg);	
	}
	
	@Override
	public int compareTo(Appointment o) {
		if(this.getStart().after(o.getStart())) {
			return 1;
		}
		else if(this.getStart().before(o.getStart())) {
			return -1;
		}
		else return 0;
	}
	@Override
	public String toString() {
		return String.format("%s", DashboardPanel.sdfDate.format(getStart()) + ", " + DashboardPanel.sdfTime.format(getStart()) + " - " + DashboardPanel.sdfTime.format(getEnd()) + ": " + getIssue().getName());
	}
	
	public int getId() { return id;	}
	
	public void setId(int id) { this.id = id; }
	
	public int getEmployeeId() { return employeeId; }
	
	public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
	
	public int getCustomerId() { return customerId; }
	
	public void setCustomerId(int customerId) { this.customerId = customerId; }
	
	public User getEmployee() { return employee; }

	public void setEmployee(User employee) { this.employee = employee; }

	public Issue getIssue() { return issue; }

	public void setIssue(Issue issue) { this.issue = issue; }

	public Date getStart() { return start; }
	
	public void setStart(Date d) { this.start = d; }
	
	public Date getEnd() { return end; }

	public void setEnd(Date end) { this.end = end; }

	public String getMessage() { return message; }
	
	public void setMessage(String message) { this.message = message; }

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	

}

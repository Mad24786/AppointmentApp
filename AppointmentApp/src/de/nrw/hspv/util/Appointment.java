package de.nrw.hspv.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author mfern
 *
 */

@SuppressWarnings("serial")
public class Appointment implements Serializable, Comparable<Appointment> {
		
	int id, employeeId, customerId;
	User employee;
	Issue issue;
	Date start;
	Date end;
	String customerMessage;
	
	
	/**
	 * 
	 * Constructor creates an appointment object with 
	 * 
	 * @param eId	ID of employee object who is going to handle this appointment
	 * @param i		Issue object for this appointment
	 * @param s		Date object that marks the beginning of this appointment
	 * @param e		The end of this appointment
	 * @param cMsg	A custom message 
	 */
	public Appointment(User eId, Issue i, Date s, Date e, String cMsg){
		setEmployee(eId);
		setIssue(i);
		setStart(s);
		setEnd(e);
		setCustomerMessage(cMsg);	
	}
	
	public void main(String[] args){
		
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

	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
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

}

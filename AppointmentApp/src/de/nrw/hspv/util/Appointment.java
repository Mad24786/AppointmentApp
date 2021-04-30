package de.nrw.hspv.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Appointment implements Serializable, Comparable<Appointment> {
		
	int id, employeeId, customerId;
	Issue issue;
	Date dateAndTime;
	String customerMessage;
	
	public Appointment(int eId, Issue i, Date d, String cMsg){
		setCustomerId(eId);
		setIssue(i);
		setDateAndTime(d);
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
	
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date d) {
		this.dateAndTime = d;
	}
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}

	@Override
	public int compareTo(Appointment o) {
		if(this.getDateAndTime().after(o.getDateAndTime())) {
			return 1;
		}
		else if(this.getDateAndTime().before(o.getDateAndTime())) {
			return -1;
		}
		else return 0;
	}

}

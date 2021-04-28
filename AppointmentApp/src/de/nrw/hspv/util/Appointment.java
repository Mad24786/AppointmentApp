package de.nrw.hspv.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Appointment implements Serializable {
		
	int id, employeeId, customerId, issueId;
	long dateAndTime;
	String customerMessage;
	
	public Appointment(int cId, int iId, int[] d, String cMsg){
		setCustomerId(cId);
		setIssueId(iId);
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
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public long getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(int[] d) {
		String myDate = String.format("%d/%d/%d %d:%d", d[0], d[1], d[2], d[3], d[4]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = null;
		try {
			date = sdf.parse(myDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dateAndTime = date.getTime();
	}
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}

}

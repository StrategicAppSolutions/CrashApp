package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	String contact_fname;
	String contact_lname;
	long contact_phone;
	String contact_email;
	public Contact(){
		
	}
	public String getContact_fname() {
		return contact_fname;
	}
	public void setContact_fname(String contact_fname) {
		this.contact_fname = contact_fname;
	}
	public String getContact_lname() {
		return contact_lname;
	}
	public void setContact_lname(String contact_lname) {
		this.contact_lname = contact_lname;
	}
	public long getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(long contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
	
	
}

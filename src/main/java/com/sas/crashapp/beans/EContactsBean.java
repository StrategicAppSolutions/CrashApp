package com.sas.crashapp.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class EContactsBean {
	long attorney_id;
	long user_id;
	List<Contact> contacts;
	int success;
	public EContactsBean(){
		
	}
	public long getAttorney_id() {
		return attorney_id;
	}
	public void setAttorney_id(long attorney_id) {
		this.attorney_id = attorney_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
}

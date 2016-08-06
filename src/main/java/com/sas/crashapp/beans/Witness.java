package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Witness {
	String witness_fname;
	String witness_lname;
	long witness_phone;
	String call_permission;
	//long witness_id;
	public Witness(){
		
	}
	public String getWitness_fname() {
		return witness_fname;
	}
	public void setWitness_fname(String witness_fname) {
		this.witness_fname = witness_fname;
	}
	public String getWitness_lname() {
		return witness_lname;
	}
	public void setWitness_lname(String witness_lname) {
		this.witness_lname = witness_lname;
	}
	public long getWitness_phone() {
		return witness_phone;
	}
	public void setWitness_phone(long witness_phone) {
		this.witness_phone = witness_phone;
	}
	public String getCall_permission() {
		return call_permission;
	}
	public void setCall_permission(String call_permission) {
		this.call_permission = call_permission;
	}
	/*public long getWitness_id() {
		return witness_id;
	}
	public void setWitness_id(long witness_id) {
		this.witness_id = witness_id;
	}*/
	
	
}

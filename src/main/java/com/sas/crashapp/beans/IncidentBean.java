package com.sas.crashapp.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IncidentBean {
	long attorney_id;
	long user_id;
	List<Images> images;
	List<Witness> witness;
	
	String insurance_company;
	String insurance_policy;
	int success;
	long incident_id;
	
	
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
	public List<Witness> getWitness() {
		return witness;
	}
	public void setWitness(List<Witness> witness) {
		this.witness = witness;
	}
	public String getInsurance_company() {
		return insurance_company;
	}
	public void setInsurance_company(String insurance_company) {
		this.insurance_company = insurance_company;
	}
	public String getInsurance_policy() {
		return insurance_policy;
	}
	public void setInsurance_policy(String insurance_policy) {
		this.insurance_policy = insurance_policy;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public long getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(long incident_id) {
		this.incident_id = incident_id;
	}
	public List<Images> getImages() {
		return images;
	}
	public void setImages(List<Images> images) {
		this.images = images;
	}
	
}

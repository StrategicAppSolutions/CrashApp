package com.sas.crashapp.beans;

import java.util.List;

public class IncidentImages {
	
	long user_id;
	long attorney_id;
	long incident_id;
	int success;
	List<Images> images;
	public long getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(long incident_id) {
		this.incident_id = incident_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getAttorney_id() {
		return attorney_id;
	}
	public void setAttorney_id(long attorney_id) {
		this.attorney_id = attorney_id;
	}
	public List<Images> getImages() {
		return images;
	}
	public void setImages(List<Images> images) {
		this.images = images;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	
	
	

}

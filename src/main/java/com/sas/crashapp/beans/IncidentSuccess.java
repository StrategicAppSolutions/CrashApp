package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IncidentSuccess {
	int success;
	long incident_id;
	String image_url;
	String details_url;
	
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getDetails_url() {
		return details_url;
	}
	public void setDetails_url(String details_url) {
		this.details_url = details_url;
	}
	public IncidentSuccess(){
		
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
}

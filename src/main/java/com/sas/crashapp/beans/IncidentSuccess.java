package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IncidentSuccess {
	int success;
	long incident_id;
	String imgUpload;
	
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
	public String getImgUpload() {
		return imgUpload;
	}
	public void setImgUpload(String imgUpload) {
		this.imgUpload = imgUpload;
	}
}

package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorBean {
	public int success;
	public int error_code;
	public String error_description;
	public ErrorBean(){
		
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

}

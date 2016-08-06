package com.sas.crashapp.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Images {
	String img_url;
	String bucket_name;
	String key_name;
	
	public Images(){
		
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getBucket_name() {
		return bucket_name;
	}
	public void setBucket_name(String bucket_name) {
		this.bucket_name = bucket_name;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	} 
	
}

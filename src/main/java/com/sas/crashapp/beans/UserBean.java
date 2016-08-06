package com.sas.crashapp.beans;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBean {
	int success;
	long user_id;
	long attorney_id;
	String email;
	String first_name;
	String last_name;
	String password;
	String address_l1;
	String address_l2;
	long zip_code;
	String city;
	String state_province;
	long phone;
	String reg_type;
	String created_date;
	String updated_date;
	
	public UserBean(){
		
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress_l1() {
		return address_l1;
	}
	public void setAddress_l1(String address_l1) {
		this.address_l1 = address_l1;
	}
	public String getAddress_l2() {
		return address_l2;
	}
	public void setAddress_l2(String address_l2) {
		this.address_l2 = address_l2;
	}
	public long getZip_code() {
		return zip_code;
	}
	public void setZip_code(long zip_code) {
		this.zip_code = zip_code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState_province() {
		return state_province;
	}
	public void setState_province(String state_province) {
		this.state_province = state_province;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getReg_type() {
		return reg_type;
	}
	public void setReg_type(String reg_type) {
		this.reg_type = reg_type;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
}

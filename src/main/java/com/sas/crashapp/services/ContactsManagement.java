package com.sas.crashapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.sas.crashapp.beans.Contact;
import com.sas.crashapp.beans.EContactsBean;
import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.UserBean;
import com.sas.crashapp.interfaces.Contacts;

public class ContactsManagement implements Contacts {
	
	public Object createContacts(EContactsBean contactsBean){
		QueryRunner run;
		DBService dbService;
		Connection con=null;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
			UserBean user = run.query(con,prop.getProperty("getUserWithAttorneyID"),resultHandler,contactsBean.getUser_id(),contactsBean.getAttorney_id());
			if(user!=null){
				PreparedStatement ps=con.prepareStatement(prop.getProperty("createContactsList"));
				for(Contact contact:contactsBean.getContacts()){
					ps.setLong(1,contactsBean.getUser_id());
					ps.setString(2,contact.getContact_fname());
					ps.setString(3,contact.getContact_lname());
					ps.setString(4,contact.getContact_email());
					ps.setLong(5,contact.getContact_phone());
					ps.addBatch();
				}
				ps.executeBatch();
				contactsBean.setSuccess(1);
				return contactsBean;
			}else{
				return getError(901,"User Not Registered");
			}
		}catch(SQLException e){
			e.printStackTrace();
			//logger.error("checkLogin SQL Exception Error " +e.getMessage());
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	
	@Override
	public Object getContacts(long user_id) {
		DBService dbService;
		Connection con=null;
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			String query="SELECT * FROM EMERGENCY_CONTACTS WHERE USER_ID=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setLong(1,user_id);
			ResultSet contactsSet=ps.executeQuery();
			if(contactsSet.next()){
				EContactsBean contactsBean=new EContactsBean();
				Contact contact=new Contact();
				List<Contact> contactList=new LinkedList<Contact>();
				contact.setContact_fname(contactsSet.getString("contact_fname"));
				contact.setContact_lname(contactsSet.getString("contact_lname"));
				contact.setContact_email(contactsSet.getString("contact_email"));
				contact.setContact_phone(contactsSet.getLong("contact_phone"));
				contactList.add(contact);
				while(contactsSet.next()){
					contact=new Contact();
					contact.setContact_fname(contactsSet.getString("contact_fname"));
					contact.setContact_lname(contactsSet.getString("contact_lname"));
					contact.setContact_email(contactsSet.getString("contact_email"));
					contact.setContact_phone(contactsSet.getLong("contact_phone"));
					contactList.add(contact);
				}
				contactsBean.setContacts(contactList);
				contactsBean.setUser_id(user_id);
				contactsBean.setSuccess(1);
				return contactsBean;
			}else{
				return getError(901,"No Details Found");
			}
		}catch(SQLException e){
			e.printStackTrace();
			//logger.error("checkLogin SQL Exception Error " +e.getMessage());
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}

	@Override
	public Object deleteContacts(long user_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Properties getProperties(String propfile){
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = this.getClass().getClassLoader().getResourceAsStream(propfile);
			prop.load(input);
			return prop;
		}catch(FileNotFoundException e){
			//logger.error("getProperties config.properties file not found" +e.getMessage());
		}catch(IOException e){
			//logger.error("getProperties config.properties file reading error" +e.getMessage());
		}
		return prop;
	}
	
	private void closeConnections(Connection con){
		try{
			if(con!=null)
				DbUtils.close(con);
			}catch(Exception e){
				//logger.error("checkLogin Connection Close Error" +e.getMessage());
			}	
	}
	
	private ErrorBean getError(int code,String description){
		ErrorBean errorBean=new ErrorBean();
		errorBean.setError_code(code);
		errorBean.setError_description(description);
		errorBean.setSuccess(0);
		return errorBean;
	}

	

}

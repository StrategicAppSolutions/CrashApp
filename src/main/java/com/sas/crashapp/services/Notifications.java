package com.sas.crashapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sas.crashapp.beans.EmailBean;
import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.UserBean;

public class Notifications {
	final static Logger logger = LogManager.getLogger(Notifications.class);

	public Object sendNotification(UserBean user){
		QueryRunner run;
		Connection con=null;
		SendMail mail;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<EmailBean> resultHandler = new BeanHandler<EmailBean>(EmailBean.class);
			EmailBean email = run.query(con,prop.getProperty("email_details"),resultHandler,user.getUser_id());
			if(email!=null){
				mail=new SendMail();
				mail.initialNotify(email);
				IncidentManagement incidentManagement=new IncidentManagement();
				return incidentManagement.createIncident(user.getUser_id(),user.getAttorney_id()); 
			}else{
				return getError(901,"User Not Registered");
			}
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("sendNotification SQL Exception Error " +e.getMessage());
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	
	private void closeConnections(Connection con){
		try{
			if(con!=null)
				DbUtils.close(con);
			}catch(Exception e){
				logger.error("checkLogin Connection Close Error" +e.getMessage());
			}	
	}
	
	
	private Properties getProperties(String propfile){
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = this.getClass().getClassLoader().getResourceAsStream(propfile);
			prop.load(input);
			return prop;
		}catch(FileNotFoundException e){
			logger.error("getProperties config.properties file not found" +e.getMessage());
		}catch(IOException e){
			logger.error("getProperties config.properties file reading error" +e.getMessage());
		}
		return prop;
	}
	
	private ErrorBean getError(int code,String description){
		ErrorBean errorBean=new ErrorBean();
		errorBean.setError_code(code);
		errorBean.setError_description(description);
		errorBean.setSuccess(0);
		return errorBean;
	}

}

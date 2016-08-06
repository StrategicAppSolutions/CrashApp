package com.sas.crashapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.UserBean;
import com.sas.crashapp.interfaces.User;

public class UserManagement implements User {
	final static Logger logger = LogManager.getLogger(UserManagement.class);
	
	public Object checkLogin(UserBean login){
		QueryRunner run;
		DBService dbService;
		Connection con=null;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
			UserBean user = run.query(con,prop.getProperty("checkLogin"),resultHandler,login.getEmail().toLowerCase(),login.getAttorney_id());
			if(user!=null){
				if(user.getPassword().equals(login.getPassword())){
					user.setSuccess(1);
					user.setPassword(null);
					user.setUpdated_date(null);
					user.setCreated_date(null);
					return user;	
				}else{
					return getError(902,"Authentication Error");
				}
			}else{
				return getError(901,"User Not Registered");
			}
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("checkLogin SQL Exception Error " +e.getMessage());
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	
	public Object createUser(UserBean userBean){
		Connection con=null;
		QueryRunner run;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
			UserBean user = run.query(con,prop.getProperty("checkLogin"),resultHandler,userBean.getEmail().toLowerCase(),userBean.getAttorney_id());
			if(user!=null){
				return getError(905,"User Already Registered");
			}else{
				int update = run.update(con,prop.getProperty("createUser"),userBean.getAttorney_id(),userBean.getEmail().toLowerCase(),userBean.getFirst_name(),userBean.getLast_name(),userBean.getPassword(),userBean.getAddress_l1(),userBean.getAddress_l2(),userBean.getZip_code(),userBean.getCity(),userBean.getState_province(),userBean.getPhone(),userBean.getReg_type(),getDate(),getDate());
				if(update!=0){
					user = run.query(con,prop.getProperty("checkLogin"),resultHandler,userBean.getEmail().toLowerCase(),userBean.getAttorney_id());
					user.setSuccess(1);
					user.setPassword(null);
					user.setReg_type(null);
					user.setUpdated_date(null);
					user.setCreated_date(null);
					return user;
				}else
					return getError(904,"Insert Failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	
	public Object createUserFB(UserBean userBean){
		PreparedStatement ps=null;
		Connection con=null;
		QueryRunner run;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
			UserBean user = run.query(con,prop.getProperty("checkLogin"),resultHandler,userBean.getEmail().toLowerCase(),userBean.getAttorney_id());
			if(user!=null){
				user.setSuccess(1);
				user.setPassword(null);
				user.setReg_type(null);
				return user;
			}else{
				int update = run.update(con,prop.getProperty("createUser_FB"),userBean.getAttorney_id(),userBean.getFirst_name(),userBean.getLast_name(),userBean.getEmail().toLowerCase(),userBean.getReg_type(),getDate(),getDate());
				if(update!=0){
					user = run.query(con,prop.getProperty("checkLogin"),resultHandler,userBean.getEmail().toLowerCase(),userBean.getAttorney_id());
					user.setSuccess(1);
					user.setPassword(null);
					user.setReg_type(null);
					user.setUpdated_date(null);
					user.setCreated_date(null);
					return user;
				}else
					return getError(904,"Insert Failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
			return getError(903,"Internal Error");
		}finally{
			closeConnections(ps,con);
		}
	}
	@Override
	public Object updateUser(UserBean userBean) {
		Connection con=null;
		QueryRunner run;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
				int update = run.update(con,prop.getProperty("update_user"),userBean.getFirst_name(),userBean.getLast_name(),userBean.getPhone(),userBean.getPassword(),userBean.getAddress_l1(),userBean.getAddress_l2(),userBean.getZip_code(),userBean.getCity(),userBean.getState_province(),getDate(),userBean.getUser_id());
				System.out.println(update);
				if(update!=0){
					UserBean user = run.query(con,prop.getProperty("getUser"),resultHandler,userBean.getUser_id());
					user.setSuccess(1);
					user.setUpdated_date(null);
					user.setCreated_date(null);
					return user;
			}else
					return getError(901,"User Not Registered");
		}catch(SQLException e){
			e.printStackTrace();
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	@Override
	public Object getUser(long user_id) {
		Connection con=null;
		QueryRunner run;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			run = new QueryRunner();
			ResultSetHandler<UserBean> resultHandler = new BeanHandler<UserBean>(UserBean.class);
			UserBean user = run.query(con,prop.getProperty("getUser"),resultHandler,user_id);
			if(user!=null){
				user.setSuccess(1);
				return user;
			}else return getError(901,"User Not Registered");
		}catch(SQLException e){
			e.printStackTrace();
			return getError(903,"Internal Error");
		}finally{
			closeConnections(con);
		}
	}
	
	@Override
	public Object deleteUser(long user_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ErrorBean getError(int code,String description){
		ErrorBean errorBean=new ErrorBean();
		errorBean.setError_code(code);
		errorBean.setError_description(description);
		errorBean.setSuccess(0);
		return errorBean;
	}
	
	
	private void closeConnections(PreparedStatement ps,Connection con){
		try{
			if(con!=null)
				con.close();
			if(ps!=null)
				ps.close();
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
	private void closeConnections(Connection con){
		try{
			if(con!=null)
				DbUtils.close(con);
			}catch(Exception e){
				logger.error("checkLogin Connection Close Error" +e.getMessage());
			}	
	}
	
	private Date getDate(){
		Calendar calendar = Calendar.getInstance();
		Date date = new java.sql.Date(calendar.getTime().getTime());	
		return date;
	}
}

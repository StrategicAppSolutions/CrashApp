package com.sas.crashapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

public class DBService {
	
	public Connection getConnection(){
		Properties prop = getProperties("dbconfig.properties");
		Connection con=null;
		try{
			DbUtils.loadDriver(prop.getProperty("db.driver"));
		    con = DriverManager.getConnection(prop.getProperty("db.connectionurl"),prop.getProperty("db.username"),prop.getProperty("db.password"));
			
		}catch(SQLException e){
			//logger.error("getConnection DriverManager error" +e.getMessage());
		}
		return con;
				
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
}

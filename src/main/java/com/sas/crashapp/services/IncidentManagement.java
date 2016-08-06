package com.sas.crashapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.Images;
import com.sas.crashapp.beans.IncidentBean;
import com.sas.crashapp.beans.IncidentImages;
import com.sas.crashapp.beans.IncidentSuccess;
import com.sas.crashapp.beans.Witness;

public class IncidentManagement {
	
	public Object createIncident(IncidentBean incident){
		Connection con=null;
		PreparedStatement ps;
		IncidentSuccess success;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			final String generatedColumns[] = { "INCIDENT_ID" };
			ps=con.prepareStatement(prop.getProperty("create_incident"),generatedColumns);
			ps.setLong(1,incident.getAttorney_id());
			ps.setLong(2,incident.getUser_id());
			ps.setDate(3,getDate());
			ps.setDate(4,getDate());
			ps.setString(5,incident.getInsurance_company());
			ps.setString(6,incident.getInsurance_policy());
			int update=ps.executeUpdate();
			if(update!=0){
				ResultSet keys=ps.getGeneratedKeys();
				keys.next();
				incident.setIncident_id(keys.getLong(1));
				ps.close();
				keys.close();
				PreparedStatement witness_ps=con.prepareStatement(prop.getProperty("create_witness"));
				for(Witness witness:incident.getWitness()){
					witness_ps.setLong(1,incident.getIncident_id());
					witness_ps.setString(2,witness.getWitness_fname());
					witness_ps.setString(3,witness.getWitness_lname());
					witness_ps.setLong(4,witness.getWitness_phone());
					witness_ps.setString(5,witness.getCall_permission());
					witness_ps.addBatch();
				}
				witness_ps.executeBatch();
				witness_ps.close();
				success=new IncidentSuccess();
				success.setSuccess(1);
				success.setIncident_id(incident.getIncident_id());
				success.setImgUpload("/webapi/incidents/upload/"+incident.getIncident_id());
				return success;
			}else
				return getError(904,"Insert Failed");
	}catch(SQLException e){
		e.printStackTrace();
		return getError(903,"Internal Error");
	}finally{
			closeConnections(con);
		}
	}
	
	public Object insertImages(IncidentImages incidentImages){
		Connection con=null;
		PreparedStatement ps;
		DBService dbService;
		Properties prop=getProperties("dbqueries.properties");
		try{
			dbService=new DBService();
			con=dbService.getConnection();
			ps=con.prepareStatement(prop.getProperty("insert_images"));
				for(Images image:incidentImages.getImages()){
					ps.setLong(1,incidentImages.getIncident_id());
					ps.setString(2,image.getKey_name());
					ps.setString(3,image.getBucket_name());
					ps.setString(4,image.getImg_url());
					ps.addBatch();
				}
				ps.executeBatch();
				ps.close();
				
				PreparedStatement incidentps=con.prepareStatement("SELECT * FROM INCIDENTS WHERE INCIDENT_ID=?");
				incidentps.setLong(1,incidentImages.getIncident_id());
				ResultSet incidentset=incidentps.executeQuery();
				IncidentBean incidentBean=new IncidentBean();
				if(incidentset.next()){
					incidentBean.setAttorney_id(incidentset.getLong(3));
					incidentBean.setIncident_id(incidentset.getLong(1));
					incidentBean.setUser_id(incidentset.getLong(2));
					incidentBean.setInsurance_company(incidentset.getString(6));
					incidentBean.setInsurance_policy(incidentset.getString(7));
				}
				Images image=new Images();
				List<Images> imageList=new LinkedList<Images>();
				PreparedStatement imageps=con.prepareStatement(prop.getProperty("join_images"));
				imageps.setLong(1,incidentImages.getIncident_id());
				ResultSet imageset=imageps.executeQuery();
				while(imageset.next()){
					image.setImg_url(imageset.getString("RESOURCE_URL"));
					imageList.add(image);
					image=new Images();
				}
				incidentBean.setImages(imageList);
				imageps.close();
				imageset.close();
				Witness witness=new Witness();
				List<Witness> witnessList=new LinkedList<Witness>();
				PreparedStatement witnessps=con.prepareStatement(prop.getProperty("join_witness"));
				witnessps.setLong(1,incidentImages.getIncident_id());
				ResultSet witnessset=witnessps.executeQuery();
				while(witnessset.next()){
					witness.setWitness_fname(witnessset.getString("WITNESS_FNAME"));
					witness.setWitness_lname(witnessset.getString("WITNESS_LNAME"));
					witness.setCall_permission(witnessset.getString("CALL_PERMISSION"));
					witness.setWitness_phone(witnessset.getLong("WITNESS_PHONE"));
					witnessList.add(witness);
				}
				
				incidentBean.setSuccess(1);
				incidentBean.setWitness(witnessList);
				return incidentBean;
	}catch(SQLException e){
		e.printStackTrace();
		return getError(903,"Internal Error");
	}finally{
			closeConnections(con);
		}
	}
	
	private ErrorBean getError(int code,String description){
		ErrorBean errorBean=new ErrorBean();
		errorBean.setError_code(code);
		errorBean.setError_description(description);
		errorBean.setSuccess(0);
		return errorBean;
	}
	
	private void closeConnections(Connection con){
		try{
			if(con!=null)
				con.close();
			}catch(Exception e){
				//logger.error("checkLogin Connection Close Error" +e.getMessage());
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
			//logger.error("getProperties config.properties file not found" +e.getMessage());
		}catch(IOException e){
			//logger.error("getProperties config.properties file reading error" +e.getMessage());
		}
		return prop;
	}
	
	private Date getDate(){
		Calendar calendar = Calendar.getInstance();
		Date date = new java.sql.Date(calendar.getTime().getTime());	
		return date;
	}

}

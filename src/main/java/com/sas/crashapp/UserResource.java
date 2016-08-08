package com.sas.crashapp;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.UserBean;
import com.sas.crashapp.services.UserManagement;

//User Resource - Handles User Login, User SignUp, User Update, Send Details of User

@Path("user")
public class UserResource {
	
	/*User Login Method
	Request Type: Get
	Path: /user/login
	Parameters:
	Headers: email,password,attorney_id*/
	
	@GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(@HeaderParam("email") String email,@HeaderParam("password") String password,@HeaderParam("attorney_id") long attorney_id) {
		//Service Object
		UserManagement userManagement;
    	UserBean userBean;
    	ErrorBean eb;
    	if(email!=null&&password!=null&&attorney_id!=0){
    		userBean=new UserBean();
    		userManagement =new UserManagement();
    		userBean.setEmail(email);
        	userBean.setPassword(password);
        	userBean.setAttorney_id(attorney_id);
        	return Response.ok(userManagement.checkLogin(userBean), MediaType.APPLICATION_JSON).build();	
    	}else{
    		eb=new ErrorBean();
    		eb.setSuccess(0);
    		eb.setError_code(900);
    		eb.setError_description("Invalid Request");
    		return Response.ok(eb, MediaType.APPLICATION_JSON).build();
    		
    		
    	}
    	
	}
	
	/*Method to Get Details of A user
	Request Type: Get
	Path: /user/{user_id}
	Parameters:
	Path: user_id*/
	
	@GET
	@Path("{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("user_id") long user_id){
		ErrorBean eb;
		UserManagement user;
		if(user_id!=0){
			user=new UserManagement();
			return Response.ok(user.getUser(user_id),MediaType.APPLICATION_JSON).build();
			
			
		}else{
			eb=new ErrorBean();
			eb.setSuccess(0);
			eb.setError_code(900);
			eb.setError_description("Invalid Request");
    		return Response.ok(eb, MediaType.APPLICATION_JSON).build();
		}
		
	}
	/*User Details Update Method
	Request Type: PUT
	Path: /user/update/{user_id}
	Parameters:
	Path: user_id
	JSON Body:
	String email - Mandatory
	String first_name - Mandatory
	String last_name - Mandatory
	String password - Mandatory for Regular Signup;
	String address_l1
	String address_l2
	long zip_code
	String city
	String state_province
	long phone - Mandatory*/
	
	@PUT
	@Path("update/{user_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("user_id")long user_id,UserBean ub){
		UserManagement user;
		ErrorBean eb;
		if(user_id!=0&&ub.getEmail()!=null&&ub.getFirst_name()!=null&&ub.getLast_name()!=null&&(Long)ub.getPhone()!=null){
			ub.setUser_id(user_id);
			user=new UserManagement();
			return Response.ok(user.updateUser(ub),MediaType.APPLICATION_JSON).build();
		}else{
			eb=new ErrorBean();
			eb.setSuccess(0);
			eb.setError_code(900);
			eb.setError_description("Invalid Request");
    		return Response.ok(eb, MediaType.APPLICATION_JSON).build();
		}
	}
	/*User Creation Method
	Request Type: POST
	Path: /user/create
	Parameters:
	Header: attorney_id
	JSON Body:
	String email - Mandatory
	String first_name - Mandatory
	String last_name - Mandatory
	String password - Mandatory for Regular Signup;
	String address_l1
	String address_l2
	long zip_code
	String city
	String state_province
	long phone - Mandatory
	String reg_type - Mandatory*/
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@HeaderParam("attorney_id") long attorney_id,UserBean ub){
		ErrorBean eb;
		UserManagement userManagement;
		if(ub.getReg_type().equals("signup")&&(Long)attorney_id!=null&&ub.getEmail()!=null&&ub.getFirst_name()!=null&&ub.getLast_name()!=null&&ub.getPassword()!=null&&(Long)ub.getPhone()!=null){
			ub.setAttorney_id(attorney_id);
			userManagement =new UserManagement();
			return Response.ok(userManagement.createUser(ub),MediaType.APPLICATION_JSON).build();
		}
		else if(ub.getReg_type().equals("fb")&&(Long)attorney_id!=null&&ub.getEmail()!=null&&ub.getFirst_name()!=null&&ub.getLast_name()!=null){
			ub.setAttorney_id(attorney_id);
			userManagement =new UserManagement();
			return Response.ok(userManagement.createUserFB(ub),MediaType.APPLICATION_JSON).build();
		}else{
			eb=new ErrorBean();
			eb.setSuccess(0);
			eb.setError_code(900);
			eb.setError_description("Invalid Request");
    		return Response.ok(eb, MediaType.APPLICATION_JSON).build();
		}
	}
}

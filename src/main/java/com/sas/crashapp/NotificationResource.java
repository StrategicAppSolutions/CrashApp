package com.sas.crashapp;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sas.crashapp.beans.ErrorBean;
import com.sas.crashapp.beans.UserBean;
import com.sas.crashapp.services.Notifications;

@Path("notifications")
public class NotificationResource {
	
	
	@GET
	 @Path("trigger")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response senEmail(@HeaderParam("user_id") long user_id,@HeaderParam("attorney_id") long attorney_id){
		ErrorBean eb;
		UserBean ub; 
		Notifications notify;
		if(user_id!=0&&attorney_id!=0){
			ub=new UserBean();
			 ub.setUser_id(user_id);
			 ub.setAttorney_id(attorney_id);
			 notify=new Notifications();
			 return Response.ok(notify.sendNotification(ub), MediaType.APPLICATION_JSON).build();
		 }else{
			eb=new ErrorBean();
    		eb.setSuccess(0);
    		eb.setError_code(900);
    		eb.setError_description("Invalid Request");
    		return Response.ok(eb, MediaType.APPLICATION_JSON).build();
		 }
	 }
}

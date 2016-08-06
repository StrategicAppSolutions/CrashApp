package com.sas.crashapp;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sas.crashapp.beans.EContactsBean;
import com.sas.crashapp.services.ContactsManagement;

@Path("contacts")
public class ContactsService {

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createContact(@HeaderParam("attorney_id") long attorney_id,@HeaderParam("user_id") long user_id,EContactsBean contactsBean){
		ContactsManagement contactsManagement;
		if((Long)attorney_id!=null&&(Long) user_id!=null){
			contactsBean.setAttorney_id(attorney_id);
			contactsBean.setUser_id(user_id);
			contactsManagement=new ContactsManagement();
			return Response.ok(contactsManagement.createContacts(contactsBean),MediaType.APPLICATION_JSON).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
}
package com.sas.crashapp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sas.crashapp.beans.Images;
import com.sas.crashapp.beans.IncidentBean;
import com.sas.crashapp.beans.IncidentImages;
import com.sas.crashapp.beans.UserBean;
import com.sas.crashapp.services.IncidentManagement;
import com.sas.crashapp.services.Notifications;
import com.sas.crashapp.services.S3Upload;

//Incident Resource - Handles the Incident Requests

@Path("incidents")
public class IncidentResource {
	
	//Creates New Incident
	@GET
	 @Path("create")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response createIncident(@HeaderParam("user_id") long user_id,@HeaderParam("attorney_id") long attorney_id){
		UserBean ub; 
		Notifications notify;
		if(user_id!=0&&attorney_id!=0){
			ub=new UserBean();
			ub.setUser_id(user_id);
			ub.setAttorney_id(attorney_id);
			notify=new Notifications();
			return Response.ok(notify.sendNotification(ub), MediaType.APPLICATION_JSON).build();
		 }else 	return Response.status(Response.Status.BAD_REQUEST).build();

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("upload/images/{incident_id}")
    
	public Response uploadImages(@Context HttpServletRequest request,@HeaderParam("attorney_id") long attorney_id,@HeaderParam("user_id") long user_id,@PathParam("incident_id") long incident_id)
    {
		 List<Images> imageList;
		 Images image;
		 IncidentImages incidentImages;
		 IncidentManagement im;
        //checks whether there is a file upload request or not
		 if(incident_id!=0&&attorney_id!=0&&user_id!=0&&ServletFileUpload.isMultipartContent(request))
        {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            imageList=new LinkedList<Images>();
            image=new Images();
            try{
                	final List<FileItem> items = fileUpload.parseRequest(request);
	                System.out.println(items.size());
                	if (items != null)
	                { 
	                	Iterator<FileItem> item=items.iterator();
	                	while(item.hasNext()){
	                		S3Upload s3 = new S3Upload();
	                		s3.fileUploader(item.next());
	                		image.setImg_url(s3.getPath());
	                		image.setKey_name(s3.getKey());
	                		image.setBucket_name(s3.getBucket());
	                		imageList.add(image);
	                		image=new Images();
	                	}
	                	incidentImages=new IncidentImages();
	                	incidentImages.setAttorney_id(attorney_id);
	                	incidentImages.setIncident_id(incident_id);
	                	incidentImages.setUser_id(user_id);
	                	incidentImages.setImages(imageList);
	                	im=new IncidentManagement();
	                	return Response.ok(im.insertImages(incidentImages),MediaType.APPLICATION_JSON).build();
	                }
                }catch(Exception e){
                	e.printStackTrace();
                	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                
        }return Response.status(Response.Status.BAD_REQUEST).build();

    }
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("upload/details/{incident_id}")
	public Response insertDetails(@Context HttpServletRequest request,@HeaderParam("attorney_id") long attorney_id,@HeaderParam("user_id") long user_id,@PathParam("incident_id") long incident_id,IncidentBean incident){
		if(incident_id!=0&&attorney_id!=0&&user_id!=0){
			incident.setAttorney_id(attorney_id);
			incident.setIncident_id(incident_id);
			incident.setUser_id(user_id);
			return Response.ok(new IncidentManagement().inserDetails(incident),MediaType.APPLICATION_JSON).build();
		}else return Response.status(Response.Status.BAD_REQUEST).build();
		
    }
}

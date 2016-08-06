package com.sas.crashapp.services;

import javax.mail.*;
import javax.mail.internet.*;

import com.sas.crashapp.beans.EmailBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SendMail {
	
	public boolean initialNotify(EmailBean email){
		email.setMessage("<p>Hello "+email.getAttorney_fname()+" "+email.getAttorney_lname()+",</p>"
				+ "<p>Your client <strong>"+email.getFirst_name()+" "+email.getLast_name()+"</strong> has reported an accident.</p>"
				+ "<p><strong>Name:</strong>"+email.getFirst_name()+" "+email.getLast_name()+"</p>"
				+ "<p><strong>Email:</strong>"+email.getEmail()+"</p>"
				+ "<p><strong>Phone:</strong>"+email.getPhone()+"</p>"
				+ "<p><strong>Address:</strong><br>"+email.getAddress_l1()+"<br>"+email.getAddress_l2()+"<br>"+email.getCity()+" "+email.getState_province()+" "+email.getZip_code()+"</p>"
				+ "<p>&nbsp;</p>"
				+ "<p>Strategic App Solutions LLC</p>"
				+ "<p>&nbsp;</p>"
				+ "<p>This message (including any attachments) contains confidential information intended for a specific individual and purpose, and is protected by law. If you are not the intended recipient, you should delete this message and any disclosure, copying, or distribution of this message, or the taking of any action based on it, by you is strictly prohibited.</p>"
				+ "<p>&nbsp;</p>");
		System.out.println(email.getAttorney_email()+" "+email.getMessage());
		return send(email.getAttorney_email(),email.getMessage(),"Incident Reported");
	}
	private  boolean send(String to,String content,String subject){
		final Properties prop = new Properties();
		InputStream input = null;
		try{
			input = this.getClass().getClassLoader().getResourceAsStream("emailconfig.properties");
			prop.load(input);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		Session session = Session.getInstance(prop,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(prop.getProperty("mail.username"),prop.getProperty("mail.password"));
					}
				  });
		try {

			Message message = new MimeMessage(session);
			 InternetAddress me = new InternetAddress(prop.getProperty("mail.username"));
		     me.setPersonal("Strategic App Solutions");
		     message.setContent(content, "text/html; charset=utf-8");
			message.setFrom(me);
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			Transport.send(message);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

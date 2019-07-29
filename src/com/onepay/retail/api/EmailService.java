package com.onepay.retail.api;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService 
{
	public static int sendMail(String to, String subject, String bodyText)
    {
		int status = 0;		
		
		try
		{
			final String user = "retail_channel@1pay.in";
			final String pass = "ashish_1987";
					
			String host = "smtp.gmail.com";
			
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.user", user);
			properties.setProperty("mail.smtp.password", pass);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "false");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.port", "465");
			
			Session session = Session.getDefaultInstance(properties, 
				    new javax.mail.Authenticator(){
				        protected PasswordAuthentication getPasswordAuthentication() {
				            return new PasswordAuthentication(
				                user, pass);// Specify the User name and the PassWord
				        }
				    });
	
			// compose the message
			try {
				MimeMessage message = new MimeMessage(session);
				// message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						to));
				message.setSubject(subject);
				message.setContent(bodyText,"text/html");
	
				Transport.send(message);
				status = 1;
				System.out.println("e-mail sent successfully....");
	
			} catch (MessagingException mex) {
				status = -1;
				mex.printStackTrace();
			}
			return status;
		}
		catch (Exception e) {
			System.out.println("Exception while sending mail....."+e.getMessage());
			e.printStackTrace();
		}
		return status;
    }
	
	public static void main(String[] args) {
		int mail=sendMail("sudhir@1pay.in", "Kuch bhi", "Hi this is from retail channel");
		if(mail==1)
		{
			System.out.println("Mail sent successfully...");
		}
		else
			System.out.println("Mail cant send successfully...");
	}
}

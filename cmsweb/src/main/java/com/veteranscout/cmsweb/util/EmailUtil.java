package com.veteranscout.cmsweb.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailUtil {

	CommonUtil common = new CommonUtil();
	String module_name = "email_util";

	public String sendEmail(HttpServletRequest request, String toEmail, String subject, String body){
		String result = "";
		String sCode="";
		JSONObject jsonResult = new JSONObject();
		Properties properties = new Properties();
		properties.put("mail.smtp.user", "cs@scout.co.kr"); //구글 계정
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.domain", "mail.scout.co.kr");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		
		try {
			Authenticator auth = new senderAccount();
			Session session = Session.getInstance(properties, auth);
			session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
			MimeMessage msg = new MimeMessage(session);
	
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress("cs@scout.co.kr"); // 보내는사람 EMAIL
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(toEmail);    //받는사람 EMAIL
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			String sDate="11";
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a"); 
			sDate = sdf.format(dt).toString(); 
			Random random=new Random();
			sCode = ""+(100000+random.nextInt(800000));
			HttpSession hss=request.getSession();
			hss.setAttribute("sCode",  sCode );
			msg.setContent(body, "text/html;charset=KSC5601"); //메일 전송될 내용
			Transport.send(msg);
			
			jsonResult.put("result","ok");
			jsonResult.put("message","메일 발송되었습니다.");
			result = jsonResult.toString();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				jsonResult.put("result","error");
				jsonResult.put("message",e.getMessage());	
				result = jsonResult.toString();
			} catch (JSONException ex1) {
				ex1.printStackTrace();
			}
			
		}

		return result;
	}

	private class senderAccount extends javax.mail.Authenticator {
		 
		public PasswordAuthentication getPasswordAuthentication() {
//	            return new PasswordAuthentication("platformersmanager", "vmffotvhajtm"); // @gmail.com 제외한 계정 ID, PASS
			return new PasswordAuthentication("cs@scout.co.kr", "!dhsfkdls2015"); // @gmail.com 제외한 계정 ID, PASS	
 
		}	
	}

	public JSONObject sendMailLinux(HttpServletRequest request,String fromEmail, String toEmail, String subject, String body){

		String result = "";
		String sCode="";
		JSONObject jsonResult = new JSONObject();
		// SUBSTITUTE YOUR EMAIL ADDRESSES HERE!
		String to = toEmail;
		String from = fromEmail;
		// SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!
		//String host = "localhost";
		String host = common.g_email_host;
		String port = "25";
		// Create properties, get Session
		Properties props = new Properties();
		
		
		
   
		try {

			//http://docs.oracle.com/javaee/6/api/javax/mail/Session.html
			// If using static Transport.send(),
			// need to specify which host to send it to
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.debug", "true");			
			//props.put("mail.smtp.auth", "true"); 
			/*
			props.put("mail.smtp.auth", "true"); 
			props.put("mail.smtp.starttls.enable","true"); 
			props.put("mail.smtp.EnableSSL.enable","true");
			*/

			//props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
			//props.setProperty("mail.smtp.socketFactory.fallback", "false");   
			//props.setProperty("mail.smtp.port", "465");   
			//props.setProperty("mail.smtp.socketFactory.port", "465"); 

			//props.put("mail.smtp.ssl.enable", "true");
			//props.put("mail.smtp.ssl.trust", host);

			// To see what is going on behind the scene
			
			
			
			common.log(module_name,"sendMailLinux from :"+from);
			common.log(module_name,"sendMailLinux to :"+ to);
			common.log(module_name,"sendMailLinux subject :"+subject);
			// common.log(module_name,"Content  :"+body);
			// Session session = Session.getInstance(props);
			
			String username = "postfix_ssl@mlinux.scout.co.kr";
			String password = "sslapdlf1";
			// SMTPAuthenticator pwd_auth = new SMTPAuthenticator(username,password);

			// Session session = Session.getInstance(props,pwd_auth);						
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});		
			
			// session.setDebug(true); //for debug
			
			// Instantiate a message
			Message msg = new MimeMessage(session);
   
			//Set message attributes
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			msg.setContent(body, "text/html;charset=KSC5601"); //메일 전송될 내용

			//Transport transport = session.getTransport("smtps"); 
			Transport transport = session.getTransport("smtp"); 
			transport.send(msg);
			//Transport.send(msg);
			jsonResult.put("result","ok");
			jsonResult.put("message","메일 발송되었습니다.");
			result = jsonResult.toString();

			common.log(module_name,"sendMailLinux result :"+result);
		}
		catch (MessagingException mex) {
			// Prints all nested (chained) exceptions as well
			common.log(module_name,"sendMailLinux error :"+mex.getMessage().toString());
			mex.printStackTrace();
			jsonResult.put("result","error");
			jsonResult.put("message",mex.getMessage());	
			result = jsonResult.toString();

			common.log(module_name,"sendMailLinux result :"+result);
		}
		return jsonResult;
	}

	public JSONObject sendMailLinuxSmtps(HttpServletRequest request,String fromEmail, String toEmail, String subject, String body){

		String result = "";
		String sCode="";
		JSONObject jsonResult = new JSONObject();
		// SUBSTITUTE YOUR EMAIL ADDRESSES HERE!
		String to = toEmail;
		String from = fromEmail;
		// SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!
		//String host = "localhost";
		String host = common.g_email_host;
		String port = "465";
		// Create properties, get Session
		Properties props = new Properties();
		
		
		
   
		try {

			//http://docs.oracle.com/javaee/6/api/javax/mail/Session.html
			// If using static Transport.send(),
			// need to specify which host to send it to
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			
			// props.put("mail.smtp.auth", "true"); 
			props.put("mail.smtp.starttls.enable", "true");						
			props.put("mail.smtp.debug", "true");
			/*
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			*/
			/*
			props.put("mail.smtp.auth", "true"); 
			props.put("mail.smtp.starttls.enable","true"); 
			props.put("mail.smtp.EnableSSL.enable","true");
			*/

			//props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
			//props.setProperty("mail.smtp.socketFactory.fallback", "false");   
			//props.setProperty("mail.smtp.port", "465");   
			//props.setProperty("mail.smtp.socketFactory.port", "465"); 

			//props.put("mail.smtp.ssl.enable", "true");
			//props.put("mail.smtp.ssl.trust", host);

			// To see what is going on behind the scene
			
			
			
			common.log(module_name,"sendMailLinuxSmtps from :"+from);
			common.log(module_name,"sendMailLinuxSmtps to :"+ to);
			common.log(module_name,"sendMailLinuxSmtps subject :"+subject);
			// common.log(module_name,"Content  :"+body);
			// Session session = Session.getInstance(props);
			
			String username = "postfix_ssl@mlinux.scout.co.kr";
			String password = "sslapdlf1";
			// SMTPAuthenticator pwd_auth = new SMTPAuthenticator(username,password);

			// Session session = Session.getInstance(props,pwd_auth);						
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});		
			
			session.setDebug(true); //for debug
			
			// Instantiate a message
			Message msg = new MimeMessage(session);
   
			//Set message attributes
			// msg.setFrom(new InternetAddress(from));
			/*
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			*/
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(toEmail);    //받는사람 EMAIL
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setSentDate(new Date()); 
			// msg.setSentDate(sDate);
			Random random=new Random();
			sCode = ""+(100000+random.nextInt(800000));
			HttpSession hss=request.getSession();
			hss.setAttribute("sCode",  sCode );
			msg.setContent(body, "text/html;charset=KSC5601"); //메일 전송될 내용

			//Transport transport = session.getTransport("smtps"); 
			/*
			Transport transport = session.getTransport("smtps"); 
			transport.send(msg);
			*/
			Transport.send(msg);
			jsonResult.put("result","ok");
			jsonResult.put("message","메일 발송되었습니다.");
			result = jsonResult.toString();

			common.log(module_name,"sendMailLinuxSmpts result :"+result);
		}
		catch (MessagingException mex) {
			// Prints all nested (chained) exceptions as well
			common.log(module_name,"sendMailLinuxSmpts error :"+mex.getMessage().toString());
			mex.printStackTrace();
			jsonResult.put("result","error");
			jsonResult.put("message",mex.getMessage());	
			result = jsonResult.toString();

			common.log(module_name,"sendMailLinuxSmpts result :"+result);
		}
		return jsonResult;
	}

	class SMTPAuthenticator extends Authenticator {
		private String username;
		private String password;
		public SMTPAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
		}
		public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
		}
	}

	
	
}
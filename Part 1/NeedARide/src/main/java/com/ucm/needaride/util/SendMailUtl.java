package com.ucm.needaride.util;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtl {

	private String toMailId;
	private String msgContent ;
	private String msgSubject;
	
	public SendMailUtl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public  void sender(String toid, String subject, String content) {
		this.toMailId = toid;
		this.msgSubject = subject;
		this.msgContent=content;
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("urrides@gmail.com","1234@password");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("urrides@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMailId));
			message.setSubject(msgSubject);
			message.setContent(msgContent,"text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
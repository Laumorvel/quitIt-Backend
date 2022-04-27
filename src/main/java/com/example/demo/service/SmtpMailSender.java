package com.example.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	

	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * Envia emails al correo que le indiquemos con el texto, asunto y el email del destinatario.
	 * @param to
	 * @param subject
	 * @param body
	 * @param from
	 * @throws MessagingException
	 */
	public void send(String to, String subject, String body, String from) throws MessagingException {
	
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		String finalMessage = body + ". By: " + from;
		
		helper = new MimeMessageHelper(message,true);
		
		
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(finalMessage,true);
		
		
		javaMailSender.send(message);
	}

}

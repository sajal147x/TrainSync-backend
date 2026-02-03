
package com.trainSync.service;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.trainSync.TrainSyncApplication;

/**
 * Author: Sajal Gupta
 * Created on: Jan 23, 2026 2:16:17 PM
 */
@Service
public class EmailService {
	
	 private final JavaMailSender mailSender;
	 
	 public EmailService(JavaMailSender mailSender) {
		 this.mailSender = mailSender;
	 }
	
	/**
	 * 
	 * @param email
	 */
	public void sendEmail(String email, String subject, String body) {

       
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("trainsynchronize@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

	
	public static void main(String[] args) {
		// Start Spring context
		ApplicationContext ctx = SpringApplication.run(TrainSyncApplication.class, args);
		// Get the EmailService bean (Spring will inject JavaMailSender)
		EmailService emailService = ctx.getBean(EmailService.class);
		// Test sending email
		emailService.sendEmail("chromathings@gmail.com", "Test Subject from Main",
				"This email was sent from the main method");

	}
}

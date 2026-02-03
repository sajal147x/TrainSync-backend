
package com.trainSync.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.trainSync.TrainSyncApplication;
import com.trainSync.config.exception.GlobalExceptionHandler;

/**
 * Author: Sajal Gupta
 * Created on: Jan 23, 2026 2:16:17 PM
 * @Comment : using sendgrid api directly instead of javamailsender because java smtp causing issues in production
 */
@Service
public class EmailService {
	 private static final Logger log =
	            LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	 
	 private final SendGrid sendGrid;
	 
	 public EmailService(@Value("${sendgrid.api.key}") String apiKey) {
	        this.sendGrid = new SendGrid(apiKey);
	    }
	
	/**
	 * 
	 * @param email
	 */
	public void sendEmail(String email, String subject, String body) {

		Email from = new Email("trainsynchronize@gmail.com"); // verified sender
		Email toEmail = new Email(email);
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, toEmail, content);

		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		try {
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
		} catch (Exception e) {
			log.error("SENDGRID EMAIL FAILED", e.getMessage());
		}
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

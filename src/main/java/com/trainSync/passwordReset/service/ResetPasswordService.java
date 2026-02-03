
package com.trainSync.passwordReset.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainSync.service.EmailService;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 23, 2026 1:44:15 PM
 */
@Service
public class ResetPasswordService {
	
	private final UserDetailsRepository userDetailsRepository;
	
	private final EmailService emailService;
	
	private final PasswordEncoder passwordEncoder;

	public ResetPasswordService(UserDetailsRepository userDetailsRepository, EmailService emailService, PasswordEncoder passwordEncoder	) {
		this.userDetailsRepository = userDetailsRepository;
		this.emailService = emailService;
		this.passwordEncoder=passwordEncoder;
	}

	/**
	 * 
	 * @param userNameOrEmail
	 * @return
	 */
	public UserDetails findUserFromUsernameOrEmail(String userNameOrEmail) {
		
		UserDetails user = userDetailsRepository.findByEmail(userNameOrEmail);
		if(user==null) {
			user = userDetailsRepository.findByUsername(userNameOrEmail);
		}
		return user;
	}

	/**
	 * create a random password and send to user
	 * @param email
	 */
	public void sendPasswordResetLink(UserDetails user) {
		
		if(user==null || "INACTIVE".equals(user.getAccountStatus())) {
			return;
		}
		String tempPassword = RandomStringUtils.randomAlphanumeric(10);
		user.setPasswordHash(passwordEncoder.encode(tempPassword)); // Assuming Spring Security passwordEncoder
	    userDetailsRepository.save(user);
        
        //COMPOSE AND SEND EMAIL
        String body = "Your temporary password is:\n\n" +
                tempPassword + "\n\n" +
                "You can now log in using this password.";
        
        emailService.sendEmail(user.getEmail(), "Password Reset", body);
        
	}
	

}

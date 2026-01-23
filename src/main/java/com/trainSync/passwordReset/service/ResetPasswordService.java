
package com.trainSync.passwordReset.service;

import org.springframework.stereotype.Service;

import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 23, 2026 1:44:15 PM
 */
@Service
public class ResetPasswordService {
	
	private final UserDetailsRepository userDetailsRepository;
	
	public ResetPasswordService(UserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository=userDetailsRepository;
	}

	/**
	 * 
	 * @param userNameOrEmail
	 * @return
	 */
	public String findEmailFromUsernameOrEmail(String userNameOrEmail) {
		
		UserDetails user = userDetailsRepository.findByEmail(userNameOrEmail);
		if(user==null) {
			user = userDetailsRepository.findByUsername(userNameOrEmail);
		}
		if(user==null) {
			return null;
		}
		return user.getEmail();
	}

	/**
	 * 
	 * @param email
	 */
	public void sendPasswordResetLink(String email) {
		// TODO Auto-generated method stub
		
	}
	
	

}

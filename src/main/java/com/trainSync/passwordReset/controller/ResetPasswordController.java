
package com.trainSync.passwordReset.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.passwordReset.dto.ResetPasswordRequest;
import com.trainSync.passwordReset.dto.ResetPasswordResponse;
import com.trainSync.passwordReset.service.ResetPasswordService;

/**
 * Author: Sajal Gupta
 * Created on: Jan 23, 2026 1:39:44 PM
 */
@RestController
@RequestMapping("/api")
public class ResetPasswordController {
	
	private final ResetPasswordService resetPasswordService;
	
	public ResetPasswordController(ResetPasswordService resetPasswordService) {
		this.resetPasswordService = resetPasswordService;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/reset-password")
	public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
		String email = resetPasswordService.findEmailFromUsernameOrEmail(request.getUserNameOrEmail());

		if (email != null && !email.isBlank()) {
			resetPasswordService.sendPasswordResetLink(email);
		}

		return ResponseEntity.ok(ResetPasswordResponse.builder()
				.message("If an account exists, a password reset link has been sent.").build());
	}	

}

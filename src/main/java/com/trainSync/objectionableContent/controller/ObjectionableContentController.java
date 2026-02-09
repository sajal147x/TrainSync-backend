
package com.trainSync.objectionableContent.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.config.exception.UnauthorizedException;
import com.trainSync.objectionableContent.dto.FlagContentRequest;
import com.trainSync.service.EmailService;
import com.trainSync.service.JwtService;

/**
 * class to handle blocking and reporting users, app store guidelines (fuck em)
 * Author: Sajal Gupta
 * Created on: Feb 9, 2026 9:54:13 AM
 */
@RestController
@RequestMapping("/api")
public class ObjectionableContentController {
	
	private final JwtService jwtService;
	
	private final EmailService emailService;
	
	public ObjectionableContentController(JwtService jwtService, EmailService emailService) {
		this.jwtService=jwtService;
		this.emailService=emailService;
	}
	
	
	@PostMapping("/flag-content")
	public ResponseEntity<String> flagInappropriateContent(@RequestHeader("Authorization") String authHeader, @RequestBody FlagContentRequest request){
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		
		String token = authHeader.replace("Bearer ", "");
		UUID userId = UUID.fromString(jwtService.extractUserId(token)); // validate JWT and extract Supabase UUID
		
		emailService.sendEmail("sajal147y@gmail.com", "CONTENT FLAGGED" , "User ID: "+userId.toString()+"\ngroupId: "+request.getGroupId()+"\nreason: "+request.getText());
		
		return ResponseEntity.ok("SUCCESS");
	}
	

}

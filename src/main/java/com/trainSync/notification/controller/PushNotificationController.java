
package com.trainSync.notification.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.notification.dto.PushNotificationTokenRequest;
import com.trainSync.notification.service.PushNotificationService;
import com.trainSync.service.JwtService;

/**
 * Author: Sajal Gupta
 * Created on: Jan 19, 2026 4:03:58 PM
 */
@RestController
@RequestMapping("/api")
public class PushNotificationController {
	
	private final JwtService jwtService;
	
	private final PushNotificationService pushNotificationService;
	
	public PushNotificationController(JwtService jwtService, PushNotificationService pushNotificationService) {
		this.jwtService = jwtService;
		this.pushNotificationService = pushNotificationService;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@PostMapping("/register-push-token")
	public ResponseEntity<Integer> registerPushNotificationToken(@RequestHeader("Authorization") String authHeader,
			@RequestBody PushNotificationTokenRequest request) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);
		
		pushNotificationService.savePushNotificationToken(userId, request.getPushNotificationToken(), request.getPlatform());
		return ResponseEntity.ok(200);
	}

}

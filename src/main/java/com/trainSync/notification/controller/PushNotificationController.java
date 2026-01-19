
package com.trainSync.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.notification.dto.PushNotificationTokenRequest;

/**
 * Author: Sajal Gupta
 * Created on: Jan 19, 2026 4:03:58 PM
 */
@RestController
@RequestMapping("/api")
public class PushNotificationController {
	
	/**
	 * 
	 * @return
	 */
	@PostMapping("/register-push-token")
	public ResponseEntity<Integer> registerPushNotificationToken(@RequestHeader("Authorization") String authHeader,
			@RequestBody PushNotificationTokenRequest request) {
		
		System.out.println("Received push notification token: " + request.getPushNotificationToken() +
				" for platform: " + request.getPlatform());
		return ResponseEntity.ok(200);
	}

}

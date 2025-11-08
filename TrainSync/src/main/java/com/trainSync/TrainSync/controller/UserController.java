package com.trainSync.TrainSync.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.TrainSync.dto.UserUpdateRequest;
import com.trainSync.TrainSync.model.UserDetails;
import com.trainSync.TrainSync.repository.UserDetailsRepository;
import com.trainSync.TrainSync.service.JwtService;

/**
 * Author: Sajal Gupta Date: Nov 7, 2025
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private JwtService jwtService;

	/**
	 * 
	 * @param authHeader
	 * @return
	 */
	@GetMapping("/me")
	public ResponseEntity<UserDetails> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.replace("Bearer ", "");
			String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
			System.out.println(userIdStr);
			UUID userId = UUID.fromString(userIdStr);

			UserDetails user = userDetailsRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found"));

			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(401).build(); // Unauthorized if JWT invalid
		}
	}

	/**
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authHeader,
			@RequestBody UserUpdateRequest request) {
		try {
			String token = authHeader.replace("Bearer ", "");
			String userIdStr = jwtService.extractUserId(token);
			UUID userId = UUID.fromString(userIdStr);
			System.out.println(request.getProfilePictureBase64());
			Optional<UserDetails> optionalUser = userDetailsRepository.findById(userId);
			if (optionalUser.isEmpty()) {
				return ResponseEntity.status(404).body("User not found");
			}

			UserDetails user = optionalUser.get();
			user.setName(request.getName());
			user.setAge(request.getAge());

			userDetailsRepository.save(user);

			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to update user");
		}
	}

}

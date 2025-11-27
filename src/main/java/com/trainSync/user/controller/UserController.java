package com.trainSync.user.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.service.SupabaseStorageService;
import com.trainSync.user.dto.UserUpdateRequest;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

/**
 * Author: Sajal Gupta Date: Nov 7, 2025
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserDetailsRepository userDetailsRepository;

	private final JwtService jwtService;
	
	private final SupabaseStorageService storageService;
	
	UserController(UserDetailsRepository userDetailsRepository, JwtService jwtService, SupabaseStorageService storageService) {
		this.userDetailsRepository = userDetailsRepository;
		this.jwtService = jwtService;
		this.storageService = storageService;
	}

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
			Optional<UserDetails> optionalUser = userDetailsRepository.findById(userId);
			if (optionalUser.isEmpty()) {
				return ResponseEntity.status(404).body("User not found");
			}
			UserDetails user = optionalUser.get();
			user.setName(request.getName());
			user.setAge(request.getAge());
			
			 if (request.getProfilePictureBase64() != null && !request.getProfilePictureBase64().isEmpty()) {
		            String fileName = userId + ".png";
		            String publicUrl = storageService.uploadBase64Image(request.getProfilePictureBase64(), fileName, "profile-pictures");
		            user.setProfilePictureUrl(publicUrl);
		        }
			userDetailsRepository.save(user);

			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to update user");
		}
	}

}

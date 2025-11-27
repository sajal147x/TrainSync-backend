
package com.trainSync.community.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.community.dto.FriendsResponseDto;
import com.trainSync.community.service.FriendService;
import com.trainSync.service.JwtService;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 2:46:54 PM
 */
@RestController
@RequestMapping("/api/community")
public class CommunityController {
	
	private final JwtService jwtService;
	private final FriendService friendService;
	
	CommunityController(JwtService jwtService, FriendService friendService) {
		this.jwtService = jwtService;
		this.friendService = friendService;
	}
	
	@GetMapping("/friends-for-user")
	public ResponseEntity<?> getFriendsForUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);
		
		List<FriendsResponseDto> friends = friendService.getFriendsForUser(userId);
		// Implementation for community overview
		return ResponseEntity.ok().body(friends);
	}

}

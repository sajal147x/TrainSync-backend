
package com.trainSync.community.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.community.dto.UserSearchDto;
import com.trainSync.community.dto.UserSearchResponseDto;
import com.trainSync.community.service.FriendRequestService;
import com.trainSync.service.JwtService;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.service.UserService;

/**
 * Author: Sajal Gupta Created on: Nov 27, 2025 12:38:27 PM
 */
@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

	private final UserService userService;
	private final JwtService jwtService;
 	private final FriendRequestService friendRequestService;

	FriendRequestController(UserService userService, JwtService jwtService, FriendRequestService friendRequestService) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.friendRequestService = friendRequestService;
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchForUser(@RequestHeader("Authorization") String authHeader, @RequestBody UserSearchDto userSearchDto) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);
		UserDetails loggedInUser = userService.findById(userId);
		//make sure user is not searching for themselves
		UserDetails user = userService.findByUsername(userSearchDto.getUserName());
		if (user == null || user.getId().equals(loggedInUser.getId())) {
			return ResponseEntity.ok().body("User Not Found");
		}

	
		UserSearchResponseDto responseDto = UserSearchResponseDto.builder().
				userId(user.getId().toString())
				.name(user.getName())
				.age(user.getAge())
				.profilePictureUrl(user.getProfilePictureUrl())
				.requestStatus(friendRequestService.friendRequestStatus(loggedInUser.getId(), user.getId()))
				.build();

		return ResponseEntity.ok().body(responseDto);

	}
	
	@PostMapping("/send-request")
	public ResponseEntity<?> sendFriendRequest(@RequestHeader("Authorization") String authHeader, @RequestBody UserSearchDto userSearchDto) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID loggedinUserId = UUID.fromString(userIdStr);
		UUID toUserId = UUID.fromString(userSearchDto.getUserId());
		
		friendRequestService.sendFriendRequest(loggedinUserId, toUserId);
		
		return ResponseEntity.ok().body(200);

	}
	
	@GetMapping("/received-requests")
	public ResponseEntity<?> getReceivedRequests(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID loggedinUserId = UUID.fromString(userIdStr);
		
		List<UserSearchResponseDto> receivedRequests = friendRequestService.getReceivedFriendRequests(loggedinUserId);
		return ResponseEntity.ok().body(receivedRequests);
	}
	
	@PostMapping("/accept-request")
	public ResponseEntity<?> acceptFriendRequest(@RequestHeader("Authorization") String authHeader, @RequestBody UserSearchDto userSearchDto) {
		String requestId=userSearchDto.getRequestId();
		
		friendRequestService.acceptFriendRequest(UUID.fromString(requestId));
		
		
		return ResponseEntity.ok().body(200);
		

	}

}

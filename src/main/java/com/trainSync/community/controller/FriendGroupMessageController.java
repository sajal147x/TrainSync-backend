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

import com.trainSync.community.dto.GroupMessageDto;
import com.trainSync.community.dto.GroupMessageRequest;
import com.trainSync.community.service.FriendGroupMessageService;
import com.trainSync.service.JwtService;

/**
 * @author sajalgupta
 * @dateCreated Jan 17 2026
 * 
 */

@RestController
@RequestMapping("/api")
public class FriendGroupMessageController {
	
	private final JwtService jwtService;
	
	private final FriendGroupMessageService groupMessageService;
	
	public FriendGroupMessageController(JwtService jwtService, FriendGroupMessageService groupMessageService) {
		this.jwtService = jwtService;
		this.groupMessageService = groupMessageService;
	}
	
	/**
	 * 
	 * @param authHeader
	 * @param request
	 * @return
	 */
	@PostMapping("/send-message")
	public ResponseEntity<Integer> sendMessage(@RequestHeader("Authorization") String authHeader,
			@RequestBody GroupMessageRequest request) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);

		groupMessageService.saveSentMessage(userId, UUID.fromString(request.getGroupId()), request.getMessage());

		return ResponseEntity.ok(200);
	}

	/**
	 * 
	 * @param authHeader
	 * @param request
	 * @return
	 */
	@PostMapping("/get-group-messages")
	public ResponseEntity<List<GroupMessageDto>> getGroupMessages(@RequestHeader("Authorization") String authHeader,
			@RequestBody GroupMessageRequest request) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);
		
		List<GroupMessageDto> messages = groupMessageService.getGroupMessages(userId, UUID.fromString(request.getGroupId()));
		return ResponseEntity.ok(messages);
	}

}

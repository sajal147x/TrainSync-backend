
package com.trainSync.community.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.community.dto.FriendGroupCreateDto;
import com.trainSync.community.service.FriendGroupService;
import com.trainSync.config.exception.UnauthorizedException;
import com.trainSync.service.JwtService;

import jakarta.validation.Valid;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:44:28 AM
 */

@RestController
@RequestMapping("/api")
public class FriendGroupController {
	
	FriendGroupController(JwtService jwtService, FriendGroupService groupService) {
		this.jwtService = jwtService;
		this.groupService = groupService;
		
	}
	
	private final JwtService jwtService;
	private final FriendGroupService groupService;
	
	
	
	/**
	 * 
	 * @param authHeader
	 * @param dto
	 * @return
	 */
	@PostMapping("/create-group")
	public ResponseEntity<String> createGroup(@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody FriendGroupCreateDto dto) {
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		
		String token = authHeader.replace("Bearer ", "");
		UUID userId = UUID.fromString(jwtService.extractUserId(token)); // validate JWT and extract Supabase UUID
		String groupId = groupService.createGroup(userId, dto.getGroupName(), dto.getMemberIds());
		return ResponseEntity.ok(groupId);
		
	}

}


package com.trainSync.community.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.community.dto.EditGroupRequest;
import com.trainSync.community.dto.FriendGroupCreateDto;
import com.trainSync.community.dto.FriendGroupSummaryDto;
import com.trainSync.community.dto.GroupLeaderboardDto;
import com.trainSync.community.dto.GroupMemberDto;
import com.trainSync.community.dto.GroupRequest;
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
	
	
	/**
	 * 
	 * @param authHeader
	 * @return
	 */
	@GetMapping("/get-groups-for-user")
	public ResponseEntity<List<FriendGroupSummaryDto>> getGroupsForUser(@RequestHeader("Authorization") String authHeader){
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		
		String token = authHeader.replace("Bearer ", "");
		UUID userId = UUID.fromString(jwtService.extractUserId(token)); // validate JWT and extract Supabase UUID
		
		List<FriendGroupSummaryDto> groups = groupService.getGroupsForUser(userId);
		
		return ResponseEntity.ok(groups);
		
	}
	
	/**
	 * 
	 * @param authHeader
	 * @return
	 */
	@PostMapping("/group-leaderboard")
	public ResponseEntity<List<GroupLeaderboardDto>> getGroupLeaderboard(@RequestHeader("Authorization") String authHeader, @RequestBody GroupRequest groupRequest){
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		List<GroupLeaderboardDto> groups = groupService.computeAndGetGroupLeaderboard(groupRequest.getGroupId(), groupRequest.getTimeFrame());

		return ResponseEntity.ok(groups);
		
	}
	
	@PostMapping("/get-group-members")
	public ResponseEntity<List<GroupMemberDto>> editGroup(@RequestHeader("Authorization") String authHeader, @RequestBody GroupRequest groupRequest){
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		List<GroupMemberDto> groupMembers = groupService.getGroupMembers(groupRequest.getGroupId());

		return ResponseEntity.ok(groupMembers);
		
	}

	@PostMapping("/edit-group")
	public ResponseEntity<Integer> editGroup(@RequestHeader("Authorization") String authHeader, @RequestBody EditGroupRequest editGroupRequest){
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new UnauthorizedException("Missing or invalid Authorization header");
	    }
		
		groupService.editGroup(editGroupRequest.getProfilePictureBase64(), editGroupRequest.getToRemoveUserIds(), editGroupRequest.getGroupId());
		return ResponseEntity.ok(200);

	}
	

}

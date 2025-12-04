
package com.trainSync.community.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.community.dto.FriendSummaryDto;
import com.trainSync.community.service.FriendSummaryService;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 4:09:22 PM
 */
@RestController
@RequestMapping("/api/community")
public class FriendSummaryController {
	
	private final FriendSummaryService friendSummaryService;
	
	FriendSummaryController(FriendSummaryService friendSummaryService) {
		this.friendSummaryService = friendSummaryService;
	}
	
	@GetMapping("/friend-workout-summary")
	public ResponseEntity<?> getFriendSummary(@RequestHeader("Authorization") String authHeader,
			@RequestParam String friendUserId) {

		UUID friendUserIdUuid = UUID.fromString(friendUserId);

		FriendSummaryDto friendSummaryDto = friendSummaryService.getFriendSummary(friendUserIdUuid);

		return ResponseEntity.ok(friendSummaryDto);

	}
	

}

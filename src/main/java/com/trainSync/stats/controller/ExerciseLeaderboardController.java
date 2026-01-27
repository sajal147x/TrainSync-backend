
package com.trainSync.stats.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.stats.dto.ExerciseLeaderBoardDto;
import com.trainSync.stats.dto.ExerciseLeaderBoardRequest;
import com.trainSync.stats.service.ExerciseLeaderboardService;

/**
 * Author: Sajal Gupta
 * Created on: Jan 26, 2026 7:13:48 PM
 */
@RestController
@RequestMapping("/api")
public class ExerciseLeaderboardController {
	
	private final JwtService jwtService;
	
	private final ExerciseLeaderboardService exerciseLeaderboardService;
	
	public ExerciseLeaderboardController(JwtService jwtService, ExerciseLeaderboardService exerciseLeaderboardService) {
		this.jwtService = jwtService;
		this.exerciseLeaderboardService = exerciseLeaderboardService;
	}
	
	
	/**
	 * 
	 * @param authHeader
	 * @param request
	 * @return
	 */
	@PostMapping("/exerciseLeaderboard")
	public ResponseEntity<List<ExerciseLeaderBoardDto>> getExerciseLeaderBoardByStatType(
			@RequestHeader("Authorization") String authHeader, @RequestBody ExerciseLeaderBoardRequest request) {
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
		UUID userId = UUID.fromString(userIdStr);
		List<ExerciseLeaderBoardDto> dtos = exerciseLeaderboardService.computeExerciseLeaderboard(userId,
				request.getStatType(), UUID.fromString(request.getExerciseLibraryId()));

		return ResponseEntity.ok(dtos);
	}



}

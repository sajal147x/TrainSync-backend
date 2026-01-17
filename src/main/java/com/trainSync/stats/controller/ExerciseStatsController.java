
package com.trainSync.stats.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.stats.dto.ExerciseCountDto;
import com.trainSync.stats.dto.ExerciseStatsDto;
import com.trainSync.stats.dto.ExerciseStatsRequest;
import com.trainSync.stats.service.ExerciseStatsService;

/**
 * Author: Sajal Gupta
 * Created on: Jan 16, 2026 10:32:22 AM
 */
@RestController
@RequestMapping("/api")
public class ExerciseStatsController {
	
	private final ExerciseStatsService exerciseStatsService;
	
	private final JwtService jwtService;
	
	public ExerciseStatsController(ExerciseStatsService exerciseStatsService, JwtService jwtService) {
		this.exerciseStatsService = exerciseStatsService;
		this.jwtService = jwtService;
	}
	
	/**
	 * 
	 * @param authHeader
	 * @param dto
	 * @return
	 */
	@RequestMapping("/exercise-stats")
	public ResponseEntity<ExerciseStatsDto> getExerciseStats(@RequestHeader("Authorization") String authHeader,
			@RequestBody ExerciseStatsRequest dto) {
		// Extract user ID from JWT token
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token);
		UUID userId = UUID.fromString(userIdStr);

		ExerciseStatsDto statsDto = exerciseStatsService.computeExerciseProgression(userId,
				UUID.fromString(dto.getExerciseLibraryId()), dto.getStatType(), dto.getTimeFrameMonths());

		return ResponseEntity.ok(statsDto);
	}

}

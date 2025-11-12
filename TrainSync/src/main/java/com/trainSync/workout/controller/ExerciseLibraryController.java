package com.trainSync.workout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.dto.ExerciseDto;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.MuscleTag;
import com.trainSync.workout.respository.ExerciseLibraryRepository;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseLibraryController {

	@Autowired
	private ExerciseLibraryRepository exerciseLibraryRepository;

	@GetMapping
	public Page<ExerciseDto> getExercises(@RequestParam(required = false) String searchText,
			@RequestParam(required = false) String muscleTag, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		Page<ExerciseLibrary> exercises;

		if (searchText != null && muscleTag != null) {
			exercises = exerciseLibraryRepository.findByNameContainingAndMuscleTags_Name(searchText, muscleTag,
					pageable);
		} else if (searchText != null) {
			exercises = exerciseLibraryRepository.findByNameContaining(searchText, pageable);
		} else if (muscleTag != null) {
			exercises = exerciseLibraryRepository.findByMuscleTags_Name(muscleTag, pageable);
		} else {
			exercises = exerciseLibraryRepository.findAll(pageable);
		}

		return exercises.map(e -> new ExerciseDto(e.getId(), e.getName(),
				e.getMuscleTags().stream().map(MuscleTag::getName).toList()));
	}
}

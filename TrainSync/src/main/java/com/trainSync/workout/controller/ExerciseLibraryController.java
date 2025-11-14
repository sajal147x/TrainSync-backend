package com.trainSync.workout.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.dto.EquipmentTagDto;
import com.trainSync.workout.dto.ExerciseDto;
import com.trainSync.workout.dto.MuscleTagDto;
import com.trainSync.workout.model.EquipmentTag;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryEquipmentLink;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.model.MuscleTag;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.MuscleTagRepository;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseLibraryController {

	@Autowired
	private ExerciseLibraryRepository exerciseLibraryRepository;
	
	@Autowired
	private MuscleTagRepository muscleTagRepository;
	
	
	/**
	 * 
	 * @param searchText
	 * @param muscleTag
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping
	public Page<ExerciseDto> getExercises(@RequestParam(required = false) String searchText,
			@RequestParam(required = false) String muscleTag, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		System.out.println("EXERCISE LIB CALLED");
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		Page<ExerciseLibrary> exercises;

		if (searchText != null && muscleTag != null) {
			exercises = exerciseLibraryRepository.findByNameContainingAndMuscleTags_NameIgnoreCase(searchText,
					muscleTag, pageable);
		} else if (searchText != null) {
			exercises = exerciseLibraryRepository.findByNameContainingIgnoreCase(searchText, pageable);
		} else if (muscleTag != null) {
			exercises = exerciseLibraryRepository.findByMuscleTags_Name(muscleTag, pageable);
		} else {
			exercises = exerciseLibraryRepository.findAll(pageable);
		}

		List<ExerciseDto> dtoList = new ArrayList<>();
		for (ExerciseLibrary e : exercises) {
			ExerciseDto dto = new ExerciseDto();
			dto.setId(e.getId().toString());
			dto.setName(e.getName());
			List<MuscleTagDto> muscleTags = new ArrayList<>();

			for (ExerciseLibraryTagLink tag : e.getTagLinks()) {
				MuscleTagDto muscleTagDto = new MuscleTagDto();
				muscleTagDto.setName(tag.getMuscleTag().getName());
				muscleTagDto.setLevel(tag.getLevel());
				muscleTags.add(muscleTagDto);
			}
			dto.setMuscleTags(muscleTags);
			for (EquipmentTag equipmentTag : e.getEquipmentTags()) {
				EquipmentTagDto equipmentDto = new EquipmentTagDto();
				equipmentDto.setId(equipmentTag.getId().toString());
				equipmentDto.setName(equipmentTag.getName());
				dto.getEquipmentTags().add(equipmentDto);
			}

			dtoList.add(dto);
		}
		// Wrap into Page<ExerciseDto>
		Page<ExerciseDto> dtoPage = new PageImpl<>(dtoList, pageable, page);

		return dtoPage;

	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/muscletags")
	public List<String> getMuscleTags() {
	    return muscleTagRepository.findAll()
	            .stream()
	            .map(MuscleTag::getName)
	            .sorted(String::compareToIgnoreCase)
	            .toList();
	}

}

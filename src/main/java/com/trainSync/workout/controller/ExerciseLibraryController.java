package com.trainSync.workout.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.dto.EquipmentTagDto;
import com.trainSync.workout.dto.ExerciseDto;
import com.trainSync.workout.dto.MuscleTagDto;
import com.trainSync.workout.model.EquipmentTag;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.model.MuscleTag;
import com.trainSync.workout.respository.EquipmentTagRepository;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.MuscleTagRepository;
import com.trainSync.workout.service.ExerciseLibraryService;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseLibraryController {

	ExerciseLibraryController(ExerciseLibraryRepository exerciseLibraryRepository,
			MuscleTagRepository muscleTagRepository, EquipmentTagRepository equipmentTagRepository, ExerciseLibraryService exerciseLibraryService) {
		this.exerciseLibraryRepository = exerciseLibraryRepository;
		this.muscleTagRepository = muscleTagRepository;
		this.equipmentTagRepository = equipmentTagRepository;
		this.exerciseLibraryService=exerciseLibraryService;
	}
	
	private final ExerciseLibraryRepository exerciseLibraryRepository;
	private final MuscleTagRepository muscleTagRepository;
	private final EquipmentTagRepository equipmentTagRepository;
	private final ExerciseLibraryService exerciseLibraryService;
	
	
	/**
	 * 
	 * @param searchText
	 * @param muscleTag
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping
	public List<ExerciseDto> getExercises(@RequestParam(required = false) String searchText,
			@RequestParam(required = false) String muscleTag, @RequestParam(required = false) String equipmentTag) {
		

		// Convert to DTO
		List<ExerciseDto> dtoList = exerciseLibraryService.getExercises(searchText, muscleTag, equipmentTag);
		

		return dtoList;
	}

	
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/muscletags")
	public List<MuscleTagDto> getMuscleTags() {
		List<MuscleTagDto> dtos = new ArrayList<MuscleTagDto>();
		List<MuscleTag> ls = muscleTagRepository.findAll();
		for (MuscleTag tag : ls) {
			MuscleTagDto dto = new MuscleTagDto();
			dto.setId(tag.getId().toString());
			dto.setName(tag.getName());
			dtos.add(dto);
		}
		return dtos;

	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/equipmenttags")
	public List<EquipmentTagDto> getEquipmentTags() {
		List<EquipmentTagDto> dtos = new ArrayList<EquipmentTagDto>();
		List<EquipmentTag> ls = equipmentTagRepository.findAll();
		for (EquipmentTag tag : ls) {
			EquipmentTagDto dto = new EquipmentTagDto();
			dto.setId(tag.getId().toString());
			dto.setName(tag.getName());
			dtos.add(dto);
		}
		return dtos;

	}
	
	public static void main(String[] args) {}

}

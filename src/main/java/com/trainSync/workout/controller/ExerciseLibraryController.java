package com.trainSync.workout.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseLibraryController {

	ExerciseLibraryController(ExerciseLibraryRepository exerciseLibraryRepository,
			MuscleTagRepository muscleTagRepository, EquipmentTagRepository equipmentTagRepository) {
		this.exerciseLibraryRepository = exerciseLibraryRepository;
		this.muscleTagRepository = muscleTagRepository;
		this.equipmentTagRepository = equipmentTagRepository;
	}
	
	private final ExerciseLibraryRepository exerciseLibraryRepository;
	private final MuscleTagRepository muscleTagRepository;
	private final EquipmentTagRepository equipmentTagRepository;
	
	
	
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
			@RequestParam(required = false) String muscleTag, @RequestParam(required = false) String equipmentTag, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ExerciseLibrary> exercises;
		UUID muscleTagUuid = muscleTag != null ? UUID.fromString(muscleTag) : null;
		UUID equipmentTagUuid = equipmentTag != null ? UUID.fromString(equipmentTag) : null;
		
		System.out.println("SEARCH TEXT " + searchText + "MUSCLE TAG" + muscleTag + "EQUIPMENT TAG" + equipmentTag);
		//search text, muscle tag, and equipment
		if (searchText != null && muscleTagUuid != null && equipmentTagUuid != null) {
			exercises = exerciseLibraryRepository.findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
					searchText, muscleTagUuid,"PRIMARY", equipmentTagUuid, pageable);
		} 
		//search text and muscle tag
		else if (searchText != null && muscleTagUuid != null) {
			exercises = exerciseLibraryRepository.findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_Level(searchText,
					muscleTagUuid,"PRIMARY", pageable);
		}
		//search text and equipment tag
		else if (searchText != null && equipmentTagUuid != null) {
			exercises = exerciseLibraryRepository.findByNameContainingIgnoreCaseAndEquipment_Id(searchText,
					equipmentTagUuid, pageable);
		} 
		//muscle tag and equipment tag
		else if (muscleTagUuid != null && equipmentTagUuid != null) {
			exercises = exerciseLibraryRepository.findByTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(muscleTagUuid, "PRIMARY",
					equipmentTagUuid, pageable);
		} 
		//only search text
		else if (searchText != null) {
			exercises = exerciseLibraryRepository.findByNameContainingIgnoreCase(searchText, pageable);
		} 
		//only muscle tag
		else if (muscleTagUuid != null) {
			exercises = exerciseLibraryRepository.findByTagLinks_MuscleTag_IdAndTagLinks_Level(muscleTagUuid, "PRIMARY", pageable);
		} 
		//only equipment tag
		else if (equipmentTagUuid != null) {
			exercises = exerciseLibraryRepository.findByEquipment_Id(equipmentTagUuid, pageable);
		}
		// no filter
		else {
			exercises = exerciseLibraryRepository.findAll(pageable);
		}

		// Convert to DTO
		List<ExerciseDto> dtoList = convertExerciseToDto(exercises, equipmentTagUuid);
		// Wrap into Page<ExerciseDto>
		Page<ExerciseDto> dtoPage = new PageImpl<>(dtoList, pageable, exercises.getTotalElements());
		

		return dtoPage;
	}


    /**
     *
     * @param exercises
     * @param equipmentTagUuid
     * @return
     */
	private List<ExerciseDto> convertExerciseToDto(Page<ExerciseLibrary> exercises, UUID equipmentTagUuid) {
		List<ExerciseDto> dtoList = new ArrayList<>();

		for (ExerciseLibrary e : exercises) {

			EquipmentTagDto equipmentDto = new EquipmentTagDto();
			equipmentDto.setId(e.getEquipment().getId().toString());
			equipmentDto.setName(e.getEquipment().getName());
			ExerciseDto dto = new ExerciseDto();
			dto.setEquipmentTag(equipmentDto);
			dto.setId(e.getId().toString());
			dto.setExercisePictureUrl(e.getExercisePictureUrl());
			dto.setName(e.getDisplayName());

			for (ExerciseLibraryTagLink tag : e.getTagLinks()) {
				MuscleTagDto muscleTagDto = new MuscleTagDto();
				muscleTagDto.setId(tag.getMuscleTag().getId().toString());
				muscleTagDto.setName(tag.getMuscleTag().getName());
				muscleTagDto.setLevel(tag.getLevel());
				dto.getMuscleTags().add(muscleTagDto);
			}
			dtoList.add(dto);

		}

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

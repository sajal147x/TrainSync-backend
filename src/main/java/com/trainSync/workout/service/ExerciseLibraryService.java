
package com.trainSync.workout.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trainSync.workout.dto.EquipmentTagDto;
import com.trainSync.workout.dto.ExerciseDto;
import com.trainSync.workout.dto.MuscleTagDto;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.respository.ExerciseLibraryRepository;

/**
 * Author: Sajal Gupta Created on: Jan 26, 2026 10:35:24 PM
 */
@Service
public class ExerciseLibraryService {

	private final ExerciseLibraryRepository repository;

	public ExerciseLibraryService(ExerciseLibraryRepository repository) {
		this.repository = repository;
	}

	@Cacheable(value = "exercises", key = "#searchText + ':' + #muscleTag + ':' + #equipmentTag" )
	public List<ExerciseDto> getExercises(String searchText, String muscleTag, String equipmentTag) {
		List<ExerciseLibrary> exercises = fetchFromRepository(searchText, muscleTag, equipmentTag);
		List<ExerciseDto> dtoList = convertExerciseToDto(exercises);
		return dtoList;
	}

	private List<ExerciseLibrary> fetchFromRepository(String searchText, String muscleTag, String equipmentTag) {
		UUID muscleTagUuid = muscleTag != null ? UUID.fromString(muscleTag) : null;
		UUID equipmentTagUuid = equipmentTag != null ? UUID.fromString(equipmentTag) : null;

		if (searchText != null && muscleTagUuid != null && equipmentTagUuid != null) {
			return repository.findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
					searchText, muscleTagUuid, "PRIMARY", equipmentTagUuid);
		} else if (searchText != null && muscleTagUuid != null) {
			return repository.findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_Level(searchText,
					muscleTagUuid, "PRIMARY");
		} else if (searchText != null && equipmentTagUuid != null) {
			return repository.findByNameContainingIgnoreCaseAndEquipment_Id(searchText, equipmentTagUuid);
		} else if (muscleTagUuid != null && equipmentTagUuid != null) {
			return repository.findByTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(muscleTagUuid, "PRIMARY",
					equipmentTagUuid);
		} else if (searchText != null) {
			return repository.findByNameContainingIgnoreCase(searchText);
		} else if (muscleTagUuid != null) {
			return repository.findByTagLinks_MuscleTag_IdAndTagLinks_Level(muscleTagUuid, "PRIMARY");
		} else if (equipmentTagUuid != null) {
			return repository.findByEquipment_Id(equipmentTagUuid);
		} else {
			return repository.findAll();
		}
	}

	/**
	 *
	 * @param exercises
	 * @param equipmentTagUuid
	 * @return
	 */
	private List<ExerciseDto> convertExerciseToDto(List<ExerciseLibrary> exercises) {
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

}

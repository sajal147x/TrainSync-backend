
package com.trainSync.seed;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.model.EquipmentTag;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryEquipmentLink;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.model.MuscleTag;
import com.trainSync.workout.respository.EquipmentTagRepository;
import com.trainSync.workout.respository.ExerciseLibraryEquipmentLinkRepository;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.ExerciseLibraryTagLinkRepository;
import com.trainSync.workout.respository.MuscleTagRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 21, 2025
 */
@RestController
@RequestMapping("/api")
public class ConfigureExerciseController {
	
	private final ExerciseLibraryRepository exerciseLibraryRepository;
    private final MuscleTagRepository muscleTagRepository;
    private final EquipmentTagRepository equipmentTagRepository;
    private final ExerciseLibraryTagLinkRepository linkRepository;
    private final ExerciseLibraryEquipmentLinkRepository equipmentLinkRepository;
    
    public ConfigureExerciseController(
            ExerciseLibraryRepository exerciseLibraryRepository,
            MuscleTagRepository muscleTagRepository,
            EquipmentTagRepository equipmentTagRepository,
            ExerciseLibraryTagLinkRepository linkRepository,
            ExerciseLibraryEquipmentLinkRepository equipmentLinkRepository
    ) {
        this.exerciseLibraryRepository = exerciseLibraryRepository;
        this.muscleTagRepository = muscleTagRepository;
        this.equipmentTagRepository = equipmentTagRepository;
        this.linkRepository = linkRepository;
        this.equipmentLinkRepository = equipmentLinkRepository;
    }
    
    
    @PostMapping("/create-exercise")
    public String createExercise(@RequestBody ExerciseConfigureDto dto) {


    	
        // Create ExerciseLibrary entry
        ExerciseLibrary exercise = new ExerciseLibrary();
        exercise.setName(dto.name);
        exercise.setName(dto.name); // displayName can be updated if needed
        exercise = exerciseLibraryRepository.save(exercise);

        // Seed MuscleTags
        if (dto.muscleTagIdsPrimary != null) {
			for (String tagDto : dto.muscleTagIdsPrimary) {
				System.out.println("PRIMARY");
				MuscleTag tag;
				Optional<MuscleTag> tagOptional = muscleTagRepository.findById(UUID.fromString(tagDto));
				if (tagOptional.isPresent()) {
					tag = tagOptional.get();

					// Create link with PRIMARY/SECONDARY
					ExerciseLibraryTagLink link = new ExerciseLibraryTagLink(exercise, tag, "PRIMARY");
					linkRepository.save(link);
				}
			}
        }
        if (dto.muscleTagIdsSecondary != null) {
			for (String tagDto : dto.muscleTagIdsSecondary) {
				System.out.println("SECONDARY");
				MuscleTag tag;
				Optional<MuscleTag> tagOptional = muscleTagRepository.findById(UUID.fromString(tagDto));
				if (tagOptional.isPresent()) {
					tag = tagOptional.get();

					// Create link with PRIMARY/SECONDARY
					ExerciseLibraryTagLink link = new ExerciseLibraryTagLink(exercise, tag, "SECONDARY");
					linkRepository.save(link);
				}
			}
        }

        // Seed EquipmentTags
		if (dto.equipmentIds != null) {
			for (String equipmentId : dto.equipmentIds) {
				EquipmentTag equipmentTag;
				Optional<EquipmentTag> equipOptional = equipmentTagRepository.findById(UUID.fromString(equipmentId));
				if (equipOptional.isPresent()) {
					equipmentTag = equipOptional.get();

					// Create link to exercise
					ExerciseLibraryEquipmentLink link = new ExerciseLibraryEquipmentLink(exercise, equipmentTag);
					equipmentLinkRepository.save(link);
				}
			}
		}
    
    	return null;
    }

}

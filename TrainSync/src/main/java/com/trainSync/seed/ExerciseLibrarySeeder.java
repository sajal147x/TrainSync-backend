package com.trainSync.seed;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import jakarta.annotation.PostConstruct;

/**
 * Author: Sajal Gupta Date: Nov 14, 2025
 */
@Component
public class ExerciseLibrarySeeder {

    private final ExerciseLibraryRepository exerciseLibraryRepository;
    private final MuscleTagRepository muscleTagRepository;
    private final EquipmentTagRepository equipmentTagRepository;
    private final ExerciseLibraryTagLinkRepository linkRepository;
    private final ExerciseLibraryEquipmentLinkRepository equipmentLinkRepository;

    public ExerciseLibrarySeeder(
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

    @PostConstruct
    public void seed() {
        try {
            if (exerciseLibraryRepository.count() > 0) {
                System.out.println("Exercise Library already populated. Skipping seeding.");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(
                com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
            );

            InputStream is = getClass().getClassLoader().getResourceAsStream("exercises.json");
            if (is == null) {
                System.err.println("exercises.json not found");
                return;
            }

            List<ExerciseSeedDto> seedList = mapper.readValue(
                    is,
                    new TypeReference<List<ExerciseSeedDto>>() {}
            );

            for (ExerciseSeedDto dto : seedList) {
                // Create ExerciseLibrary entry
                ExerciseLibrary exercise = new ExerciseLibrary();
                exercise.setName(dto.name);
                exercise.setName(dto.name); // displayName can be updated if needed
                exercise = exerciseLibraryRepository.save(exercise);

                // Seed MuscleTags
                if (dto.muscleTags != null) {
                    for (MuscleTagSeedDto tagDto : dto.muscleTags) {
                        MuscleTag tag;
                        Optional<MuscleTag> tagOptional = muscleTagRepository.findByName(tagDto.tag);
                        if (tagOptional.isPresent()) {
                            tag = tagOptional.get();
                        } else {
                            tag = new MuscleTag();
                            tag.setName(tagDto.tag);
                            tag = muscleTagRepository.save(tag);
                        }
                        // Create link with PRIMARY/SECONDARY
                        ExerciseLibraryTagLink link = new ExerciseLibraryTagLink(
                                exercise,
                                tag,
                                tagDto.level.toUpperCase()
                        );
                        linkRepository.save(link);
                    }
                }

                // Seed EquipmentTags
                if (dto.equipment != null) {
                    for (String equipmentName : dto.equipment) {
                        EquipmentTag equipmentTag;
                        Optional<EquipmentTag> equipOptional = equipmentTagRepository.findByName(equipmentName);
                        if (equipOptional.isPresent()) {
                            equipmentTag = equipOptional.get();
                        } else {
                            equipmentTag = new EquipmentTag();
                            equipmentTag.setName(equipmentName);
                            equipmentTag = equipmentTagRepository.save(equipmentTag);
                        }
                        // Create link to exercise
                        ExerciseLibraryEquipmentLink link = new ExerciseLibraryEquipmentLink(
                                exercise,
                                equipmentTag
                        );
                        equipmentLinkRepository.save(link);
                    }
                }
            }

            System.out.println("Exercise Library seeding complete.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to seed exercise library");
        }
    }
}

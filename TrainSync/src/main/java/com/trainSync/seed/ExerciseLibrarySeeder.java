package com.trainSync.seed;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.model.MuscleTag;
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
	private final ExerciseLibraryTagLinkRepository linkRepository;

	public ExerciseLibrarySeeder(ExerciseLibraryRepository exerciseLibraryRepository,
			MuscleTagRepository muscleTagRepository, ExerciseLibraryTagLinkRepository linkRepository) {
		this.exerciseLibraryRepository = exerciseLibraryRepository;
		this.muscleTagRepository = muscleTagRepository;
		this.linkRepository = linkRepository;
	}

	@PostConstruct
	public void seed() {
		try {
			if (exerciseLibraryRepository.count() > 0) {
				System.out.println("Exercise Library already populated. Skipping seeding.");
				return;
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			InputStream is = getClass().getClassLoader().getResourceAsStream("exercises.json");

			if (is == null) {
				System.err.println(" exerciseLibrarySeed.json not found");
				return;
			}

			List<ExerciseSeedDto> seedList = mapper.readValue(is, new TypeReference<List<ExerciseSeedDto>>() {
			});

			for (ExerciseSeedDto dto : seedList) {

				ExerciseLibrary exercise = new ExerciseLibrary();
				exercise.setName(dto.name);
				exercise = exerciseLibraryRepository.save(exercise);

				for (MuscleTagSeedDto tagDto : dto.muscleTags) {
					// Check if tag already exists
					Optional<MuscleTag> tagOptional = muscleTagRepository.findByName(tagDto.tag);
					MuscleTag tag = new MuscleTag();
					if (!tagOptional.isEmpty()) {
						tag = tagOptional.get();
					} else {
						tag = new MuscleTag();
						tag = new MuscleTag();
						tag.setName(tagDto.tag);
						muscleTagRepository.save(tag);
					}
					// Create link with PRIMARY/SECONDARY level
					ExerciseLibraryTagLink link = new ExerciseLibraryTagLink(exercise, tag, tagDto.level.toUpperCase());

					linkRepository.save(link);
				}
			}

			System.out.println("Exercise Library seeding complete.");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(" Failed to seed exercise library");
		}
	}
}
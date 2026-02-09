
package com.trainSync.seed;

import java.util.ArrayList;
import java.util.List;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.opensearch.ExerciseLibraryDocument;
import com.trainSync.workout.respository.ExerciseLibraryRepository;

import jakarta.annotation.PostConstruct;

/**
 * Author: Sajal Gupta
 * Created on: Feb 8, 2026 2:16:07 PM
 */
@Component
public class OpenSearchExerciseLibSeeder {
	
	private final OpenSearchClient client;
	private final ExerciseLibraryRepository exerciseLibraryRepository;
	
	public OpenSearchExerciseLibSeeder(OpenSearchClient client, ExerciseLibraryRepository exerciseLibraryRepository) {
		this.client=client;
		this.exerciseLibraryRepository=exerciseLibraryRepository;
	}
	
	
	public void seed() {
		System.out.println("STARTED");
        List<ExerciseLibrary> exercises = exerciseLibraryRepository.findAll();

        for (ExerciseLibrary exercise : exercises) {
            ExerciseLibraryDocument doc = new ExerciseLibraryDocument();
            doc.setId(exercise.getId().toString());
            doc.setName(exercise.getName());
            doc.setExercisePictureUrl(exercise.getExercisePictureUrl());

            // Equipment nested
            if (exercise.getEquipment() != null) {
                doc.setEquipment(new ExerciseLibraryDocument.Equipment(
                        exercise.getEquipment().getId().toString(),
                        exercise.getEquipment().getName()
                ));
            }

            // Muscles nested (primary/secondary)
            List<ExerciseLibraryDocument.Muscle> muscles = new ArrayList<>();

            for (ExerciseLibraryTagLink link : exercise.getTagLinks()) {
                ExerciseLibraryDocument.Muscle muscle = new ExerciseLibraryDocument.Muscle();
                muscle.setId(link.getMuscleTag().getId().toString());
                muscle.setName(link.getMuscleTag().getName());
                muscle.setType(link.isPrimary() ? "primary" : "secondary");

                muscles.add(muscle);
            }
            doc.setMuscles(muscles);

            // Index into OpenSearch
            try {
                IndexResponse response = client.index(i -> i
                        .index("exercises") // make sure index exists or create first
                        .id(doc.getId())
                        .document(doc)
                );
                System.out.println("Indexed exercise: " + doc.getName());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
	}
	
	
	public static void main(String[] args) {
		
		
		
	}
	

}

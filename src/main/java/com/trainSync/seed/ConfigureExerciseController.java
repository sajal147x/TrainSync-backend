
package com.trainSync.seed;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.seed.dto.EditExerciseConfigDto;
import com.trainSync.service.SupabaseStorageService;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseLibraryTagLink;
import com.trainSync.workout.model.MuscleTag;
import com.trainSync.workout.respository.EquipmentTagRepository;
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
    private final SupabaseStorageService storageService;
    
    public ConfigureExerciseController(
    					SupabaseStorageService storageService,
            ExerciseLibraryRepository exerciseLibraryRepository,
            MuscleTagRepository muscleTagRepository,
            EquipmentTagRepository equipmentTagRepository,
            ExerciseLibraryTagLinkRepository linkRepository) {
    	this.storageService = storageService;
        this.exerciseLibraryRepository = exerciseLibraryRepository;
        this.muscleTagRepository = muscleTagRepository;
        this.equipmentTagRepository = equipmentTagRepository;
        this.linkRepository = linkRepository;
    }
    
    
    @PostMapping("/create-exercise")
	public String createExercise(@RequestBody ExerciseConfigureDto dto) {

		for (String equipmentId : dto.equipmentIds) {
			ExerciseLibrary exercise = new ExerciseLibrary();
			exercise.setName(dto.name);
			exercise.setEquipment(equipmentTagRepository.findById(UUID.fromString(equipmentId)).get());
			exercise = exerciseLibraryRepository.save(exercise);

			// Seed MuscleTags
			if (dto.muscleTagIdsPrimary != null) {
				for (String tagDto : dto.muscleTagIdsPrimary) {
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

		}
		return null;
	}
    
    
    @PostMapping("/edit-exercise")
    public String editExericse(@RequestBody EditExerciseConfigDto dto) throws Exception {
    	
    	ExerciseLibrary exercise = exerciseLibraryRepository.findById(UUID.fromString(dto.exerciseId)).get();
    	byte[] resized = resizeTo400(dto.pictureBase64);
    	String fileName = exercise.getName() + exercise.getEquipment().getName() + ".jpg";
    	String base64 = Base64.getEncoder().encodeToString(resized);
    	String publicUrl = storageService.uploadBase64Image(base64, fileName, "exercise-pictures");
    	exercise.setExercisePictureUrl(publicUrl);
    	exerciseLibraryRepository.save(exercise);
    	return null;
    }
    
    public byte[] resizeTo400(String base64) throws Exception {
        // decode
        byte[] decoded = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bis = new ByteArrayInputStream(decoded);

        // read as image
        BufferedImage original = ImageIO.read(bis);

        // resize → HIGH QUALITY
        BufferedImage resized = Scalr.resize(original, Scalr.Method.QUALITY, 400, 400);

        // convert back to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resized, "jpg", baos);

        return baos.toByteArray();
    }

}

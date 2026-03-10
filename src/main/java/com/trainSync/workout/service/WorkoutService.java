package com.trainSync.workout.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trainSync.TrainSyncApplication;
import com.trainSync.community.model.FriendLink;
import com.trainSync.community.repository.FriendLinkRepository;
import com.trainSync.notification.model.PushNotificationToken;
import com.trainSync.notification.repository.PushNotificationTokenRepository;
import com.trainSync.notification.service.PushNotificationService;
import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutSetRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;
import com.trainSync.util.Constants;
import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.factory.ExerciseFactory;
import com.trainSync.workout.factory.ExerciseSetFactory;
import com.trainSync.workout.factory.WorkoutFactory;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseSet;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.ExerciseSetRepository;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */
@Service
public class WorkoutService {

	//
	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;
	private final ExerciseLibraryRepository exerciseLibraryRepository;
	private final PushNotificationService pushNotificationService;
	private final FriendLinkRepository friendLinkRepository;
	private final UserDetailsRepository userDetailsRepository;
	private final PushNotificationTokenRepository pushNotificationTokenRepository;
	//
	WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository,
			ExerciseLibraryRepository exerciseLibraryRepository,
			PreMadeWorkoutSetRepository preMadeWorkoutSetRepository, ExerciseSetRepository exerciseSetRepository,
			PushNotificationService pushNotificationService, FriendLinkRepository friendLinkRepository,
			UserDetailsRepository userDetailsRepository, PushNotificationTokenRepository pushNotificationTokenRepository) {
		this.workoutRepository = workoutRepository;
		this.exerciseRepository = exerciseRepository;
		this.exerciseLibraryRepository = exerciseLibraryRepository;
		this.pushNotificationService = pushNotificationService;
		this.friendLinkRepository = friendLinkRepository;
		this.userDetailsRepository = userDetailsRepository;
		this.pushNotificationTokenRepository = pushNotificationTokenRepository;
	}
	//
	
	/**
	 * 1. create and save workout
	 * 2. create and save exercise
	 * 3. pre populate sets if exercise has been done before
	 * @param workoutDto
	 * @param userId
	 * @return
	 */
	public String createWorkout(WorkoutDto workoutDto, UUID userId) {
		
		// Create and save workout (factory pattern)
		Workout workout = new WorkoutFactory().createNewBlankWorkout(workoutDto.getWorkoutName(),
				userId, OffsetDateTime.now());
		
		workoutRepository.save(workout);
		
		// Create and save exercise linked to workout
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();

		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());

		Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, 1); //order is always 1 for new workouts
		exerciseRepository.save(exercise);
		
		
		// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(lastExerciseForUser, exercise);
		exercise.setSets(sets);
		exerciseRepository.save(exercise);
		
		sendPushNotificationForStartingWorkout(userId);

		return workout.getId().toString();
	}



	/**
	 * 1. create exercise and save it
	 * 2. pre populate sets if done before
	 * @param workoutDto
	 * @param userId
	 * @return
	 */
	public String addExerciseToWorkout(WorkoutDto workoutDto, UUID userId) {
		// Create and save exercise linked to workout
		Workout workout = workoutRepository.findById(UUID.fromString(workoutDto.getWorkoutId())).get();
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());

		Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, workoutDto.getExerciseOrder());
		exerciseRepository.save(exercise);
		// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(
				lastExerciseForUser, exercise);
		exercise.setSets(sets);
		exerciseRepository.save(exercise);

		return workout.getId().toString();
	}

	/**
	 * @param preMade
	 * @param preMadeExercises
	 * @return
	 */
	public String createWorkoutUsingPreMade(PreMadeWorkout preMade, List<PreMadeWorkoutExercise> preMadeExercises,
			UUID userId) {
		
		//Create Workout
		Workout workout = new WorkoutFactory().createWorkoutFromPreMade( preMade.getName(),
				userId, OffsetDateTime.now(), preMade);
		workoutRepository.save(workout);
		
		// Create Exercises
		for (PreMadeWorkoutExercise preMadeExercise : preMadeExercises) {
			ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(preMadeExercise.getExercise().getId())
					.get();
			
			Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());
			
			Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, preMadeExercise.getExerciseOrder());
			exerciseRepository.save(exercise);
			
			// Create Sets from previous iteration of the exercise
			List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(
					lastExerciseForUser, exercise);
			exercise.setSets(sets);
			exerciseRepository.save(exercise);
		}

		sendPushNotificationForStartingWorkout(userId);
		
		return workout.getId().toString();
	}
	
	
	/**
	 * 
	 * @param workoutId
	 * @return
	 */
	public Workout fetchWorkout(UUID workoutId) {
		return workoutRepository.findById(workoutId).get();
	}
	
	/**
	 * 1. fetch all exercises for workout
	 * 2. delete all sets for each exercise
	 * 3. delete all exercises
	 * 4. delete workout
	 * 
	 * @param workoutId
	 */
	public void deleteWorkout(String workoutId) {
		UUID workoutUuid = UUID.fromString(workoutId);
		Workout workout = workoutRepository.findById(workoutUuid).get();
		workoutRepository.delete(workout);
	}

	/**
	 * send notification to users friends that user started workout
	 * @param userId
	 */
	@Async
	public void sendPushNotificationForStartingWorkout(UUID userId) {
		UserDetails userDetails  = userDetailsRepository.findById(userId).get();
		String title = "TrainSync";
		String message = userDetails.getName() + " just started a workout!";
		
 		List<FriendLink> friendsForUser = friendLinkRepository.findByUserDetails_Id(userId);
 		List<UUID> friendIds = friendsForUser.stream().map(FriendLink::getFriendDetails).map(UserDetails::getId).toList();
 		List<PushNotificationToken> tokens = pushNotificationTokenRepository.findAllByUser_IdIn(friendIds);
 		List<String> tokenStrs = tokens.stream().map(PushNotificationToken::getToken).toList();
 		
 		Map<String, Object> data = Map.of(
				"type", Constants.NOTIF_TYPE_FRIEND_SUMMARY,
				"friendId", userId.toString(),
				"name", userDetails.getName(),
				"profilePictureUrl", userDetails.getProfilePictureUrl() != null ? userDetails.getProfilePictureUrl() : ""
				);
 		pushNotificationService.sendPushNotification(tokenStrs, title, message, data);
 		
	}

	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TrainSyncApplication.class, args);
		ExerciseRepository exerciseRepository = ctx.getBean(ExerciseRepository.class);
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(
				UUID.fromString("5283d060-9816-413b-92f3-046ce2fdbc43"),
				UUID.fromString("a1363c95-b399-4907-9015-3ab6681113de"));
		System.out.println(lastExerciseForUser.getName());
	}


}

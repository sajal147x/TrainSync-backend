
package com.trainSync.stats.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.model.FriendLink;
import com.trainSync.community.repository.FriendLinkRepository;
import com.trainSync.stats.dto.ExerciseLeaderBoardDto;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;
import com.trainSync.util.Constants;
import com.trainSync.workout.respository.ExerciseRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 26, 2026 7:18:32 PM
 */
@Service
public class ExerciseLeaderboardService {
	
	private final FriendLinkRepository friendLinkRepository;
	
	private final ExerciseRepository exerciseRepository;
	
	private final UserDetailsRepository userDetailsRepository;
	
	public ExerciseLeaderboardService(FriendLinkRepository friendLinkRepository, ExerciseRepository exerciseRepository, UserDetailsRepository userDetailsRepository) {
		this.friendLinkRepository=friendLinkRepository;
		this.exerciseRepository = exerciseRepository;
		this.userDetailsRepository = userDetailsRepository;
	}
	
	
	/**
	 * 1. find friends for user
	 * 2. get max stat type for the users, rank based on that
	 * @param userId
	 * @param statType
	 * @return
	 */
	public List<ExerciseLeaderBoardDto> computeExerciseLeaderboard(UUID userId, String statType, UUID exerciseLibraryId) {
		
		//FRIENDS FOR USER
		List<FriendLink> friendLinksForUser = friendLinkRepository.findByUserDetails_Id(userId);
		UserDetails loggedInUser = userDetailsRepository.findById(userId).get();
		List<UserDetails> usersForComparison = new ArrayList<>( friendLinksForUser.stream().map(FriendLink :: getFriendDetails).toList());
		//NEED TO ADD LOGGED IN USER FOR LEADERBOARD
		usersForComparison.add(loggedInUser);
		
		List<ExerciseLeaderBoardDto> result = new ArrayList<>();
		
		//COMPUTE STAT AND BUILD DTOS
		for(UserDetails user : usersForComparison) {
			Double statValue = 0d;
			if (statType.equalsIgnoreCase(Constants.STAT_TYPE_MAX_WEIGHT)) {
				statValue = exerciseRepository.findAllTimeMaxWeight(user.getId(), exerciseLibraryId);
			}
			if(statType.equalsIgnoreCase(Constants.STAT_TYPE_TOTAL_VOLUME)) {
				statValue=exerciseRepository.findAllTimeMaxVolume(user.getId(), exerciseLibraryId);
			}
			ExerciseLeaderBoardDto dto = ExerciseLeaderBoardDto.builder()
					.userId(user.getId().toString())
					.name(user.getName())
					.profilePictureUrl(user.getProfilePictureUrl())
					.statValue(statValue)
					.build();
			result.add(dto);
			
		}
		//SORT RESULT
		return result.stream()
	            .sorted(Comparator.comparing(
	                    ExerciseLeaderBoardDto::getStatValue,
	                    Comparator.nullsLast(Comparator.reverseOrder())
	            ))
	            .toList();
		
	}

}

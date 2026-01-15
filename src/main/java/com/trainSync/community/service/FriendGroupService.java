
package com.trainSync.community.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendGroupSummaryDto;
import com.trainSync.community.dto.GroupLeaderboardDto;
import com.trainSync.community.model.FriendGroup;
import com.trainSync.community.model.FriendGroupMemberLink;
import com.trainSync.community.repository.FriendGroupMemberLinkRepository;
import com.trainSync.community.repository.FriendGroupRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.service.UserService;
import com.trainSync.workout.dto.UserWorkoutCount;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:49:05 AM
 */
@Service
public class FriendGroupService {
	
	private final UserService userService;
	
	private final FriendGroupRepository friendGroupRepository;
	
	private final FriendGroupMemberLinkRepository friendGroupMemberLinkRepository;
	
	private final WorkoutRepository workoutRepository;
	
	FriendGroupService(UserService userService, FriendGroupRepository friendGroupRepository,
			FriendGroupMemberLinkRepository friendGroupMemberLinkRepository, WorkoutRepository workoutRepository) {
		this.userService = userService;
		this.friendGroupRepository = friendGroupRepository;
		this.friendGroupMemberLinkRepository = friendGroupMemberLinkRepository;
		this.workoutRepository = workoutRepository;
	}

	/**
	 * 1. create group
	 * 2. create group member link
	 * 3. save
	 * @param userId
	 * @param groupName
	 * @param memberIds
	 * @return
	 */
	public String createGroup(UUID createdByUserId, String groupName, List<String> memberIds) {
		
		// CREATE GROUP
		UserDetails createdByUser = userService.findById(createdByUserId);
		
		FriendGroup group = FriendGroup.builder()
				.createdByUser(createdByUser)
				.groupName(groupName)
				.createdAt(OffsetDateTime.now())
				.build();
		
		friendGroupRepository.save(group);
		
		// CREATE GROUP MEMBER LINKS
		List<FriendGroupMemberLink> memberLinks = new ArrayList<>();
		for (String memberId : memberIds) {
			UserDetails groupMember = userService.findById(UUID.fromString(memberId));
			FriendGroupMemberLink memberLink = FriendGroupMemberLink.builder()
					.friendGroup(group)
					.groupMember(groupMember)
					.joinedAt(OffsetDateTime.now())
					.build();
			memberLinks.add(memberLink);
		}
		//ADD CREATED BY USER AS MEMBER TOO
		FriendGroupMemberLink createdByMemberLink = FriendGroupMemberLink.builder()
				.friendGroup(group)
				.groupMember(createdByUser)
				.joinedAt(OffsetDateTime.now())
				.build();
		memberLinks.add(createdByMemberLink);
		
		friendGroupMemberLinkRepository.saveAll(memberLinks);
		
		return group.getId().toString();
	}

	/**
	 * 1. retrieve group member links for user
	 * 2. retrieve group details
	 * 3. map to dto
	 * @param userId
	 * @return
	 */
	public List<FriendGroupSummaryDto> getGroupsForUser(UUID userId) {
		
		List<FriendGroupMemberLink> memberLinks = friendGroupMemberLinkRepository.findByGroupMemberId(userId);
		List<FriendGroupSummaryDto> groupSummaries = new ArrayList<>();
		
		for (FriendGroupMemberLink link : memberLinks) {
			FriendGroup group = link.getFriendGroup();
			FriendGroupSummaryDto dto = FriendGroupSummaryDto.builder()
					.groupId(group.getId().toString())
					.groupName(group.getGroupName())
					.build();
					
			groupSummaries.add(dto);
		}
		
		return groupSummaries;
	}

	/**
	 * 1. retrieve group members
	 * 2. compute weekly workouts for each member
	 * 3. map to dto and sort
	 * @param groupId
	 * @return
	 */
	public List<GroupLeaderboardDto> computeAndGetGroupLeaderboard(String groupId) {
		UUID groupUUID = UUID.fromString(groupId);
		
		// RETRIEVE GROUP MEMBERS
		List<UserDetails> groupMembers = friendGroupMemberLinkRepository.findByFriendGroupId(groupUUID).stream()
				.map(FriendGroupMemberLink::getGroupMember)
				.toList();
		
		// COMPUTE WEEKLY STATS
		List<UserWorkoutCount> userWorkoutCountsThisWeek = workoutRepository.countWorkoutsPerUserSince(
				groupMembers.stream().map(UserDetails::getId).toList(),
				OffsetDateTime.now().minusDays(7)
		);
		
		//MAP FOR EFFECIENT LOOKUP
		Map<UUID, Long> workoutCountByUserId = new HashMap<>();
		for(UserWorkoutCount uwc : userWorkoutCountsThisWeek) {
			workoutCountByUserId.put(uwc.getUserId(), (long) uwc.getWorkoutCount());
		}
		
		// MAP TO DTO
		List<GroupLeaderboardDto> leaderboard = new ArrayList<>();
		for(UserDetails member : groupMembers) {
			
			GroupLeaderboardDto dto = GroupLeaderboardDto.builder()
					.userId(member.getId().toString())
					.name(member.getName())
					.profilePictureUrl(member.getProfilePictureUrl())
					.workoutsThisWeek(workoutCountByUserId.getOrDefault(member.getId(), 0L).intValue())
					.build();
			leaderboard.add(dto);
		}
		
		// SORT BY WORKOUT COUNT DESCENDING
		leaderboard.sort(Comparator.comparing(GroupLeaderboardDto::getWorkoutsThisWeek).reversed());
		
		return leaderboard;
		
	}

}

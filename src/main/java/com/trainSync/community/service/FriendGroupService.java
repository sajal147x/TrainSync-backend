
package com.trainSync.community.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendGroupSummaryDto;
import com.trainSync.community.model.FriendGroup;
import com.trainSync.community.model.FriendGroupMemberLink;
import com.trainSync.community.repository.FriendGroupMemberLinkRepository;
import com.trainSync.community.repository.FriendGroupRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;
import com.trainSync.user.service.UserService;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:49:05 AM
 */
@Service
public class FriendGroupService {
	
	private final UserService userService;
	
	private final FriendGroupRepository friendGroupRepository;
	
	private final FriendGroupMemberLinkRepository friendGroupMemberLinkRepository;
	
	FriendGroupService(UserService userService, FriendGroupRepository friendGroupRepository, FriendGroupMemberLinkRepository friendGroupMemberLinkRepository) {
		this.userService = userService;
		this.friendGroupRepository = friendGroupRepository;
		this.friendGroupMemberLinkRepository = friendGroupMemberLinkRepository;
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

}

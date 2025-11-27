
package com.trainSync.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendsResponseDto;
import com.trainSync.community.model.FriendLink;
import com.trainSync.community.repository.FriendLinkRepository;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 2:51:28 PM
 */
@Service
public class FriendService {
	
	private final FriendLinkRepository friendLinkRepository;
	
	FriendService(FriendLinkRepository friendLinkRepository) { 
		this.friendLinkRepository = friendLinkRepository;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<FriendsResponseDto> getFriendsForUser(UUID userId) {
		List<FriendLink> friendLinks = friendLinkRepository.findByUserDetails_Id(userId);
		List<FriendsResponseDto> friendsResponse = new ArrayList<>();

		if (friendLinks == null || friendLinks.isEmpty()) {
			return List.of();
		}

		for (FriendLink link : friendLinks) {
			FriendsResponseDto dto = FriendsResponseDto.builder()
					.userId(link.getFriendDetails().getId().toString())
					.name(link.getFriendDetails().getName())
					.profilePictureUrl(link.getFriendDetails().getProfilePictureUrl())
					.build();
			friendsResponse.add(dto);
		}
		return friendsResponse;
	}	
	
	

}


package com.trainSync.objectionableContent.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.model.FriendLink;
import com.trainSync.community.model.FriendRequest;
import com.trainSync.community.repository.FriendLinkRepository;
import com.trainSync.community.repository.FriendRequestRepository;
import com.trainSync.community.service.FriendGroupService;

/**
 * Author: Sajal Gupta
 * Created on: Feb 9, 2026 10:13:53 AM
 */
@Service
public class ObjectionableContentService {
	
	private final FriendLinkRepository friendLinkRepository;
	
	private final FriendRequestRepository friendRequestRepository;
	
	private final FriendGroupService friendGroupService;

	
	public ObjectionableContentService(FriendLinkRepository friendLinkRepository, FriendRequestRepository friendRequestRepository, FriendGroupService friendGroupService) {
		this.friendLinkRepository=friendLinkRepository;
		this.friendRequestRepository=friendRequestRepository;
		this.friendGroupService = friendGroupService;
	}

	/**
	 * remove friend link both ways, remove user from any groups with the blocked user
	 * @param userId
	 * @param fromString
	 */
	public void blockUser(UUID userId, UUID blockedUserId) {
		
		//FRIEND LINKS
		FriendLink userToBlockedUserLink = friendLinkRepository.findByUserDetails_IdAndFriendDetails_Id(userId, blockedUserId);
		FriendLink blockedUserToUserLink = friendLinkRepository.findByUserDetails_IdAndFriendDetails_Id(blockedUserId, userId);
		FriendRequest userToBlockedUserRequest = friendRequestRepository.findBySenderDetails_IdAndReceiverDetails_Id(userId, blockedUserId);
		FriendRequest blockedUserToUserRequest = friendRequestRepository.findBySenderDetails_IdAndReceiverDetails_Id(blockedUserId, userId);
		
		if (userToBlockedUserLink != null) {
			friendLinkRepository.delete(userToBlockedUserLink);
		}
		if (blockedUserToUserLink != null) {
			friendLinkRepository.delete(blockedUserToUserLink);
		}
		if (userToBlockedUserRequest != null) {
			friendRequestRepository.delete(userToBlockedUserRequest);
		}
		if (blockedUserToUserRequest != null) {
			friendRequestRepository.delete(blockedUserToUserRequest);
		}
		
		//REMOVING FROM GROUPS
		friendGroupService.removeUserFromAllMutualGroups(userId, blockedUserId);
		
		
	}

}

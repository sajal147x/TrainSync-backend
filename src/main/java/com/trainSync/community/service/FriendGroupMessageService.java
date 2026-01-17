package com.trainSync.community.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.model.FriendGroup;
import com.trainSync.community.model.GroupMessage;
import com.trainSync.community.repository.FriendGroupRepository;
import com.trainSync.community.repository.GroupMessageRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

/**
 * @author sajalgupta
 * 
 * 
 */
@Service
public class FriendGroupMessageService {
	
	
	private final FriendGroupRepository friendGroupRepository;
	
	private final UserDetailsRepository userDetailsRepository;
	
	private final GroupMessageRepository groupMessageRepository;

	public FriendGroupMessageService(FriendGroupRepository friendGroupRepository,
			UserDetailsRepository userDetailsRepository, GroupMessageRepository groupMessageRepository) {
		this.friendGroupRepository = friendGroupRepository;
		this.userDetailsRepository = userDetailsRepository;
		this.groupMessageRepository = groupMessageRepository;
	}
	

	/**
	 * 
	 * @param userId
	 * @param groupId
	 * @param message
	 */
	public void saveSentMessage(UUID userId, UUID groupId, String message) {
		FriendGroup group = friendGroupRepository.findById(groupId).get();
		UserDetails user = userDetailsRepository.findById(userId).get();
		
		GroupMessage groupMessage = GroupMessage.builder()
				.message(message)
				.sender(user)
				.group(group)
				.sentAt(OffsetDateTime.now())
				.build();
		
		groupMessageRepository.save(groupMessage);
		
	}
	

}

package com.trainSync.community.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendsResponseDto;
import com.trainSync.community.dto.GroupMessageDto;
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

	
	/**
	 * 
	 * @param userId
	 * @param fromString
	 * @return
	 */
	public List<GroupMessageDto> getGroupMessages(UUID loggedInUserId, UUID groupId) {
		
		List<GroupMessage> messages = groupMessageRepository.findByGroupIdOrderBySentAtAsc(groupId);
		
		List<GroupMessageDto> messageDtos = new ArrayList<>(); 
		
		for(GroupMessage msg : messages) {
			GroupMessageDto dto = GroupMessageDto.builder()
					.sentAt(msg.getSentAt())
					.message(msg.getMessage())
					.isSentByLoggedInUser(msg.getSender().getId().equals(loggedInUserId) ? "true" : "false")
					.userDto(FriendsResponseDto.builder()
							.userId(msg.getSender().getId().toString())
							.name(msg.getSender().getName())
							.profilePictureUrl(msg.getSender().getProfilePictureUrl())
							.build())
					.build();
			messageDtos.add(dto);
		}
		return messageDtos;
		
	}
	

}

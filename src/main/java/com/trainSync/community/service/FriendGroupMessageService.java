package com.trainSync.community.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendsResponseDto;
import com.trainSync.community.dto.GroupMessageDto;
import com.trainSync.community.model.FriendGroup;
import com.trainSync.community.model.FriendGroupMemberLink;
import com.trainSync.community.model.GroupMessage;
import com.trainSync.community.repository.FriendGroupMemberLinkRepository;
import com.trainSync.community.repository.FriendGroupRepository;
import com.trainSync.community.repository.GroupMessageRepository;
import com.trainSync.notification.model.PushNotificationToken;
import com.trainSync.notification.repository.PushNotificationTokenRepository;
import com.trainSync.notification.service.PushNotificationService;
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
	
	private final FriendGroupMemberLinkRepository friendGroupMemberLinkRepository;
	
	private final PushNotificationTokenRepository pushNotificationTokenRepository;
	
	private final PushNotificationService pushNotificationService;

	public FriendGroupMessageService(FriendGroupRepository friendGroupRepository,
			UserDetailsRepository userDetailsRepository, GroupMessageRepository groupMessageRepository,
			FriendGroupMemberLinkRepository friendGroupMemberLinkRepository,
			PushNotificationTokenRepository pushNotificationTokenRepository,
			PushNotificationService pushNotificationService) {
		this.friendGroupRepository = friendGroupRepository;
		this.userDetailsRepository = userDetailsRepository;
		this.groupMessageRepository = groupMessageRepository;
		this.friendGroupMemberLinkRepository = friendGroupMemberLinkRepository;
		this.pushNotificationTokenRepository = pushNotificationTokenRepository;
		this.pushNotificationService = pushNotificationService;
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

	/**
	 * find all group members except the sender and send push notification to all devices
	 * @param userId
	 * @param fromString
	 * @param message
	 * @throws IOException 
	 */
	public void sendPushNotificationToGroupMembers(UUID userId, UUID groupId, String message) throws IOException {
		
		//GROUP MEMBERS
		List<UserDetails> groupMembers = friendGroupMemberLinkRepository.findByFriendGroupId(groupId).stream()
				.map(FriendGroupMemberLink::getGroupMember)
				.toList();
		
		//COLLECT IDS EXCEPT SENDER
		List<UUID> memberIds = new ArrayList<>();
		for(UserDetails member : groupMembers) {
			if(!member.getId().equals(userId)) {
				memberIds.add(member.getId());
			}
		}
		
		//GET TOKENS FOR USERS AND SEND PUSH NOTIFICATION
		List<PushNotificationToken> tokensForUsers = pushNotificationTokenRepository.findAllByUser_IdIn(memberIds);
		List<String> tokens = tokensForUsers.stream().map(PushNotificationToken::getToken).toList();
		pushNotificationService.sendPushNotification(tokens, message, message);
		
		
		
	}
	

}

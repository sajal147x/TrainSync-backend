
package com.trainSync.community.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.UserSearchResponseDto;
import com.trainSync.community.model.FriendLink;
import com.trainSync.community.model.FriendRequest;
import com.trainSync.community.repository.FriendLinkRepository;
import com.trainSync.community.repository.FriendRequestRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.service.UserService;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 1:11:48 PM
 */
@Service
public class FriendRequestService {
	
	private final FriendRequestRepository friendRequestRepository;
	private final UserService userService;
	private final FriendLinkRepository friendLinkRepository;
	
	FriendRequestService(FriendRequestRepository friendRequestRepository, UserService userService, FriendLinkRepository friendLinkRepository) { 
		this.friendRequestRepository = friendRequestRepository;
		this.userService = userService;
		this.friendLinkRepository = friendLinkRepository;
	}
	
	
	/**
	 * check if request exists
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	public String friendRequestStatus(UUID fromUserId, UUID toUserId) {
		FriendRequest request = friendRequestRepository.findBySenderDetails_IdAndReceiverDetails_Id(fromUserId,
				toUserId);
		FriendRequest reverseRequest = friendRequestRepository.findBySenderDetails_IdAndReceiverDetails_Id(toUserId,
				fromUserId);

		if (request == null && reverseRequest == null) {
			return FriendRequest.STATUS_NONE;
		}

		if (request != null) {
			return request.getStatus();
		} else {
			return reverseRequest.getStatus();
		}
	}


	/**
	 * 
	 * @param loggedinUserId
	 * @param toUserId
	 */
	public void sendFriendRequest(UUID loggedinUserId, UUID toUserId) {
		FriendRequest existingRequest = friendRequestRepository
				.findBySenderDetails_IdAndReceiverDetails_Id(loggedinUserId, toUserId);
		if (existingRequest != null) {
			// Request already exists
			return;
		}
		UserDetails senderDetails = userService.findById(loggedinUserId);
		UserDetails receiverDetails = userService.findById(toUserId);

		FriendRequest newRequest = FriendRequest.builder()
				.senderDetails(senderDetails)
				.receiverDetails(receiverDetails)
				.status(FriendRequest.STATUS_PENDING)
				.sentAt(java.time.OffsetDateTime.now()).build();

		friendRequestRepository.save(newRequest);
	}


	/**
	 * 
	 * @param loggedinUserId
	 * @return
	 */
	public List<UserSearchResponseDto> getReceivedFriendRequests(UUID loggedinUserId) {
		List<FriendRequest> receivedPendingRequests = friendRequestRepository
				.findByReceiverDetails_IdAndStatus(loggedinUserId, FriendRequest.STATUS_PENDING);
		List<UserSearchResponseDto> resultDtos = new ArrayList<>();
		for (FriendRequest request : receivedPendingRequests) {
			UserDetails sender = request.getSenderDetails();
			UserSearchResponseDto dto = UserSearchResponseDto.builder()
					.requestId(request.getId().toString())
					.userId(sender.getId().toString())
					.name(sender.getName())
					.age(sender.getAge())
					.profilePictureUrl(sender.getProfilePictureUrl())
					.requestStatus(FriendRequest.STATUS_PENDING)
					.build();
			resultDtos.add(dto);
		}
		return resultDtos;
	}

	
	/**
	 * 
	 * @param requestId
	 */
	public void acceptFriendRequest(UUID requestId) {
		FriendRequest request = friendRequestRepository.findById(requestId).get();
		// build a -> b link
		FriendLink toLink = FriendLink.builder().userDetails(request.getSenderDetails())
				.friendDetails(request.getReceiverDetails()).linkedAt(OffsetDateTime.now()).build();
		// build b -> a link
		FriendLink fromLink = FriendLink.builder().userDetails(request.getReceiverDetails())
				.friendDetails(request.getSenderDetails()).linkedAt(OffsetDateTime.now()).build();
		friendLinkRepository.save(toLink);
		friendLinkRepository.save(fromLink);
		// update request status
		request.setStatus(FriendRequest.STATUS_ACCEPTED);
		friendRequestRepository.save(request);
	}

}

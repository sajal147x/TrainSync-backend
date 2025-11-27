
package com.trainSync.community.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.model.FriendRequest;
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
	
	FriendRequestService(FriendRequestRepository friendRequestRepository, UserService userService){ 
		this.friendRequestRepository = friendRequestRepository;
		this.userService = userService;
	}
	
	
	/**
	 * check if request exists
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	public String friendRequestStatus(UUID fromUserId, UUID toUserId) {
		FriendRequest request = friendRequestRepository.findBySenderDetails_IdAndReceiverDetails_Id(fromUserId, toUserId);
		if(request==null) {
			return FriendRequest.STATUS_NONE;
		}
		
		return request.getStatus();
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

}

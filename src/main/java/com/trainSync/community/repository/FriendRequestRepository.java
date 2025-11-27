
package com.trainSync.community.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.community.model.FriendRequest;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 1:09:29 PM
 */
public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {

	FriendRequest findBySenderDetails_IdAndReceiverDetails_Id(UUID senderId, UUID receiverId);

	List<FriendRequest> findByReceiverDetails_IdAndStatus(UUID receiverId, String status);

}

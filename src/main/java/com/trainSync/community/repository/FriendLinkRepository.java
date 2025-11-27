
package com.trainSync.community.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.community.model.FriendLink;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 2:42:10 PM
 */
public interface FriendLinkRepository  extends JpaRepository<FriendLink, UUID>{

	List<FriendLink> findByUserDetails_Id(UUID userId);

}

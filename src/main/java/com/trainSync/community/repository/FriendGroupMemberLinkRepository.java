
package com.trainSync.community.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.community.model.FriendGroupMemberLink;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 1:06:07 PM
 */
public interface FriendGroupMemberLinkRepository extends JpaRepository<FriendGroupMemberLink, UUID> {

	List<FriendGroupMemberLink> findByGroupMemberId(UUID userId);

}

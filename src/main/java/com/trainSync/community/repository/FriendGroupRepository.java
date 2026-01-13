
package com.trainSync.community.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.community.model.FriendGroup;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 1:02:14 PM
 */
public interface FriendGroupRepository extends JpaRepository<FriendGroup, UUID>  {

}

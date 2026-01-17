package com.trainSync.community.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.community.model.GroupMessage;

/**
 * @author sajalgupta
 * 
 */

public interface GroupMessageRepository extends JpaRepository<GroupMessage, UUID>{

	List<GroupMessage> findByGroupIdOrderBySentAtAsc(UUID groupId);

}

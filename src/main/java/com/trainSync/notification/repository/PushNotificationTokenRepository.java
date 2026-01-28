
package com.trainSync.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.notification.model.PushNotificationToken;

/**
 * Author: Sajal Gupta
 * Created on: Jan 20, 2026 10:24:01 AM
 */
public interface PushNotificationTokenRepository extends JpaRepository<PushNotificationToken, UUID> {
	
	public List<PushNotificationToken> findAllByUser_IdIn(List<UUID> userIds);

	public boolean existsByUser_IdAndToken(UUID userId, String pushNotificationToken);

	public PushNotificationToken findByUser_IdAndToken(UUID userId, String pushNotificationToken);

}

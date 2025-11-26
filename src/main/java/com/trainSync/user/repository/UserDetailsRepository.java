package com.trainSync.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.user.model.UserDetails;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: Nov 7, 2025
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
}

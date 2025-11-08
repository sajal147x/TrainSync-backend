package com.trainSync.TrainSync.repository;

import com.trainSync.TrainSync.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: Nov 7, 2025
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
}

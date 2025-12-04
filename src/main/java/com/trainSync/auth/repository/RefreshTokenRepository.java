
package com.trainSync.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.auth.model.RefreshToken;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 10:27:50 AM
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
}


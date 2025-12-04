
package com.trainSync.auth.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.auth.model.RefreshToken;
import com.trainSync.auth.repository.RefreshTokenRepository;
import com.trainSync.user.model.UserDetails;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 10:28:10 AM
 */

@Service
public class RefreshTokenService {

    // 30 days
    private static final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 30;

    private final RefreshTokenRepository repo;
    
    public RefreshTokenService(RefreshTokenRepository repo) {
		this.repo = repo;
	}

    public RefreshToken createRefreshToken(UserDetails user) {

        
        RefreshToken refreshToken = RefreshToken.builder()
				.userDetails(user)
				.token(UUID.randomUUID().toString())
				.expiredDate(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
				.build();

        return repo.save(refreshToken);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiredDate().before(new Date());
    }

    public void revokeToken(RefreshToken token) {
        token.setRevoked(true);
        repo.save(token);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repo.findByToken(token);
    }
}
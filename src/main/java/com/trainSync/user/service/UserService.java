package com.trainSync.user.service;

import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Date: Nov 7, 2025
 */
@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    /**
     * Save user details locally after successful Supabase signup
     */
    public UserDetails createUser(UUID id, String email) {
        UserDetails user = new UserDetails();
        user.setId(id);
        user.setName("");
        user.setEmail(email);
        return userDetailsRepository.save(user);
    }

	public boolean existsByUsername(String username) {
		return userDetailsRepository.existsByUsername(username);
	}

	public UserDetails saveUser(UserDetails user) {
		userDetailsRepository.save(user);
		return user;
	}

	public UserDetails findByUsername(String username) {
		return userDetailsRepository.findByUsername(username);
		
	}
	
	public UserDetails findById(UUID userId) {
		Optional<UserDetails> userOpt = userDetailsRepository.findById(userId);
		return userOpt.orElse(null);
	}

	public void deleteAccount(UUID userId) {
		UserDetails user = userDetailsRepository.findById(userId).get();
		user.setAccountStatus("INACTIVE");
		userDetailsRepository.save(user);
	}
	
}

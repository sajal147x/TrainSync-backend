package com.trainSync.TrainSync.service;

import com.trainSync.TrainSync.model.UserDetails;
import com.trainSync.TrainSync.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

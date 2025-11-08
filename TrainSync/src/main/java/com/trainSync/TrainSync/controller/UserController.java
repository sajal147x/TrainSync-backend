package com.trainSync.TrainSync.controller;

import com.trainSync.TrainSync.model.UserDetails;
import com.trainSync.TrainSync.repository.UserDetailsRepository;
import com.trainSync.TrainSync.service.JwtService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Sajal Gupta
 * Date: Nov 7, 2025
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<UserDetails> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
            System.out.println(userIdStr);
            UUID userId = UUID.fromString(userIdStr);  
            
            UserDetails user = userDetailsRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).build(); // Unauthorized if JWT invalid
        }
    }
    
    
}

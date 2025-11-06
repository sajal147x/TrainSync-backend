package com.trainSync.TrainSync.controller;

import com.trainSync.TrainSync.dto.SignUpRequest;
import org.springframework.web.bind.annotation.*;
/**
 * Author: Sajal Gupta
 * Date: 2025-11-06
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequest request) {
        // Print email and password to console
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());

        return "Received signup request for " + request.getEmail();
    }
}

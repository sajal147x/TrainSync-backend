package com.trainSync.user.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.auth.RefreshRequest;
import com.trainSync.auth.model.RefreshToken;
import com.trainSync.auth.service.RefreshTokenService;
import com.trainSync.notification.repository.PushNotificationTokenRepository;
import com.trainSync.notification.service.PushNotificationService;
import com.trainSync.service.JwtService;
import com.trainSync.user.dto.LogoutRequest;
import com.trainSync.user.dto.SignUpRequest;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.service.UserService;

/**
 * Author: Sajal Gupta 
 * Date: 2025-11-06 
 * API Calls related to auth (sign up, sign in etc.)
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${default.email}")
    private String defaultEmail;

    @Value("${default.password}")
    private String defaultPass;

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final PushNotificationService pushNotificationService;
    
    public AuthController(UserService userService, JwtService jwtService, 
                          PasswordEncoder passwordEncoder,
                          RefreshTokenService refreshTokenService, PushNotificationService pushNotificationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.pushNotificationService=pushNotificationService;
    }

    /**
     * SIGNUP
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Already Exists");
        }

        UserDetails user = UserDetails.builder()
                .username(request.getUsername())
                .age(request.getAge())
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userService.saveUser(user);

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(Map.of(
                "userId", user.getId().toString(),
                "username", user.getUsername(),
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }

    /**
     * SIGNIN / LOGIN
     */
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SignUpRequest request) {

        UserDetails user = userService.findByUsername(request.getUsername());
        if (user == null || "INACTIVE".equals(user.getAccountStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(Map.of(
                "userId", user.getId().toString(),
                "username", user.getUsername(),
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }

    /**
     * REFRESH ACCESS TOKEN
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest request) {
    	
    	System.out.println("REFRESH CALLED");

        RefreshToken savedToken = refreshTokenService.findByToken(request.refreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (savedToken.isRevoked()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token revoked");
        }

        if (refreshTokenService.isExpired(savedToken)) {
            refreshTokenService.revokeToken(savedToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        // Generate new access token
        UserDetails user = userService.findById(savedToken.getUserDetails().getId());

        String newAccessToken = jwtService.generateToken(user.getId().toString(), user.getUsername());

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", savedToken.getToken()  // reuse same refresh token until expiry
        ));
    }

    /**
     * LOGOUT (REVOKE REFRESH TOKEN)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader, @RequestBody LogoutRequest request) {
    	
    	String token = authHeader.replace("Bearer ", "");
		UUID userId = UUID.fromString(jwtService.extractUserId(token)); 

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.revokeToken(refreshToken);
        
        pushNotificationService.removePushNotifToken(userId, request.getPushNotificationToken());
        

        return ResponseEntity.ok("Logged out successfully");
    }
    
    /**
     * LOGOUT (REVOKE REFRESH TOKEN)
     */
    @PostMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String authHeader, @RequestBody LogoutRequest request) {
    	
    	String token = authHeader.replace("Bearer ", "");
		UUID userId = UUID.fromString(jwtService.extractUserId(token)); 

		userService.deleteAccount(userId);
        
        

        return ResponseEntity.ok("Account Deleted Succesfully");
    }
    
    
    
    
}

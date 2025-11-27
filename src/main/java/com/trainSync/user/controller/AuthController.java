package com.trainSync.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * SIGNUP CODE
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
		if (userService.existsByUsername(request.getUsername())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Already Exists");
	    }

	    UserDetails user = new UserDetails();
	    user.setUsername(request.getUsername());
	    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
	    user.setName(request.getName());
	    user.setAge(request.getAge());

	    user = userService.saveUser(user);

	    String token = jwtService.generateToken(
	            user.getId().toString(),
	            user.getUsername()
	    );

	    return ResponseEntity.ok(Map.of(
	            "userId", user.getId().toString(),
	            "username", user.getUsername(),
	            "accessToken", token
	    ));
	}


	/**
	 * SIGN IN CODE
	 * @param request
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody SignUpRequest request) {

		UserDetails user = userService.findByUsername(request.getUsername());
		if (user == null) {
			System.out.println("HERE");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Found");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}

		String token = jwtService.generateToken(user.getId().toString(), user.getUsername());

		return ResponseEntity
				.ok(Map.of("userId", user.getId().toString(), "username", user.getUsername(), "accessToken", token));
	}

}

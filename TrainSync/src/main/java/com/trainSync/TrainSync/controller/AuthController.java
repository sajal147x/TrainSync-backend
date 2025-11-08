package com.trainSync.TrainSync.controller;

import com.trainSync.TrainSync.dto.SignUpRequest;
import com.trainSync.TrainSync.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import org.json.JSONObject;

/**
 * Author: Sajal Gupta 
 * Date: 2025-11-06 
 * API Calls related to auth (sign up, sign in etc.)
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Value("${supabase.url}")
	private String supabaseUrl;

	@Value("${supabase.key}")
	private String supabaseKey;
	
	@Value("${default.email}")
	private String defaultEmail;
	
	@Value("${default.password}")
	private String defaultPass;
	
	@Autowired
	private UserService userService;

	/**
	 * SIGNUP CODE
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
		try {
			// Prepare request body
			JSONObject body = new JSONObject();
			body.put("email", request.getEmail());
			body.put("password", request.getPassword());

			// Prepare HTTP headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("apikey", supabaseKey);
			headers.set("Authorization", "Bearer " + supabaseKey);

			HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

			// Send POST to Supabase Auth API
			RestTemplate restTemplate = new RestTemplate();
			String url = supabaseUrl + "/auth/v1/admin/users";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			// Create entry in user table
			if (response.getStatusCode().is2xxSuccessful()) {
				JSONObject jsonResponse = new JSONObject(response.getBody());
				UUID supabaseId = UUID.fromString(jsonResponse.getString("id"));
				userService.createUser(supabaseId, request.getEmail());
			}

			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed");
		}
	}
	
	/**
	 * SIGN IN CODE
	 * @param request
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<String> login(@RequestBody SignUpRequest request) {
	    try {
	        // Prepare JSON body
	        JSONObject body = new JSONObject();
	        
	        body.put("email", request.getEmail());
	        body.put("password", request.getPassword());
	        if(request.getEmail() == null || request.getEmail().isBlank()) {
	        	body.put("email", defaultEmail);
	        	body.put("password", defaultPass);
	        }

	        // Headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("apikey", supabaseKey);

	        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

	        // Call Supabase Auth login
	        String url = supabaseUrl + "/auth/v1/token?grant_type=password";
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

	        return ResponseEntity.ok(response.getBody());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
	    }
	}

}

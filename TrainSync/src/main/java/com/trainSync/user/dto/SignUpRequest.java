package com.trainSync.user.dto;
/**
 * Author: Sajal Gupta
 * Date: 2025-11-06
 */

public class SignUpRequest {
    private String email;
    private String password;

    // Constructors
    public SignUpRequest() {}

    public SignUpRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

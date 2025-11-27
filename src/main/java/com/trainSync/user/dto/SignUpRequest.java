package com.trainSync.user.dto;
/**
 * Author: Sajal Gupta
 * Date: 2025-11-06
 */

public class SignUpRequest {
	private String name;
	private int age;
    private String username;

    private String password;

    // Constructors
    public SignUpRequest() {}

    public SignUpRequest(String username, String password) {
        this.setUsername(username);
        this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

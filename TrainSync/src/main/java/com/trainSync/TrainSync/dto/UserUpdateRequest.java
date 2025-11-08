
package com.trainSync.TrainSync.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Sajal Gupta
 * Date: Nov 8, 2025
 */

public class UserUpdateRequest {
	
	private String name;
	
	private Integer age;
	
	private String profilePictureBase64; 

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the profilePictureBase64
	 */
	public String getProfilePictureBase64() {
		return profilePictureBase64;
	}

	/**
	 * @param profilePictureBase64 the profilePictureBase64 to set
	 */
	public void setProfilePictureBase64(String profilePictureBase64) {
		this.profilePictureBase64 = profilePictureBase64;
	}

 

}

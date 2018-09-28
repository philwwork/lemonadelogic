package com.game.main.utility;

public class UserInfo {


	private String username;
	private String email;
	private String token;
	private String message;
	
	
	public UserInfo(String username, String email) {
		this.username=username;
		this.email=email;

		
	}
	

	public UserInfo(String username, String email, String token) {
		this.username=username;
		this.email=email;
		this.token=token;
	}
	
	
	// ONLY use this for errors.
	public UserInfo(String message) {
		this.message=message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return username;
	}
	public void setName(String userName) {
		this.username = userName;
	}

	
	
}

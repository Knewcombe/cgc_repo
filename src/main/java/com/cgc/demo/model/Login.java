package com.cgc.demo.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Login {
	
	@NotEmpty(message = "Please enter a username")
	private String username;
	
	@NotEmpty(message = "please enter a password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}

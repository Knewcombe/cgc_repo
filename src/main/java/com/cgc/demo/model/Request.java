package com.cgc.demo.model;

/**
 * Request.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * Request
 * 
 * Request object for business and association sign up form.
 * This just holds basic information that will be email to admin account.
 *
 */

public class Request {
	
	private String full_name;
	private String orginization_name;
	private String phone_number;
	private String email;
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getOrginization_name() {
		return orginization_name;
	}
	public void setOrginization_name(String orginization_name) {
		this.orginization_name = orginization_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

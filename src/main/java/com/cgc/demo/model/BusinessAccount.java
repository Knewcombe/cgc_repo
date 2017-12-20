package com.cgc.demo.model;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


public class BusinessAccount {
	
	private int business_account_id;
	
	@NotEmpty(message = "Please enter a username")
	private String username;
	
	@NotEmpty(message = "Please enter a password")
	private String password;
	
	@Valid
	private BusinessProfile businessProfile;

	public int getBusiness_account_id() {
		return business_account_id;
	}

	public void setBusiness_account_id(int business_account_id) {
		this.business_account_id = business_account_id;
	}

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

	public BusinessProfile getBusinessProfile() {
		return businessProfile;
	}

	public void setBusinessProfile(BusinessProfile businessProfile) {
		this.businessProfile = businessProfile;
	}
	
	
}

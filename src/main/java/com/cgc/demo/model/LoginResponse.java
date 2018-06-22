package com.cgc.demo.model;

/**
 * LoginResponse.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * LoginResponse
 * 
 * Response object for Mobile Application to hold BusinessAccount object and token string.
 *
 */

public class LoginResponse{
    public String token;
    
    public BusinessAccount business;
    
    /**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String token, BusinessAccount business
	 * Constructor for LoginResponse.
	 */
    public LoginResponse(final String token, BusinessAccount business) {
        this.token = token;
        this.business = business;
    }
}

package com.cgc.demo.model;
/**
 * Json.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * Json
 * 
 * Json object to for response to Mobile application.
 * Will only hold a string.
 *
 */
public class Json {
	String status;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String status
	 * Constructor for Json object.
	 */
	public Json(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

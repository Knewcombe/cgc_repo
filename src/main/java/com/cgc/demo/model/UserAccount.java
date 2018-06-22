package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * UserAccount.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * UserAccount
 * 
 * Object used to store information for user account.
 *
 */

public class UserAccount {
	
	private int user_account_id;
	@NotEmpty(message = "Please enter a username")
	private String username;
	@NotEmpty(message = "Please enter a password")
	private String password;
	@Valid
	private UserProfile userProfile;
	
	private List<Questions> questions = new ArrayList<Questions>();
	
	public int getUser_account_id() {
		return user_account_id;
	}
	public void setUser_account_id(int user_account_id) {
		this.user_account_id = user_account_id;
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
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public Questions getQuestion(int index) {
		return questions.get(index);
	}
	
	public List<Questions> getQuestions() {
		return questions;
	}
	
	public void setQuestion(Questions questions) {
		this.questions.add(questions);
	}
	
	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}
	
}

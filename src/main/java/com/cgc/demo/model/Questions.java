package com.cgc.demo.model;

/**
 * Questions.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * Questions
 * 
 * Object will hold information for User questions and answer.
 *
 */

public class Questions {
	
	private int user_question_id;
	
	private int user_account_id;
	
	private String question;
	
	private String answer;
	
	
	public int getUser_question_id() {
		return user_question_id;
	}
	public void setUser_question_id(int user_question_id) {
		this.user_question_id = user_question_id;
	}
	public int getUser_account_id() {
		return user_account_id;
	}
	public void setUser_account_id(int user_account_id) {
		this.user_account_id = user_account_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	

}

package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.Questions;

public interface UserQuestionDAO {
	
	public void setQuestion(Questions question);
	
	public List<Questions> getUserQuestion(int user_account_id);
	
	public Questions getRandomUserQuestion(int user_account_id);
	
	public Questions getAnswer(int user_question_id);
	

}

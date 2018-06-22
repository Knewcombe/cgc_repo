package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.Questions;

/**
 * User Question DAO will get all information needed for User Question model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class UserQuestionDAOImpl implements UserQuestionDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Questions question
	 * @return void
	 * Set question for user account
	 */
	public void setQuestion(Questions question){
		jdbcTemplate.update(
			    "INSERT INTO user_questions (user_account_id, question, answer) VALUES (?, ?, ?)", new Object[]{
			    		question.getUser_account_id(), 
			    		question.getQuestion(), 
			    		question.getAnswer()
			    });
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_account_id
	 * @return List<Questions>
	 * Get all questions for user account
	 */
	@SuppressWarnings("unchecked")
	public List<Questions> getUserQuestion(int user_account_id){
		List<Questions> question = null;
		
		question = this.jdbcTemplate.query("SELECT * FROM user_questions WHERE user_account_id = ?",
				new Object[] { user_account_id }, new UserQuestionMapper());
		return question.size() > 0 ? question : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_account_id
	 * @return Questions
	 * Get a random question for user account.
	 */
	@SuppressWarnings("unchecked")
	public Questions getRandomUserQuestion(int user_account_id){
		List<Questions> question = null;
		
		question = this.jdbcTemplate.query("SELECT user_question_id, question FROM user_questions WHERE user_account_id = ? ORDER BY RAND()",
				new Object[] { user_account_id }, new QuestionMapper());
		//System.out.println(question.size());
		return question.size() > 0 ? question.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_account_id
	 * @return Questions
	 * Getting the answer for user question.
	 */
	@SuppressWarnings("unchecked")
	public Questions getAnswer(int user_question_id){
		List<Questions> question = null;
		question = this.jdbcTemplate.query("SELECT user_account_id, answer FROM user_questions WHERE user_question_id = ?",
				new Object[] { user_question_id }, new AnswerMapper());
		System.out.println(question.get(0).getAnswer());
		return question.size() > 0 ? question.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Questions
	 * Mapper for user question
	 */
	public class UserQuestionMapper implements RowMapper {

		public Questions mapRow(ResultSet rs, int arg1) throws SQLException {
			Questions question = new Questions();
			question.setUser_question_id(rs.getInt("user_question_id"));
			question.setUser_account_id(rs.getInt("user_account_id"));
			question.setQuestion(rs.getString("question"));
			question.setAnswer(rs.getString("answer"));
			return question;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Questions
	 * Mapper for question
	 */
	public class QuestionMapper implements RowMapper {

		public Questions mapRow(ResultSet rs, int arg1) throws SQLException {
			Questions question = new Questions();
			question.setUser_question_id(rs.getInt("user_question_id"));
			question.setQuestion(rs.getString("question"));
			return question;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Questions
	 * Mapper for answer
	 */
	public class AnswerMapper implements RowMapper {

		public Questions mapRow(ResultSet rs, int arg1) throws SQLException {
			Questions question = new Questions();
			question.setUser_account_id(rs.getInt("user_account_id"));
			question.setAnswer(rs.getString("answer"));
			return question;
		}
	}

}

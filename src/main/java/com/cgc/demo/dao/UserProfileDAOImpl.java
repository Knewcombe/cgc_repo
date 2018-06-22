package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.cgc.demo.model.Search;
import com.cgc.demo.model.UserProfile;
import com.mysql.jdbc.PreparedStatement;

/**
 * User Profile DAO will get all information needed for User Profile model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class UserProfileDAOImpl implements UserProfileDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index
	 * @return UserProfile
	 * Get user profile for user account
	 */
	@SuppressWarnings("unchecked")
	public UserProfile getUserProfile(int index) {
		List<UserProfile> userProfile = null;

		userProfile = this.jdbcTemplate.query("SELECT * FROM user_profile WHERE user_account_id = ?",
				new Object[] { index },new UserProfileMapper());

		return userProfile.size() > 0 ? userProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return boolean
	 * Check to see if there is a user profile for user account.
	 */
	public boolean checkForUser(int user_profile_id){

		Integer isUser = this.jdbcTemplate.queryForObject("SELECT count(*) FROM user_profile WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, Integer.class);
		
		return isUser != null && isUser > 0;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String cardId
	 * @return UserProfile
	 * Get the user id based on matching card id.
	 */
	@SuppressWarnings("unchecked")
	public UserProfile getUserId(String cardId){
		
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT user_profile_id FROM user_profile WHERE card_id = ?", new Object[] {cardId}, new UserIdMapper());
		
		return userProfile.size() > 0 ? userProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return UserProfile
	 * Get the username based on user profile id
	 */
	@SuppressWarnings("unchecked")
	public UserProfile getUserName(int user_profile_id){
		
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT first_name, last_name FROM user_profile WHERE user_profile_id = ?", new Object[] {user_profile_id}, new UserNameMapper());
		return userProfile.size() > 0 ? userProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return UserProfile
	 * Get contact info for UserProfile
	 */
	@SuppressWarnings("unchecked")
	public UserProfile getContactInfo(int user_profile_id){
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT province_code, city, address, postal_code, phone, email FROM user_profile WHERE user_profile_id = ?", new Object[] {user_profile_id}, new UserContactMapper());
		
		return userProfile.size() > 0 ? userProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserProfile userProfile
	 * @return int
	 * Setting the user profile for user account and return the unique identifier
	 */
	public int registerUser(final UserProfile userProfile) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
						throws SQLException {
					PreparedStatement ps = (PreparedStatement) connection.prepareStatement("INSERT INTO user_profile (user_account_id, card_id, first_name, last_name, date_of_birth, gender, province_code, city, address, postal_code, phone, email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", new String[] {"id"});
			            ps.setInt(1, userProfile.getUser_account_id());
			            ps.setString(2, (!userProfile.getCard_id().isEmpty() && userProfile.getCard_id() != null) ? userProfile.getCard_id() : null);
			            ps.setString(3, userProfile.getFirst_name());
			            ps.setString(4, userProfile.getLast_name());
			            ps.setString(5, userProfile.getDate_of_birth());
			            ps.setString(6, userProfile.getGender());
			            ps.setString(7, userProfile.getProvince_code());
			            ps.setString(8, userProfile.getCity());
			            ps.setString(9, userProfile.getAddress());
			            ps.setString(10, userProfile.getPostal_code());
			            ps.setString(11, userProfile.getPhone());
			            ps.setString(12, userProfile.getEmail());
			            return ps;
					}
				}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Search search
	 * @return List<UserProfile>
	 * Search for user profile based on search object.
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfile> searchForUser(Search search){
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT * FROM user_profile WHERE phone = ?",
				new Object[] { search.getPhone() }, new UserProfileMapper());
		
		return userProfile.size() > 0 ? userProfile : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String search
	 * @return List<UserProfile>
	 * Find user based on search string.
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfile> findUser(String search){
		
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT *, MATCH ( `first_name`, `last_name`, `card_id`, `province_code`, `city`, `address`, `phone`, `email`, `postal_code`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) as `rel` FROM `user_profile` WHERE MATCH ( `first_name`, `last_name`, `card_id`, `province_code`, `city`, `address`, `phone`, `email`, `postal_code`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) ORDER BY `rel` DESC",
				new Object[] { search, search }, new UserProfileMapper());
				
		
		return userProfile.size() > 0 ? userProfile : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return UserProfile
	 * Get the card id for user profile
	 */
	@SuppressWarnings("unchecked")
	public UserProfile getCardId(int user_profile_id){
		List<UserProfile> userProfile = null;
		
		userProfile = this.jdbcTemplate.query("SELECT card_id FROM user_profile WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, new CardIdMapper());
		System.out.println(userProfile.size());
		return userProfile.size() > 0 ? userProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserProfile
	 * Maper for user profile name
	 */
	public class UserNameMapper implements RowMapper {

		public UserProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			UserProfile userProfile = new UserProfile();
			userProfile.setFirst_name(rs.getString("first_name"));
			userProfile.setLast_name(rs.getString("last_name"));
			return userProfile;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String email
	 * @return int
	 * Checking to make sure email us unique
	 */
	public int checkEmail(String email){
		int userAccountId = 0;
		try{
			userAccountId = this.jdbcTemplate.queryForObject("SELECT user_account_id FROM user_profile WHERE email = ?",
					new Object[] { email }, Integer.class);
		}catch(Exception e){
			return 0;
		}
		
		return userAccountId > 0 ? userAccountId : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String email
	 * @return boolean
	 * Checking to make sure email is valid
	 */
	public boolean emailValid(String email){
		int count = jdbcTemplate.queryForObject("SELECT count(*) from user_profile where email = ?",
				new Object[] { email }, Integer.class);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserProfile
	 * Mapper for user profile.
	 */
	public class UserProfileMapper implements RowMapper {

		public UserProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			UserProfile userProfile = new UserProfile();
			userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
			userProfile.setUser_account_id(rs.getInt("user_account_id"));
			userProfile.setCard_id(rs.getString("card_id"));
			userProfile.setFirst_name(rs.getString("first_name"));
			userProfile.setLast_name(rs.getString("last_name"));
			userProfile.setDate_of_birth(rs.getString("date_of_birth"));
			userProfile.setGender(rs.getString("gender"));
			userProfile.setProvince_code(rs.getString("province_code"));
			userProfile.setCity(rs.getString("city"));
			userProfile.setAddress(rs.getString("address"));
			userProfile.setPostal_code(rs.getString("postal_code"));
			userProfile.setPhone(rs.getString("phone"));
			userProfile.setEmail(rs.getString("email"));
			return userProfile;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserProfile
	 * Mapper for user profile contact information.
	 */
	public class UserContactMapper implements RowMapper {

		public UserProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			UserProfile userProfile = new UserProfile();
			userProfile.setProvince_code(rs.getString("province_code"));
			userProfile.setCity(rs.getString("city"));
			userProfile.setAddress(rs.getString("address"));
			userProfile.setPostal_code(rs.getString("postal_code"));
			userProfile.setPhone(rs.getString("phone"));
			userProfile.setEmail(rs.getString("email"));
			return userProfile;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserProfile
	 * Mapper for user profile id
	 */
	public class UserIdMapper implements RowMapper {

		public UserProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			UserProfile userProfile = new UserProfile();
			userProfile.setUser_profile_id(rs.getInt("user_profile_id"));
			return userProfile;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserProfile
	 * Mapper for user profile card id
	 */
	public class CardIdMapper implements RowMapper {

		public UserProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			UserProfile userProfile = new UserProfile();
			userProfile.setCard_id((rs.getString("card_id")));
			return userProfile;
		}
	}

}

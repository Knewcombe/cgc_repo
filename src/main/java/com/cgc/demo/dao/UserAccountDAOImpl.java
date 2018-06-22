package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.cgc.demo.model.Login;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.mysql.jdbc.PreparedStatement;

/**
 * User Account DAO will get all information needed for User Account model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class UserAccountDAOImpl implements UserAccountDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Login login
	 * @return UserAccount
	 * Getting the user account where username matches.
	 */
	@SuppressWarnings("unchecked")
	public UserAccount login(Login login) {

		List<UserAccount> userAccount = null;
		try {
			userAccount = this.jdbcTemplate.query("SELECT * FROM user_account WHERE username = ?",
					new Object[] { login.getUsername() }, new UserAccountMapper());

			return userAccount.size() > 0 ? userAccount.get(0) : null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserAccount userAccount
	 * @return void
	 * Change the password of user account
	 */
	public void changePassword(UserAccount userAccount){
		this.jdbcTemplate.update(
                "UPDATE user_account SET password = ? WHERE user_account_id = ?", 
                userAccount.getPassword(), userAccount.getUser_account_id());
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return String
	 * Getting the password where username matches
	 */
	public String getPassword(String username) {
		String password = "";
		try {
			password = this.jdbcTemplate.queryForObject("SELECT password FROM user_account WHERE username = ?",
					new Object[] { username }, String.class);
			return password;
		} catch (EmptyResultDataAccessException e) {
			return "";
		}

	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserAccount userAccount
	 * @return int
	 * Setting the user in database
	 */
	public int registerUser(final UserAccount userAccount) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO user_account (username, password) VALUES (?,?)", new String[] { "id" });
				ps.setString(1, userAccount.getUsername());
				ps.setString(2, userAccount.getPassword());
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return boolean
	 * Checking username to make sure its unique.
	 */
	public boolean isValid(String username) {
		int count = jdbcTemplate.queryForObject("SELECT count(*) from user_account where username = ?",
				new Object[] { username }, Integer.class);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return boolean
	 * Checking username to make sure its unique.
	 */
	public boolean checkUsername(String username){
		int count = jdbcTemplate.queryForObject("SELECT count(*) from user_account where username = ?",
				new Object[] { username }, Integer.class);
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
	 * @return UserAccount
	 * Mapper for user account
	 */
	public class UserAccountMapper implements RowMapper {

		public UserAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			UserAccount userAccount = new UserAccount();
			userAccount.setUser_account_id(rs.getInt("user_account_id"));
			userAccount.setUsername(rs.getString("username"));
			return userAccount;
		}
	}
	

}

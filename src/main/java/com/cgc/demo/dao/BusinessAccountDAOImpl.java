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

import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.Login;
import com.mysql.jdbc.PreparedStatement;

/**
 * Business Account DAO will get all information needed for BusinessAccount model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class BusinessAccountDAOImpl implements BusinessAccountDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Login
	 * @return BusinessAccount
	 * Logging into Business account by checking username.
	 */
	@SuppressWarnings("unchecked")
	public BusinessAccount login(Login login) {
		// TODO Auto-generated method stub
		List<BusinessAccount> businessAccount = null;
		try {
			businessAccount = this.jdbcTemplate.query("SELECT * FROM business_account WHERE username = ?",
					new Object[] { login.getUsername() }, new BusinessAccountMapper());

			return businessAccount.size() > 0 ? businessAccount.get(0) : null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return String
	 * Getting the password with matching username.
	 */
	public String getPassword(String username) {
		String password = "";
		try {
			password = this.jdbcTemplate.queryForObject("SELECT password FROM business_account WHERE username = ?",
					new Object[] { username }, String.class);
			return password;
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param BusinessAccount businessAccount
	 * @return int
	 * Register business account and will return the unique identifier of the businessAccount
	 */
	public int registerUser(final BusinessAccount businessAccount) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO business_account (username, password) VALUES (?,?)", new String[] { "id" });
				ps.setString(1, businessAccount.getUsername());
				ps.setString(2, businessAccount.getPassword());
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
	 * Checking usernames for businesss account. If matching username will return false.
	 */
	public boolean isValid(String username) {

		int count = jdbcTemplate.queryForObject("SELECT count(*) from business_account where username = ?",
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
	 * @return BusinessAccount
	 * Mapper for business Account
	 */
	public class BusinessAccountMapper implements RowMapper {

		public BusinessAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			BusinessAccount businessAccount = new BusinessAccount();
			businessAccount.setBusiness_account_id(rs.getInt("business_account_id"));
			businessAccount.setUsername(rs.getString("username"));
			return businessAccount;
		}
	}

}

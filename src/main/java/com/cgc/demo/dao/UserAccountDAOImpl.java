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

public class UserAccountDAOImpl implements UserAccountDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public UserAccount login(Login login) {
		// TODO Auto-generated method stub

		List<UserAccount> userAccount = null;
		try {
			userAccount = this.jdbcTemplate.query("SELECT * FROM user_account WHERE username = ?",
					new Object[] { login.getUsername() }, new UserAccountMapper());

			return userAccount.size() > 0 ? userAccount.get(0) : null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

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

	public boolean isValid(String username) {
		int count = jdbcTemplate.queryForObject("SELECT count(*) from user_account where username = ?",
				new Object[] { username }, Integer.class);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	

	public class UserAccountMapper implements RowMapper {

		public UserAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			UserAccount userAccount = new UserAccount();
			userAccount.setUser_account_id(rs.getInt("user_account_id"));
			userAccount.setUsername(rs.getString("username"));
			return userAccount;
		}
	}

}

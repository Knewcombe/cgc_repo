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

public class BusinessAccountDAOImpl implements BusinessAccountDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

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

	public boolean isValid(String username) {

		int count = jdbcTemplate.queryForObject("SELECT count(*) from business_account where username = ?",
				new Object[] { username }, Integer.class);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	public class BusinessAccountMapper implements RowMapper {

		public BusinessAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			BusinessAccount businessAccount = new BusinessAccount();
			businessAccount.setBusiness_account_id(rs.getInt("business_account_id"));
			businessAccount.setUsername(rs.getString("username"));
			return businessAccount;
		}
	}

}

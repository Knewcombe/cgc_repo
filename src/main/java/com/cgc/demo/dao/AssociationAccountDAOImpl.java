package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.Login;

/**
 * Association Account DAO will get all information needed for AssociationAccount model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class AssociationAccountDAOImpl implements AssociationAccountDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return String
	 * Method will return the password from the matching password.
	 */
	public String getPassword(String username){
		String password = "";
		try {
			password = this.jdbcTemplate.queryForObject("SELECT password FROM association_account WHERE username = ?",
					new Object[] { username }, String.class);
			return password;
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String username
	 * @return booelan
	 * Checking usernames for Association accounts in database.
	 * If a username is found it will return false.
	 */
	public boolean checkUsername(String username){
		int count = jdbcTemplate.queryForObject("SELECT count(*) from association_account where username = ?",
				new Object[] { username }, Integer.class);
		System.err.println("Checking username for association: " + count);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Login
	 * @return AssociationAccount
	 * Getting Association account model from database with matching username.
	 */
	@SuppressWarnings("unchecked")
	public AssociationAccount login(Login login){
		List<AssociationAccount> associationAccount = null;
		
		associationAccount = this.jdbcTemplate.query("SELECT * FROM association_account WHERE username = ?",
				new Object[] { login.getUsername() }, new AssociationAccountMapper());
		
		return associationAccount.size() > 0 ? associationAccount.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Login
	 * @return ResultSet rs, int arg1
	 * Mapper for AssociationAccount model.
	 */
	public class AssociationAccountMapper implements RowMapper {

		public AssociationAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			AssociationAccount associationAccount = new AssociationAccount();
			associationAccount.setAssociation_id(rs.getInt("association_account_id"));
			associationAccount.setUsername(rs.getString("username"));
			return associationAccount;
		}
	}

}

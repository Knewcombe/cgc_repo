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

public class AssociationAccountDAOImpl implements AssociationAccountDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
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
	
	@SuppressWarnings("unchecked")
	public AssociationAccount login(Login login){
		List<AssociationAccount> associationAccount = null;
		
		associationAccount = this.jdbcTemplate.query("SELECT * FROM association_account WHERE username = ?",
				new Object[] { login.getUsername() }, new AssociationAccountMapper());
		
		return associationAccount.size() > 0 ? associationAccount.get(0) : null;
	}
	
	public class AssociationAccountMapper implements RowMapper {

		public AssociationAccount mapRow(ResultSet rs, int arg1) throws SQLException {
			AssociationAccount associationAccount = new AssociationAccount();
			associationAccount.setAssociation_id(rs.getInt("association_account_id"));
			associationAccount.setUsername(rs.getString("username"));
			return associationAccount;
		}
	}

}

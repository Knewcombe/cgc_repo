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

import com.cgc.demo.model.Transaction;
import com.mysql.jdbc.PreparedStatement;

public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<Transaction> getUserTransaction(int user_profile_id) {
		// TODO Auto-generated method stub
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction : null;
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getBusinessTransaction(int business_profile_id) {
		// TODO Auto-generated method stub
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction : null;
	}
	
	@SuppressWarnings("unchecked")
	public Transaction getOnetransaction(int transaction_id){
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE transaction_id = ?",
				new Object[] { transaction_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction.get(0) : null;
	}

	public int setTransaction(final Transaction transaction) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		System.out.println("Time and date: " + transaction.getDate_of_purhase());

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO transaction (user_profile_id, business_profile_id, total, date_of_purchase) VALUES (?,?,?,NOW())", new String[] { "id" });
				ps.setInt(1, transaction.getUser_profile_id());
				ps.setInt(2, transaction.getBusiness_profile_id());
				ps.setDouble(3, transaction.getTotal());
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public double getBusinessTotal(int business_profile_id) {
		double total = 0.0;
		total = jdbcTemplate.queryForObject("SELECT SUM(precent_total) FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, Double.class);
		return total;
	}

	public double getUserTotal(int user_profile_id) {
		double total = 0.0;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(precent_total) FROM transaction WHERE user_profile_id = ?",
					new Object[] { user_profile_id }, Double.class);
			System.out.println("Total found: "+ total);
			return total;
		}catch(Exception e){
			//System.err.println(e);
			return 0.0;
		}
	}
	
	public void updateTransaction(int transaction_id, double amount, double percent_amount){
		jdbcTemplate.update("UPDATE transaction SET total = ?, precent_total = ? WHERE transaction_id = ?", amount, percent_amount, transaction_id);
	}
	
	public int getTotalBusinessTransactions(int business_profile_id){
		int numberOfTransactions = 0;
		numberOfTransactions = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, Integer.class);
		return numberOfTransactions;
	}
	
	public double getBusinessAmount(int business_profile_id){
		double total = 0.0;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(total) FROM transaction WHERE business_profile_id = ?",
					new Object[] { business_profile_id }, Double.class);
			System.out.println("Total found: "+ total);
			return total;
		}catch(Exception e){
			//System.err.println(e);
			return 0.0;
		}
	}

	@SuppressWarnings("rawtypes")
	public class TransactionMapper implements RowMapper {

		public Transaction mapRow(ResultSet rs, int arg1) throws SQLException {
			Transaction transaction = new Transaction();
			transaction.setTransaction_id(rs.getInt("transaction_id"));
			transaction.setUser_profile_id(rs.getInt("user_profile_id"));
			transaction.setBusiness_profile_id(rs.getInt("business_profile_id"));
			transaction.setTotal(rs.getDouble("total"));
			transaction.setPrecent_total(rs.getDouble("precent_total"));
			transaction.setDate_of_purhase(rs.getString("date_of_purchase"));
			return transaction;
		}
	}

}

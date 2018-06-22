package com.cgc.demo.dao;

import java.math.BigDecimal;
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

/**
 * Transaction DAO will get all information needed for Transaction model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return List<Transaction>
	 * Getting all transaction for User
	 */
	@SuppressWarnings("unchecked")
	public List<Transaction> getUserTransaction(int user_profile_id) {
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int business_profile_id
	 * @return List<Transaction>
	 * Getting all transactions for Business.
	 */
	@SuppressWarnings("unchecked")
	public List<Transaction> getBusinessTransaction(int business_profile_id) {
		// TODO Auto-generated method stub
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int transaction_id
	 * @return Transaction
	 * Getting transaction based on transaction id
	 */
	@SuppressWarnings("unchecked")
	public Transaction getOnetransaction(int transaction_id){
		List<Transaction> transaction = null;

		transaction = this.jdbcTemplate.query("SELECT * FROM transaction WHERE transaction_id = ?",
				new Object[] { transaction_id }, new TransactionMapper());

		return transaction.size() > 0 ? transaction.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param Transaction transaction
	 * @return int
	 * Setting Transaction and return unique identifier
	 */
	public int setTransaction(final Transaction transaction) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		System.out.println("Time and date: " + transaction.getDate_of_purchase());

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO transaction (user_profile_id, business_profile_id, total, date_of_purchase) VALUES (?,?,?,NOW())", new String[] { "id" });
				ps.setInt(1, transaction.getUser_profile_id());
				ps.setInt(2, transaction.getBusiness_profile_id());
				ps.setBigDecimal(3, transaction.getTotal());
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int business_profile_id
	 * @return BigDecimal
	 * Getting the sum total from all transactions from business profile id
	 */
	public BigDecimal getBusinessTotal(int business_profile_id) {
		BigDecimal total;
		total = jdbcTemplate.queryForObject("SELECT SUM(precent_total) FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, BigDecimal.class);
		return total;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return BigDecimal
	 * Getting the sum total from all transactions from user profile id
	 */
	public BigDecimal getUserTotal(int user_profile_id) {
		BigDecimal total;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(precent_total) FROM transaction WHERE user_profile_id = ?",
					new Object[] { user_profile_id }, BigDecimal.class);
			return total;
		}catch(Exception e){
			return new BigDecimal("0.0");
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return BigDecimal
	 * Getting the sum total fees from all transactions from user profile id
	 */
	public BigDecimal getUserTotalFees(int user_profile_id){
		BigDecimal total;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(fee_total) FROM transaction WHERE user_profile_id = ?",
					new Object[] { user_profile_id }, BigDecimal.class);
			return total;
		}catch(Exception e){
			//System.err.println(e);
			return new BigDecimal("0.0");
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return BigDecimal
	 * Getting the sum total funds from all transactions from user profile id
	 */
	public BigDecimal getUserTotalFunds(int user_profile_id){
		BigDecimal total;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(funds_total) FROM transaction WHERE user_profile_id = ?",
					new Object[] { user_profile_id }, BigDecimal.class);
			return total;
		}catch(Exception e){
			//System.err.println(e);
			return new BigDecimal("0.0");
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int transaction_id, BigDecimal amount, BigDecimal percent_amount, BigDecimal totalFee, BigDecimal totalFunds
	 * @return void
	 * Update transaction with all totals
	 */
	public void updateTransaction(int transaction_id, BigDecimal amount, BigDecimal percent_amount, BigDecimal totalFee, BigDecimal totalFunds){
		jdbcTemplate.update("UPDATE transaction SET total = ?, precent_total = ?, fee_total = ?, funds_total = ? WHERE transaction_id = ?", amount, percent_amount, totalFee, totalFunds, transaction_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int business_profile_id
	 * @return int
	 * Get the total count of transactions for business.
	 */
	public int getTotalBusinessTransactions(int business_profile_id){
		int numberOfTransactions = 0;
		numberOfTransactions = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction WHERE business_profile_id = ?",
				new Object[] { business_profile_id }, Integer.class);
		return numberOfTransactions;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int business_profile_id
	 * @return BigDecimal
	 * Get the sum total for business
	 */
	public BigDecimal getBusinessAmount(int business_profile_id){
		BigDecimal total;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(total) FROM transaction WHERE business_profile_id = ?",
					new Object[] { business_profile_id }, BigDecimal.class);
			//System.out.println("Total found: "+ total);
			System.out.println("Test : "+ total.toString());
			return total;
		}catch(Exception e){
			//System.err.println(e);
			return new BigDecimal("0.0");
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Transaction
	 * Mapper for transaction 
	 */
	@SuppressWarnings("rawtypes")
	public class TransactionMapper implements RowMapper {

		public Transaction mapRow(ResultSet rs, int arg1) throws SQLException {
			Transaction transaction = new Transaction();
			transaction.setTransaction_id(rs.getInt("transaction_id"));
			transaction.setUser_profile_id(rs.getInt("user_profile_id"));
			transaction.setBusiness_profile_id(rs.getInt("business_profile_id"));
			transaction.setTotal(rs.getBigDecimal("total"));
			transaction.setPrecent_total(rs.getBigDecimal("precent_total"));
			transaction.setDate_of_purchase(rs.getString("date_of_purchase"));
			transaction.setFunds_total(rs.getBigDecimal("funds_total"));
			transaction.setFee_total(rs.getBigDecimal("fee_total"));
			return transaction;
		}
	}

}

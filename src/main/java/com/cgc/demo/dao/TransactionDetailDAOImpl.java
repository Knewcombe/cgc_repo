package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.TransactionDetail;

/**
 * Transaction Details DAO will get all information needed for Transaction Details model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class TransactionDetailDAOImpl implements TransactionDetailDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param TransactionDetail transactionDetail
	 * @return void
	 * Setting Transaction details to database
	 */
	public void setTransactionDetails(TransactionDetail transactionDetail) {
		jdbcTemplate.update(
				"INSERT INTO transaction_details (transaction_details_id, transaction_id, name, amount, fee, funds, transaction_type, transaction_rate, method_of_pyment, precent_amount) VALUES (?,?,?,?,?,?,?,?,?,?)",
				new Object[] { 
						transactionDetail.getTransaction_details_id(), 
						transactionDetail.getTransaction_id(),
						transactionDetail.getName(), 
						transactionDetail.getAmount(),
						transactionDetail.getFee(),
						transactionDetail.getFunds(),
						transactionDetail.getTransaction_type(), 
						transactionDetail.getTransaction_rate(), 
						transactionDetail.getMethod_of_payment(), 
						transactionDetail.getPrecent_amount() 
						});
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int transaction_id
	 * @return List<TransactionDetail>
	 * Get all transaction details for a transaction
	 */
	@SuppressWarnings("unchecked")
	public List<TransactionDetail> getTransactionDetails(int transaction_id) {
		List<TransactionDetail> transactionDetail = null;

		transactionDetail = jdbcTemplate.query("SELECT * FROM transaction_details WHERE transaction_id = ?",
				new Object[] { transaction_id }, new TransactionDetailMapper());

		return transactionDetail.size() > 0 ? transactionDetail : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int type
	 * @return double
	 * Getting the total of transaction details
	 */
	public double getTotalPreferanceAmount(int type){
		double total = 0.0;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transaction_details WHERE transaction_type = ?",
					new Object[] { type }, Double.class);
			System.out.println("Total found: "+ total);
			return total;
		}catch(Exception e){
			System.err.println(e);
			return 0.0;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int type
	 * @return double
	 * Getting the percent total of transaction details
	 */
	public double getTotalPreferancePercentAmount(int type){
		double total = 0.0;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(precent_amount) FROM transaction_details WHERE transaction_type = ?",
					new Object[] { type }, Double.class);
			System.out.println("Total found: "+ total);
			return total;
		}catch(Exception e){
			System.err.println(e);
			return 0.0;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return TransactionDetail
	 * Mapper for transaction details.
	 */
	@SuppressWarnings("rawtypes")
	public class TransactionDetailMapper implements RowMapper {

		public TransactionDetail mapRow(ResultSet rs, int arg1) throws SQLException {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setTransaction_details_id(rs.getInt("transaction_details_id"));
			transactionDetail.setTransaction_id(rs.getInt("transaction_id"));
			transactionDetail.setAmount(rs.getBigDecimal("amount"));
			transactionDetail.setPrecent_amount(rs.getBigDecimal("precent_amount"));
			transactionDetail.setName(rs.getString("name"));
			transactionDetail.setTransaction_type(rs.getInt("transaction_type"));
			transactionDetail.setMethod_of_payment(rs.getString("method_of_pyment"));
			transactionDetail.setTransaction_rate(rs.getBigDecimal("transaction_rate"));
			transactionDetail.setFee(rs.getBigDecimal("fee"));
			transactionDetail.setFunds(rs.getBigDecimal("funds"));
			return transactionDetail;
		}
	}
}

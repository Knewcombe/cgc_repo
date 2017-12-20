package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.TransactionDetail;

public class TransactionDetailDAOImpl implements TransactionDetailDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void setTransactionDetails(TransactionDetail transactionDetail) {
		jdbcTemplate.update(
				"INSERT INTO transaction_details (transaction_details_id, transaction_id, name, amount, transaction_type, transaction_rate, method_of_pyment, precent_amount) VALUES (?,?,?,?,?,?,?,?)",
				new Object[] { transactionDetail.getTransaction_details_id(), transactionDetail.getTransaction_id(),
						transactionDetail.getName(), transactionDetail.getAmount(), transactionDetail.getTransaction_type(), transactionDetail.getTransaction_rate(), transactionDetail.getMethod_of_pyment(), 
						transactionDetail.getPrecent_amount() });
	}

	@SuppressWarnings("unchecked")
	public List<TransactionDetail> getTransactionDetails(int transaction_id) {
		List<TransactionDetail> transactionDetail = null;

		transactionDetail = jdbcTemplate.query("SELECT * FROM transaction_details WHERE transaction_id = ?",
				new Object[] { transaction_id }, new TransactionDetailMapper());

		return transactionDetail.size() > 0 ? transactionDetail : null;
	}

	@SuppressWarnings("rawtypes")
	public class TransactionDetailMapper implements RowMapper {

		public TransactionDetail mapRow(ResultSet rs, int arg1) throws SQLException {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setTransaction_details_id(rs.getInt("transaction_details_id"));
			transactionDetail.setTransaction_id(rs.getInt("transaction_id"));
			transactionDetail.setAmount(rs.getDouble("amount"));
			transactionDetail.setPrecent_amount(rs.getDouble("precent_amount"));
			transactionDetail.setName(rs.getString("name"));
			transactionDetail.setTransaction_type(rs.getInt("transaction_type"));
			transactionDetail.setMethod_of_pyment(rs.getString("method_of_pyment"));
			transactionDetail.setTransaction_rate(rs.getDouble("transaction_rate"));
			return transactionDetail;
		}
	}
}

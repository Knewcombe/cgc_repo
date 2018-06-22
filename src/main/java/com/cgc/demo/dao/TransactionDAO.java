package com.cgc.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cgc.demo.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> getUserTransaction(int user_profile_id);
	
	public List<Transaction> getBusinessTransaction(int business_profile_id);
	
	public Transaction getOnetransaction(int transaction_id);
	
	public int setTransaction(Transaction transaction);
	
	public BigDecimal getBusinessTotal(int business_profile_id);
	
	public BigDecimal getUserTotal(int user_profile_id);
	
	public BigDecimal getUserTotalFees(int user_profile_id);
	
	public BigDecimal getUserTotalFunds(int user_profile_id);
	
	public void updateTransaction(int transaction_id, BigDecimal amount, BigDecimal percent_amount, BigDecimal totalFee, BigDecimal totalFunds);
	
	public int getTotalBusinessTransactions(int business_profile_id);
	
	public BigDecimal getBusinessAmount(int business_profile_id);
	
	

}

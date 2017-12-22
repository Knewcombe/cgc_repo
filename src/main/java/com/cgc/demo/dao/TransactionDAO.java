package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> getUserTransaction(int user_profile_id);
	
	public List<Transaction> getBusinessTransaction(int business_profile_id);
	
	public Transaction getOnetransaction(int transaction_id);
	
	public int setTransaction(Transaction transaction);
	
	public double getBusinessTotal(int business_profile_id);
	
	public double getUserTotal(int user_profile_id);
	
	public void updateTransaction(int transaction_id, double amount, double percent_amount);
	
	public int getTotalBusinessTransactions(int business_profile_id);
	
	public double getBusinessAmount(int business_profile_id);
	
	

}

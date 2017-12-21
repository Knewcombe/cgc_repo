package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.TransactionDetail;

public interface TransactionDetailDAO {
	
	public void setTransactionDetails(TransactionDetail transactionDetail);
	
	public List<TransactionDetail> getTransactionDetails(int transaction_id);
	
	public double getTotalPreferanceAmount(int type);
	
	public double getTotalPreferancePercentAmount(int type);

}

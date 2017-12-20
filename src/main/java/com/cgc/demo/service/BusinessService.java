package com.cgc.demo.service;

import java.util.List;

import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.Search;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserProfile;

public interface BusinessService {
	
	public BusinessAccount login(Login login);
	
	public void register(BusinessAccount businessAccount);
	
	public List<Transaction> getTransaction(int business_profile_id);
	
	public Transaction getOneTransaction(int transaction_id);
	
	public List<TransactionDetail> getTransactionDetails(int transaction_id);
	
	public void setTransaction(Transaction transaction, List<BusinessPreference> businessPreference);
	
	public double getTransactionTotal(int business_profile_id);
	
	public boolean checkForUser(int user_profile_id);
	
	public List<UserProfile> searchForUser(Search search);
	
	public boolean checkUserName(String username);
	
	public UserProfile getUserId(String cardId);
	
	public List<UserProfile> searchForUser(String search);
	
	public UserProfile getCardId(int user_profile_id);

}

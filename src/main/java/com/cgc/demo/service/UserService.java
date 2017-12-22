package com.cgc.demo.service;

import java.util.List;

import com.cgc.demo.model.Login;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserProfile;

public interface UserService {
	
	public UserAccount login(Login login);
	
	public void register(UserAccount userAccount);
	
	public List<Transaction> getTransaction(int user_profile_id);
	
	public Transaction getOneTransaction(int transaction_id);
	
	public List<TransactionDetail> getTransactionDetails(int transaction_id);
	
	public double getTransactionTotal(int user_profile_id);
	
	public boolean checkUsername(String username);
	
	public UserProfile getUserName(int user_profile_id);
	
	public UserProfile getUserProfile(int user_profile_id);

}

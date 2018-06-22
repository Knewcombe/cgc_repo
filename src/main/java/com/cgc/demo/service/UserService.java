package com.cgc.demo.service;

import java.math.BigDecimal;
import java.util.List;

import com.cgc.demo.model.Login;
import com.cgc.demo.model.Questions;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;

public interface UserService {
	
	public UserAccount login(Login login);
	
	public void register(UserAccount userAccount);
	
	public List<Transaction> getTransaction(int user_profile_id);
	
	public Transaction getOneTransaction(int transaction_id);
	
	public List<TransactionDetail> getTransactionDetails(int transaction_id);
	
	public BigDecimal getTransactionTotal(int user_profile_id);
	
	public BigDecimal getTransactionTotalFees(int user_profile_id);
	
	public BigDecimal getTransactionTotalFunds(int user_profile_id);
	
	public boolean checkUsername(String username);
	
	public UserProfile getUserName(int user_profile_id);
	
	public UserProfile getUserProfile(int user_profile_id);
	
	public Questions getUserQuestion(UserProfile userProfile);
	
	public UserAccount matchQuestion(Questions question);
	
	public void changePassword(UserAccount userAccount);
	
	public boolean checkEmail(String email);
	
	public void updateUserAssociationInfo(int user_profile_id, BigDecimal fundsTotal);
	
	public List<UserAssociation> updateUserAssociation(List<UserAssociation> associations, int user_profile_id);

}

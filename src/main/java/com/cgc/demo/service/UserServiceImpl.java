package com.cgc.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.FamilyMemberDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.dao.UserAccountDAO;
import com.cgc.demo.dao.UserAssociationDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserProfile;

public class UserServiceImpl implements UserService{
	
	@Autowired
	UserAccountDAO userAccountDAO;
	
	@Autowired
	UserProfileDAO userProfileDAO;
	
	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	FamilyMemberDAO familyMemberDAO;
	
	@Autowired
	UserAssociationDAO userAssociationDAO;
	
	@Autowired
	TransactionDetailDAO transactionDetailDAO;
	
	@Autowired
	Util util;
	
	public UserAccount login(Login login){
		UserAccount userAccount = null;
		if(util.checkPassword(login.getPassword(), userAccountDAO.getPassword(login.getUsername()))){
			userAccount = userAccountDAO.login(login);
		}
		
		if(userAccount != null){
			//System.out.println("Got account, getting profile"+ businessAccount.getBusiness_account_id());
			userAccount.setUserProfile(userProfileDAO.getUserProfile(userAccount.getUser_account_id()));
			userAccount.getUserProfile().setFamily(familyMemberDAO.getFamilyMember(userAccount.getUserProfile().getUser_profile_id()));
			userAccount.getUserProfile().setUserAssociation(userAssociationDAO.getUserAssociation(userAccount.getUserProfile().getUser_profile_id()));
		}
		return userAccount;
	}
	
	public void register(UserAccount userAccount){
		userAccount.setPassword(util.encodePassword(userAccount.getPassword()));
		userAccount.getUserProfile().setUser_account_id(userAccountDAO.registerUser(userAccount));
		
		int user_profile_id = userProfileDAO.registerUser(userAccount.getUserProfile());
		
		for(int i = 0; i < userAccount.getUserProfile().getFamily().size(); i++){
			if(userAccount.getUserProfile().getFamily(i).getFirst_name() != "" && userAccount.getUserProfile().getFamily(i).getLast_name() != ""){
				userAccount.getUserProfile().getFamily(i).setUser_profile_id(user_profile_id);
				familyMemberDAO.setFamilyMember(userAccount.getUserProfile().getFamily(i));
			}
		}
		
		for(int e = 0; e < userAccount.getUserProfile().getUserAssociation().size(); e++){
			System.out.println("Testing the user association");
			System.out.println(userAccount.getUserProfile().getUserAssociation(e).getDonation_amount());
			if(userAccount.getUserProfile().getUserAssociation(e).getDonation_amount() != 0){
				userAccount.getUserProfile().getUserAssociation(e).setUser_profile_id(user_profile_id);
				//System.out.println("Testing donation amount " + userAccount.getUserProfile().getUserAssociation(e).getDonation_amount());
				userAssociationDAO.setUserAssociation(userAccount.getUserProfile().getUserAssociation(e));
			}
		}
	}
	
	public List<Transaction> getTransaction(int user_profile_id){
		return transactionDAO.getUserTransaction(user_profile_id);
	}
	
	
	public double getTransactionTotal(int user_profile_id){
		return transactionDAO.getUserTotal(user_profile_id);
	}

	public Transaction getOneTransaction(int transaction_id) {
		return transactionDAO.getOnetransaction(transaction_id);
	}
	
	public boolean checkUsername(String username){
		return userAccountDAO.isValid(username);
	}

	public List<TransactionDetail> getTransactionDetails(int transaction_id) {
		// TODO Auto-generated method stub
		return transactionDetailDAO.getTransactionDetails(transaction_id);
	}
	
	public UserProfile getUserName(int user_profile_id){
		return userProfileDAO.getUserName(user_profile_id);
	}

}

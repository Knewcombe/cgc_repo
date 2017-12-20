package com.cgc.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.BusinessAccountDAO;
import com.cgc.demo.dao.BusinessPreferanceDAO;
import com.cgc.demo.dao.BusinessProfileDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.Search;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserProfile;


public class BusinessServiceImpl implements BusinessService {

	@Autowired
	BusinessAccountDAO businessAccountDAO;

	@Autowired
	BusinessProfileDAO businessProfileDAO;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	UserProfileDAO userProfileDAO;

	@Autowired
	BusinessPreferanceDAO businessPreferanceDAO;

	@Autowired
	TransactionDetailDAO transactionDetailDAO;
	
	@Autowired
	Util util;

	public BusinessAccount login(Login login) {
		// Will build the business account model here, getting all of the
		// information needed to send back to the view.
		BusinessAccount businessAccount = null;
		if(util.checkPassword(login.getPassword(), businessAccountDAO.getPassword(login.getUsername()))){
			businessAccount = businessAccountDAO.login(login);
		}
		
		if (businessAccount != null) {
			// System.out.println("Got account, getting profile"+
			// businessAccount.getBusiness_account_id());
			businessAccount.setBusinessProfile(
					businessProfileDAO.getBusinessProfile(businessAccount.getBusiness_account_id()));
			businessAccount.getBusinessProfile().setBusinessPreferance(businessPreferanceDAO
					.getAllBusinessPreference(businessAccount.getBusinessProfile().getBusiness_profile_id()));
		}
		return businessAccount;
	}

	public void register(BusinessAccount businessAccount) {
		businessAccount.setPassword(util.encodePassword(businessAccount.getPassword()));
		
		businessAccount.getBusinessProfile().setBusiness_account_id(businessAccountDAO.registerUser(businessAccount));
		int business_profile_id = businessProfileDAO.registerUser(businessAccount.getBusinessProfile());
		for (int i = 0; i < businessAccount.getBusinessProfile().getBusinessPreferance().size(); i++) {
			if (businessAccount.getBusinessProfile().getBusinessPreferance(i).getName() != null) {
				businessAccount.getBusinessProfile().getBusinessPreferance(i)
						.setBusiness_profile_id(business_profile_id);
				if (!businessAccount.getBusinessProfile().getBusinessPreferance(i).getName().equals("Gasoline & Fuel")) {
					businessAccount.getBusinessProfile().getBusinessPreferance(i).setCash_percent(
							businessAccount.getBusinessProfile().getBusinessPreferance(i).getCash_percent() / 100);
					businessAccount.getBusinessProfile().getBusinessPreferance(i).setCredit_percent(
							businessAccount.getBusinessProfile().getBusinessPreferance(i).getCredit_percent() / 100);
					businessAccount.getBusinessProfile().getBusinessPreferance(i).setDebit_percent(
							businessAccount.getBusinessProfile().getBusinessPreferance(i).getDebit_percent() / 100);
					if (businessAccount.getBusinessProfile().getBusinessPreferance(i)
							.getSale_clearance_percent() != 0.0) {
						businessAccount.getBusinessProfile().getBusinessPreferance(i)
								.setSale_clearance_percent(businessAccount.getBusinessProfile().getBusinessPreferance(i)
										.getSale_clearance_percent() / 100);
					}else{
						businessAccount.getBusinessProfile().getBusinessPreferance(i).setCash_percent(
								businessAccount.getBusinessProfile().getBusinessPreferance(i).getCash_percent());
						businessAccount.getBusinessProfile().getBusinessPreferance(i).setCredit_percent(
								businessAccount.getBusinessProfile().getBusinessPreferance(i).getCredit_percent());
						businessAccount.getBusinessProfile().getBusinessPreferance(i).setDebit_percent(
								businessAccount.getBusinessProfile().getBusinessPreferance(i).getDebit_percent());
					}
				}
				businessPreferanceDAO
						.setBusinessPreferance(businessAccount.getBusinessProfile().getBusinessPreferance(i));
			}
		}
	}

	public List<Transaction> getTransaction(int business_profile_id) {

		return transactionDAO.getBusinessTransaction(business_profile_id);
	}

	public Transaction getOneTransaction(int transaction_id) {
		return transactionDAO.getOnetransaction(transaction_id);
	}

	public List<TransactionDetail> getTransactionDetails(int transaction_id) {
		return transactionDetailDAO.getTransactionDetails(transaction_id);
	}
	
	public boolean checkUserName(String username){
		return businessAccountDAO.isValid(username);
	}

	public void setTransaction(Transaction transaction, List<BusinessPreference> businessPreference) {
		int transaction_id = transactionDAO.setTransaction(transaction);
		double total = 0;
		double precentTotal = 0;
		for (int i = 0; i < transaction.getTransactionDetail().size(); i++) {
			if (transaction.getTransactionDetail(i).getAmount() > 0.0) {

				switch (transaction.getTransactionDetail(i).getMethod_of_pyment()) {
				case "DE":
					transaction.getTransactionDetail(i)
							.setTransaction_rate(businessPreference.get(i).getDebit_percent());
					transaction.getTransactionDetail(i)
							.setPrecent_amount(transaction.getTransactionDetail(i).getAmount()
									* businessPreference.get(i).getDebit_percent());
					break;
				case "CR":
					transaction.getTransactionDetail(i)
							.setTransaction_rate(businessPreference.get(i).getCredit_percent());
					transaction.getTransactionDetail(i)
							.setPrecent_amount(transaction.getTransactionDetail(i).getAmount()
									* businessPreference.get(i).getCredit_percent());
					break;
				case "CA":
					transaction.getTransactionDetail(i)
							.setTransaction_rate(businessPreference.get(i).getCash_percent());
					transaction.getTransactionDetail(i)
							.setPrecent_amount(transaction.getTransactionDetail(i).getAmount()
									* businessPreference.get(i).getCash_percent());
					break;
				}
				if (!transaction.getTransactionDetail(i).getName().equals("Gasoline & Fuel")) {
					total += transaction.getTransactionDetail(i).getAmount();
					System.out.println("Name " + transaction.getTransactionDetail(i).getName() + " Total " + total);
				}
				precentTotal += transaction.getTransactionDetail(i).getPrecent_amount();

				transaction.getTransactionDetail(i).setTransaction_id(transaction_id);
				transactionDetailDAO.setTransactionDetails(transaction.getTransactionDetail(i));
			}
		}
		transactionDAO.updateTransaction(transaction_id, total, precentTotal);
	}

	public double getTransactionTotal(int business_profile_id) {
		return transactionDAO.getBusinessTotal(business_profile_id);
	}

	public boolean checkForUser(int user_profile_id) {
		return userProfileDAO.checkForUser(user_profile_id);
	}

	public List<UserProfile> searchForUser(Search search) {
		return userProfileDAO.searchForUser(search);
	}
	
	public UserProfile getUserId(String cardId){
		return userProfileDAO.getUserId(cardId);
	}
	
	public List<UserProfile> searchForUser(String search){
		return userProfileDAO.findUser(search);
	}
	
	public UserProfile getCardId(int user_profile_id){
		return userProfileDAO.getCardId(user_profile_id);
	}

}

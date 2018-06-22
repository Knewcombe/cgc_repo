package com.cgc.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.BusinessAccountDAO;
import com.cgc.demo.dao.BusinessPreferanceDAO;
import com.cgc.demo.dao.BusinessProfileDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.Search;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserProfile;

/**
 * Service used to control all Business functions.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

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
	UserService userService;
	
	@Autowired
	Util util;
	
	/**
	 * Finds Business Account for login and gather all information needed.
	 * 
	 * If no Business Account is found will return null.
	 * 
	 * @param Login login
	 * @return BusinessAccount
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BusinessAccount login(Login login) {
		BusinessAccount businessAccount = null;
		if(util.checkPassword(login.getPassword(), businessAccountDAO.getPassword(login.getUsername()))){
			businessAccount = businessAccountDAO.login(login);
		}
		
		if (businessAccount != null) {
			businessAccount.setBusinessProfile(businessProfileDAO.getBusinessProfile(businessAccount.getBusiness_account_id()));
			businessAccount.getBusinessProfile().setBusinessPreferance(businessPreferanceDAO.getAllBusinessPreference(businessAccount.getBusinessProfile().getBusiness_profile_id()));
		}
		return businessAccount;
	}

	/**
	 * Register business account
	 * 
	 * @param BusinessAccount businessAccount
	 * @return void
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
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
	
	/**
	 * Getting profile for Business Account.
	 * 
	 * @param int business_profile_id
	 * @return BusinessProfile
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BusinessProfile getProfile(int business_profile_id){
		BusinessProfile profile = businessProfileDAO.getBusinessProfile(business_profile_id);
		profile.setBusinessPreferance(businessPreferanceDAO.getAllBusinessPreference(business_profile_id));
		return profile;
	}
	
	/**
	 * Getting all transactions for business profile.
	 * 
	 * @param int business_profile_id
	 * @return List<Transaction>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Transaction> getTransaction(int business_profile_id) {

		return transactionDAO.getBusinessTransaction(business_profile_id);
	}
	
	/**
	 * Getting a single transaction for business profile.
	 * 
	 * @param int transaction_id
	 * @return Transaction
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Transaction getOneTransaction(int transaction_id) {
		return transactionDAO.getOnetransaction(transaction_id);
	}
	
	/**
	 * Getting transaction details for transaction.
	 * 
	 * @param int transaction_id
	 * @return List<TransactionDetail>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<TransactionDetail> getTransactionDetails(int transaction_id) {
		return transactionDetailDAO.getTransactionDetails(transaction_id);
	}
	
	/**
	 * Check username for business account to make sure its unique
	 * 
	 * @param String username
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkUserName(String username){
		return businessAccountDAO.isValid(username);
	}
	
	/**
	 * Check email for business account to make sure its unique
	 * 
	 * @param String email
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkEmail(String email){
		return businessProfileDAO.checkEmail(email);
	}
	
	/**
	 * Get all business profile to view for users.
	 * 
	 * @return List<BusinessProfile>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<BusinessProfile> getAllBusiness(){
		return businessProfileDAO.getAllBusinessProfile();
	}
	
	/**
	 * Setting a transaction made by a user and returning the unique identifier
	 * 
	 * @param Transaction transaction, List<BusinessPreference> businessPreference
	 * @return int
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public int setTransaction(Transaction transaction, List<BusinessPreference> businessPreference) {
		int transaction_id = transactionDAO.setTransaction(transaction);
		BigDecimal total = new BigDecimal("0.00");
		BigDecimal precentTotal = new BigDecimal("0.00");
		BigDecimal feeTotal = new BigDecimal("0.00");
		BigDecimal fundsTotal = new BigDecimal("0.00");
		for (int i = 0; i < transaction.getTransactionDetail().size(); i++) {
			if (transaction.getTransactionDetail(i).getAmount().doubleValue() != 0.0) {
				BigDecimal amount;
				BigDecimal rate;
				switch (transaction.getTransactionDetail(i).getMethod_of_payment()) {
				case "DE":
					transaction.getTransactionDetail(i).setTransaction_rate(new BigDecimal(businessPreference.get(i).getDebit_percent()));
					amount = new BigDecimal(transaction.getTransactionDetail(i).getAmount().toPlainString());
					rate = new BigDecimal(businessPreference.get(i).getDebit_percent());
					transaction.getTransactionDetail(i).setPrecent_amount(amount.multiply(rate));
					transaction.getTransactionDetail(i).setFee(transaction.getTransactionDetail(i).getPrecent_amount().multiply(new BigDecimal("0.20")));
					transaction.getTransactionDetail(i).setFunds(transaction.getTransactionDetail(i).getPrecent_amount().subtract(transaction.getTransactionDetail(i).getFee()));
					break;
				case "CR":
					transaction.getTransactionDetail(i).setTransaction_rate(new BigDecimal(businessPreference.get(i).getCredit_percent()));
					amount = new BigDecimal(transaction.getTransactionDetail(i).getAmount().toPlainString());
					rate = new BigDecimal(businessPreference.get(i).getCredit_percent());
					transaction.getTransactionDetail(i).setPrecent_amount(amount.multiply(rate));
					transaction.getTransactionDetail(i).setFee(transaction.getTransactionDetail(i).getPrecent_amount().multiply(new BigDecimal("0.20")));
					transaction.getTransactionDetail(i).setFunds(transaction.getTransactionDetail(i).getPrecent_amount().subtract(transaction.getTransactionDetail(i).getFee()));
					break;
				case "CA":
					transaction.getTransactionDetail(i).setTransaction_rate(new BigDecimal(businessPreference.get(i).getCash_percent()));
					amount = new BigDecimal(transaction.getTransactionDetail(i).getAmount().toPlainString());
					rate = new BigDecimal(businessPreference.get(i).getCash_percent());
					transaction.getTransactionDetail(i).setPrecent_amount(amount.multiply(rate));
					transaction.getTransactionDetail(i).setFee(transaction.getTransactionDetail(i).getPrecent_amount().multiply(new BigDecimal("0.20")));
					transaction.getTransactionDetail(i).setFunds(transaction.getTransactionDetail(i).getPrecent_amount().subtract(transaction.getTransactionDetail(i).getFee()));
					break;
				}
				if (!transaction.getTransactionDetail(i).getName().equals("Gasoline & Fuel")) {
					total = new BigDecimal(transaction.getTransactionDetail(i).getAmount().add(total).toPlainString());
				}
				precentTotal = new BigDecimal(transaction.getTransactionDetail(i).getPrecent_amount().add(precentTotal).toPlainString());
				feeTotal = new BigDecimal(transaction.getTransactionDetail(i).getFee().add(feeTotal).toPlainString());
				fundsTotal = new BigDecimal(transaction.getTransactionDetail(i).getFunds().add(fundsTotal).toPlainString());
				transaction.getTransactionDetail(i).setTransaction_id(transaction_id);
				
				transactionDetailDAO.setTransactionDetails(transaction.getTransactionDetail(i));
			}
		}
		transactionDAO.updateTransaction(transaction_id, total, precentTotal, feeTotal, fundsTotal);
		userService.updateUserAssociationInfo(transaction.getUser_profile_id(), fundsTotal);
		
		return transaction_id;
	}
	
	/**
	 * Getting the total amount of all transactions related to the business profile
	 * 
	 * @param int business_profile_id
	 * @return BigDecimal
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BigDecimal getTransactionTotal(int business_profile_id) {
		return transactionDAO.getBusinessTotal(business_profile_id);
	}
	
	/**
	 * Check if a user exists.
	 * 
	 * @param int user_profile_id
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkForUser(int user_profile_id) {
		return userProfileDAO.checkForUser(user_profile_id);
	}
	
	/**
	 * Search for a user profile using a search object.
	 * 
	 * @param Search search
	 * @return List<UserProfile>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserProfile> searchForUser(Search search) {
		return userProfileDAO.searchForUser(search);
	}
	
	/**
	 * Getting the user profile with the card string.
	 * 
	 * @param String cardId
	 * @return UserProfile
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserProfile getUserId(String cardId){
		return userProfileDAO.getUserId(cardId);
	}
	
	/**
	 * Getting the user profile with the search string.
	 * 
	 * @param String search
	 * @return List<UserProfile>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserProfile> searchForUser(String search){
		return userProfileDAO.findUser(search);
	}
	
	/**
	 * Getting card id based on user profile id.
	 * 
	 * @param int user_profile_id
	 * @return UserProfile
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserProfile getCardId(int user_profile_id){
		return userProfileDAO.getCardId(user_profile_id);
	}

}

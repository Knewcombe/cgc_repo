package com.cgc.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.FamilyMemberDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.dao.UserAccountDAO;
import com.cgc.demo.dao.UserAssociationDAO;
import com.cgc.demo.dao.UserAssociationInfoDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.dao.UserQuestionDAO;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.Questions;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserAssociationInfo;
import com.cgc.demo.model.UserProfile;

/**
 * Service used to control all User functions.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

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
	UserQuestionDAO userQuestionDAO;
	
	@Autowired
	UserAssociationInfoDAO userAssociationInfoDAO;
	
	@Autowired
	Util util;
	
	/**
	 * Getting User account for login.
	 * 
	 * @param Login login
	 * @return UserAccount
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserAccount login(Login login){
		UserAccount userAccount = null;
		//Checking password
		if(util.checkPassword(login.getPassword(), userAccountDAO.getPassword(login.getUsername()))){
			userAccount = userAccountDAO.login(login);
		}
		if(userAccount != null){
			userAccount.setUserProfile(userProfileDAO.getUserProfile(userAccount.getUser_account_id()));
			userAccount.getUserProfile().setFamily(familyMemberDAO.getFamilyMember(userAccount.getUserProfile().getUser_profile_id()));
			userAccount.getUserProfile().setUserAssociation(userAssociationDAO.getUserAssociation(userAccount.getUserProfile().getUser_profile_id()));
			for(UserAssociation userAss : userAccount.getUserProfile().getUserAssociation()){
				userAss.setUserAssociationInfo(userAssociationInfoDAO.getUserAssociationInfo(userAss.getUser_association_id()));
			}
		}
		return userAccount;
	}
	
	/**
	 * Set user account into database.
	 * 
	 * @param UserAccount userAccount
	 * @return void
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public void register(UserAccount userAccount){
		userAccount.setPassword(util.encodePassword(userAccount.getPassword()));
		userAccount.getUserProfile().setUser_account_id(userAccountDAO.registerUser(userAccount));
		
		int user_profile_id = userProfileDAO.registerUser(userAccount.getUserProfile());
		
		for(int f = 0; f < userAccount.getQuestions().size(); f++){
			userAccount.getQuestion(f).setUser_account_id(user_profile_id);
			userQuestionDAO.setQuestion(userAccount.getQuestion(f));
		}
		
		for(int i = 0; i < userAccount.getUserProfile().getFamily().size(); i++){
			if(userAccount.getUserProfile().getFamily(i).getFirst_name() != null && userAccount.getUserProfile().getFamily(i).getLast_name() != null){
				userAccount.getUserProfile().getFamily(i).setUser_profile_id(user_profile_id);
				familyMemberDAO.setFamilyMember(userAccount.getUserProfile().getFamily(i));
			}
		}
		
		for(int e = 0; e < userAccount.getUserProfile().getUserAssociation().size(); e++){
			System.out.println("Testing the user association");
			if(userAccount.getUserProfile().getUserAssociation(e).getDonation_amount().doubleValue() != 0.0){
				userAccount.getUserProfile().getUserAssociation(e).setUser_profile_id(user_profile_id);
				int associationKey = userAssociationDAO.setUserAssociation(userAccount.getUserProfile().getUserAssociation(e));
				userAssociationInfoDAO.setUserAssociationInfo(associationKey, user_profile_id);
			}
		}
	}
	
	/**
	 * update User Association
	 * 
	 * @param List<UserAssociation> associations, int user_profile_id
	 * @return List<UserAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserAssociation> updateUserAssociation(List<UserAssociation> associations, int user_profile_id){
		List<UserAssociation> test = userAssociationDAO.getUserAssociation(user_profile_id);
		for(UserAssociation userAssociation : associations){
			//Checking if user association alreadt exsists in database.
			if(userAssociation.getUser_association_id() != 0){
				for(UserAssociation userCompair : test){
					Boolean needsChange = false;
					System.out.println("Getting the id " +userCompair.getUser_association_id()+" : "+userAssociation.getUser_association_id());
					if(userCompair.getUser_association_id() == userAssociation.getUser_association_id()){
						//Start checking the items in the list...
						if(userCompair.getAssociation_id() != userAssociation.getAssociation_id()){
							needsChange = true;
							//needsRemove = false;
						}else{
							if(userCompair.getTeam_id() != userAssociation.getTeam_id()){
								needsChange = true;
								//needsRemove = false;
							}else{
								if(userCompair.getPlayer_id() != userAssociation.getPlayer_id()){
									needsChange = true;
									//needsRemove = false;
								}
							}
						}
						if(userCompair.getCharity_id() != userAssociation.getCharity_id()){
							needsChange = true;
						}
						if(userCompair.getNonprof_id() != userAssociation.getNonprof_id()){
							needsChange = true;
							//needsRemove = false;
						}
						System.out.println("Changing: " + needsChange);
						if(needsChange){
							//Will need to update everything
							userAssociation.setUser_profile_id(user_profile_id);
							userAssociationDAO.deativateAssociation(userCompair.getUser_association_id());
							int associationKey = userAssociationDAO.setUserAssociation(userAssociation);
							userAssociationInfoDAO.setUserAssociationInfo(associationKey, user_profile_id);
						}else{
							//Will need to update the donation amount
							System.out.println("Seeing associations " +userCompair.getActive()+" : "+userAssociation.getActive());
							if(userCompair.getActive() != userAssociation.getActive()){
								userAssociationDAO.deativateAssociation(userCompair.getUser_association_id());
							}else{
								System.err.println("Seeing item info: "+userAssociation.getDonation_amount().doubleValue());
								userAssociationDAO.updateDonationAmount(userCompair.getUser_association_id(), userAssociation.getDonation_amount());
							}
						}
					}
				}
			}else{
				//Need to add the item to the list.
				System.err.println("Seeing item info: "+userAssociation.getDonation_amount().doubleValue());
				if(userAssociation.getDonation_amount().doubleValue() != 0.0){
					System.err.println("Adding new item");
					System.err.println("checking for Personal: "+userAssociation.getPersonal());
					userAssociation.setUser_profile_id(user_profile_id);
					int associationKey = userAssociationDAO.setUserAssociation(userAssociation);
					userAssociationInfoDAO.setUserAssociationInfo(associationKey, user_profile_id);
				}
			}
		}
		
		List<UserAssociation> newUsers = userAssociationDAO.getUserAssociation(user_profile_id);
		for(UserAssociation userAss : newUsers){
			userAss.setUserAssociationInfo(userAssociationInfoDAO.getUserAssociationInfo(userAss.getUser_association_id()));
		}
		return newUsers;
	}
	
	/**
	 * Getting all transactions linked to user profile.
	 * 
	 * @param int user_profile_id
	 * @return List<Transaction>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Transaction> getTransaction(int user_profile_id){
		return transactionDAO.getUserTransaction(user_profile_id);
	}
	
	/**
	 * Get transactions amount total for all transactions that is linked to user profile.
	 * 
	 * @param int user_profile_id
	 * @return BigDecimal
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BigDecimal getTransactionTotal(int user_profile_id){
		return transactionDAO.getUserTotal(user_profile_id);
	}
	
	/**
	 * Get transactions fee total for all transactions that is linked to user profile.
	 * 
	 * @param int user_profile_id
	 * @return BigDecimal
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BigDecimal getTransactionTotalFees(int user_profile_id){
		return transactionDAO.getUserTotalFees(user_profile_id);
	}
	
	/**
	 * Get transactions funds total for all transactions that is linked to user profile.
	 * 
	 * @param int user_profile_id
	 * @return BigDecimal
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public BigDecimal getTransactionTotalFunds(int user_profile_id){
		return userAssociationInfoDAO.getSumOfUser(user_profile_id);
		//return transactionDAO.getUserTotalFunds(user_profile_id);
	}
	
	/**
	 * Get single transactions
	 * 
	 * @param int user_profile_id
	 * @return BigDecimal
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Transaction getOneTransaction(int transaction_id) {
		return transactionDAO.getOnetransaction(transaction_id);
	}
	
	/**
	 * Checking username for users to make sure its unique
	 * 
	 * @param String username
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkUsername(String username){
		return userAccountDAO.isValid(username);
	}
	
	/**
	 * Checking email for users to make sure its unique
	 * 
	 * @param String email
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkEmail(String email){
		return userProfileDAO.emailValid(email);
	}

	/**
	 * Getting transactions detail for user transaction.
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
	 * Getting username for user profile.
	 * 
	 * @param int user_profile_id
	 * @return UserProfile
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserProfile getUserName(int user_profile_id){
		return userProfileDAO.getUserName(user_profile_id);
	}
	
	/**
	 * Getting username for user profile.
	 * 
	 * @param int user_profile_id
	 * @return UserProfile
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserProfile getUserProfile(int user_profile_id){
		return userProfileDAO.getContactInfo(user_profile_id);
	}
	
	/**
	 * Get user questoins for user profile.
	 * 
	 * @param UserProfile userProfile
	 * @return Questions
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Questions getUserQuestion(UserProfile userProfile){
		int userAccountId = userProfileDAO.checkEmail(userProfile.getEmail());
		if(userAccountId != 0){
			return userQuestionDAO.getRandomUserQuestion(userAccountId);
		}else{
			return null;
		}
	}
	
	/**
	 * Match the user answers to confirm their profile.
	 * 
	 * @param Questions question
	 * @return UserAccount
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public UserAccount matchQuestion(Questions question){
		Questions answer = userQuestionDAO.getAnswer(question.getUser_question_id());
		if(answer.getAnswer().equals(question.getAnswer())){
			UserAccount userAccount = new UserAccount();
			System.err.println("Does the answer have it?: "+ answer.getUser_account_id());
			userAccount.setUser_account_id(answer.getUser_account_id());
			return userAccount;
		}else{
			return null;
		}
	}
	
	/**
	 * Update user association info when UserAssocations changes or transaction is made.
	 * 
	 * @param int user_profile_id, BigDecimal fundsTotal
	 * @return void
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public void updateUserAssociationInfo(int user_profile_id, BigDecimal fundsTotal){
		//List<UserAssociationInfo> info = new ArrayList<UserAssociationInfo>();
		List<UserAssociationInfo> userAssociation = userAssociationInfoDAO.getAllUserAssociationInfo(user_profile_id);
		for(UserAssociationInfo userAssInfo : userAssociation){
			UserAssociation userAss = userAssociationDAO.getAssociation(userAssInfo.getUser_association_id());
			userAssInfo.setTotal_amount(userAssInfo.getTotal_amount().add(userAss.getDonation_amount().multiply(fundsTotal)));
			userAssociationInfoDAO.updateUserAssociationInfo(userAssInfo);
		}
	}
	
	/**
	 * Change the user account password.
	 * 
	 * @param UserAccount userAccount
	 * @return void
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public void changePassword(UserAccount userAccount){
		userAccount.setPassword(util.encodePassword(userAccount.getPassword()));
		userAccountDAO.changePassword(userAccount);
	}

}

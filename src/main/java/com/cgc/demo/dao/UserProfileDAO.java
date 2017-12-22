package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.Search;
import com.cgc.demo.model.UserProfile;

public interface UserProfileDAO{
	
	public UserProfile getUserProfile(int index);
	
	public int registerUser(UserProfile userProfile);
	
	public boolean checkForUser(int user_profile_id);
	
	public List<UserProfile> searchForUser(Search search);
	
	public UserProfile getUserId(String cardId);
	
	public List<UserProfile> findUser(String search);
	
	public UserProfile getCardId(int user_profile_id);
	
	public UserProfile getUserName(int user_profile_id);
	
	public UserProfile getContactInfo(int user_profile_id);
}

package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.UserProfile;

public interface BusinessProfileDAO {
	
	public BusinessProfile getBusinessProfile(int index);
	
	public int registerUser(BusinessProfile businessProfile);
	
	public List<BusinessProfile> getAllBusinessProfile();
	
	public boolean checkEmail(String email);

}

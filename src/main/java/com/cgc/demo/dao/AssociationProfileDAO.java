package com.cgc.demo.dao;

import com.cgc.demo.model.AssociationProfile;

public interface AssociationProfileDAO {
	
	public AssociationProfile getProfile(int association_account_id);
	
	public boolean checkEmail(String email);

}

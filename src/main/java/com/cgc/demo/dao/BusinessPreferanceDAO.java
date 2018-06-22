package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.BusinessPreference;

public interface BusinessPreferanceDAO {
	
	public void setBusinessPreferance(BusinessPreference businessPreference);
	
	public List<BusinessPreference> getAllBusinessPreference(int business_profile_id);
	

}

package com.cgc.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cgc.demo.model.UserAssociationInfo;

public interface UserAssociationInfoDAO {
	
	public void setUserAssociationInfo(UserAssociationInfo userAssociationInfo);
	
	public void setUserAssociationInfo(int user_association_id, int user_profile_id);
	
	public UserAssociationInfo getUserAssociationInfo(int user_association_id);
	
	public List<UserAssociationInfo> getAllUserAssociationInfo(int user_profile_id);
	
	public void updateUserAssociationInfo(UserAssociationInfo userAssociationInfo);
	
	public BigDecimal getSumOfUser(int user_profile_id);

}

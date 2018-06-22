package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.CharityAssociation;

public interface CharityAssociationDAO {
	
	public List<CharityAssociation> getProvince();
	
	public List<CharityAssociation> getCharityCommunity(String province_code);
	
	public List<CharityAssociation> getCharityName(String province_code, String community);
	
	public CharityAssociation getCharityName(int charity_id);
	
	public CharityAssociation getCharity(int account_id);
	
	public List<CharityAssociation> getCharity();
	
	public List<CharityAssociation> searchCharity(String search);
	
	public CharityAssociation getCharityInfo(int charity_id);

}

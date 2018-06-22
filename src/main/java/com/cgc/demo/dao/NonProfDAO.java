package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.NonProfAssociation;

public interface NonProfDAO {
	
	public List<NonProfAssociation> getProvince();
	
	public List<NonProfAssociation> getCommunity(String province_code);
	
	public List<NonProfAssociation> getName(String province_code, String community);
	
	public NonProfAssociation getName(int nonProf_id);
	
	public NonProfAssociation getNonProf(int account_id);
	
	public NonProfAssociation getNonProfInfo(int nonprof_id);
	
	public List<NonProfAssociation> getNonProf();
	
	public List<NonProfAssociation> searchNonProf(String search);

}

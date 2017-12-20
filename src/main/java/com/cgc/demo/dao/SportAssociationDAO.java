package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.SportAssociation;

public interface SportAssociationDAO {
	
	public SportAssociation getAssoication(int index);
	
	public List<SportAssociation> getAssoication();
	
	public List<SportAssociation> getProvinces();
	
	public List<SportAssociation> getCommunities(String province_code);
	
	public List<SportAssociation> getSports(String province_code, String community);
	
	public List<SportAssociation> getAssociationName(String province_code, String community, String sport);
	
	public SportAssociation getSportNames(int association_id);
	
	public SportAssociation getSportAssociation(int account_id);
}

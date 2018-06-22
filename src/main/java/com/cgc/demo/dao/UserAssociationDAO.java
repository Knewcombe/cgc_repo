package com.cgc.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cgc.demo.model.UserAssociation;

public interface UserAssociationDAO {
	
	public List<UserAssociation> getUserAssociation(int user_profile_id);
	
	public List<UserAssociation> getChairtyAssociation(int chairty_id);
	
	public List<UserAssociation> getSportAssociation(int sport_id);
	
	public List<UserAssociation> getNonProfAssociation(int nonProf_id);
	
	public int setUserAssociation(UserAssociation userAssociation);
	
	public List<UserAssociation> getTeamAssociation(int team_id);
	
	public List<UserAssociation> getPlayerAssociation(int player_id);
	
	public UserAssociation getAssociation(int user_association_id);
	
	public void deativateAssociation(int user_association_id);
	
	public void updateDonationAmount(int user_association_id, BigDecimal amount);
}

package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.UserAssociation;

public interface UserAssociationDAO {
	
	public List<UserAssociation> getUserAssociation(int user_profile_id);
	
	public List<UserAssociation> getChairtyAssociation(int chairty_id);
	
	public List<UserAssociation> getSportAssociation(int sport_id);
	
	public List<UserAssociation> getNonProfAssociation(int nonProf_id);
	
	public void setUserAssociation(UserAssociation userAssociation);
	
	public List<UserAssociation> getTeamAssociation(int team_id);
	
	public List<UserAssociation> getPlayerAssociation(int player_id);

}

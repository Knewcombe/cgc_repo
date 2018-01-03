package com.cgc.demo.service;

import java.util.List;

import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAssociation;

public interface AssociationService {
	
	public AssociationAccount login(Login login);

	public List<SportAssociation> getAssociation();
	
	public SportAssociation getAssociation(int association_id);
	
	public List<SportAssociation> getProvinces();
	
	public List<SportAssociation> getCommunity(String province_code);
	
	public List<SportAssociation> getSports(String province_code, String community);
	
	public List<SportAssociation> getAssociationName(String province_code, String community, String sport);
	
	public List<Teams> getDivision(int index);
	
	public List<Teams> getGender(int index, String division);
	
	public List<Teams> getTeamName(int index, String division, String gender);
	
	public Teams getTeam(int team_id);
	
	public Teams getTeamName(int team_id);
	
	public Player getPlayerName(int player_id);
	
	public List<Player> getPlayers(int team_id);
	
	public List<CharityAssociation> getCharityProvince();
	
	public List<CharityAssociation> getCharityCommunity(String province_code);
	
	public List<CharityAssociation> getCharityName(String province_code, String community);
	
	public List<NonProfAssociation> getNonProfProvince();
	
	public List<NonProfAssociation> getNonProfCommunity(String province_code);
	
	public List<NonProfAssociation> getNonProfName(String province_code, String community);
	
	public CharityAssociation getCharityName(int charity_id);
	
	public NonProfAssociation getNonProfName(int nonProf_id);
	
	public SportAssociation getSportNames(int association_id, int team_id, int player_id);
	
	public List<UserAssociation> getUserAsscoiation(AssociationAccount associationAccount);
	
	public List<UserAssociation> getTeamUserAssociation(int team_id);
	
	public List<UserAssociation> getPlayerUserAssociation(int player_id);
	
	public List<CharityAssociation> getAllCharities();
	
	public List<NonProfAssociation> getAllNonProf();
}

package com.cgc.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.AssociationAccountDAO;
import com.cgc.demo.dao.CharityAssociationDAO;
import com.cgc.demo.dao.NonProfDAO;
import com.cgc.demo.dao.PlayersDAO;
import com.cgc.demo.dao.SportAssociationDAO;
import com.cgc.demo.dao.TeamsDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.UserAssociationDAO;
import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAssociation;

public class AssociationServiceImpl implements AssociationService{
	
	@Autowired
	SportAssociationDAO associationDAO;
	
	@Autowired
	AssociationAccountDAO associationAccountDAO;
	
	@Autowired
	UserAssociationDAO userAssociationDAO; 
	
	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	TeamsDAO teamDAO;
	
	@Autowired
	PlayersDAO playersDAO;
	
	@Autowired
	CharityAssociationDAO charityAssociationDAO;
	
	@Autowired
	NonProfDAO nonProfDAO;
	
	@Autowired
	Util util;
	
	public AssociationAccount login(Login login){
		AssociationAccount associationAccount = null;
		if(util.checkPassword(login.getPassword(), associationAccountDAO.getPassword(login.getUsername()))){
			associationAccount = associationAccountDAO.login(login);
		}
		
		if (associationAccount != null) {
			//Need to get all of the information
			associationAccount.setCharityAssociation(charityAssociationDAO.getCharity(associationAccount.getAssociation_id()));
			associationAccount.setNonProfAssociation(nonProfDAO.getNonProf(associationAccount.getAssociation_id()));
			associationAccount.setSportAssociation(associationDAO.getSportAssociation(associationAccount.getAssociation_id()));
			if(associationAccount.getSportAssociation() != null){
				associationAccount.getSportAssociation().setTeams(teamDAO.getTeams(associationAccount.getSportAssociation().getAssociation_id()));
				if(associationAccount.getSportAssociation().getTeams() != null){
					for(int i = 0; i < associationAccount.getSportAssociation().getTeams().size(); i++){
						associationAccount.getSportAssociation().getTeam(i).setPlayers(playersDAO.getPlayers(associationAccount.getSportAssociation().getTeam(i).getTeam_id()));
					}
				}
			}
		}
		return associationAccount;
	}

	public List<SportAssociation> getAssociation() {
		// TODO Auto-generated method stub
		List<SportAssociation> association = associationDAO.getAssoication();
		for(int i = 0; i < association.size(); i++){
			association.get(i).setTeams(teamDAO.getTeams(association.get(i).getAssociation_id()));
		}
		return association;
	}

	public SportAssociation getAssociation(int association_id) {
		// TODO Auto-generated method stub
		SportAssociation association = associationDAO.getAssoication(association_id);
		association.setTeams(teamDAO.getTeams(association.getAssociation_id()));
		return association;
	}
	
	public Teams getTeam(int team_id){
		
		return teamDAO.getTeam(team_id);
	}
	
	public List<SportAssociation> getProvinces(){
		
		return associationDAO.getProvinces();
	}
	
	public List<SportAssociation> getCommunity(String province_code){
		return associationDAO.getCommunities(province_code);
	}
	
	public List<SportAssociation> getSports(String province_code, String community){
		return associationDAO.getSports(province_code, community);
	}
	
	public List<SportAssociation> getAssociationName(String province_code, String community, String sport){
		return associationDAO.getAssociationName(province_code, community, sport);
	}
	
	public List<Teams> getDivision(int index){
		return teamDAO.getDivision(index);
	}
	
	public List<Teams> getGender(int index, String division){
		return teamDAO.getGender(index, division);
	}
	
	public List<Teams> getTeamName(int index, String division, String gender){
		return teamDAO.getTeamName(index, division, gender);
	}
	
	public List<Player> getPlayers(int team_id){
		return playersDAO.getPlayers(team_id);
	}
	
	public List<CharityAssociation> getCharityProvince(){
		return charityAssociationDAO.getProvince();
	}
	
	public List<CharityAssociation> getCharityCommunity(String province_code){
		return charityAssociationDAO.getCharityCommunity(province_code);
	}
	
	public List<CharityAssociation> getCharityName(String province_code, String community){
		return charityAssociationDAO.getCharityName(province_code, community);
	}
	
	public List<NonProfAssociation> getNonProfProvince(){
		return nonProfDAO.getProvince();
	}
	
	public List<NonProfAssociation> getNonProfCommunity(String province_code){
		return nonProfDAO.getCommunity(province_code);
	}
	
	public List<NonProfAssociation> getNonProfName(String province_code, String community){
		return nonProfDAO.getName(province_code, community);
	}
	
	public CharityAssociation getCharityName(int charity_id){
		return charityAssociationDAO.getCharityName(charity_id);
	}
	
	public NonProfAssociation getNonProfName(int nonProf_id){
		return nonProfDAO.getName(nonProf_id);
	}
	
	public Teams getTeamName(int team_id){
		return teamDAO.getTeamName(team_id);
	}
	
	public Player getPlayerName(int player_id){
		return playersDAO.getPlayerName(player_id);
	}
	
	public SportAssociation getSportNames(int association_id, int team_id, int player_id){
		SportAssociation association = associationDAO.getSportNames(association_id);
		if(team_id != 0){
			association.setTeam(teamDAO.getTeamName(team_id));
		}
		if(player_id != 0){
			association.getTeam(0).setPlayers(playersDAO.getPlayer(player_id));
		}
		return association;
	}
	
	public List<UserAssociation> getUserAsscoiation(AssociationAccount associationAccount){
		//need to get the users associations that have the association_id
		List<UserAssociation> userAssociation = new ArrayList<UserAssociation>();
		if(associationAccount.getCharityAssociation() != null){
			System.out.println("chairty");
			userAssociation = userAssociationDAO.getChairtyAssociation(associationAccount.getCharityAssociation().getAssociation_id());
		}else if(associationAccount.getSportAssociation() != null){
			System.out.println("sport");
			userAssociation = userAssociationDAO.getSportAssociation(associationAccount.getSportAssociation().getAssociation_id());
		}else if(associationAccount.getNonProfAssociation() != null){
			System.out.println("nonprof");
			userAssociation = userAssociationDAO.getNonProfAssociation(associationAccount.getNonProfAssociation().getAssociation_id());
		}
		//System.out.println(userAssociation.isEmpty());
		//after getting the user associations go through them to find the sum for each users transactions and divide by the assocaiotns amount
		if(userAssociation != null){
			for(int i = 0; i < userAssociation.size(); i++){
				System.out.println("User ID: "+ userAssociation.get(i).getUser_profile_id());
				userAssociation.get(i).setSum_total(transactionDAO.getUserTotal(userAssociation.get(i).getUser_profile_id()) * userAssociation.get(i).getDonation_amount());
				System.out.println(userAssociation.get(i).getDonation_amount());
				System.out.println(userAssociation.get(i).getSum_total());
			}
		}
		return userAssociation;
	}
}

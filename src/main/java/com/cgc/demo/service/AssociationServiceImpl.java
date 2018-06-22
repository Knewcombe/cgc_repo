package com.cgc.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;

import com.cgc.demo.dao.AssociationAccountDAO;
import com.cgc.demo.dao.AssociationProfileDAO;
import com.cgc.demo.dao.CharityAssociationDAO;
import com.cgc.demo.dao.NonProfDAO;
import com.cgc.demo.dao.PlayersDAO;
import com.cgc.demo.dao.SportAssociationDAO;
import com.cgc.demo.dao.TeamsDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.UserAssociationDAO;
import com.cgc.demo.dao.UserAssociationInfoDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.SearchResults;
import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserAssociationInfo;

/**
 * Service used to control all Association functions.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

public class AssociationServiceImpl implements AssociationService{
	
	@Autowired
	SportAssociationDAO associationDAO;
	
	@Autowired
	AssociationAccountDAO associationAccountDAO;
	
	@Autowired
	AssociationProfileDAO associationProfileDAO;
	
	@Autowired
	UserAssociationDAO userAssociationDAO;
	
	@Autowired
	UserAssociationInfoDAO userAssociationInfoDAO;
	
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
	UserProfileDAO userProfileDAO;
	
	@Autowired
	Util util;
	
	/**
	 * Finds Charity Account for login and gather all information needed.
	 * 
	 * If no Charity Account is found will return null.
	 *
	 * @param  Login login
	 * @return AssociationAccount
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public AssociationAccount login(Login login){
		AssociationAccount associationAccount = null;
		if(util.checkPassword(login.getPassword(), associationAccountDAO.getPassword(login.getUsername()))){
			associationAccount = associationAccountDAO.login(login);
		}
		//Checking is assocationAccount is null
		if (associationAccount != null) {
			//Need to get all of the information
			//Will find any and all associations need to attach to account.
			associationAccount.setAssociationProfile(associationProfileDAO.getProfile(associationAccount.getAssociation_id()));
			associationAccount.setCharityAssociation(charityAssociationDAO.getCharity(associationAccount.getAssociation_id()));
			associationAccount.setNonProfAssociation(nonProfDAO.getNonProf(associationAccount.getAssociation_id()));
			associationAccount.setSportAssociation(associationDAO.getSportAssociation(associationAccount.getAssociation_id()));
			//Gather teams if there is a sport association
			if(associationAccount.getSportAssociation() != null){
				associationAccount.getSportAssociation().setTeams(teamDAO.getTeams(associationAccount.getSportAssociation().getAssociation_id()));
				//Gathering players if there is a teams
				if(associationAccount.getSportAssociation().getTeams() != null){
					for(int i = 0; i < associationAccount.getSportAssociation().getTeams().size(); i++){
						associationAccount.getSportAssociation().getTeam(i).setPlayers(playersDAO.getPlayers(associationAccount.getSportAssociation().getTeam(i).getTeam_id()));
					}
				}
			}
		}
		return associationAccount;
	}
	
	/**
	 * Using associationAccountDAO to check username with the database.
	 * This is to make sure the username is unique.
	 *
	 * @param  String username
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkUsername(String username){
		return associationAccountDAO.checkUsername(username);
	}
	
	/**
	 * Using associationAccountDAO to check email with the database.
	 * This is to make sure the email is unique.
	 *
	 * @param  String email
	 * @return boolean
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public boolean checkEmail(String email){
		return associationProfileDAO.checkEmail(email);
	}
	
	/**
	 * Getting all Sport Associations and teams that are needed.
	 *
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> getAssociation() {
		List<SportAssociation> association = associationDAO.getAssoication();
		for(int i = 0; i < association.size(); i++){
			association.get(i).setTeams(teamDAO.getTeams(association.get(i).getAssociation_id()));
		}
		return association;
	}
	
	/**
	 * Get sport association from DAO using the association id
	 *
	 * @param  int association_id
	 * @return SportAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public SportAssociation getAssociation(int association_id) {
		SportAssociation association = associationDAO.getAssoication(association_id);
		association.setTeams(teamDAO.getTeams(association.getAssociation_id()));
		return association;
	}
	
	/**
	 * Finding sport association with association id and optional team id and player id
	 * If there is no team id or player id set the params as 0
	 *
	 * @param  int association_id, int team_id, int player_id
	 * @return SportAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public SportAssociation getSportAssociation(int association_id, int team_id, int player_id){
		SportAssociation association = associationDAO.getAssoication(association_id);
		
		if(team_id != 0){
			association.setTeam(teamDAO.getTeam(team_id));
		}
		if(player_id != 0){
			association.getTeam(0).setPlayers(playersDAO.getPlayer(player_id));
		}
		return association;
	}
	
	/**
	 * Finding charity based on identifier.
	 *
	 * @param  int charity_id
	 * @return CharityAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public CharityAssociation getCharityAssociation(int charity_id){
		return charityAssociationDAO.getCharityInfo(charity_id);
	}
	
	/**
	 * Finding nonProf based on identifier.
	 *
	 * @param  int charity_id
	 * @return NonProfAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public NonProfAssociation getNonProfInfo(int nonProf_id){
		return nonProfDAO.getNonProfInfo(nonProf_id);
	}
	
	/**
	 * Finding teams based on identifier.
	 *
	 * @param  int team_id
	 * @return Teams
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Teams getTeam(int team_id){
		
		return teamDAO.getTeam(team_id);
	}
	
	/**
	 * Getting all the provinces from all Sport Associations
	 *
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> getProvinces(){
		
		return associationDAO.getProvinces();
	}
	
	/**
	 * Getting all the communities for Sport Associations based on province_code selected.
	 *
	 * @param  String province_code
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> getCommunity(String province_code){
		return associationDAO.getCommunities(province_code);
	}
	
	/**
	 * Getting all the sport types for Sport Associations based on province_code and community selected.
	 *
	 * @param  String province_code, String community
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> getSports(String province_code, String community){
		return associationDAO.getSports(province_code, community);
	}
	
	/**
	 * Getting all the Sport association based on province_code, community and sport type selected.
	 *
	 * @param  String province_code, String community, String sport
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> getAssociationName(String province_code, String community, String sport){
		return associationDAO.getAssociationName(province_code, community, sport);
	}
	
	/**
	 * Getting the division for all teams related for Sport Association selected.
	 * 
	 * Needs Sport Association identifier.
	 *
	 * @param  int index
	 * @return List<Teams>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Teams> getDivision(int index){
		return teamDAO.getDivision(index);
	}
	
	/**
	 * Getting the gender for all teams related for Sport Association and gender selected.
	 *
	 * @param  int index, String division
	 * @return List<Teams>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Teams> getGender(int index, String division){
		return teamDAO.getGender(index, division);
	}
	
	/**
	 * Getting the gender for all teams related for Sport Association, gender and division selected.
	 *
	 * @param  int index, String division, String gender
	 * @return List<Teams>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Teams> getTeamName(int index, String division, String gender){
		return teamDAO.getTeamName(index, division, gender);
	}
	
	/**
	 * Getting all the players for team
	 *
	 * Needs the team_id to find all players.
	 *
	 * @param  int team_id
	 * @return List<Player>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<Player> getPlayers(int team_id){
		return playersDAO.getPlayers(team_id);
	}
	
	/**
	 * Getting all Charity provinces.
	 *
	 * @return List<CharityAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<CharityAssociation> getCharityProvince(){
		return charityAssociationDAO.getProvince();
	}
	
	/**
	 * Getting all the Charity communities based on province code
	 *
	 * @param  String province_code
	 * @return List<CharityAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<CharityAssociation> getCharityCommunity(String province_code){
		return charityAssociationDAO.getCharityCommunity(province_code);
	}
	
	/**
	 * getting the charity names based on province code and community
	 *
	 * @param  String province_code, String community
	 * @return List<CharityAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<CharityAssociation> getCharityName(String province_code, String community){
		return charityAssociationDAO.getCharityName(province_code, community);
	}
	
	/**
	 * Getting all provinces related to nonprofs
	 *
	 * @return List<NonProfAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<NonProfAssociation> getNonProfProvince(){
		return nonProfDAO.getProvince();
	}
	
	/**
	 * Getting all communities related to nonprof used province_code
	 * 
	 * @param String province_code
	 * @return List<NonProfAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<NonProfAssociation> getNonProfCommunity(String province_code){
		return nonProfDAO.getCommunity(province_code);
	}
	
	/**
	 * Getting all names related to nonprof used province_code and community
	 * 
	 * @param String province_code, String community
	 * @return List<NonProfAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<NonProfAssociation> getNonProfName(String province_code, String community){
		return nonProfDAO.getName(province_code, community);
	}
	
	/**
	 * Getting the names for charity using the charity id
	 * 
	 * @param int charity_id
	 * @return CharityAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public CharityAssociation getCharityName(int charity_id){
		return charityAssociationDAO.getCharityName(charity_id);
	}
	
	/**
	 * Getting the name for nonProf using the nonProf_id
	 * 
	 * @param int nonProf_id
	 * @return NonProfAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public NonProfAssociation getNonProfName(int nonProf_id){
		return nonProfDAO.getName(nonProf_id);
	}
	
	/**
	 * Getting the name for team using the team_id
	 * 
	 * @param int team_id
	 * @return Teams
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Teams getTeamName(int team_id){
		return teamDAO.getTeamName(team_id);
	}
	
	/**
	 * Getting the name for player using the player_id
	 * 
	 * @param int player_id
	 * @return Player
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public Player getPlayerName(int player_id){
		return playersDAO.getPlayerName(player_id);
	}
	
	/**
	 * Getting the sport, team and player names.
	 * 
	 * Team_id and player_id are optional, if none are available set to 0;
	 * 
	 * @param int association_id, int team_id, int player_id
	 * @return SportAssociation
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
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
	
	/**
	 * Getting user associations related to associationAccount
	 * 
	 * @param AssociationAccount associationAccount
	 * @return List<UserAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserAssociation> getUserAsscoiation(AssociationAccount associationAccount){
		//need to get the users associations that have the association_id
		List<UserAssociation> userAssociation = null;
		if(associationAccount.getCharityAssociation() != null){
			userAssociation = userAssociationDAO.getChairtyAssociation(associationAccount.getCharityAssociation().getAssociation_id());
		}else if(associationAccount.getSportAssociation() != null){
			userAssociation = userAssociationDAO.getSportAssociation(associationAccount.getSportAssociation().getAssociation_id());
		}else if(associationAccount.getNonProfAssociation() != null){
			userAssociation = userAssociationDAO.getNonProfAssociation(associationAccount.getNonProfAssociation().getAssociation_id());
		}
		//after getting the user associations go through them to find the sum for each users transactions and divide by the user association amount
		if(userAssociation != null){
			for(int i = 0; i < userAssociation.size(); i++){
				userAssociation.get(i).setUserAssociationInfo(userAssociationInfoDAO.getUserAssociationInfo(userAssociation.get(i).getUser_association_id()));
				userAssociation.get(i).setUser_name((userAssociation.get(i).getUser_profile_id() == 0) ? null : userProfileDAO.getUserName(userAssociation.get(i).getUser_profile_id()));
				userAssociation.get(i).setPlayer_name((userAssociation.get(i).getPlayer_id() == 0) ? "N/A" : playersDAO.getPlayerName(userAssociation.get(i).getPlayer_id()).getName());
				userAssociation.get(i).setTeam_name((userAssociation.get(i).getTeam_id() == 0) ? "N/A" : teamDAO.getTeamName(userAssociation.get(i).getTeam_id()).getName());
			}
		}
		return userAssociation;
	}
	
	/**
	 * Getting all user association related to the selected team.
	 * 
	 * @param int team_id
	 * @return List<UserAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserAssociation> getTeamUserAssociation(int team_id){
		List<UserAssociation> userAssociation = userAssociationDAO.getTeamAssociation(team_id);
		if(userAssociation != null){
			for(int i = 0; i < userAssociation.size(); i++){
				userAssociation.get(i).setUserAssociationInfo(userAssociationInfoDAO.getUserAssociationInfo(userAssociation.get(i).getUser_association_id()));
				userAssociation.get(i).setUser_name((userAssociation.get(i).getUser_profile_id() == 0) ? null : userProfileDAO.getUserName(userAssociation.get(i).getUser_profile_id()));
				userAssociation.get(i).setPlayer_name((userAssociation.get(i).getPlayer_id() == 0) ? "N/A" : playersDAO.getPlayerName(userAssociation.get(i).getPlayer_id()).getName());
				userAssociation.get(i).setTeam_name((userAssociation.get(i).getTeam_id() == 0) ? "N/A" : teamDAO.getTeamName(userAssociation.get(i).getTeam_id()).getName());
			}
		}
		return userAssociation;
	}
	
	/**
	 * Getting all user association related to the selected player.
	 * 
	 * @param int player_id
	 * @return List<UserAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<UserAssociation> getPlayerUserAssociation(int player_id){
		List<UserAssociation> userAssociation =  userAssociationDAO.getPlayerAssociation(player_id);
		
		if(userAssociation != null){
			for(int i = 0; i < userAssociation.size(); i++){
				//System.out.println("User ID: "+ userAssociation.get(i).getUser_profile_id());
				//userAssociation.get(i).setSum_total(transactionDAO.getUserTotal(userAssociation.get(i).getUser_profile_id()).multiply(userAssociation.get(i).getDonation_amount()));
				userAssociation.get(i).setUserAssociationInfo(userAssociationInfoDAO.getUserAssociationInfo(userAssociation.get(i).getUser_association_id()));
				userAssociation.get(i).setUser_name((userAssociation.get(i).getUser_profile_id() == 0) ? null : userProfileDAO.getUserName(userAssociation.get(i).getUser_profile_id()));
				userAssociation.get(i).setPlayer_name((userAssociation.get(i).getPlayer_id() == 0) ? "N/A" : playersDAO.getPlayerName(userAssociation.get(i).getPlayer_id()).getName());
				userAssociation.get(i).setTeam_name((userAssociation.get(i).getTeam_id() == 0) ? "N/A" : teamDAO.getTeamName(userAssociation.get(i).getTeam_id()).getName());
			}
		}
		return userAssociation;
	}
	
	/**
	 * Get all Charity Associations
	 * 
	 * @return List<CharityAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<CharityAssociation> getAllCharities(){
		return charityAssociationDAO.getCharity();
	}
	
	/**
	 * Get all NonProf Associations
	 * 
	 * @return List<NonProfAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<NonProfAssociation> getAllNonProf(){
		return nonProfDAO.getNonProf();
	}
	
	/**
	 * Search for the Sport associations with a search string
	 * 
	 * @param String search
	 * @return List<SportAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<SportAssociation> searchSport(String search){
		List<SportAssociation> sports = new ArrayList<SportAssociation>();
		List<Teams> teams = new ArrayList<Teams>();
		List<Player> players = new ArrayList<Player>();
		teams = teamDAO.searchTeams(search);
		players = playersDAO.searchPlayer(search);
		if(players != null){
			for (Player player : players) {
				Teams tempTeam = teamDAO.getTeam(player.getTeam_id());
				tempTeam.setPlayers(player);
				teams.add(tempTeam);
			}
		}
		if(teams != null){
			for (Teams team : teams) {
				SportAssociation tempSport = associationDAO.getAssoication(team.getAssociation_id());
				tempSport.setTeam(team);
				sports.add(tempSport);
			}
		}
		sports.addAll(associationDAO.searchSport(search));
		return sports;
	}
	
	/**
	 * Search for the Charity associations with a search string
	 * 
	 * @param String search
	 * @return List<CharityAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<CharityAssociation> searchChairty(String search){
		return charityAssociationDAO.searchCharity(search);
	}
	
	/**
	 * Search for the Nonprof associations with a search string
	 * 
	 * @param String search
	 * @return List<NonProfAssociation>
	 * @author Kyle Newcombe
	 * @since 0.1
	 */
	public List<NonProfAssociation> searchNonProf(String search){
		return nonProfDAO.searchNonProf(search);
	}
}

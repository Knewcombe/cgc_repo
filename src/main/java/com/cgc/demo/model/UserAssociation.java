package com.cgc.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAssociation.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * UserAssociation
 * 
 * User association is used to connect the user profile object with associations.
 * This will keep track of all Users selected for communities.
 *
 */

public class UserAssociation {

	private int team_id;
	private int player_id;
	private int charity_id;
	private int nonprof_id;
	private int association_id;
	private int user_association_id;
	private int user_profile_id;
	private BigDecimal donation_amount = new BigDecimal("0.00");
	private BigDecimal sum_total = new BigDecimal("0.00");
	private String user_first_name;
	private String user_last_name;
	private String team_name;
	private String player_name;
	private UserProfile userProfile;
	private UserAssociationInfo userAssociationInfo;
	private Boolean active;
	private Boolean personal;
	

	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public Boolean getPersonal() {
		return personal;
	}


	public void setPersonal(Boolean personal) {
		this.personal = personal;
	}


	public UserAssociationInfo getUserAssociationInfo() {
		return userAssociationInfo;
	}


	public void setUserAssociationInfo(UserAssociationInfo userAssociationInfo) {
		this.userAssociationInfo = userAssociationInfo;
	}


	public UserProfile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}


	public String getUser_first_name() {
		return user_first_name;
	}


	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}


	public String getUser_last_name() {
		return user_last_name;
	}


	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}


	public String getTeam_name() {
		return team_name;
	}


	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}


	public String getPlayer_name() {
		return player_name;
	}


	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	private SportAssociation association;
	
	public int getTeam_id() {
		return team_id;
	}
	
	
	public int getPlayer_id() {
		return player_id;
	}


	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}


	public int getCharity_id() {
		return charity_id;
	}


	public void setCharity_id(int charity_id) {
		this.charity_id = charity_id;
	}


	public int getNonprof_id() {
		return nonprof_id;
	}


	public void setNonprof_id(int nonprof_id) {
		this.nonprof_id = nonprof_id;
	}


	public int getAssociation_id() {
		return association_id;
	}


	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}


	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public int getUser_association_id() {
		return user_association_id;
	}
	public void setUser_association_id(int user_association_id) {
		this.user_association_id = user_association_id;
	}
	public int getUser_profile_id() {
		return user_profile_id;
	}
	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
	}
	public BigDecimal getDonation_amount() {
		return donation_amount;
	}
	public void setDonation_amount(BigDecimal donation_amount) {
		this.donation_amount = donation_amount;
	}
	public SportAssociation getAssociation() {
		return association;
	}
	public void setAssociation(SportAssociation association) {
		this.association = association;
	}

	public BigDecimal getSum_total() {
		return sum_total;
	}

	public void setSum_total(BigDecimal sum_total) {
		this.sum_total = sum_total;
	}
	
	public void setUser_name(UserProfile user){
		if(user != null){
			this.user_first_name = user.getFirst_name();
			this.user_last_name = user.getLast_name();
		}
	}
	
	public String getUser_name(){
		return this.user_first_name +" "+ this.user_last_name;
	}
	

}

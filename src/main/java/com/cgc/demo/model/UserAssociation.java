package com.cgc.demo.model;


public class UserAssociation {
	
//	`association_id` int(11) NOT NULL,
//	  `user_association_id` int(11) NOT NULL,
//	  `user_profile_id` int(11) NOT NULL,
//	  `donation_amount` DECIMAL(3, 2) NOT NULL,
	
	private int team_id;
	private int player_id;
	private int charity_id;
	private int nonprof_id;
	private int association_id;
	private int user_association_id;
	private int user_profile_id;
	private double donation_amount;
	private Boolean chairty_recipts;
	private double sum_total;
	
	public Boolean getChairty_recipts() {
		return chairty_recipts;
	}


	public void setChairty_recipts(Boolean chairty_recipts) {
		this.chairty_recipts = chairty_recipts;
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
	public double getDonation_amount() {
		return donation_amount;
	}
	public void setDonation_amount(double donation_amount) {
		this.donation_amount = donation_amount;
	}
	public SportAssociation getAssociation() {
		return association;
	}
	public void setAssociation(SportAssociation association) {
		this.association = association;
	}

	public double getSum_total() {
		return sum_total;
	}

	public void setSum_total(double sum_total) {
		this.sum_total = sum_total;
	}
	

}

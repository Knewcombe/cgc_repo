package com.cgc.demo.model;

import java.util.List;

/**
 * SearchResults.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * SearchResults
 * 
 * Search results for Community Associations.
 *
 */

public class SearchResults {
	
	private List<SportAssociation> sport;
	
	private List<CharityAssociation> charity;
	
	private List<NonProfAssociation> nonProf;
	
	private List<Teams> team;
	
	private List<Player> player;

	public List<SportAssociation> getSport() {
		return sport;
	}
	
	public void setSport(SportAssociation sport) {
		this.sport.add(sport);
	}

	public void setSport(List<SportAssociation> sport) {
		this.sport = sport;
	}

	public List<CharityAssociation> getCharity() {
		return charity;
	}
	
	public void setCharity(CharityAssociation charity) {
		this.charity.add(charity);
	}

	public void setCharity(List<CharityAssociation> charity) {
		this.charity = charity;
	}

	public List<NonProfAssociation> getNonProf() {
		return nonProf;
	}
	
	public void setNonProf(NonProfAssociation nonProf) {
		this.nonProf.add(nonProf);
	}

	public void setNonProf(List<NonProfAssociation> nonProf) {
		this.nonProf = nonProf;
	}

	public List<Teams> getTeam() {
		return team;
	}
	
	public void setTeam(Teams team) {
		this.team.add(team);
	}

	public void setTeam(List<Teams> team) {
		this.team = team;
	}

	public List<Player> getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player.add(player);
	}

	public void setPlayer(List<Player> player) {
		this.player = player;
	}
	
	

}

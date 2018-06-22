package com.cgc.demo.model;

/**
 * Player.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * Player
 * 
 * This will hold the information for player.
 *
 */

public class Player {
	
	private int player_id;
	private int team_id;
	private String name;
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

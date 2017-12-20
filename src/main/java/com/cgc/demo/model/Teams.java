package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Teams {
	
	private int association_id;
	private int team_id;
	private String division;
	private String gender;
	private String name;
	private String start_date;
	private String end_date;
	
	private List<Player> players = new ArrayList<Player>();
	
	public int getAssociation_id() {
		return association_id;
	}
	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayers(int index){
		return players.get(index);
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void setPlayers(Player player){
		this.players.add(player);
	}
	

}

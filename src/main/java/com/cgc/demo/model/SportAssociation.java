package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

public class SportAssociation {

	private int association_id;
	private String province_code;
	private String community;
	private String sport;
	private String name;
	
	private List<Teams> teams = new ArrayList<Teams>();
	
	public int getAssociation_id() {
		return association_id;
	}
	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Teams> getTeams() {
		return teams;
	}
	public void setTeams(List<Teams> teams) {
		this.teams = teams;
	}
	
	public Teams getTeam(int index){
		return teams.get(index);
	}
	
	public void setTeam(Teams team){
		this.teams.add(team);
	}
	

}

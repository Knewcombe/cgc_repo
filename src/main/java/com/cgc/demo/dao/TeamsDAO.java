package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.Teams;

public interface TeamsDAO {
	
	public List<Teams> getTeams();
	
	public List<Teams> getTeams(int association_id);
	
	public Teams getTeam(int index);
	
	public List<Teams> getDivision(int index);
	
	public List<Teams> getGender(int index, String division);
	
	public List<Teams> getTeamName(int index, String division, String gender);
	
	public Teams getTeamName(int team_id);
	
	public List<Teams> searchTeams(String search);

}

package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.Teams;

public class TeamsDAOImpl implements TeamsDAO {
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public List<Teams> getTeams(){
		List<Teams> teams = null;

		teams = this.jdbcTemplate.query("SELECT * FROM sport_association_team",new TeamMapper());

		return teams.size() > 0 ? teams : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Teams> getTeams(int association_id){
		List<Teams> teams = null;

		teams = this.jdbcTemplate.query("SELECT * FROM sport_association_team WHERE association_id = ?", new Object[] {association_id}, new TeamMapper());

		return teams.size() > 0 ? teams : null;
	}
	
	@SuppressWarnings("unchecked")
	public Teams getTeam(int index){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT * FROM sport_association_team WHERE team_id = ?",
				new Object[] { index },new TeamMapper());

		return team.size() > 0 ? team.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Teams> getDivision(int index){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT DISTINCT division FROM sport_association_team WHERE association_id = ?",
				new Object[] { index },new DivisionMapper());

		return team.size() > 0 ? team : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Teams> getGender(int index, String division){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT DISTINCT gender FROM sport_association_team WHERE association_id = ? AND division = ?",
				new Object[] { index, division },new GenderMapper());

		return team.size() > 0 ? team : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Teams> getTeamName(int index, String division, String gender){
		List<Teams> team = null;
		
		team = this.jdbcTemplate.query("SELECT name, team_id FROM sport_association_team WHERE association_id = ? AND division = ? AND gender = ?",
				new Object[] { index, division, gender },new TeamNameMapper());

		return team.size() > 0 ? team : null;
	}
	
	@SuppressWarnings("unchecked")
	public Teams getTeamName(int team_id){
		List<Teams> team = null;
		
		team = this.jdbcTemplate.query("SELECT name, team_id FROM sport_association_team WHERE team_id = ?",
				new Object[] { team_id },new TeamNameMapper());
		
		return team.size() > 0 ? team.get(0) : null;
	}
	
	public class TeamMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setAssociation_id(rs.getInt("association_id"));
			team.setTeam_id(rs.getInt("team_id"));
			team.setName(rs.getString("name"));
			team.setDivision(rs.getString("division"));
			team.setGender(rs.getString("gender"));
			return team;
		}
	}
	
	public class DivisionMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setDivision(rs.getString("division"));
			return team;
		}
	}
	
	public class GenderMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setGender(rs.getString("gender"));
			return team;
		}
	}
	
	public class TeamNameMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setTeam_id(rs.getInt("team_id"));
			team.setName(rs.getString("name"));
			return team;
		}
	}

}

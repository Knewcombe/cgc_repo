package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.UserProfileDAOImpl.UserProfileMapper;
import com.cgc.demo.model.Teams;

/**
 * Teams DAO will get all information needed for Teams model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class TeamsDAOImpl implements TeamsDAO {
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<Teams>
	 * Getting all teams for viewing
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> getTeams(){
		List<Teams> teams = null;

		teams = this.jdbcTemplate.query("SELECT * FROM sport_association_team",new TeamMapper());

		return teams.size() > 0 ? teams : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int association_id
	 * @return List<Teams>
	 * Get all teams based on association id
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> getTeams(int association_id){
		List<Teams> teams = null;

		teams = this.jdbcTemplate.query("SELECT * FROM sport_association_team WHERE association_id = ?", new Object[] {association_id}, new TeamMapper());

		return teams.size() > 0 ? teams : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index
	 * @return Teams
	 * Getting the team based on team_id
	 */
	@SuppressWarnings("unchecked")
	public Teams getTeam(int index){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT * FROM sport_association_team WHERE team_id = ?",
				new Object[] { index },new TeamMapper());

		return team.size() > 0 ? team.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index
	 * @return List<Teams>
	 * Getting division based on association_id
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> getDivision(int index){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT DISTINCT division FROM sport_association_team WHERE association_id = ?",
				new Object[] { index },new DivisionMapper());

		return team.size() > 0 ? team : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index, String division
	 * @return List<Teams>
	 * Getting genders based on association_id and division
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> getGender(int index, String division){
		List<Teams> team = null;

		team = this.jdbcTemplate.query("SELECT DISTINCT gender FROM sport_association_team WHERE association_id = ? AND division = ?",
				new Object[] { index, division },new GenderMapper());

		return team.size() > 0 ? team : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index, String division, String gender
	 * @return List<Teams>
	 * Getting name based on association_id, division and gender
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> getTeamName(int index, String division, String gender){
		List<Teams> team = null;
		
		team = this.jdbcTemplate.query("SELECT name, team_id FROM sport_association_team WHERE association_id = ? AND division = ? AND gender = ?",
				new Object[] { index, division, gender },new TeamNameMapper());

		return team.size() > 0 ? team : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int team_id
	 * @return Teams
	 * Getting name based on team_id
	 */
	@SuppressWarnings("unchecked")
	public Teams getTeamName(int team_id){
		List<Teams> team = null;
		
		team = this.jdbcTemplate.query("SELECT name, team_id FROM sport_association_team WHERE team_id = ?",
				new Object[] { team_id },new TeamNameMapper());
		
		return team.size() > 0 ? team.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String search
	 * @return List<Teams>
	 * Getting teams based on search string.
	 */
	@SuppressWarnings("unchecked")
	public List<Teams> searchTeams(String search){
		List<Teams> team = null;
		
		team = this.jdbcTemplate.query("SELECT *, MATCH ( `name`, `division`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) as `rel` FROM `sport_association_team` WHERE MATCH ( `name`, `division`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) ORDER BY `rel` DESC",
				new Object[] { search, search }, new TeamMapper());
		
		return team;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Teams
	 * Mapper for Team model
	 */
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
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Teams
	 * Mapper for divisions
	 */
	public class DivisionMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setDivision(rs.getString("division"));
			return team;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Teams
	 * Mapper for gender
	 */
	public class GenderMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setGender(rs.getString("gender"));
			return team;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Teams
	 * Mapper for team name
	 */
	public class TeamNameMapper implements RowMapper {

		public Teams mapRow(ResultSet rs, int arg1) throws SQLException {
			Teams team = new Teams();
			team.setTeam_id(rs.getInt("team_id"));
			team.setName(rs.getString("name"));
			return team;
		}
	}

}

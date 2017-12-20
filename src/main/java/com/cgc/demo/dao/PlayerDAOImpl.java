package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.Player;

public class PlayerDAOImpl implements PlayersDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public List<Player> getPlayers(int team_id){
		
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT * FROM sport_association_players WHERE team_id = ?", new Object[] {team_id},new PlayerMapper());

		return players.size() > 0 ? players : null;
	}
	
	@SuppressWarnings("unchecked")
	public Player getPlayer(int player_id){
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT * FROM sport_association_players WHERE player_id = ?", new Object[] {player_id},new PlayerMapper());

		return players.size() > 0 ? players.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public Player getPlayerName(int player_id){
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT name FROM sport_association_players WHERE player_id = ?", new Object[] {player_id},new PlayerNameMapper());

		return players.size() > 0 ? players.get(0) : null;
	}
	
	public class PlayerMapper implements RowMapper {

		public Player mapRow(ResultSet rs, int arg1) throws SQLException {
			Player players = new Player();
			players.setTeam_id(rs.getInt("team_id"));
			players.setPlayer_id(rs.getInt("player_id"));
			players.setName(rs.getString("name"));
			return players;
		}
	}
	
	public class PlayerNameMapper implements RowMapper {

		public Player mapRow(ResultSet rs, int arg1) throws SQLException {
			Player players = new Player();
			players.setName(rs.getString("name"));
			return players;
		}
	}

}

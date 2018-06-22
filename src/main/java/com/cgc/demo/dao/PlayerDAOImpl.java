package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.UserProfileDAOImpl.UserProfileMapper;
import com.cgc.demo.model.Player;

/**
 * Player DAO will get all information needed for Player model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class PlayerDAOImpl implements PlayersDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int team_id
	 * @return List<Player>
	 * Getting the players based on team_id
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getPlayers(int team_id){
		
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT * FROM sport_association_players WHERE team_id = ?", new Object[] {team_id},new PlayerMapper());

		return players.size() > 0 ? players : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int player_id
	 * @return Player
	 * Getting player based on id
	 */
	@SuppressWarnings("unchecked")
	public Player getPlayer(int player_id){
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT * FROM sport_association_players WHERE player_id = ?", new Object[] {player_id},new PlayerMapper());

		return players.size() > 0 ? players.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int player_id
	 * @return Player
	 * Getting the player name based on id.
	 */
	@SuppressWarnings("unchecked")
	public Player getPlayerName(int player_id){
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT name FROM sport_association_players WHERE player_id = ?", new Object[] {player_id},new PlayerNameMapper());

		return players.size() > 0 ? players.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String search
	 * @return List<Player>
	 * Getting all the players based on search results.
	 */
	@SuppressWarnings("unchecked")
	public List<Player> searchPlayer(String search){
		List<Player> players = null;

		players = this.jdbcTemplate.query("SELECT *, MATCH ( `name`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) as `rel` FROM `sport_association_players` WHERE MATCH ( `name`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) ORDER BY `rel` DESC",
				new Object[] { search, search }, new PlayerMapper());

		return players;
		
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Player
	 * Mapper for player model
	 */
	public class PlayerMapper implements RowMapper {

		public Player mapRow(ResultSet rs, int arg1) throws SQLException {
			Player players = new Player();
			players.setTeam_id(rs.getInt("team_id"));
			players.setPlayer_id(rs.getInt("player_id"));
			players.setName(rs.getString("name"));
			return players;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return Player
	 * Mapper for player name
	 */
	public class PlayerNameMapper implements RowMapper {

		public Player mapRow(ResultSet rs, int arg1) throws SQLException {
			Player players = new Player();
			players.setName(rs.getString("name"));
			return players;
		}
	}

}

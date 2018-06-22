package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.Player;

public interface PlayersDAO {
	
	public List<Player> getPlayers(int team_id);
	
	public Player getPlayer(int player_id);
	
	public Player getPlayerName(int player_id);
	
	public List<Player> searchPlayer(String search);

}

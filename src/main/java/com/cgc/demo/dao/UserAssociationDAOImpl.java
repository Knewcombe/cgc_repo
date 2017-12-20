package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.SportAssociationDAOImpl.AssociationMapper;
import com.cgc.demo.model.UserAssociation;



public class UserAssociationDAOImpl implements UserAssociationDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<UserAssociation> getUserAssociation(int user_profile_id) {
		// TODO Auto-generated method stub
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE user_profile_id = ?",
				new Object[] { user_profile_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getChairtyAssociation(int chairty_id){
		List<UserAssociation> userAssociation = null;
		System.out.println("Getting information for chairty: "+ chairty_id);
		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE charity_id = ?",
				new Object[] { chairty_id },new UserAssociationMapper());
		System.out.println("Test "+ userAssociation.get(0).getAssociation_id());
		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getSportAssociation(int sport_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE association_id = ?",
				new Object[] { sport_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getNonProfAssociation(int nonProf_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE nonprof_id = ?",
				new Object[] { nonProf_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}

	public void setUserAssociation(UserAssociation userAssociation) {
		// TODO Auto-generated method stub
		
		this.jdbcTemplate.update("INSERT INTO user_association (team_id, association_id, player_id, user_profile_id, donation_amount, charity_id, nonprof_id, chairty_recipts) VALUES (?,?,?,?,?,?,?,?)", 
			      new Object[] { 
			    		  userAssociation.getTeam_id() != 0 ? userAssociation.getTeam_id() : null,
			    		  userAssociation.getAssociation_id() != 0 ? userAssociation.getAssociation_id() : null,
			    		  userAssociation.getPlayer_id() != 0 ? userAssociation.getPlayer_id() : null,
			    		  userAssociation.getUser_profile_id(),
			    		  userAssociation.getDonation_amount(),
			    		  userAssociation.getCharity_id() != 0 ? userAssociation.getCharity_id() : null,
			    		  userAssociation.getNonprof_id() != 0 ? userAssociation.getNonprof_id() : null,
			    	      userAssociation.getChairty_recipts()
			      });
		
	}
	
	public class UserAssociationMapper implements RowMapper {

		public UserAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			UserAssociation userAssociation = new UserAssociation();
			userAssociation.setTeam_id(rs.getInt("team_id"));
			userAssociation.setAssociation_id(rs.getInt("association_id"));
			userAssociation.setPlayer_id(rs.getInt("player_id"));
			userAssociation.setCharity_id(rs.getInt("charity_id"));
			userAssociation.setNonprof_id(rs.getInt("nonprof_id"));
			userAssociation.setUser_association_id(rs.getInt("user_association_id"));
			userAssociation.setUser_profile_id(rs.getInt("user_profile_id"));
			userAssociation.setDonation_amount(rs.getDouble("donation_amount"));
			userAssociation.setChairty_recipts(rs.getBoolean("chairty_recipts"));
			return userAssociation;
		}
	}

}

package com.cgc.demo.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.cgc.demo.model.UserAssociation;
import com.mysql.jdbc.PreparedStatement;

/**
 * User Association DAO will get all information needed for User Association model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class UserAssociationDAOImpl implements UserAssociationDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return List<UserAssociation>
	 * Getting all active user association for user profile.
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getUserAssociation(int user_profile_id) {
		// TODO Auto-generated method stub
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE user_profile_id = ? AND active = 1",
				new Object[] { user_profile_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return UserAssociation
	 * Getting user association for user profile.
	 */
	@SuppressWarnings("unchecked")
	public UserAssociation getAssociation(int user_association_id) {
		// TODO Auto-generated method stub
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE user_association_id = ?",
				new Object[] { user_association_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int chairty_id
	 * @return List<UserAssociation>
	 * Getting all user association for selected charity
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getChairtyAssociation(int chairty_id){
		List<UserAssociation> userAssociation = null;
		System.out.println("Getting information for chairty: "+ chairty_id);
		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE charity_id = ? AND active = 1",
				new Object[] { chairty_id },new UserAssociationMapper());
		System.out.println("Test "+ userAssociation.get(0).getAssociation_id());
		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int sport_id
	 * @return List<UserAssociation>
	 * Getting all user association for selected sport
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getSportAssociation(int sport_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE association_id = ? AND active = 1",
				new Object[] { sport_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int nonProf_id
	 * @return List<UserAssociation>
	 * Getting all user association for selected nonProf
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getNonProfAssociation(int nonProf_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE nonprof_id = ? AND active = 1",
				new Object[] { nonProf_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int team_id
	 * @return List<UserAssociation>
	 * Getting all user association for selected teams
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getTeamAssociation(int team_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE team_id = ? AND active = 1",
				new Object[] { team_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int player_id
	 * @return List<UserAssociation>
	 * Getting all user association for selected player
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociation> getPlayerAssociation(int player_id){
		List<UserAssociation> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association WHERE player_id = ? AND active = 1",
				new Object[] { player_id },new UserAssociationMapper());

		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_association_id
	 * @return void
	 * Deactivate association without removing user association from database.
	 */
	public void deativateAssociation(int user_association_id){
		this.jdbcTemplate.update("UPDATE user_association SET active = 0 WHERE user_association_id = ?", user_association_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_association_id, BigDecimal amount
	 * @return void
	 * updating the donation amount for user association
	 */
	public void updateDonationAmount(int user_association_id, BigDecimal amount){
		this.jdbcTemplate.update("UPDATE user_association SET donation_amount = ? WHERE user_association_id = ?", amount, user_association_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserAssociation userAssociation
	 * @return int
	 * Setting user association and returning unique identifier
	 */
	public int setUserAssociation(final UserAssociation userAssociation) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO user_association (team_id, association_id, player_id, user_profile_id, donation_amount, charity_id, nonprof_id, active, personal) VALUES (?,?,?,?,?,?,?,?,?)", new String[] { "id" });
				if(userAssociation.getTeam_id() != 0){
					ps.setInt(1, userAssociation.getTeam_id());
				}else{
					ps.setNull(1, java.sql.Types.INTEGER);
				}
				
				if(userAssociation.getAssociation_id() != 0){
					ps.setInt(2, userAssociation.getAssociation_id());
				}else{
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				
				if(userAssociation.getPlayer_id() != 0){
					ps.setInt(3, userAssociation.getPlayer_id());
				}else{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				ps.setInt(4, userAssociation.getUser_profile_id());
				ps.setBigDecimal(5, userAssociation.getDonation_amount());
				
				if(userAssociation.getCharity_id() != 0){
					ps.setInt(6, userAssociation.getCharity_id());
				}else{
					ps.setNull(6, java.sql.Types.INTEGER);
				}
				
				if(userAssociation.getNonprof_id() != 0){
					ps.setInt(7, userAssociation.getNonprof_id());
				}else{
					ps.setNull(7, java.sql.Types.INTEGER);
				}
				
				if(userAssociation.getActive() != null){
					ps.setBoolean(8, userAssociation.getActive());
				}else{
					//ps.setNull(8, java.sql.Types.INTEGER);
					ps.setBoolean(9, false);
				}
				if(userAssociation.getPersonal() != null){
					ps.setBoolean(9, userAssociation.getPersonal());
				}else{
					//ps.setNull(9, java.sql.Types.INTEGER);
					ps.setBoolean(9, false);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserAssociation
	 * Mapper for User Association
	 */
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
			userAssociation.setDonation_amount(rs.getBigDecimal("donation_amount"));
			userAssociation.setActive(rs.getBoolean("active"));
			userAssociation.setPersonal(rs.getBoolean("personal"));
			return userAssociation;
		}
	}

}

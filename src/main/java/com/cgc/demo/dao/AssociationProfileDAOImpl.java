package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.AssociationAccountDAOImpl.AssociationAccountMapper;
import com.cgc.demo.model.AssociationProfile;

/**
 * Association Profile DAO will get all information needed for AssociationProfile model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class AssociationProfileDAOImpl implements AssociationProfileDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int association_account_id
	 * @return AssociationProfile
	 * Getting the Association Profile from database with matching association_account_id.
	 * returns null if nothing is found.
	 */
	@SuppressWarnings("unchecked")
	public AssociationProfile getProfile(int association_account_id){
		List<AssociationProfile> associationProfile = null;
		
		associationProfile = this.jdbcTemplate.query("SELECT * FROM association_profile WHERE association_account_id = ?",
				new Object[] { association_account_id }, new AssociationProfileMapper());
		
		return associationProfile.size() > 0 ? associationProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String email
	 * @return boolean
	 * Checking the email for Association profile, if there is a matching email returns false.
	 */
	public boolean checkEmail(String email){
		int count = jdbcTemplate.queryForObject("SELECT count(*) from association_profile where email = ?",
				new Object[] { email }, Integer.class);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return AssociationProfile
	 * Mapper for AssociationProfile model.
	 */
	public class AssociationProfileMapper implements RowMapper {

		public AssociationProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			AssociationProfile associationProfile = new AssociationProfile();
			associationProfile.setAssociation_profile_id(rs.getInt("association_profile_id"));
			associationProfile.setAddress(rs.getString("address"));
			associationProfile.setCity(rs.getString("city"));
			associationProfile.setEmail(rs.getString("email"));
			associationProfile.setPhone(rs.getString("phone"));
			associationProfile.setProstal_code(rs.getString("postal_code"));
			associationProfile.setProvince_code(rs.getString("province_code"));
			return associationProfile;
		}
	}
	
}

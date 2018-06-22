package com.cgc.demo.dao;

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

import com.cgc.demo.model.BusinessProfile;
import com.mysql.jdbc.PreparedStatement;

/**
 * Business Profile DAO will get all information needed for BusinessProfile model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class BusinessProfileDAOImpl implements BusinessProfileDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int index
	 * @return BusinessProfile
	 * getting business profile from database based on the index
	 */
	@SuppressWarnings("unchecked")
	public BusinessProfile getBusinessProfile(int index) {
		// TODO Auto-generated method stub
		List<BusinessProfile> businessProfile = null;

		businessProfile = this.jdbcTemplate.query("SELECT * FROM business_profile WHERE business_account_id = ?",
				new Object[] { index }, new BusinessProfileMapper());

		return businessProfile.size() > 0 ? businessProfile.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param BusinessProfile businessProfile
	 * @return int
	 * Setting the business profile in database and return unique identifier
	 */
	public int registerUser(final BusinessProfile businessProfile) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(java.sql.Connection connection)
					throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
						"INSERT INTO business_profile (business_account_id, province_code, city, address, postal_code, phone, email, business_name, main_contact) VALUES (?,?,?,?,?,?,?,?,?)",
						new String[] { "id" });
				ps.setInt(1, businessProfile.getBusiness_account_id());
				ps.setString(2, businessProfile.getProvince_code());
				ps.setString(3, businessProfile.getCity());
				ps.setString(4, businessProfile.getAddress());
				ps.setString(5, businessProfile.getPostal_code());
				ps.setString(6, businessProfile.getPhone());
				ps.setString(7, businessProfile.getEmail());
				ps.setString(8, businessProfile.getBusiness_name());
				ps.setString(9, businessProfile.getMain_contact());

				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<BusinessProfile>
	 * get all business profiles to view.
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessProfile> getAllBusinessProfile(){
		List<BusinessProfile> businessProfile = null;

		businessProfile = this.jdbcTemplate.query("SELECT * FROM business_profile", new DisplayProfileMapper());

		return businessProfile.size() > 0 ? businessProfile : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param string email
	 * @return boolean
	 * Check the email to make sure its unique
	 */
	public boolean checkEmail(String email){
		int count = jdbcTemplate.queryForObject("SELECT count(*) from business_profile where email = ?",
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
	 * @return BusinessProfile
	 * Mapper for business profile
	 */
	public class BusinessProfileMapper implements RowMapper {

		public BusinessProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			BusinessProfile businessProfile = new BusinessProfile();
			businessProfile.setBusiness_profile_id(rs.getInt("business_profile_id"));
			businessProfile.setBusiness_account_id(rs.getInt("business_account_id"));
			businessProfile.setProvince_code(rs.getString("province_code"));
			businessProfile.setCity(rs.getString("city"));
			businessProfile.setAddress(rs.getString("address"));
			businessProfile.setPostal_code(rs.getString("postal_code"));
			businessProfile.setPhone(rs.getString("phone"));
			businessProfile.setEmail(rs.getString("email"));
			businessProfile.setBusiness_name(rs.getString("business_name"));
			businessProfile.setMain_contact(rs.getString("main_contact"));
			businessProfile.setWeb_link(rs.getString("web_link"));
			businessProfile.setImage(rs.getString("image"));
			return businessProfile;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return BusinessProfile
	 * Mapper for public profile
	 */
	public class DisplayProfileMapper implements RowMapper {

		public BusinessProfile mapRow(ResultSet rs, int arg1) throws SQLException {
			BusinessProfile businessProfile = new BusinessProfile();
			businessProfile.setBusiness_profile_id(rs.getInt("business_profile_id"));
			businessProfile.setProvince_code(rs.getString("province_code"));
			businessProfile.setCity(rs.getString("city"));
			businessProfile.setAddress(rs.getString("address"));
			businessProfile.setPostal_code(rs.getString("postal_code"));
			businessProfile.setBusiness_name(rs.getString("business_name"));
			businessProfile.setWeb_link(rs.getString("web_link"));
			businessProfile.setImage(rs.getString("image"));
			return businessProfile;
		}
	}

}

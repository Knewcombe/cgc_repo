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

public class BusinessProfileDAOImpl implements BusinessProfileDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public BusinessProfile getBusinessProfile(int index) {
		// TODO Auto-generated method stub
		List<BusinessProfile> businessProfile = null;

		businessProfile = this.jdbcTemplate.query("SELECT * FROM business_profile WHERE business_account_id = ?",
				new Object[] { index }, new BusinessProfileMapper());

		return businessProfile.size() > 0 ? businessProfile.get(0) : null;
	}

	public int registerUser(final BusinessProfile businessProfile) {
		// TODO Auto-generated method stub
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
			return businessProfile;
		}
	}

}

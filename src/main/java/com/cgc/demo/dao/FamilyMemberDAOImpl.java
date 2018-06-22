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

import com.cgc.demo.dao.BusinessProfileDAOImpl.BusinessProfileMapper;
import com.cgc.demo.model.FamilyMember;

/**
 * Family Member DAO will get all information needed for Family Member model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class FamilyMemberDAOImpl implements FamilyMemberDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return List<FamilyMember>
	 * Getting all Family members for user profile
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyMember> getFamilyMember(int user_profile_id) {
		List<FamilyMember> familyMember = null;

		familyMember = this.jdbcTemplate.query("SELECT * FROM family_member WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, new FamilyMemberMapper());

		return familyMember.size() > 0 ? familyMember : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param FamilyMember familyMember
	 * @return void
	 * Setting family members.
	 */
	public void setFamilyMember(FamilyMember familyMember) {
		this.jdbcTemplate.update(
				"INSERT INTO family_member (user_profile_id, gender, first_name, last_name, date_of_birth, phone, card_id) VALUES (?,?,?,?,?,?,?)",
				new Object[] { familyMember.getUser_profile_id(), familyMember.getGender(), familyMember.getFirst_name(),
						familyMember.getLast_name(), familyMember.getDate_of_birth(), familyMember.getPhone(), familyMember.getCard_id()});

	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return FamilyMember
	 * Mapper for family member.
	 */
	public class FamilyMemberMapper implements RowMapper {

		public FamilyMember mapRow(ResultSet rs, int arg1) throws SQLException {
			FamilyMember familyMember = new FamilyMember();
			familyMember.setMember_id(rs.getInt("member_id"));
			familyMember.setUser_profile_id(rs.getInt("user_profile_id"));
			familyMember.setFirst_name(rs.getString("first_name"));
			familyMember.setLast_name(rs.getString("last_name"));
			familyMember.setDate_of_birth(rs.getString("date_of_birth"));
			familyMember.setPhone(rs.getString("phone"));
			familyMember.setCard_id(rs.getString("card_id"));
			familyMember.setGender(rs.getString("gender"));
			return familyMember;
		}
	}

}

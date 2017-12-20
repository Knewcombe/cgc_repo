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

public class FamilyMemberDAOImpl implements FamilyMemberDAO {

	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<FamilyMember> getFamilyMember(int user_profile_id) {
		// TODO Auto-generated method stub
		List<FamilyMember> familyMember = null;

		familyMember = this.jdbcTemplate.query("SELECT * FROM family_member WHERE user_profile_id = ?",
				new Object[] { user_profile_id }, new FamilyMemberMapper());

		return familyMember.size() > 0 ? familyMember : null;
	}

	public void setFamilyMember(FamilyMember familyMember) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update(
				"INSERT INTO family_member (user_profile_id, first_name, last_name, date_of_birth) VALUES (?,?,?,?)",
				new Object[] { familyMember.getUser_profile_id(), familyMember.getFirst_name(),
						familyMember.getLast_name(), familyMember.getDate_of_birth()});

	}

	public class FamilyMemberMapper implements RowMapper {

		public FamilyMember mapRow(ResultSet rs, int arg1) throws SQLException {
			FamilyMember familyMember = new FamilyMember();
			familyMember.setMember_id(rs.getInt("member_id"));
			familyMember.setUser_profile_id(rs.getInt("user_profile_id"));
			familyMember.setFirst_name(rs.getString("first_name"));
			familyMember.setLast_name(rs.getString("last_name"));
			familyMember.setDate_of_birth(rs.getString("date_of_birth"));
			return familyMember;
		}
	}

}

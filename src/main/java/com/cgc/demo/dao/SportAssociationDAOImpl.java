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

import com.cgc.demo.dao.UserProfileDAOImpl.UserProfileMapper;
import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.UserProfile;

public class SportAssociationDAOImpl implements SportAssociationDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public SportAssociation getSportAssociation(int account_id){
		List<SportAssociation> association = null;

		association = this.jdbcTemplate.query("SELECT * FROM sport_association WHERE association_account_id = ?",
				new Object[] { account_id },new AssociationMapper());

		return association.size() > 0 ? association.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public SportAssociation getAssoication(int index) {
		List<SportAssociation> association = null;

		association = this.jdbcTemplate.query("SELECT * FROM sport_association WHERE association_id = ?",
				new Object[] { index },new AssociationMapper());

		return association.size() > 0 ? association.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SportAssociation> getAssoication() {
		List<SportAssociation> association = null;

		association = this.jdbcTemplate.query("SELECT * FROM sport_association",new AssociationMapper());

		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SportAssociation> getProvinces(){
		List<SportAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT province_code FROM sport_association", new ProvinceMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SportAssociation> getCommunities(String province_code){
		List<SportAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT community FROM sport_association WHERE province_code = ?",
				new Object[] { province_code },new CommunityMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SportAssociation> getSports(String province_code, String community){
		List<SportAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT sport FROM sport_association WHERE province_code = ? AND community = ?",
				new Object[] { province_code, community },new SportMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SportAssociation> getAssociationName(String province_code, String community, String sport){
		List<SportAssociation> association = null;
		
		if(sport != ""){
			association = this.jdbcTemplate.query("SELECT association_id, name FROM sport_association WHERE province_code = ? AND community = ? AND sport = ?",
					new Object[] { province_code, community, sport },new AssociationNameMapper());
		}else{
			association = this.jdbcTemplate.query("SELECT association_id, name FROM sport_association WHERE province_code = ? AND community = ?",
					new Object[] { province_code, community },new AssociationNameMapper());
		}
		
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public SportAssociation getSportNames(int association_id){
		List<SportAssociation> association = null;
		association = this.jdbcTemplate.query("SELECT association_id, name FROM sport_association WHERE association_id = ?",
				new Object[] { association_id },new AssociationNameMapper());
		return association.size() > 0 ? association.get(0) : null;
	}
	
	public class AssociationMapper implements RowMapper {

		public SportAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			SportAssociation association = new SportAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setProvince_code(rs.getString("province_code"));
			association.setCommunity(rs.getString("community"));
			association.setSport(rs.getString("sport"));
			association.setName(rs.getString("name"));
			return association;
		}
	}
	
	
	public class ProvinceMapper implements RowMapper {

		public SportAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			SportAssociation association = new SportAssociation();
			association.setProvince_code(rs.getString("province_code"));
			return association;
		}
	}
	
	public class CommunityMapper implements RowMapper {

		public SportAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			SportAssociation association = new SportAssociation();
			association.setCommunity(rs.getString("community"));
			return association;
		}
	}
	
	public class SportMapper implements RowMapper {

		public SportAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			SportAssociation association = new SportAssociation();
			association.setSport(rs.getString("sport"));
			return association;
		}
	}
	
	public class AssociationNameMapper implements RowMapper{
		public SportAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			SportAssociation association = new SportAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setName(rs.getString("name"));
			return association;
		}
	}

}

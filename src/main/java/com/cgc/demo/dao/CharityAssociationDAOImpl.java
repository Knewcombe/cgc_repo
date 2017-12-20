package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.CharityAssociation;

public class CharityAssociationDAOImpl implements CharityAssociationDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public CharityAssociation getCharity(int account_id){
		List<CharityAssociation> association = null;
		association = this.jdbcTemplate.query("SELECT * FROM charity_association WHERE association_account_id = ?",
				new Object[] { account_id },new CharityMapper());
		return association.size() > 0 ? association.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getProvince(){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT province_code FROM charity_association", new ProvinceMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getCharityCommunity(String province_code){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT community FROM charity_association WHERE province_code = ?",
				new Object[] { province_code },new CommunityMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getCharityName(String province_code, String community){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT association_id, name FROM charity_association WHERE province_code = ? AND community = ?",
				new Object[] { province_code, community},new NameMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	@SuppressWarnings("unchecked")
	public CharityAssociation getCharityName(int charity_id){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT association_id, name FROM charity_association WHERE association_id = ?",
				new Object[] { charity_id },new NameMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	public class CharityMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setProvince_code(rs.getString("province_code"));
			association.setCommunity(rs.getString("community"));
			association.setName(rs.getString("name"));
			return association;
		}
	}
	
	public class ProvinceMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setProvince_code(rs.getString("province_code"));
			return association;
		}
	}
	
	public class CommunityMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setCommunity(rs.getString("community"));
			return association;
		}
	}
	
	public class NameMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setName(rs.getString("name"));
			return association;
		}
	}

}

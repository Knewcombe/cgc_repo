package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.CharityAssociationDAOImpl.CommunityMapper;
import com.cgc.demo.dao.CharityAssociationDAOImpl.NameMapper;
import com.cgc.demo.dao.CharityAssociationDAOImpl.ProvinceMapper;
import com.cgc.demo.model.NonProfAssociation;

public class NonProfDAOImpl implements NonProfDAO {
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public NonProfAssociation getNonProf(int account_id){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM nonprof_association WHERE association_account_id = ?",
				new Object[] { account_id },new NonProfMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getProvince(){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT province_code FROM nonprof_association", new ProvinceMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getCommunity(String province_code){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT community FROM nonprof_association WHERE province_code = ?",
				new Object[] { province_code },new CommunityMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getName(String province_code, String community){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT association_id, name FROM nonprof_association WHERE province_code = ? AND community = ?",
				new Object[] { province_code, community},new NameMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	@SuppressWarnings("unchecked")
	public NonProfAssociation getName(int nonProf_id){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT association_id, name FROM nonprof_association WHERE association_id = ?",
				new Object[] { nonProf_id },new NameMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	public class NonProfMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setProvince_code(rs.getString("province_code"));
			association.setCommunity(rs.getString("community"));
			association.setName(rs.getString("name"));
			return association;
		}
	}
	
	public class ProvinceMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setProvince_code(rs.getString("province_code"));
			return association;
		}
	}
	
	public class CommunityMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setCommunity(rs.getString("community"));
			return association;
		}
	}
	
	public class NameMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setName(rs.getString("name"));
			return association;
		}
	}

}

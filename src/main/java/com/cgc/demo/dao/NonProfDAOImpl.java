package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.NonProfAssociation;

/**
 * Non Prof DAO will get all information needed for Non Prof model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class NonProfDAOImpl implements NonProfDAO {
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int account_id
	 * @return NonProfAssociation
	 * Get the non prof base on account_id
	 */
	@SuppressWarnings("unchecked")
	public NonProfAssociation getNonProf(int account_id){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM nonprof_association WHERE association_account_id = ?",
				new Object[] { account_id },new NonProfMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<NonProfAssociation>
	 * get all the provinces for nonProf associations
	 */
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getProvince(){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT province_code FROM nonprof_association", new ProvinceMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String province_code
	 * @return List<NonProfAssociation>
	 * get all communities based on the province_code
	 */
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getCommunity(String province_code){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT community FROM nonprof_association WHERE province_code = ?",
				new Object[] { province_code },new CommunityMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String province_code, String community
	 * @return List<NonProfAssociation>
	 * Getting all the name from non prof association based on the province code and community
	 */
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getName(String province_code, String community){
		
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT association_id, name FROM nonprof_association WHERE province_code = ? AND community = ?",
				new Object[] { province_code, community},new NameMapper());
		
		return association.size() > 0 ? association : null;
		
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<NonProfAssociation>
	 * Get all non prof for viewing
	 */
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> getNonProf(){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM nonprof_association", new NonProfMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int nonprof_id
	 * @return NonProfAssociation
	 * Get non prof info for association account.
	 */
	@SuppressWarnings("unchecked")
	public NonProfAssociation getNonProfInfo(int nonprof_id){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM nonprof_association WHERE association_id = ?",
				new Object[] { nonprof_id },new NonProfMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String search
	 * @return List<NonProfAssociation>
	 * Get nonprof based on search string. Will return all nonprofs that match.
	 */
	@SuppressWarnings("unchecked")
	public List<NonProfAssociation> searchNonProf(String search){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT *, MATCH ( `name`, `community`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) as `rel` FROM `nonprof_association` WHERE MATCH ( `name`, `community`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) ORDER BY `rel` DESC",
				new Object[] { search, search }, new NonProfMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int nonProf_id
	 * @return NonProfAssociation
	 * Get the name of none prof based on id
	 */
	@SuppressWarnings("unchecked")
	public NonProfAssociation getName(int nonProf_id){
		List<NonProfAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT association_id, name FROM nonprof_association WHERE association_id = ?",
				new Object[] { nonProf_id },new NameMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return NonProfAssociation
	 * Mapper for full nonprof model
	 */
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

	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return NonProfAssociation
	 * Mapper for province code
	 */
	public class ProvinceMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setProvince_code(rs.getString("province_code"));
			return association;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return NonProfAssociation
	 * Mapper for community
	 */
	public class CommunityMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setCommunity(rs.getString("community"));
			return association;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return NonProfAssociation
	 * Mapper for name
	 */
	public class NameMapper implements RowMapper {

		public NonProfAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			NonProfAssociation association = new NonProfAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setName(rs.getString("name"));
			return association;
		}
	}

}

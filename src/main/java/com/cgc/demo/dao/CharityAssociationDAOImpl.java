package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.dao.UserProfileDAOImpl.UserProfileMapper;
import com.cgc.demo.model.CharityAssociation;

/**
 * Charity Association DAO will get all information needed for CharityAssociation model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class CharityAssociationDAOImpl implements CharityAssociationDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int account_id
	 * @return CharityAssociation
	 * Getting the charity association information for AssociationAccount
	 */
	@SuppressWarnings("unchecked")
	public CharityAssociation getCharity(int account_id){
		List<CharityAssociation> association = null;
		association = this.jdbcTemplate.query("SELECT * FROM charity_association WHERE association_account_id = ?",
				new Object[] { account_id },new CharityMapper());
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<CharityAssociation>
	 * Getting all province code for all charity
	 */
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getProvince(){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT province_code FROM charity_association", new ProvinceMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String province_code
	 * @return List<CharityAssociation>
	 * Get all communities based on selected province code
	 */
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getCharityCommunity(String province_code){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT community FROM charity_association WHERE province_code = ?",
				new Object[] { province_code },new CommunityMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String province_code, String community
	 * @return List<CharityAssociation>
	 * Get all charities names based on selected province code and community
	 */
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getCharityName(String province_code, String community){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT DISTINCT association_id, name FROM charity_association WHERE province_code = ? AND community = ?",
				new Object[] { province_code, community},new NameMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int charity_id
	 * @return CharityAssociation
	 * Get charity name based on the selected charity_id
	 */
	@SuppressWarnings("unchecked")
	public CharityAssociation getCharityName(int charity_id){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT association_id, name FROM charity_association WHERE association_id = ?",
				new Object[] { charity_id },new NameMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @return List<CharityAssociation>
	 * Get all charity association to view
	 */
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> getCharity(){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM charity_association", new CharityMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param String search
	 * @return List<CharityAssociation>
	 * Searching for the charity with search string. Will return a list of Charities that match the search string.
	 */
	@SuppressWarnings("unchecked")
	public List<CharityAssociation> searchCharity(String search){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT *, MATCH ( `community`, `name`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) as `rel` FROM `charity_association` WHERE MATCH ( `community`, `name`) AGAINST ('+'?'*' IN NATURAL LANGUAGE MODE) ORDER BY `rel` DESC",
				new Object[] { search, search }, new CharityMapper());
		
		return association.size() > 0 ? association : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int charity_id
	 * @return CharityAssociation
	 * Getting the information for Charity with charity_id to view.
	 */
	@SuppressWarnings("unchecked")
	public CharityAssociation getCharityInfo(int charity_id){
		List<CharityAssociation> association = null;
		
		association = this.jdbcTemplate.query("SELECT * FROM charity_association WHERE association_id = ?",
				new Object[] { charity_id },new CharityMapper());
		
		return association.size() > 0 ? association.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return CharityAssociation
	 * Mapper for full CharityAssociation model
	 */
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
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return CharityAssociation
	 * Mapper to show province_code
	 */
	public class ProvinceMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setProvince_code(rs.getString("province_code"));
			return association;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return CharityAssociation
	 * Mapper to show all communities
	 */
	public class CommunityMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setCommunity(rs.getString("community"));
			return association;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return CharityAssociation
	 * Mapper for charity name
	 */
	public class NameMapper implements RowMapper {

		public CharityAssociation mapRow(ResultSet rs, int arg1) throws SQLException {
			CharityAssociation association = new CharityAssociation();
			association.setAssociation_id(rs.getInt("association_id"));
			association.setName(rs.getString("name"));
			return association;
		}
	}

}

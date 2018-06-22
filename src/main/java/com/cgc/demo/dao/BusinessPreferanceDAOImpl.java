package com.cgc.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.BusinessPreference;

/**
 * Business Preference DAO will get all information needed for BusinessPreference model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class BusinessPreferanceDAOImpl implements BusinessPreferanceDAO{
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param BusinessPreference businessPreference
	 * @return void
	 * Storing the business preference into the database.
	 */
	public void setBusinessPreferance(BusinessPreference businessPreference){
		System.out.println(businessPreference.getBusiness_profile_id() +" "+
			  			businessPreference.getPreference_id() +" "+
			  			businessPreference.getName()+" "+
			  			businessPreference.getCash_percent()+" "+
			  			businessPreference.getDebit_percent()+" "+
			  			businessPreference.getCredit_percent()+" "+
			  			businessPreference.getSale_clearance_percent());
		jdbcTemplate.update("INSERT INTO business_preference (business_profile_id, preference_id, name, cash_percent, debit_percent, credit_percent, sale_clearance_percent) VALUES (?,?,?,?,?,?,?)", 
			      new Object[] { 
			    		businessPreference.getBusiness_profile_id(),
			  			businessPreference.getPreference_id(),
			  			businessPreference.getName(),
			  			businessPreference.getCash_percent(),
			  			businessPreference.getDebit_percent(),
			  			businessPreference.getCredit_percent(),
			  			businessPreference.getSale_clearance_percent()
			      });
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int business_profile_id
	 * @return List<BusinessPreference>
	 * Get all business preference for business profile.
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessPreference> getAllBusinessPreference(int business_profile_id){
		List<BusinessPreference> businessPreference = null;
		System.err.println("Getting business preferance");
		businessPreference = this.jdbcTemplate.query("SELECT * FROM business_preference WHERE business_profile_id = ?",
				new Object[] { business_profile_id },new BusinessPreferenceMapper());
		
		return businessPreference.size() > 0 ? businessPreference : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param BusinessPreference
	 * @return ResultSet rs, int arg1
	 * Mapper for BusinessPreference
	 */
	public class BusinessPreferenceMapper implements RowMapper {

		public BusinessPreference mapRow(ResultSet rs, int arg1) throws SQLException {
			BusinessPreference businessPreference = new BusinessPreference();
			businessPreference.setBusiness_profile_id(rs.getInt("business_profile_id"));
			businessPreference.setPreference_id(rs.getInt("preference_id"));
			businessPreference.setName(rs.getString("name"));
			businessPreference.setCash_percent(rs.getDouble("cash_percent"));
			businessPreference.setDebit_percent(rs.getDouble("debit_percent"));
			businessPreference.setCredit_percent(rs.getDouble("credit_percent"));
			businessPreference.setSale_clearance_percent(rs.getDouble("sale_clearance_percent"));
			return businessPreference;
		}
	}
}

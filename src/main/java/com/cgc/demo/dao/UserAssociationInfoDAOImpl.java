package com.cgc.demo.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cgc.demo.model.UserAssociationInfo;

/**
 * User Association Info DAO will get all information needed for User Association Info model
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
public class UserAssociationInfoDAOImpl implements UserAssociationInfoDAO {
	
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserAssociationInfo userAssociationInfo
	 * @return void
	 * Setting user association info
	 */
	public void setUserAssociationInfo(UserAssociationInfo userAssociationInfo){
		this.jdbcTemplate.update("INSERT INTO user_association_info (user_association_id, user_profile_id, total_amount, start_date) VALUES (?,?,?,NOW())", 
			      new Object[] { 
			    		  userAssociationInfo.getUser_association_id(),
			    		  userAssociationInfo.getUser_profile_id(),
			    		  userAssociationInfo.getTotal_amount()
			      });
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_association_id, int user_profile_id
	 * @return void
	 * Setting user association info (with out object)
	 */
	public void setUserAssociationInfo(int user_association_id, int user_profile_id){
		this.jdbcTemplate.update("INSERT INTO user_association_info (user_association_id, user_profile_id, total_amount, start_date) VALUES (?,?,0.00,NOW())", 
			      new Object[] { 
			    		  user_association_id,
			    		  user_profile_id
			      });
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_association_id
	 * @return UserAssociationInfo
	 * get User association info for user association
	 */
	@SuppressWarnings("unchecked")
	public UserAssociationInfo getUserAssociationInfo(int user_association_id ){
		List<UserAssociationInfo> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association_info WHERE user_association_id = ?",
				new Object[] { user_association_id },new UserAssociationInfoMapper());
		return userAssociation.size() > 0 ? userAssociation.get(0) : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return List<UserAssociationInfo>
	 * get all User association info for user profile id
	 */
	@SuppressWarnings("unchecked")
	public List<UserAssociationInfo> getAllUserAssociationInfo(int user_profile_id){
		List<UserAssociationInfo> userAssociation = null;

		userAssociation = this.jdbcTemplate.query("SELECT * FROM user_association_info WHERE user_profile_id = ?",
				new Object[] { user_profile_id },new UserAssociationInfoMapper());
		return userAssociation.size() > 0 ? userAssociation : null;
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param int user_profile_id
	 * @return BigDecimal
	 * Get the sum of all user association info for user profile
	 */
	public BigDecimal getSumOfUser(int user_profile_id){
		BigDecimal total;
		try{
			total = jdbcTemplate.queryForObject("SELECT SUM(total_amount) FROM user_association_info WHERE user_profile_id = ?",
					new Object[] { user_profile_id }, BigDecimal.class);
			return total;
		}catch(Exception e){
			return new BigDecimal("0.0");
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param UserAssociationInfo userAssociationInfo
	 * @return void
	 * update user association
	 */
	public void updateUserAssociationInfo(UserAssociationInfo userAssociationInfo){
		
		this.jdbcTemplate.update("UPDATE user_association_info set total_amount = ? WHERE user_association_id = ?", 
			      new Object[] { 
			    		  userAssociationInfo.getTotal_amount(),
			    		  userAssociationInfo.getUser_association_id()
			      });
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param ResultSet rs, int arg1
	 * @return UserAssociationInfo
	 * Mapper for user association info
	 */
	public class UserAssociationInfoMapper implements RowMapper {

		public UserAssociationInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserAssociationInfo userAssociation = new UserAssociationInfo();
			userAssociation.setUser_association_id(rs.getInt("user_association_id"));
			userAssociation.setTotal_amount(rs.getBigDecimal("total_amount"));
			userAssociation.setUser_profile_id(rs.getInt("user_profile_id"));
			userAssociation.setStart_date(rs.getString("start_date"));
			userAssociation.setEnd_date(rs.getString("end_date"));
			return userAssociation;
		}
	}

}

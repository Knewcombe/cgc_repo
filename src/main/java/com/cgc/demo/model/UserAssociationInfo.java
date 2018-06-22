package com.cgc.demo.model;

import java.math.BigDecimal;

/**
 * UserAssociationInfo.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * UserAssociationInfo
 * 
 * Object used to store information for each UserAssociations.
 *
 */

public class UserAssociationInfo {
	
	private int user_association_id;
	private BigDecimal total_amount = new BigDecimal("0.00");
	private int user_association_info_id;
	private String start_date;
	private String end_date;
	private int user_profile_id;
	
	public int getUser_association_id() {
		return user_association_id;
	}
	public void setUser_association_id(int user_association_id) {
		this.user_association_id = user_association_id;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	public int getUser_association_info_id() {
		return user_association_info_id;
	}
	public void setUser_association_info_id(int user_association_info_id) {
		this.user_association_info_id = user_association_info_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getUser_profile_id() {
		return user_profile_id;
	}
	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
	}
	
	

}

package com.cgc.demo.model;

/**
 * CharityAssociation.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * CharityAssociation 
 * 
 * Charity Association will hold information for Chairty Assoications
 *
 */

public class CharityAssociation {
	
	private int association_id;
	private String province_code;
	private String community;
	private String type;
	private String name;
	
	public int getAssociation_id() {
		return association_id;
	}
	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

package com.cgc.demo.model;
/**
 * AssociationProfile.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * AssociationProfile
 * 
 * Profile for the Association account. Only used for back end not ment to be shown to other users.
 *
 */
public class AssociationProfile {
	
	int association_profile_id;
	
	int association_account_id;
	
	String province_code;
	
	String city;
	
	String address;
	
	String prostal_code;
	
	String phone;
	
	String email;

	public int getAssociation_profile_id() {
		return association_profile_id;
	}

	public void setAssociation_profile_id(int association_profile_id) {
		this.association_profile_id = association_profile_id;
	}

	public int getAssociation_account_id() {
		return association_account_id;
	}

	public void setAssociation_account_id(int association_account_id) {
		this.association_account_id = association_account_id;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProstal_code() {
		return prostal_code;
	}

	public void setProstal_code(String prostal_code) {
		this.prostal_code = prostal_code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	  

}

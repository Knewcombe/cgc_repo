package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class BusinessProfile {
	
	private int business_profile_id;
	private int business_account_id;
	
	@NotEmpty(message = "Please Enter a City")
	private String city;
	@NotEmpty(message = "Please Select a Province")
	private String province_code;
	@NotEmpty(message = "Please enter a Address")
	private String address;
	@NotEmpty(message = "Please Enter a Postal code")
	@Size(min = 6, max = 6, message = "Postal code must be 6 characters")
	private String postal_code;
	@NotEmpty(message = "Please enter a phone number")
	private String phone;
	@NotEmpty(message = "Please enter a email")
	@Email(message = "Please enter a valid email")
	private String email;
	@NotEmpty(message = "Please enter a business name")
	private String business_name;
	@NotEmpty(message = "Please enter a main contact")
	private String main_contact;
	
	private List<BusinessPreference> businessPreferance = new ArrayList<BusinessPreference>();
	
	public int getBusiness_profile_id() {
		return business_profile_id;
	}
	public void setBusiness_profile_id(int business_profile_id) {
		this.business_profile_id = business_profile_id;
	}
	public int getBusiness_account_id() {
		return business_account_id;
	}
	public void setBusiness_account_id(int business_acount_id) {
		this.business_account_id = business_acount_id;
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
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
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
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getMain_contact() {
		return main_contact;
	}
	public void setMain_contact(String main_contact) {
		this.main_contact = main_contact;
	}
	
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public List<BusinessPreference> getBusinessPreferance() {
		return businessPreferance;
	}
	
	public BusinessPreference getBusinessPreferance(int index) {
		return businessPreferance.get(index);
	}
	
	public void setBusinessPreferance(List<BusinessPreference> businessPreferance) {
		this.businessPreferance = businessPreferance;
	}
	
	public void setBusinessPreferance(BusinessPreference businessPreferance) {
		this.businessPreferance.add(businessPreferance);
	}
	
}

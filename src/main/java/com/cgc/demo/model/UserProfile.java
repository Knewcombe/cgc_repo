package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

//import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class UserProfile {
	
	private int user_profile_id;
	private int user_account_id;
	private String card_id;
	
    @NotEmpty(message = "Please enter a first name")
    private String first_name;
	
	@NotEmpty(message = "Please enter a last name")
	private String last_name;
	
	//@Column(name = "DATE_OF_BIRTH")  
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	@NotEmpty(message = "Please enter your date of birth")
	private String date_of_birth;
	
	@NotEmpty(message = "Please enter your gender")
	private String gender;
	
	@NotEmpty(message = "Please select a province")
	private String province_code;
	
	@NotEmpty(message = "Please enter a city")
	private String city;
	
	@NotEmpty(message = "Please enter a address")
	private String address;
	
	@NotEmpty(message = "Please enter a postal code")
	@Size(min = 6, max = 6, message="Postal code must be 6 characters")
	private String postal_code;
	
	@NotEmpty(message = "Please enter a phone number")
	@Size(min = 10, max = 10, message="Phone number must be 10 digites")
	private String phone;
	
	@NotEmpty(message = "Please enter a email")
	@Email(message = "Please enter a valid email")
	private String email;
	
	private List<FamilyMember> family = new ArrayList<FamilyMember>();
	
	
	private List<UserAssociation> userAssociation = new ArrayList<UserAssociation>();

	public int getUser_profile_id() {
		return user_profile_id;
	}

	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
	}

	public int getUser_account_id() {
		return user_account_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUser_account_id(int user_account_id) {
		this.user_account_id = user_account_id;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
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

	public List<FamilyMember> getFamily() {
		return family;
	}
	
	public FamilyMember getFamily(int index) {
		return family.get(index);
	}

	public void setFamily(List<FamilyMember> family) {
		this.family = family;
	}
	
	public void setFamily(FamilyMember family) {
		this.family.add(family);
	}

	public List<UserAssociation> getUserAssociation() {
		return userAssociation;
	}
	
	public UserAssociation getUserAssociation(int index) {
		return userAssociation.get(index);
	}

	public void setUserAssociation(List<UserAssociation> userAssociation) {
		this.userAssociation = userAssociation;
	}
	
	public void setUserAssociation(UserAssociation userAssociation) {
		this.userAssociation.add(userAssociation);
	}
	
}

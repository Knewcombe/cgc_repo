package com.cgc.demo.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class FamilyMember {
	
	private int member_id;
	private int user_profile_id;
	
	@NotEmpty(message = "Please enter a first name")
	private String first_name;
	
	@NotEmpty(message = "Please enter a last name")
	private String last_name;
	
	@NotEmpty(message = "Please enter the date of birth")
	@DateTimeFormat
	private String date_of_birth;


	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getUser_profile_id() {
		return user_profile_id;
	}

	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
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

}

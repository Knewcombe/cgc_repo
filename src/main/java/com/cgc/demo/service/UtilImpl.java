package com.cgc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  

public class UtilImpl implements Util{
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	public String encodePassword(String password){
		return bcryptEncoder.encode(password);
	}
	
	public Boolean checkPassword(String password, String encodedPassword){
		//System.out.println(bcryptEncoder.encode(password));
		return bcryptEncoder.matches(password, encodedPassword);
	}
}

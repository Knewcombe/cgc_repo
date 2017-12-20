package com.cgc.demo.dao;

import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.Login;

public interface BusinessAccountDAO {
	
	public BusinessAccount login(Login login);
	
	public int registerUser(BusinessAccount businessAccount);
	
	public boolean isValid(String username);
	
	public String getPassword(String username);
}

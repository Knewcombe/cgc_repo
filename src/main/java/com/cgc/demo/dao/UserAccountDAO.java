package com.cgc.demo.dao;

import com.cgc.demo.model.Login;
import com.cgc.demo.model.UserAccount;

public interface UserAccountDAO{
	
	public UserAccount login(Login login);
	
	public int registerUser(UserAccount userAccount);
	
	public boolean isValid(String username);
	
	public String getPassword(String username);
	
	public boolean checkUsername(String username);
	
	public void changePassword(UserAccount userAccount);
	
}

package com.cgc.demo.dao;

import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.Login;

public interface AssociationAccountDAO {
	
	public AssociationAccount login(Login login);
	
	public String getPassword(String username);
	
	public boolean checkUsername(String username);

}

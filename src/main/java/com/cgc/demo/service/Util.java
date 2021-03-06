package com.cgc.demo.service;

import java.io.ByteArrayInputStream;

import com.cgc.demo.model.Request;

public interface Util {
	
	public String encodePassword(String password);
	
	public Boolean checkPassword(String password, String encodedPassword);
	
	public ByteArrayInputStream generateAssociationReport(int association_id);
	
	public ByteArrayInputStream generateBusinessReport(int business_id);
	
	public ByteArrayInputStream generateTeamReport(int team_id);
	
	public ByteArrayInputStream generatePlayerReport(int player_id);
	
	public ByteArrayInputStream generateUserReport(int user_profile_id);
	
	public boolean checkUserName(String userName);
	
	public void sendMail(Request request);
}

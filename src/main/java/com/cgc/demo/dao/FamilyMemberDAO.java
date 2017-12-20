package com.cgc.demo.dao;

import java.util.List;

import com.cgc.demo.model.FamilyMember;

public interface FamilyMemberDAO {
	
	public List<FamilyMember> getFamilyMember(int user_profile_id);
	
	public void setFamilyMember(FamilyMember familyMember);

}

package com.cgc.demo.model;
/**
 * AssociationAccount.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * AssociationAccount 
 * 
 * Used to hold information for the associator account after logging in.
 * It will hold objects for each type, but will only used one that has been gathered
 * from the database.
 *
 */
public class AssociationAccount {
	
	int association_id;
	
	String username;
	
	AssociationProfile associationProfile = null;
	
	CharityAssociation charityAssociation = null;
	
	SportAssociation sportAssociation = null;
	
	NonProfAssociation nonProfAssociation = null;

	public int getAssociation_id() {
		return association_id;
	}

	public void setAssociation_id(int association_id) {
		this.association_id = association_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CharityAssociation getCharityAssociation() {
		return charityAssociation;
	}

	public void setCharityAssociation(CharityAssociation charityAssociation) {
		this.charityAssociation = charityAssociation;
	}

	public SportAssociation getSportAssociation() {
		return sportAssociation;
	}

	public void setSportAssociation(SportAssociation sportAssociation) {
		this.sportAssociation = sportAssociation;
	}

	public NonProfAssociation getNonProfAssociation() {
		return nonProfAssociation;
	}

	public void setNonProfAssociation(NonProfAssociation nonProfAssociation) {
		this.nonProfAssociation = nonProfAssociation;
	}

	public AssociationProfile getAssociationProfile() {
		return associationProfile;
	}

	public void setAssociationProfile(AssociationProfile associationProfile) {
		this.associationProfile = associationProfile;
	}

}

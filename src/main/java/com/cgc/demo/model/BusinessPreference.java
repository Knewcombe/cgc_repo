package com.cgc.demo.model;

/**
 * BusinessPreferences.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * BusinessPreferences 
 * 
 * Business preferences will allow the business to set the type of transaction they will send to the server.
 * All information will come from the database and cannot be changed.
 * 
 * This object is held within the business profile to reference when needed.
 * 
 * This object will also allow for setting up Transaction object.
 *
 */

public class BusinessPreference {
	
	private int business_profile_id;
	
	private int preference_id;
	
	private String name;
	
	private double debit_percent;
	
	private double cash_percent;
	
	private double credit_percent;
	
	private double sale_clearance_percent;

	public int getBusiness_profile_id() {
		return business_profile_id;
	}

	public void setBusiness_profile_id(int business_profile_id) {
		this.business_profile_id = business_profile_id;
	}

	public int getPreference_id() {
		return preference_id;
	}

	public void setPreference_id(int preference_id) {
		this.preference_id = preference_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDebit_percent() {
		return debit_percent;
	}

	public void setDebit_percent(double debit_percent) {
		this.debit_percent = debit_percent;
	}

	public double getCash_percent() {
		return cash_percent;
	}

	public void setCash_percent(double cash_percent) {
		this.cash_percent = cash_percent;
	}

	public double getCredit_percent() {
		return credit_percent;
	}

	public void setCredit_percent(double credit_percent) {
		this.credit_percent = credit_percent;
	}


	public double getSale_clearance_percent() {
		return sale_clearance_percent;
	}

	public void setSale_clearance_percent(double sale_clearance_percent) {
		this.sale_clearance_percent = sale_clearance_percent;
	}
	
	

}

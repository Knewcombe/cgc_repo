package com.cgc.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
//import org.hibernate.validator.constraints.NotEmpty;

public class Transaction {
	
	private int transaction_id;
	
	@Min(value = 1, message = "Please enter a User Id")
	private int user_profile_id;
	
	private int business_profile_id;
	
	private double total;
	
	private double precent_total;
	
	private String date_of_purhase;
	
	private List<TransactionDetail> transactionDetail = new ArrayList<TransactionDetail>();

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getUser_profile_id() {
		return user_profile_id;
	}

	public void setUser_profile_id(int user_profile_id) {
		this.user_profile_id = user_profile_id;
	}

	public int getBusiness_profile_id() {
		return business_profile_id;
	}

	public void setBusiness_profile_id(int business_profile_id) {
		this.business_profile_id = business_profile_id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDate_of_purhase() {
		return date_of_purhase;
	}

	public void setDate_of_purhase(String date_of_purhase) {
		this.date_of_purhase = date_of_purhase;
	}

	public List<TransactionDetail> getTransactionDetail() {
		return transactionDetail;
	}
	
	public TransactionDetail getTransactionDetail(int index) {
		return transactionDetail.get(index);
	} 

	public void setTransactionDetail(List<TransactionDetail> transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
	
	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail.add(transactionDetail);
	}

	public double getPrecent_total() {
		return precent_total;
	}

	public void setPrecent_total(double precent_total) {
		this.precent_total = precent_total;
	}
	
}

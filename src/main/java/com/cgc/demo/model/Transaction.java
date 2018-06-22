package com.cgc.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Transaction.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * Transaction
 * 
 * Transaction object used to store transaction information.
 *
 */

public class Transaction {
	
	private int transaction_id;
	
	@Min(value = 1, message = "Please enter a User Id")
	private int user_profile_id;
	
	private int business_profile_id;
	
	private String date_of_purchase;
	
	private BigDecimal total = new BigDecimal("0.00");
	
	private BigDecimal precent_total = new BigDecimal("0.00");
	
	private BigDecimal fee_total = new BigDecimal("0.00");
	
	private BigDecimal funds_total = new BigDecimal("0.00");
	@JsonUnwrapped
	private List<TransactionDetail> transactionDetail = new ArrayList<TransactionDetail>();
	
//	public Transaction(int transaction_id, int user_profile_id, int business_profile_id, String date_of_purcahse, int total, int precent_total, int fee_total, int funds_total){
//		this.transaction_id = transaction_id;
//		this.user_profile_id = user_profile_id;
//		this.business_profile_id = business_profile_id;
//		this.date_of_purhase = date_of_purcahse;
//		this.total = BigDecimal.valueOf(total);
//		this.precent_total = BigDecimal.valueOf(precent_total);
//		this.fee_total = BigDecimal.valueOf(fee_total);
//		this.funds_total = BigDecimal.valueOf(funds_total);
//	}

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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDate_of_purchase() {
		return date_of_purchase;
	}

	public void setDate_of_purchase(String date_of_purchase) {
		this.date_of_purchase = date_of_purchase;
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
	@JsonIgnore
	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail.add(transactionDetail);
	}

	public BigDecimal getPrecent_total() {
		return precent_total;
	}

	public void setPrecent_total(BigDecimal precent_total) {
		this.precent_total = precent_total;
	}

	public BigDecimal getFee_total() {
		return fee_total;
	}

	public void setFee_total(BigDecimal fee_total) {
		this.fee_total = fee_total;
	}

	public BigDecimal getFunds_total() {
		return funds_total;
	}

	public void setFunds_total(BigDecimal funds_total) {
		this.funds_total = funds_total;
	}
	
	
}

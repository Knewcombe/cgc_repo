package com.cgc.demo.model;

import java.math.BigDecimal;

/**
 * TransactionDetail.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * TransactionDetail
 * 
 * TransactionDetail object used to store transaction information.
 * Stored within the Transaction object.
 *
 */

public class TransactionDetail {
	
	private int transaction_details_id;
	
	private int transaction_id;
	
	private String name;
	
	private int transaction_type;
	
	private String method_of_payment;
	
	private BigDecimal precent_amount = new BigDecimal("0.00");;
	
	private BigDecimal transaction_rate = new BigDecimal("0.00");;
	
	private BigDecimal amount = new BigDecimal("0.00");;
	
	private BigDecimal fee = new BigDecimal("0.00");;
	
	private BigDecimal funds = new BigDecimal("0.00");;

	public int getTransaction_details_id() {
		return transaction_details_id;
	}

	public void setTransaction_details_id(int transaction_details_id) {
		this.transaction_details_id = transaction_details_id;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrecent_amount() {
		return precent_amount;
	}

	public void setPrecent_amount(BigDecimal precent_amount) {
		this.precent_amount = precent_amount;
	}

	public int getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(int transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getMethod_of_payment() {
		return method_of_payment;
	}

	public void setMethod_of_payment(String method_of_pyment) {
		this.method_of_payment = method_of_pyment;
	}

	public BigDecimal getTransaction_rate() {
		return transaction_rate;
	}

	public void setTransaction_rate(BigDecimal transaction_rate) {
		this.transaction_rate = transaction_rate;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}
	
}

package com.cgc.demo.model;

public class TransactionDetail {
	
	private int transaction_details_id;
	
	private int transaction_id;
	
	private String name;
	
	private double amount;
	
	private int transaction_type;
	
	private String method_of_pyment;
	
	private double precent_amount;
	
	private double transaction_rate;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPrecent_amount() {
		return precent_amount;
	}

	public void setPrecent_amount(double precent_amount) {
		this.precent_amount = precent_amount;
	}

	public int getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(int transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getMethod_of_pyment() {
		return method_of_pyment;
	}

	public void setMethod_of_pyment(String method_of_pyment) {
		this.method_of_pyment = method_of_pyment;
	}

	public double getTransaction_rate() {
		return transaction_rate;
	}

	public void setTransaction_rate(double transaction_rate) {
		this.transaction_rate = transaction_rate;
	}

}

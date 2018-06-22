package com.cgc.demo.model;

import java.math.BigDecimal;

/**
 * MobileResponce.java
 *
 * @author Kyle Newcombe
 * @since 0.1
 */

/**
 * MobileResponce
 * 
 * This response is for when transaction is complete on mobile application.
 *
 */

public class MobileResponce {
	
	BigDecimal total;
	BigDecimal funds;
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getFunds() {
		return funds;
	}
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

}

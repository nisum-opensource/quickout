/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

public class DiscountInfo {

	private Double discount;
	private PaymentSummary paymentSummary;
	
	public DiscountInfo() {
	}

	public DiscountInfo(Double discount, PaymentSummary paymentSummary) {
		this.discount = discount;
		this.paymentSummary = paymentSummary;
	}


	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public PaymentSummary getPaymentSummary() {
		return paymentSummary;
	}

	public void setPaymentSummary(PaymentSummary paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

}

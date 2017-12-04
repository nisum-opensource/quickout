/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

/**
 * It contains the taxAmount and checkout object with the updated PaymentSummary 
 *
 */
public class TaxInfo {

	private Double tax;
	private PaymentSummary paymentSummary;
	public TaxInfo(Double tax, PaymentSummary paymentSummary) {
		super();
		this.tax = tax;
		this.paymentSummary = paymentSummary;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public PaymentSummary getPaymentSummary() {
		return paymentSummary;
	}
	public void setPaymentSummary(PaymentSummary paymentSummary) {
		this.paymentSummary = paymentSummary;
	}
	
}

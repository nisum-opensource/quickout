/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

/**
 *         It contains the shipping rate along with the updated payment
 *         information in checkout.
 */
public class ShippingRate {

	private Double price;
	private String title;
	private boolean phoneRequired;
	private PaymentSummary paymentSummary;

	public ShippingRate() {
	}

	public ShippingRate(Double price, String title, boolean phoneRequired, PaymentSummary paymentSummary) {
		this.price = price;
		this.title = title;
		this.phoneRequired = phoneRequired;
		this.paymentSummary = paymentSummary;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isPhoneRequired() {
		return phoneRequired;
	}

	public void setPhoneRequired(boolean phoneRequired) {
		this.phoneRequired = phoneRequired;
	}

	public PaymentSummary getPaymentSummary() {
		return paymentSummary;
	}

	public void setPaymentSummary(PaymentSummary paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

}

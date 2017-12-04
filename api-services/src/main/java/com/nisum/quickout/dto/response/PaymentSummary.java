/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

/**
 * PaymentSummary consists of subTotal, estimatedTax, shippingCost and totalAmount
 */
public class PaymentSummary {
	
	private Double subTotal;
	private Double shippingCost;
	private Double estimatedTax;
	//TODO: If we remove the setters for the above 3 we can calculate the totalAmount
	private Double totalAmount;
	
	public PaymentSummary(Double subTotal, Double shippingCost, Double estimatedTax, Double totalAmount) {
		this.subTotal = subTotal;
		this.shippingCost = shippingCost;
		this.estimatedTax = estimatedTax;
		this.totalAmount = totalAmount;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Double getEstimatedTax() {
		return estimatedTax;
	}

	public void setEstimatedTax(Double estimatedTax) {
		this.estimatedTax = estimatedTax;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}

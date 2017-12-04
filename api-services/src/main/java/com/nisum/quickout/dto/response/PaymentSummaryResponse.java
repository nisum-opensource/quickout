/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

public class PaymentSummaryResponse {
	private float sub_total;
	private float shipping;
	private float estimated_tax;
	private float order_total;

	public PaymentSummaryResponse() {
	}

	public PaymentSummaryResponse(float sub_total, float shipping, float estimated_tax, float order_total) {
		this.sub_total = sub_total;
		this.shipping = shipping;
		this.estimated_tax = estimated_tax;
		this.order_total = order_total;
	}

	public float getSub_total() {
		return sub_total;
	}

	public void setSub_total(float sub_total) {
		this.sub_total = sub_total;
	}

	public float getShipping() {
		return shipping;
	}

	public void setShipping(float shipping) {
		this.shipping = shipping;
	}

	public float getEstimated_tax() {
		return estimated_tax;
	}

	public void setEstimated_tax(float estimated_tax) {
		this.estimated_tax = estimated_tax;
	}

	public float getOrder_total() {
		return order_total;
	}

	public void setOrder_total(float order_total) {
		this.order_total = order_total;
	}

	@Override
	public String toString() {
		return "PaymentSummaryResponse [sub_total=" + sub_total + ", shipping=" + shipping + ", estimated_tax="
				+ estimated_tax + ", order_total=" + order_total + "]";
	}

}

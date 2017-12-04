/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto;

import com.nisum.quickout.dto.response.PaymentSummary;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;

/**
 * 
 * Checkout contains cart/bucket items, shippingInformation, PaymentInformation,
 * PaymentSummary. After order creation the OrderId and flag are set.
 */
public class Checkout {
	
	private Cart cart;
	private PaymentSummary paymentSummary;
	private PaymentInfo paymentInfo;
	private ShipmentInfo shipmentInfo;
	private Customer customer;
	
	public Checkout(Cart cart, ShipmentInfo shipment, Customer customer, PaymentInfo pi, PaymentSummary ps) {
		this.cart = cart;
		this.shipmentInfo = shipment;
		this.customer= customer;
		this.paymentInfo = pi;
		this.paymentSummary = ps;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PaymentSummary getPaymentSummary() {
		return paymentSummary;
	}

	public void setPaymentSummary(PaymentSummary paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public ShipmentInfo getShipmentInfo() {
		return shipmentInfo;
	}

	public void setShipmentInfo(ShipmentInfo shipmentInfo) {
		this.shipmentInfo = shipmentInfo;
	}

}

/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.dto.Checkout;
import com.nisum.quickout.dto.CreditCard;
import com.nisum.quickout.dto.PaymentInfo;
import com.nisum.quickout.dto.response.PaymentSummary;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;

@Service
@Transactional
public class CheckoutService {

	@Autowired private CartService cartService;
	@Autowired private ShipmentService shipmentService;
	@Autowired private ObjectFactory objectFactory;
	
	public Checkout createOrder(String accountCode, String cartId) {
		Cart cart = cartService.get(accountCode, cartId);
		ShipmentInfo shipment = shipmentService.find(cartId, accountCode);
		Customer customer = shipment.getCustomer();
		CreditCard cc = objectFactory.createCreditCard("2839472934789238", 8, 2019, 433);
		PaymentInfo pi = objectFactory.createPaymentInfo("US", "FN", "LN", "510", "2345555", "510", "2346666", "temp@temp.com", 
				"Nisum", shipment.getCustomer().getPrimaryAddress(), "CC", cc);
		PaymentSummary ps = objectFactory.createNewPaymentSummary(100.0, 4.5, 10.3, 114.8);
		Checkout checkout = objectFactory.createCheckout(cart, shipment, customer, pi, ps);
		
		return checkout;
	}

}

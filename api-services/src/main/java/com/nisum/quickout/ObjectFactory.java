/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/
package com.nisum.quickout;

import org.springframework.stereotype.Component;

import com.nisum.quickout.dto.Checkout;
import com.nisum.quickout.dto.CreditCard;
import com.nisum.quickout.dto.PaymentInfo;
import com.nisum.quickout.dto.response.PaymentSummary;
import com.nisum.quickout.persistence.domain.Address;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;

/**
 * An Object factory to create objects of different types. Mainly added to keep the unit tests clean 
 * and simple without depending on the complexity PowerMockito.
 * 
 * @author Riaz Uddin
 *
 */
@Component
public class ObjectFactory {
	public Cart createCart(String accountCode){
		return new Cart(accountCode);
	}

	public ShipmentInfo createShipment(Cart c, Customer customer){
		return new ShipmentInfo(c, customer);
	}

	public Checkout createCheckout(Cart cart, ShipmentInfo shipment, Customer customer, PaymentInfo pi,
			PaymentSummary ps){
		return new Checkout(cart, shipment, customer, pi, ps);
	}

	public PaymentSummary createNewPaymentSummary(double d, double e, double f, double g) {
		return new PaymentSummary(100.0, 4.5, 10.3, 114.8);
	}

	public PaymentInfo createPaymentInfo(String string, String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, String string9, Address address, String string10,
			CreditCard cc){
		return new PaymentInfo("US", "FN", "LN", "510", "2345555", "510", "2346666", "temp@temp.com", 
				"Nisum", address, "CC", cc);
	}

	public CreditCard createCreditCard(String string, int i, int j, int k){
		return new CreditCard("2839472934789238", 8, 2019, 433);
	}
}

/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/


package com.nisum.quickout.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;
import com.nisum.quickout.persistence.domain.View;

/**
 * It contains Shipping Contact Information.
 */
public class ShipmentResponse {

    public ShipmentResponse() {
    }
  
    public ShipmentResponse(ShipmentInfo shipment) {
		this.cart = shipment.getCart();
		this.customer = shipment.getCustomer();
	}

    @JsonView(View.Compact.class)
    private Cart cart;
    @JsonView(View.Full.class)
	private Customer customer;
    
    public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
}

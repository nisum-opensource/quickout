/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Adnan Ahmed
 * 
 *         It contains Shipping Address, Shipping Contact Information & optional
 *         Notification Email and Phone Number.
 */
@Entity
@Table(name = "shipment_info")
public class ShipmentInfo extends BaseAuditableDomain{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) @JsonIgnore
	private Long id;

	public ShipmentInfo(){}
	
	public ShipmentInfo(Cart cart) {
		this.cart = cart;
	}

	public ShipmentInfo(Cart cart, Customer customer) {
		this.cart = cart;
		this.customer = customer;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="customer_id")
	private Customer customer;

	@OneToOne(optional = false)
	private Cart cart;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "ShipmentInfo [id="+ id +", customer=" + customer + ", cart=" + cart + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentInfo other = (ShipmentInfo) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		return true;
	}
	
}

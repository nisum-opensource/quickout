/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
 * 
*/
package com.nisum.quickout.persistence.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Cart extends BaseAuditableDomain {

	public enum CartStatus {
		OPEN, CLOSED;
	}

	@Id
	@NotBlank
	@JsonView(View.Compact.class)
	private String id;

	@NotBlank
	@Column
	@JsonIgnore
	private String accountCode;

	@Column
	@NotNull
	@JsonIgnore
	private CartStatus status;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
	private List<CartDetails> cartDetails = new LinkedList<>();

	@OneToOne(mappedBy = "cart")
	@JsonIgnore
	private ShipmentInfo shipment;

	public Cart(){}
	
	public Cart(String accountCode) {
		this.accountCode = accountCode;
		this.id = UUID.randomUUID().toString();
		this.status = CartStatus.OPEN;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ShipmentInfo getShipment() {
		return shipment;
	}

	public void setShipment(ShipmentInfo shipment) {
		this.shipment = shipment;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getId() {
		return id;
	}

	public CartStatus getStatus() {
		return status;
	}

	public void setStatus(CartStatus status) {
		this.status = status;
	}

	public List<CartDetails> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", accountCode=" + accountCode + ", status=" + status + "]";
	}

	public void add(CartDetails detail) {
		cartDetails.add(detail);
	}

	public void addAll(List<CartDetails> details) {
		cartDetails.addAll(details);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountCode == null) ? 0 : accountCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Cart other = (Cart) obj;
		if (accountCode == null) {
			if (other.accountCode != null)
				return false;
		} else if (!accountCode.equals(other.accountCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Customer extends BaseAuditableDomain {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "first_name")
	@JsonView(View.Full.class)
	private String firstName;
	@Column(name = "last_name")
	@JsonView(View.Full.class)
	private String lastName;
	@Column
	@JsonView(View.Full.class)
	private String phone;
	@Column
	@JsonView(View.Full.class)
	private String email;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy="customer")
	private Set<Address> addresses = new HashSet<>();
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy="customer")
	private Set<ShipmentInfo> shipments = new HashSet<>();
	
	public Customer(){}

	public Customer(String firstName, String lastName, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@JsonGetter("addresses")
	@JsonView(View.Full.class)
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		addresses.forEach((a) -> a.setCustomer(this));
		this.addresses.addAll(addresses);
	}
	
	@JsonGetter("primaryAddress")
	@JsonView(View.Full.class)
	public Address getPrimaryAddress() {
		if(CollectionUtils.isNotEmpty(addresses))
			return addresses.iterator().next();
		
		return null;
	}

	public Set<ShipmentInfo> getShipments() {
		return shipments;
	}

	public void setShipments(Set<ShipmentInfo> shipments) {
		shipments.forEach((s) -> s.setCustomer(this));
		this.shipments.addAll(shipments);
	}
	
	public void removeShipment(ShipmentInfo shipment) {
		shipments.remove(shipment);
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", email=" + email
				+ "]";
	}
}

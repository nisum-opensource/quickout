/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.exception.QuickoutInvalidInputException;
import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;
import com.nisum.quickout.persistence.repository.ShipmentRepository;

@Service
@Transactional
public class ShipmentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShipmentRepository shipmentRepository;

	@Autowired
	private CartService cartService;
	
	@Autowired CustomerService customerService;

	@Autowired
	private ObjectFactory objectFactory;

	public ShipmentInfo create(String cartId, String accountCode, Customer customer) {
		if (customer == null)
			throw new QuickoutInvalidInputException("Customer information is missing");

		Cart c = cartService.get(accountCode, cartId);
		customerService.save(customer);
		logger.debug(String.format("Creating new shipment for %s", c.getId()));
		return shipmentRepository.save(objectFactory.createShipment(c, customer));
	}

	public ShipmentInfo updateShipment(String cartId, String accountCode, Customer customer) {
		if (customer == null)
			throw new QuickoutInvalidInputException("Customer information is missing");

		Cart c = cartService.get(accountCode, cartId);
		ShipmentInfo shipment = shipmentRepository.findByCart(c);
		if (shipment == null) {
			logger.debug(String.format("Creating new shipment for %s", c.getId()));
			shipment = objectFactory.createShipment(c, customer);
		} else{
			Customer now = shipment.getCustomer();
			if(now != null){
				now.getAddresses().clear();
				customerService.save(now);
				BeanUtils.copyProperties(customer, now, "id", "createDate");
				shipment.setCustomer(now);
			}
		}
		return shipmentRepository.save(shipment);
	}

	public ShipmentInfo find(String cartId, String accountCode) {
		Cart c = cartService.get(accountCode, cartId);
		ShipmentInfo shipment = shipmentRepository.findByCart(c);
		if (shipment == null) {
			throw new QuickoutNotFoundException(String.format("Shipment not found for cart %s", c.getId()));
		}
		return shipment;
	}
}

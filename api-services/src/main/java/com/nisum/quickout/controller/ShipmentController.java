/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nisum.quickout.dto.response.ShipmentResponse;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;
import com.nisum.quickout.persistence.domain.View;
import com.nisum.quickout.service.ShipmentService;

import io.swagger.annotations.Api;

/**
 * 
 * Provides APIs to handle Shipping Information.
 * 
 */
@RestController
@Api(value = "shipment")
@CrossOrigin(origins = "*")
@RequestMapping("quickout/api/shipment")
public class ShipmentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShipmentService shipmentService;

	/**
	 * Create new shipment with given cart ID and address.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @param address
	 * @return
	 * 	JSON of created shipment
	 */
	@PostMapping
	@JsonView(View.Full.class)
	public ResponseEntity<?> createShipment(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId, @RequestBody Customer customer) {
		logger.debug(String.format("Create Shipment for account %s, cart %s", accountCode, cartId));
		ShipmentInfo shipment = shipmentService.create(cartId, accountCode, customer);
		ShipmentResponse response = new ShipmentResponse(shipment);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Update the address on the cart.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @param address  New address to be associated with the cart
	 * @return
	 * 	JSON of updated shipment.
	 */
	@PutMapping
	@JsonView(View.Full.class)
	public ResponseEntity<?> updateShipment(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId, @RequestBody Customer customer) {
		logger.debug(String.format("Update shipment for cart %s", cartId));
		ShipmentInfo shipment = shipmentService.updateShipment(cartId, accountCode, customer);
		ShipmentResponse response = new ShipmentResponse(shipment);
		return ResponseEntity.ok(response);
	}

	/**
	 * Get shipment information associated with given cart ID.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @return
	 * 	JSON message with all the shipment details in it.
	 */
	@GetMapping
	@JsonView(View.Full.class)
	public ResponseEntity<?> find(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId) {
		logger.debug(String.format("Find shipment for cart %s", cartId));
		ShipmentInfo shipment = shipmentService.find(cartId, accountCode);
		ShipmentResponse response = new ShipmentResponse(shipment);
		return ResponseEntity.ok(response);
	}
}

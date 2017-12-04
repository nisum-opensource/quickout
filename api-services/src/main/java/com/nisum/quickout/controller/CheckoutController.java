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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.quickout.service.CheckoutService;

import io.swagger.annotations.Api;

/** 
 * A service which is used to place an order.
 */
@RestController
@Api(value = "checkout")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://quickout.mynisum.com",
		"http://quickout.mynisum.com:8080" })
@RequestMapping(path = "/quickout/api/checkout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CheckoutController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CheckoutService checkoutService;

	/**
	 * <b>Service URL:</b>http://api.quickout.mynisum.com/quickout/api/checkout
	 * <b>Method: </b> POST
	 * 
	 * Performs checkout/placing an order functionality and returns the result
	 * of the checkout.
	 * 
	 * @param checkout
	 * @return result of the checkout containing the orderId, flag indicating if
	 *         it was successful and the paymentSummary
	 */
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestParam(value = "accountCode") String accountCode, @RequestParam(value = "cartId") String cartId){
		logger.debug(String.format("Create order for cart %s", cartId));
		return ResponseEntity.ok(checkoutService.createOrder(accountCode, cartId));
	}
}

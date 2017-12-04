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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.service.CustomerService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "customer")
@CrossOrigin(origins = "*")
@RequestMapping("quickout/api/customer")
public class CustomerController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerService customerService;

//	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		logger.debug("Create customer");
		customer = customerService.save(customer);
		return ResponseEntity.ok(customer);
	}

	@GetMapping
	public ResponseEntity<?> find(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone) {
		Customer customer = new Customer(firstName, lastName, email, phone);
		logger.debug("Find customer");
		return ResponseEntity.ok(customerService.find(customer));
	}
}

/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/


package com.nisum.quickout.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.quickout.dto.PaymentInfo;


/**
 * This service handles all the payment related activities.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="quickout/api/payment")
public class PaymentInfoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public PaymentInfoController() {
		
	}
    
    @PostMapping
    public ResponseEntity<?> createPaymentInfo(@RequestParam(value = "cartId") String cartId, @RequestBody PaymentInfo pi ){
        logger.info(String.format("Input Object {%s}, cartId: %s",pi, cartId));
        return ResponseEntity.ok(String.format("PaymentId %s", UUID.randomUUID().toString()));
    }
}

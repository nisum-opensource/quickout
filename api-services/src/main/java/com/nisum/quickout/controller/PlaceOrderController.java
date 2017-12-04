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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.quickout.dto.Checkout;


/**
 * A service which is used to place an order.
 */
//@RestController
//@CrossOrigin("*")
//@RequestMapping(path="/quickout/api/checkout", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class PlaceOrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
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
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public Checkout createOrder(@RequestBody Checkout checkout ){
        logger.info(String.format("Input Object {%s}, firstName: %s",checkout,checkout.getPaymentInfo().getFirstName()));
//        Checkout checkoutResult = new Checkout(23.0, 2.25, 3.0, 28.25);
//        checkoutResult.setOrderId("3245324");
//        checkoutResult.setFlag("successful");
//        checkoutResult.setPaymentSummary(new PaymentSummary(130.0, 10.0, 5.0, 145.0));
        return null;
    }
}

/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.response;

import java.util.List;

/**
 * It contains a list of shipping rates
 */
public class ShippingRates {

	private List<ShippingRate> shippingRates;
	
	public ShippingRates(List<ShippingRate> shippingRates) {
		this.shippingRates = shippingRates;
	}
	public List<ShippingRate> getShippingRates() {
		return shippingRates;
	}
	public void setShippingRates(List<ShippingRate> shippingRates) {
		this.shippingRates = shippingRates;
	}
}

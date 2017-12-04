/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.ShipmentInfo;

public interface ShipmentRepository extends CrudRepository<ShipmentInfo, String>{

	public ShipmentInfo findByCart(Cart cart);
}

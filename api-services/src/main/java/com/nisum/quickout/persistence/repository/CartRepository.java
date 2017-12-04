/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nisum.quickout.persistence.domain.Cart;

public interface CartRepository extends CrudRepository<Cart, String>{
	
	@Query("SELECT cart FROM Cart cart where accountCode = :accountCode and id = :cartId")
	public Cart findOne(@Param("accountCode") String accountCode, @Param("cartId") String cartId);
}

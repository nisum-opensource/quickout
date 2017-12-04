/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.CartDetails;

public interface CartDetailsRepository extends CrudRepository<CartDetails, Long>{

	@Modifying
	@Query(value="DELETE FROM CART_DETAILS WHERE cart_id = :cartId", nativeQuery=true)
	public int deleteCartItems(@Param("cartId") String cartId);
	
	@Modifying
	@Query("DELETE FROM CartDetails detail WHERE detail.sku in (:skus) and detail.cart.id = :cartId")
	public int deleteFromCart(@Param("cartId") String cartId, @Param("skus") List<String> skus);
	
	public CartDetails findByCartAndSku(Cart cart, String sku);
}

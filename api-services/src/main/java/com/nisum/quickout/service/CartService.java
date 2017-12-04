/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.exception.QuickoutInvalidInputException;
import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.exception.QuickoutRuntimeException;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Cart.CartStatus;
import com.nisum.quickout.persistence.domain.CartDetails;
import com.nisum.quickout.persistence.repository.CartDetailsRepository;
import com.nisum.quickout.persistence.repository.CartRepository;

@Service
@Transactional
public class CartService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ObjectFactory objectFactory;

	@Autowired
	private CartDetailsRepository cartDetailsRepository;

	public Cart generateCart(String accountCode) {
		if (StringUtils.isBlank(accountCode))
			throw new QuickoutInvalidInputException();
		Cart c = objectFactory.createCart(accountCode);
		logger.debug(String.format("Generated new cart: %s", c.getId()));

		return cartRepository.save(c);
	}

	public Cart addItems(String cartId, String accountCode, List<CartDetails> details) {
		if (CollectionUtils.isEmpty(details))
			throw new QuickoutInvalidInputException("No items to add to the cart.");

		Cart c = null;
		if(StringUtils.isNotBlank(cartId))
			c = get(accountCode, cartId);
		if (c == null || c.getStatus() == CartStatus.CLOSED) {
			c = generateCart(accountCode);
			logger.debug(String.format("Generated new cartId %s", c.getId()));
		}
		for (CartDetails detail : details) {
			detail.setCart(c);
		}
		c.addAll(details);
		c = cartRepository.save(c);

		return c;
	}

	public Cart get(String accountCode, String cartId) {
		if(StringUtils.isBlank(cartId) || StringUtils.isBlank(accountCode))
			throw new QuickoutInvalidInputException("Invalid input. Cart Id and Account Code are required.");
			
		Cart c = cartRepository.findOne(accountCode, cartId);
		if (c == null)
			throw new QuickoutNotFoundException(String.format("Cart not found: %s", cartId));
		logger.debug(String.format("Cart found %s", cartId));
		return c;
	}

	public int clear(String accountCode, String cartId) {
		// check if the cart is available
		get(accountCode, cartId);
		return cartDetailsRepository.deleteCartItems(cartId);
	}

	public int removeItems(String cartId, String accountCode, List<String> skus) {
		get(accountCode, cartId);
		return cartDetailsRepository.deleteFromCart(cartId, skus);
	}

	public Cart update(String accountCode, String cartId, List<CartDetails> items) {
		Cart c = get(accountCode, cartId);
		if (c == null || c.getStatus() == CartStatus.CLOSED) {
			throw new QuickoutRuntimeException(
					String.format("Cannot find cart with id %s or it is already closed", cartId));
		}

		if (CollectionUtils.isEmpty(items))
			throw new QuickoutInvalidInputException("No items to update the cart.");

		items.replaceAll((item) -> {
			item.setCart(c);
			CartDetails detail = cartDetailsRepository.findByCartAndSku(c, item.getSku());
			if (detail != null)
				BeanUtils.copyProperties(item, detail, "id", "createDate");
			else{
				detail = item;
				c.add(detail);
			}
			return detail;
		});

		Iterable<CartDetails> it = cartDetailsRepository.save(items);

		return it.iterator().next().getCart();
	}

}

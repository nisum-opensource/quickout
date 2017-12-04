/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.CartDetails;
import com.nisum.quickout.persistence.domain.ListItem;
import com.nisum.quickout.persistence.domain.View;
import com.nisum.quickout.service.CartService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "cart")
@CrossOrigin(origins = "*")
@RequestMapping("quickout/api/cart")
public class CartController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static List<ListItem> list = new ArrayList<>();

	@Autowired
	private CartService cartService;

	static {
		CartController.defaultCartItems();
	}

	private static void defaultCartItems() {
		list = new ArrayList<>();
		list.add(new ListItem(
				"http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj01-yellow_main.jpg",
				"Beaumont Summit Kit", 42.90f, 1));
		list.add(new ListItem(
				"http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj03-black_main.jpg",
				"Montana Wind Jacket", 49.09f, 1));
		list.add(new ListItem(
				"http://ec2-52-41-159-161.us-west-2.compute.amazonaws.com/pub/media/catalog/product/cache/1/thumbnail/88x110/beff4985b56e3afdbeabfc89641a4582/m/j/mj10-red_main.jpg",
				"Mars HeatTech™ Pullover", 66.99f, 1));
	}

	public static void updateList(List<ListItem> list) {
		CartController.list = list;
	}

	public static List<ListItem> getList() {
		return CartController.list;
	}

	/**
	 * Generate a new Cart with a unique cart ID.
	 * 
	 * @param accountCode  Valid account code;
	 * @return ID of the newly created cart.
	 */
	@GetMapping(path = "/generate_cart_id")
	@JsonView(View.Compact.class)
	public ResponseEntity<?> generateCartId(@RequestParam(value = "accountCode") String accountCode) {
		Cart c = cartService.generateCart(accountCode);
		log.debug(String.format("Created new cart with id %s", c.getId()));
		return ResponseEntity.ok(c);
	}

	/**
	 * Remove items from the cart listed in the skus, if any. Account Code and Cart Id must be valid.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @param skus Items are identified by SKUs
	 * @return Message in the response body indicating how many items were removed.
	 */
	@PutMapping(path = "/remove_items", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeItems(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId, @RequestBody List<String> skus) {
		int removed = cartService.removeItems(cartId, accountCode, skus);

		log.debug(String.format("Removed %d items from cart %s", removed, cartId));
		return ResponseEntity.ok(String.format("Successfully removed %d item(s) from cart %s", removed, cartId));
	}

	/**
	 * Remove all items from the cart. Account code and Cart ID must be valid.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @return Message in the response body indicating how many items were cleared.
	 */
	@DeleteMapping(path = "/clear")
	public ResponseEntity<?> clear(@RequestParam(value = "accountCode") String accountCode, 
			@RequestParam(value = "cartId") String cartId) {
		int deleted = cartService.clear(accountCode, cartId);
		
		log.debug(String.format("Removed all %d items from cart %s", deleted, cartId));
		return ResponseEntity.ok().body(String.format("Removed %d item(s) from cart %s", deleted, cartId));
	}
	
	/**
	 * Find a Cart given Account code and cart id.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @return 
	 * 	JSON message with cart and its details.
	 * 	An error message, if the cart Id is invalid. 
	 */
	@GetMapping
	public ResponseEntity<?> get(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId) {
		Cart c = cartService.get(accountCode, cartId);
		
		log.debug(String.format("Found cart with id %s", c.getId()));
		return ResponseEntity.ok(c);
	}

	/**
	 * Update the items in the cart with the items provided as items. 
	 * Update the item with provided input if it already exists in the cart.
	 * Add the item if it is not present in the cart already.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @param items  List of items to be udpated/added to the cart
	 * @return
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId") String cartId, @RequestBody List<CartDetails> items) {
		Cart c = cartService.update(accountCode, cartId, items);
		return ResponseEntity.ok(c);
	}

	/**
	 * Add new items to the cart. An error message is thrown if the item already exists in the cart.
	 * If the cart is closed or does not exist, a new cart is created.
	 * 
	 * @param accountCode  Valid account code;
	 * @param cartId  Valid cart ID.
	 * @param items  Items to be added.
	 * @return
	 * 	The newly created/updated cart with all its items in it.
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addItems(@RequestParam(value = "accountCode") String accountCode,
			@RequestParam(value = "cartId", required = false) String cartId, @RequestBody List<CartDetails> items) {
		Cart c = cartService.addItems(cartId, accountCode, items);
		return ResponseEntity.ok(c);
	}
}
package com.nisum.quickout.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.exception.QuickoutInvalidInputException;
import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.exception.QuickoutRuntimeException;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Cart.CartStatus;
import com.nisum.quickout.persistence.domain.CartDetails;
import com.nisum.quickout.persistence.repository.CartDetailsRepository;
import com.nisum.quickout.persistence.repository.CartRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CartService.class }, locations = {
		"classpath:*application-context.xml" })
public class CartServiceTest {
	private static final String ACCOUNT_CODE = "10001";

	@MockBean
	private CartRepository cartRepository;
	@MockBean
	private CartDetailsRepository cartDetailsRepository;
	@Autowired
	private CartService cartService;
	private Cart cart;
	@MockBean
	ObjectFactory of;

	@Before
	public void setUp() {
		cart = createNewCart();
	}

	private Cart createNewCart() {
		Cart c = new Cart(ACCOUNT_CODE);
		when(of.createCart(Mockito.anyString())).thenReturn(c);
		return c;
	}

	@Test(expected = QuickoutInvalidInputException.class)
	public void generateCart_NoAccountNumber() {
		cartService.generateCart("");
		verify(of, never()).createCart(Mockito.anyString());

		cartService.generateCart(null);
		verify(of, never()).createCart(Mockito.anyString());
	}

	@Test
	public void generateCart() {
		when(cartRepository.save(cart)).thenReturn(cart);
		Cart c1 = cartService.generateCart(ACCOUNT_CODE);
		assertNotNull(c1);
		assertEquals("Account code does not match", ACCOUNT_CODE, c1.getAccountCode());

		String cartId = c1.getId();
		Cart c2 = createNewCart();
		when(cartRepository.save(c2)).thenReturn(c2);
		Cart newCart = cartService.generateCart(ACCOUNT_CODE);
		String newCartId = newCart.getId();

		assertNotNull(cartId);
		assertNotNull(newCartId);
		assertNotEquals("CartId must be unique everytime a cart is generated", cartId, newCartId);
	}

	@Test
	public void addItems_ClosedCart() {
		String cartId = cart.getId();
		cart.setStatus(CartStatus.CLOSED);
		List<CartDetails> details = new LinkedList<>();
		details.add(new CartDetails("sku", "skuName", 10, 10f, 15f, "imageSource"));
		when(cartRepository.findOne(ACCOUNT_CODE, cartId)).thenReturn(cart);
		
		Cart newCart = createNewCart();
		when(of.createCart(ACCOUNT_CODE)).thenReturn(newCart);
		when(cartRepository.save(newCart)).thenReturn(newCart);
		Cart updatedCart = cartService.addItems(cartId, ACCOUNT_CODE, details);

		assertNotNull(updatedCart.getCartDetails());
		assertTrue(updatedCart.getCartDetails().size() == 1);
		assertTrue(updatedCart.getCartDetails().get(0).getSku().equals("sku"));
		assertNotEquals("CartId must be unique", cart.getId(), newCart.getId());
		verify(of, Mockito.atLeastOnce()).createCart(ACCOUNT_CODE);
	}

	@Test(expected = QuickoutInvalidInputException.class)
	public void addItems_NoCartDetails() {
		String cartId = UUID.randomUUID().toString();
		cartService.addItems(cartId, ACCOUNT_CODE, null);
		verify(cartRepository, never()).save(cart);
	}

	@Test(expected=QuickoutInvalidInputException.class)
	public void get_NullCartIdAndAccountCode() {
		cartService.get(null, null);
	}

	@Test(expected=QuickoutInvalidInputException.class)
	public void get_EmptyCartIdValidAccountCode() {
		cartService.get(" ", ACCOUNT_CODE);
	}

	@Test(expected=QuickoutInvalidInputException.class)
	public void get_ValidCartIdEmptyAccountCode() {
		cartService.get(cart.getId(), " ");
	}

	@Test(expected=QuickoutNotFoundException.class)
	public void get_ValidCartIdAndAccountCode() {
		when(cartRepository.findOne(ACCOUNT_CODE, cart.getId())).thenReturn(null);
		cartService.get(cart.getId(), ACCOUNT_CODE);
	}

	@Test
	public void get() {
		when(cartRepository.findOne(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		Cart c = cartService.get(ACCOUNT_CODE, cart.getId());
		
		assertNotNull(c);
		Assert.assertTrue(StringUtils.isNotBlank(c.getAccountCode()));
		Assert.assertTrue(StringUtils.isNotBlank(c.getId()));
	}

	@Test
	@Ignore
	public void clear() {
		//tested in cartRepository
	}

	@Test
	@Ignore
	public void testRemoveItems() {
		//tested in cartRepository
	}

	@Test(expected=QuickoutRuntimeException.class)
	public void update_ClosedCart() {
		cart.setStatus(CartStatus.CLOSED);
		cartService.update(ACCOUNT_CODE, cart.getId(), null);
	}

	@Test(expected=QuickoutInvalidInputException.class)
	public void update_EmptyCartDetails() {
		when(cartRepository.findOne(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		cartService.update(ACCOUNT_CODE, cart.getId(), null);
	}

	@Test
	public void update() {
		when(cartRepository.findOne(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		CartDetails cd1 = new CartDetails("sku1", "sku1Name", 10, 10f, 15f, "image1Source");
		CartDetails cd2 = new CartDetails("sku2", "sku2Name", 10, 10f, 15f, "image2Source");
		cart.add(cd1);
		cart.add(cd2);
		List<CartDetails> details = new LinkedList<>();
		CartDetails detail1 = new CartDetails("sku1", "skuName", 10, 10f, 15f, "imageSource");
		CartDetails detail2 = new CartDetails("skuNew", "skuNewName", 10, 10f, 15f, "imageNewSource");
		details.add(detail1);
		details.add(detail2);
		
		when(cartDetailsRepository.findByCartAndSku(cart, "sku1")).thenReturn(cd1);
		when(cartDetailsRepository.save(details)).thenReturn(details);
		Cart c = cartService.update(ACCOUNT_CODE, cart.getId(), details);
		
		assertNotNull(c);
		assertEquals("Account code does not match", cart.getAccountCode(), c.getAccountCode());
		assertEquals("Cart Id does not match", cart.getId(), c.getId());
		List<CartDetails> items = c.getCartDetails();
		assertTrue(items.size() == 3);
		
		boolean addedNew = false;
		for(CartDetails cd : items){
			if(cd.getSku().equals("sku1"))
				assertTrue(cd.getSkuName().equals("skuName"));
			else if(cd.getSku().equals("skuNew"))
				addedNew = true;
		}
		assertTrue(addedNew);	
	}

}

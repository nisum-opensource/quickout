package com.nisum.quickout.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.quickout.GlobalRestExceptionHandler;
import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.TestUtils;
import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.exception.QuickoutRuntimeException;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.CartDetails;
import com.nisum.quickout.service.CartService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CartController.class, GlobalRestExceptionHandler.class }, locations = {
		"classpath:*application-context.xml" })
@WebMvcTest(CartController.class)
public class CartControllerTest {
	private static final String ACCOUNT_CODE = "10001";

	private static final String CART_API_GENERATE_CART_ID = "/quickout/api/cart/generate_cart_id/";
	private static final String CART_API_CLEAR = "/quickout/api/cart/clear/";
	private static final String CART_API_REMOVE_ITEMS = "/quickout/api/cart/remove_items/";
	private static final String CART_API_UPDATE = "/quickout/api/cart/";
	private static final String CART_API_GET = "/quickout/api/cart/";
	private static final String CART_API_CART = "/quickout/api/cart/";
	
	private String cartId = null;
	private Cart cart = null;

	@Autowired
	private MockMvc mvc;
	@MockBean
	private CartService cartService;
	@MockBean ObjectFactory objectFactory;

	@Before
	public void setUp() {
		cartId = UUID.randomUUID().toString();
		cart = new Cart(ACCOUNT_CODE);
		cart.setId(cartId);
		Mockito.when(objectFactory.createCart(ACCOUNT_CODE)).thenReturn(cart);
	}

	@Test
	public void generateCartId_NoAccountCode() throws Exception {
		String msg = String.format("Cart not found %s", cartId);
		Mockito.when(cartService.generateCart("")).thenThrow(new QuickoutNotFoundException(msg));
		mvc.perform(get(CART_API_GENERATE_CART_ID).param("accountCode", "").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(content().string(containsString(msg)));
	}

	@Test
	public void get_InvalidInput() throws Exception {
		String msg = String.format("Cart not found %s", cartId);
		Mockito.when(cartService.get(ACCOUNT_CODE, cartId)).thenThrow(new QuickoutNotFoundException(msg));
		mvc.perform(get(CART_API_GET)
				.param("cartId", cartId)
				.param("accountCode", ACCOUNT_CODE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(content().string(containsString(msg)));
	}

	@Test
	public void get_Success() throws Exception {
		Mockito.when(cartService.get(ACCOUNT_CODE, cartId)).thenReturn(cart);
		mvc.perform(get(CART_API_GET)
				.param("cartId", cartId)
				.param("accountCode", ACCOUNT_CODE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(containsString(cartId)));
	}

	@Test
	public void generateCartId_Success() throws Exception {
		Mockito.when(cartService.generateCart(ACCOUNT_CODE)).thenReturn(cart);
		mvc.perform(get(CART_API_GENERATE_CART_ID).param("accountCode", ACCOUNT_CODE)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").value(containsString(cartId)));
	}

	@Test
	public void clearCart_InvalidInput() throws Exception {
		String msg = String.format("Cart not found %s", cartId);
		Mockito.when(cartService.clear(ACCOUNT_CODE, cartId)).thenThrow(new QuickoutNotFoundException(msg));
		mvc.perform(delete(CART_API_CLEAR)
				.param("accountCode", ACCOUNT_CODE)
				.param("cartId", cartId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(content().string(containsString(msg)));
	}

	@Test
	public void clearCart_Success() throws Exception {
		Mockito.when(cartService.clear(ACCOUNT_CODE, cartId)).thenReturn(2);
		mvc.perform(delete(CART_API_CLEAR).param("accountCode", ACCOUNT_CODE).param("cartId", cartId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(containsString("Removed 2 item(s)")));
	}

	@Test
	public void removeItems_InvalidInput() throws Exception {
		String msg = String.format("Cart not found %s", cartId);
		Mockito.when(cartService.removeItems(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(String.class)))
				.thenThrow(new QuickoutNotFoundException(msg));
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		mvc.perform(put(CART_API_REMOVE_ITEMS + requestParams).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtils.convertObjectToJsonString(new String[] { "apple" })))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(content().string(containsString(msg)));
	}
	
	@Test
	public void removeItems_Success() throws Exception {
		Mockito.when(cartService.removeItems(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(String.class)))
				.thenReturn(1);
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		mvc.perform(put(CART_API_REMOVE_ITEMS + requestParams)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtils.convertObjectToJsonString(new String[] { "apple" })))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(containsString("Successfully removed 1 item(s)")));
	}

	@Test
	public void update_InvalidInput() throws Exception {
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		String errMsg = String.format("Cannot find cart with id %s or it is already closed", cartId);
		Mockito.when(cartService.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(CartDetails.class))).thenThrow(new QuickoutRuntimeException(errMsg));
		String json = "[{\"imageSource\": \"string\",\"listPrice\": 10,\"quantity\": 10,\"sellingPrice\": 10,\"sku\": \"string\",\"skuName\": \"string\"}]";
		mvc.perform(put(CART_API_UPDATE + requestParams).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(content().string(containsString("Cannot find cart")));
	}
	
	@Test
	public void update_Success() throws Exception {
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		Mockito.when(cartService.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(CartDetails.class))).thenReturn(cart);
		String json = "[{\"imageSource\": \"string\",\"listPrice\": 10,\"quantity\": 10,\"sellingPrice\": 10,\"sku\": \"string\",\"skuName\": \"string\"}]";
		mvc.perform(put(CART_API_UPDATE + requestParams).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(containsString(cartId)));
	}
	
	@Test
	public void add_GenerateNewCart() throws Exception {
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		String json = "{\"imageSource\": \"string\",\"listPrice\": 10,"
				+ "\"quantity\": 10,\"sellingPrice\": 10,"
				+ "\"sku\": \"sku_string\",\"skuName\": \"string\"}";
		CartDetails cd = new ObjectMapper().readValue(json, CartDetails.class);
		Cart newCart = objectFactory.createCart(ACCOUNT_CODE);
		String newId = UUID.randomUUID().toString();
		newCart.setId(newId);
		newCart.add(cd);
		Mockito.when(cartService
				.addItems(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(CartDetails.class)))
				.thenReturn(newCart);
		mvc.perform(post(CART_API_CART + requestParams).contentType(MediaType.APPLICATION_JSON).content("["+json+"]"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(containsString(newId)))
				.andExpect(jsonPath("$.cartDetails").exists())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.cartDetails[0].sku").value(containsString("sku_string")));
	}
	
	@Test
	public void add_ExistingCart() throws Exception {
		String requestParams = String.format("?accountCode=%s&cartId=%s", ACCOUNT_CODE, cartId);
		String json = "{\"imageSource\": \"string\",\"listPrice\": 10,"
				+ "\"quantity\": 10,\"sellingPrice\": 10,"
				+ "\"sku\": \"sku_string\",\"skuName\": \"string\"}";
		CartDetails cd = new ObjectMapper().readValue(json, CartDetails.class);
		cart.add(cd);
		Mockito.when(cartService
				.addItems(Mockito.anyString(), Mockito.anyString(), Mockito.anyListOf(CartDetails.class)))
				.thenReturn(cart);
		mvc.perform(post(CART_API_CART + requestParams).contentType(MediaType.APPLICATION_JSON).content("["+json+"]"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(containsString(cartId)))
				.andExpect(jsonPath("$.cartDetails").exists())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.cartDetails[0].sku").value(containsString("sku_string")));
	}
}

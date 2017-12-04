package com.nisum.quickout.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nisum.quickout.GlobalRestExceptionHandler;
import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.TestUtils;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;
import com.nisum.quickout.service.ShipmentService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ShipmentController.class, GlobalRestExceptionHandler.class }, locations = {
		"classpath:*application-context.xml" })
@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private ShipmentService shipmentService;
	@MockBean ObjectFactory objectFactory;
	
	private static final String SHIPMENT_API = "/quickout/api/shipment/";
	
	private String cartId = null;
	private ShipmentInfo shipment;
	private Customer customer;
	private Cart cart = null;
	private static final String ACCOUNT_CODE = "10001";

	@Before
	public void setUp() {
		cartId = UUID.randomUUID().toString();
		cart = new Cart(ACCOUNT_CODE);
		cart.setId(cartId);
		when(objectFactory.createCart(ACCOUNT_CODE)).thenReturn(cart);
		
		customer = new Customer("FN", "LN", "email@email.com", "999999999");
//		address = new Address("address", "address2", "city", "state", "99999");
		shipment = new ShipmentInfo(cart, customer);
		shipment.setId(1L);
	}

	@Test
	public void find() throws Exception {
		Mockito.when(shipmentService.find(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(shipment);
		mvc.perform(get(SHIPMENT_API)
				.param("accountCode", ACCOUNT_CODE)
				.param("cartId", cartId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist())
				.andExpect(jsonPath("$.cart").exists())
				.andExpect(jsonPath("$.cart.id").value(containsString(cartId)))
				.andExpect(jsonPath("$.customer").exists());
//				.andExpect(jsonPath("$.customer.primaryAddress").value(containsString("address")));
	}

	@Test
	public void create() throws Exception {
		Mockito.when(shipmentService.create(Mockito.anyString(), Mockito.anyString(), Mockito.any(Customer.class)))
		.thenReturn(shipment);
		mvc.perform(post(SHIPMENT_API)
				.param("accountCode", ACCOUNT_CODE)
				.param("cartId", cartId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtils.convertObjectToJsonString(customer)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist())
				.andExpect(jsonPath("$.cart").exists())
				.andExpect(jsonPath("$.cart.id").value(containsString(cartId)))
				.andExpect(jsonPath("$.customer").exists())
//				.andExpect(jsonPath("$.customer.primaryAddress.address").value(containsString("address")))
				.andExpect(jsonPath("$.customer.id").doesNotExist());
	}

	@Test
	public void updateShipment() throws Exception {
		Mockito.when(shipmentService.updateShipment(Mockito.anyString(), Mockito.anyString(), Mockito.any(Customer.class)))
		.thenReturn(shipment);
		mvc.perform(put(SHIPMENT_API)
				.param("accountCode", ACCOUNT_CODE)
				.param("cartId", cartId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtils.convertObjectToJsonString(customer)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist())
				.andExpect(jsonPath("$.cart").exists())
				.andExpect(jsonPath("$.cart.id").value(containsString(cartId)))
				.andExpect(jsonPath("$.customer").exists())
//				.andExpect(jsonPath("$.customer.primaryAddress").value(containsString("address")))
				.andExpect(jsonPath("$.customer.id").doesNotExist());
	}

}

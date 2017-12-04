package com.nisum.quickout.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
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
import com.nisum.quickout.persistence.domain.Address;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;
import com.nisum.quickout.persistence.repository.ShipmentRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ShipmentService.class }, locations = { "classpath:*application-context.xml" })
public class ShipmentServiceTest {
	@Autowired ShipmentService shipmentService;
	@MockBean ShipmentRepository shipmentRepository;
	@MockBean CartService cartService;
	@MockBean CustomerService customerService;
	@MockBean ObjectFactory of;

	Cart cart;
	ShipmentInfo shipment;
	Customer customer;
	static final String ACCOUNT_CODE = "10001";

	@Before
	public void setUp() {
		cart = new Cart(ACCOUNT_CODE);
		customer = new Customer("FN", "LN", "email@email.com", "999999999");
		Address address = new Address("address", "address2", "city", "state", "zipCode");
		customer.getAddresses().add(address);
		address.setId(1L);
		shipment = new ShipmentInfo(cart, customer);
		when(of.createShipment(cart, customer)).thenReturn(shipment);
	}

	@Test
	public void create() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentRepository.save(shipment)).thenReturn(shipment);
		
		ShipmentInfo s = shipmentService.create(cart.getId(), ACCOUNT_CODE, customer);
		assertNotNull(s);
		assertTrue(s.getCart().getId().equals(cart.getId()));
	}

	@Test(expected=QuickoutInvalidInputException.class)
	public void update_NullAddress() {
		shipmentService.updateShipment(cart.getId(), ACCOUNT_CODE, null);
		verify(cartService, never()).get(ACCOUNT_CODE, cart.getId());
	}

	@Test
	public void update() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentRepository.findByCart(cart)).thenReturn(shipment);
		when(shipmentRepository.save(shipment)).thenReturn(shipment);
		Customer newCustomer = new Customer("FN1", "LN1", "email1@email.com", "1999999999");
		ShipmentInfo s = shipmentService.updateShipment(cart.getId(), ACCOUNT_CODE, newCustomer);
		verify(of, never()).createShipment(Mockito.any(Cart.class), Mockito.any(Customer.class));
		Customer found = s.getCustomer();
		assertNotNull(found);
		assertTrue(found.getFirstName().equals("FN1") 
				&& found.getLastName().equals("LN1") 
				&& found.getEmail().equals("email1@email.com"));
	}

	@Test
	public void update_createNewShipment() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentRepository.findByCart(cart)).thenReturn(null);
		when(shipmentRepository.save(shipment)).thenReturn(shipment);
		when(of.createShipment(cart, customer)).thenReturn(shipment);
		
		ShipmentInfo s = shipmentService.updateShipment(cart.getId(), ACCOUNT_CODE, customer);
		verify(of, times(1)).createShipment(cart, customer);
		assertNotNull(s.getCustomer());
	}

	@Test(expected=QuickoutNotFoundException.class)
	public void find_NotFound() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentRepository.findByCart(cart)).thenReturn(null);
		
		shipmentService.find(cart.getId(), ACCOUNT_CODE);
	}

	@Test
	public void find() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentRepository.findByCart(cart)).thenReturn(shipment);
		
		ShipmentInfo s = shipmentService.find(cart.getId(), ACCOUNT_CODE);
		assertNotNull(s);
	}
}

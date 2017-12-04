package com.nisum.quickout.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nisum.quickout.ObjectFactory;
import com.nisum.quickout.dto.Checkout;
import com.nisum.quickout.dto.CreditCard;
import com.nisum.quickout.dto.PaymentInfo;
import com.nisum.quickout.dto.response.PaymentSummary;
import com.nisum.quickout.persistence.domain.Address;
import com.nisum.quickout.persistence.domain.Cart;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.domain.ShipmentInfo;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CheckoutService.class }, locations = { "classpath:*application-context.xml" })
public class CheckoutServiceTest {
	@MockBean ShipmentService shipmentService;
	@MockBean CartService cartService;
	@MockBean ObjectFactory of;
	
	@Autowired
	CheckoutService checkoutService;

	Cart cart = new Cart(ACCOUNT_CODE);
	Customer customer = new Customer("FN", "LN", "email@email.com", "999999999");
	Address address;
	ShipmentInfo shipment;
	CreditCard cc = new CreditCard("2839472934789238", 8, 2019, 433);
	PaymentInfo pi = new PaymentInfo("US", "FN", "LN", "510", "2345555", "510", "2346666", "temp@temp.com", 
			"Nisum", address, "CC", cc);
	PaymentSummary ps = new PaymentSummary(100.0, 4.5, 10.3, 114.8);
	Checkout checkout;
	static final String ACCOUNT_CODE = "10001";

	@Before
	public void setUp() {
		address = new Address("address", "address2", "city", "state", "zipCode");
		customer.getAddresses().add(address);
		shipment = new ShipmentInfo(cart, customer);
		checkout = new Checkout(cart, shipment, customer, pi, ps);
		
		when(of.createCreditCard("2839472934789238", 8, 2019, 433)).thenReturn(cc);
		when(of.createPaymentInfo("US", "FN", "LN", "510", "2345555", "510", "2346666", "temp@temp.com", 
				"Nisum", shipment.getCustomer().getPrimaryAddress(), "CC", cc)).thenReturn(pi);
		when(of.createNewPaymentSummary(100.0, 4.5, 10.3, 114.8)).thenReturn(ps);
		when(of.createCheckout(cart, shipment, customer, pi, ps)).thenReturn(checkout);
	}

	@Test
	public void createOrder() {
		when(cartService.get(ACCOUNT_CODE, cart.getId())).thenReturn(cart);
		when(shipmentService.find(cart.getId(), ACCOUNT_CODE)).thenReturn(shipment);
		
		Checkout c = checkoutService.createOrder(ACCOUNT_CODE, cart.getId());
		
		assertNotNull(c);
		assertTrue(c.getCart().getId().equals(cart.getId()));
		assertTrue(c.getShipmentInfo().getCustomer().getPrimaryAddress().getAddress().equals("address"));
		assertTrue(c.getPaymentInfo().getFirstName().equals("FN"));
		
	}

}

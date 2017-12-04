package com.nisum.quickout.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.persistence.domain.Address;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.repository.CustomerRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CustomerService.class }, locations = { "classpath:*application-context.xml" })
public class CustomerServiceTest {

	@MockBean CustomerRepository customerRepository;
	@Autowired CustomerService customerService;
	
	Customer customer = null;
	Address address = null;
	
	@Before
	public void setUp() {
		customer = new Customer("FN", "LN", "email@email.com", "999999999");
		address = new Address("address", "address2", "city", "state", "99999");
	}
	
	@Test
	public void create_noAddress() {
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		assertNotNull(customer);
		customer = customerService.save(customer);
		assertTrue(customer.getAddresses().size() == 0);
		assertNull(customer.getPrimaryAddress());
	}
	
	@Test
	public void create_withAddress() {
		customer.getAddresses().add(address);
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		
		customer = customerService.save(customer);
		assertTrue(customer.getAddresses().size() == 1);
		assertNotNull(customer.getPrimaryAddress());
	}

	@Test(expected=QuickoutNotFoundException.class)
	public void update_NotFound() {
		when(customerRepository.findByEmail(customer.getEmail())).thenReturn(null);
		
		customer = customerService.update(customer);
		verify(customerRepository, never()).save(customer);
	}
	
	@Test
	public void updated() {
		customer.setId(1L);
		Customer toUpdate = new Customer("FN_u", "LN_u", "email@email.com", "111111111");
		Mockito.when(customerRepository.findByEmail(toUpdate.getEmail())).thenReturn(customer);
		Mockito.when(customerRepository.save(toUpdate)).thenReturn(toUpdate);
		
		customer = customerService.update(toUpdate);
		assertTrue(customer.getId() == 1L);
		assertTrue(customer.getFirstName().equals("FN_u"));
	}

	@Test(expected=QuickoutNotFoundException.class)
	public void find_notFound() {
		when(customerRepository.findOne(Mockito.any(Specification.class))).thenReturn(null);
		customerService.find(customer);
	}

}

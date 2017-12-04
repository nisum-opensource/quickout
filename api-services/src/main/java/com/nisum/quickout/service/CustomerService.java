/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nisum.quickout.exception.QuickoutNotFoundException;
import com.nisum.quickout.persistence.domain.Customer;
import com.nisum.quickout.persistence.repository.CustomerRepository;
import com.nisum.quickout.persistence.repository.SearchCriteria;
import com.nisum.quickout.persistence.repository.SearchCriteria.SearchOperation;
import com.nisum.quickout.persistence.repository.SearchSpecification;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer update(Customer customer) {
		Customer found = customerRepository.findByEmail(customer.getEmail().trim());
		if(found == null)
			throw new QuickoutNotFoundException(String.format("No matching records found for customer with email %s", customer.getEmail()));
		customer.setId(found.getId());
		return customerRepository.save(customer);
	}
	
	public Customer find(Customer c){
		List<Specification<Customer>> specs = new ArrayList<>();
		if(StringUtils.isNotBlank(c.getFirstName()))
			specs.add(new SearchSpecification(new SearchCriteria("firstName", SearchOperation.EQ, c.getFirstName())));
		if(StringUtils.isNotBlank(c.getLastName()))
			specs.add(new SearchSpecification(new SearchCriteria("lastName", SearchOperation.EQ, c.getLastName())));
		if(StringUtils.isNotBlank(c.getPhone()))
			specs.add(new SearchSpecification(new SearchCriteria("phone", SearchOperation.EQ, c.getPhone())));
		if(StringUtils.isNotBlank(c.getEmail()))
			specs.add(new SearchSpecification(new SearchCriteria("email", SearchOperation.EQ, c.getEmail())));
		
		if(CollectionUtils.isEmpty(specs))
			throw new QuickoutNotFoundException("No matching records found.");
		
		Specification<Customer> spec = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            spec = Specifications.where(spec).and(specs.get(i));
        }
        c = customerRepository.findOne(spec);
        if(c ==null)
        	throw new QuickoutNotFoundException("No matching records found.");
        return c;
	}
}

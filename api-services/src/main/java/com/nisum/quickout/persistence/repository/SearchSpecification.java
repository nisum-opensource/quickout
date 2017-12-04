/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nisum.quickout.persistence.domain.Customer;

public class SearchSpecification implements Specification<Customer> {

	private SearchCriteria criteria;
	
	public SearchSpecification(SearchCriteria s){
		this.criteria = s;
	}

	@Override
	public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		switch (criteria.getOperation()) {
			case GTE:
				return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case GT:
				return builder.greaterThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case LTE:
				return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case LT:
				return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case NOT_EQ:
				return builder.notEqual(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case EQ:
				return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue().toString());
			case LIKE:
				return builder.like(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		}
		return null;
	}

}

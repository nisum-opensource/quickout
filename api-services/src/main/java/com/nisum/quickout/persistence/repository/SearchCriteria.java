/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.repository;

public class SearchCriteria {

	public enum SearchOperation{
		GT, LT, GTE, LTE, EQ, NOT_EQ, LIKE;
	}
	
	private String key;
	private SearchOperation operation;
	private String value;
	
	public SearchCriteria(String key, SearchOperation op, String value){
		this.key = key;
		this.operation = op;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SearchOperation getOperation() {
		return operation;
	}

	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

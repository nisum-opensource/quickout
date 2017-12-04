/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.request;

import java.util.List;

public class Bag {
	private List<CallbackItem> bag;

	public Bag() {
	}

	public Bag(List<CallbackItem> bag) {
		this.bag = bag;
	}

	public List<CallbackItem> getBag() {
		return bag;
	}

	public void setBag(List<CallbackItem> bag) {
		this.bag = bag;
	}

	@Override
	public String toString() {
		return "Bag [bag=" + bag + "]";
	}
	
}

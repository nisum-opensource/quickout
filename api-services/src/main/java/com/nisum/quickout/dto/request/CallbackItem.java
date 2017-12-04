/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.dto.request;

public class CallbackItem {
	private String sku;
	private String product_name;
	private String product_description;
	private float list_price;
	private float discounted_price;
	private float sell_price;
	private String image_url;

	public CallbackItem() {
	}

	public CallbackItem(String sku, String product_name, String product_description, float list_price,
			float discounted_price, float sell_price, String image_url) {
		this.sku = sku;
		this.product_name = product_name;
		this.product_description = product_description;
		this.list_price = list_price;
		this.discounted_price = discounted_price;
		this.sell_price = sell_price;
		this.image_url = image_url;
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public float getList_price() {
		return list_price;
	}
	public void setList_price(float list_price) {
		this.list_price = list_price;
	}
	public float getDiscounted_price() {
		return discounted_price;
	}
	public void setDiscounted_price(float discounted_price) {
		this.discounted_price = discounted_price;
	}
	public float getSell_price() {
		return sell_price;
	}
	public void setSell_price(float sell_price) {
		this.sell_price = sell_price;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	@Override
	public String toString() {
		return "CallbackItem [sku=" + sku + ", product_name=" + product_name + ", product_description="
				+ product_description + ", list_price=" + list_price + ", discounted_price=" + discounted_price
				+ ", sell_price=" + sell_price + ", image_url=" + image_url + "]";
	}
	
}

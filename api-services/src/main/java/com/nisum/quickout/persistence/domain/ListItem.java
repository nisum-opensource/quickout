/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/


package com.nisum.quickout.persistence.domain;

/**
 * @author Adnan Ahmed
 * 
 * Description of Items in bucket
 */
public class ListItem {

    public ListItem() {
    }
    
    public ListItem(String src, String title, float price, Integer quantity) {
		this.src = src;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}

    private Long id;
    private String sku;
	private String src;
    private String title;
    private float price;
    private Integer quantity;
    private Cart cart;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "ListItem [id=" + id + ", sku=" + sku + ", src=" + src + ", title=" + title + ", price=" + price
				+ ", quantity=" + quantity + ", cart=" + cart + "]";
	}

}

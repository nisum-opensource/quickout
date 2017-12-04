/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout.persistence.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "sku_id", "cart_id" }) })
public class CartDetails extends BaseAuditableDomain {
	public CartDetails(){}
	public CartDetails(String sku, String skuName, Integer quantity, Float listPrice, Float sellingPrice,
			String imageSource) {
		super();
		this.sku = sku;
		this.skuName = skuName;
		this.quantity = quantity;
		this.listPrice = listPrice;
		this.sellingPrice = sellingPrice;
		this.imageSource = imageSource;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;

	@NotNull
	@Column(name = "sku_id")
	private String sku;

	@Column(name = "sku_name")
	private String skuName;

	@Column
	@NotNull
	@Min(1)
	private Integer quantity;

	@Column(name = "list_price")
	private Float listPrice;

	@Column(name = "selling_price")
	private Float sellingPrice;

	@Column(name = "img_src")
	private String imageSource;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Cart cart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getListPrice() {
		return listPrice;
	}

	public void setListPrice(Float listPrice) {
		this.listPrice = listPrice;
	}

	public Float getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "CartDetails [id=" + id + ", sku=" + sku + ", skuName=" + skuName + ", quantity=" + quantity
				+ ", listPrice=" + listPrice + ", sellingPrice=" + sellingPrice + ", imageSource=" + imageSource
				+ ", cart=" + cart + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartDetails other = (CartDetails) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

}

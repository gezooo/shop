package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/*
* @author gez
* @version 0.1
*/

@Entity
public class DeliveryItem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8853641347716610438L;
	
	private String productSn;
	
	private String productName;
	
	private String productHtmlFilePath;
	
	private Integer deliveryQuantity;
	
	private Product product;
	
	private Shipping shipping;
	
	private Reship reship;

	@Column(updatable = false, nullable = false)
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	@Column(updatable = false, nullable = false)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(updatable = false, nullable = false)
	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}

	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}

	@Column(updatable = false, nullable = false)
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}
	
	

}

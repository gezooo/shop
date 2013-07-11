package com.zg.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.zg.common.util.SystemConfigUtils;

@Entity
public class OrderItem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1877670970526763403L;
	
	private String productSn;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private String productHtmlFilePath;
	
	private Integer productQuantity;
	
	private Integer deliveryQuantity;
	
	private Integer totalDeliveryQuantity;
	
	private Order order;
	
	private Product product;

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

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = SystemConfigUtils.getOrderScaleBigDecimal(productPrice);
	}

	@Column(updatable = false, nullable = false)
	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}

	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}

	@Column(nullable = false)
	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Column(nullable = false)
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@Column(nullable = false)
	public Integer getTotalDeliveryQuantity() {
		return totalDeliveryQuantity;
	}

	public void setTotalDeliveryQuantity(Integer totalDeliveryQuantity) {
		this.totalDeliveryQuantity = totalDeliveryQuantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Transient
	public BigDecimal getSubTotalPrice() {
		BigDecimal subTotalPrice = this.productPrice.multiply(new BigDecimal(this.productQuantity.toString()));
		return SystemConfigUtils.getOrderScaleBigDecimal(subTotalPrice);
	}
	

}

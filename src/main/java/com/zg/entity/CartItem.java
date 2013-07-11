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
public class CartItem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1295353765438399966L;
	
	private Integer quantity;
	
	private Product product;
	
	private Member member;

	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@Transient
	public BigDecimal getPreferentialPrice() {
		if(this.member != null) {
			return this.product.getPreferentialPrice(this.member);
		} else {
			return this.product.getPrice();
		}
	}
	
	@Transient
	public BigDecimal getSubTotalPrice() {
		BigDecimal subTotalPrice = this.getPreferentialPrice().multiply(new BigDecimal(this.quantity.toString()));
		return SystemConfigUtils.getPriceScaleBigDecimal(subTotalPrice);
	}
	
	

}

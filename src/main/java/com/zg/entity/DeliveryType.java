package com.zg.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.zg.entity.Product.WeightUnit;
import com.zg.util.ArithUtil;
import com.zg.util.SystemConfigUtil;

@Entity
public class DeliveryType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3004606679866851211L;
	
	public enum DeliveryMethod {
		DELIVERY_AGAINST_PAYMENT, CASH_ON_DELIVERY
	}
	
	private String name;
	
	private DeliveryMethod deliveryMethod;
	
	private Double firstWeight;
	
	private Double continueWeight;
	
	private WeightUnit firstWeightUnit;
	
	private WeightUnit continueWeightUnit;
	
	private BigDecimal firstWeightPrice;
	
	private BigDecimal continueWeightPrice;
	
	private String description;
	
	private Integer orderList;
	
	private DeliveryCorp defaultDeliveryCorp;
	
	private Set<Order> orderSet;
	
	private Set<Shipping> shippingSet;
	
	private Set<Reship> reshipSet;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	@Column(nullable = false)
	public Double getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Double firstWeight) {
		this.firstWeight = firstWeight;
	}

	@Column(nullable = false)
	public Double getContinueWeight() {
		return continueWeight;
	}

	public void setContinueWeight(Double continueWeight) {
		this.continueWeight = continueWeight;
	}

	@Enumerated
	@Column(nullable = false)
	public WeightUnit getFirstWeightUnit() {
		return firstWeightUnit;
	}

	public void setFirstWeightUnit(WeightUnit firstWeightUnit) {
		this.firstWeightUnit = firstWeightUnit;
	}

	@Enumerated
	@Column(nullable = false)
	public WeightUnit getContinueWeightUnit() {
		return continueWeightUnit;
	}

	public void setContinueWeightUnit(WeightUnit continueWeightUnit) {
		this.continueWeightUnit = continueWeightUnit;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}

	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = SystemConfigUtil.getPriceScaleBigDecimal(firstWeightPrice);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getContinueWeightPrice() {
		return continueWeightPrice;
	}

	public void setContinueWeightPrice(BigDecimal continueWeightPrice) {
		this.continueWeightPrice = SystemConfigUtil.getPriceScaleBigDecimal(continueWeightPrice);;
	}

	@Column(length = 10000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String descirption) {
		this.description = descirption;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DeliveryCorp getDefaultDeliveryCorp() {
		return defaultDeliveryCorp;
	}

	public void setDefaultDeliveryCorp(DeliveryCorp defaultDeliveryCorp) {
		this.defaultDeliveryCorp = defaultDeliveryCorp;
	}

	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Shipping> getShippingSet() {
		return shippingSet;
	}

	public void setShippingSet(Set<Shipping> shippingSet) {
		this.shippingSet = shippingSet;
	}

	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Reship> getReshipSet() {
		return reshipSet;
	}

	public void setReshipSet(Set<Reship> reshipSet) {
		this.reshipSet = reshipSet;
	}
	
	@Transient
	public static double toWeightGram(double weight, WeightUnit weightUnit) {
		double weightGram = 0D;
		if(weightUnit == WeightUnit.g) {
			weightGram = weight;
		} else if(weightUnit == WeightUnit.kg) {
			weightGram = ArithUtil.mul(weight, 1000);
		} else if(weightUnit == WeightUnit.t) {
			weightGram = ArithUtil.mul(weight, 1000000);
		} 
		return weightGram;
	}
	
	@Transient
	public BigDecimal getDeliveryFee(double totalWeight, WeightUnit weightUnit) {
		double totalWeightGram = toWeightGram(totalWeight, weightUnit);
		double firstWeightGram = toWeightGram(this.firstWeight, this.firstWeightUnit);
		double continueWeightGram = toWeightGram(this.continueWeight, this.continueWeightUnit);
		BigDecimal deliveryFee = new BigDecimal("0");
		if(totalWeightGram <= firstWeightGram) {
			deliveryFee = this.firstWeightPrice;
		} else {
			Double continueWeightCount = Math.ceil(ArithUtil.div(ArithUtil.sub(totalWeightGram, firstWeightGram), continueWeightGram));
			deliveryFee = this.firstWeightPrice.add(continueWeightPrice.multiply(new BigDecimal(continueWeightCount.toString())));
		}
		return SystemConfigUtil.getOrderScaleBigDecimal(deliveryFee);
		
	}
	
	public BigDecimal getDeliveryFee(double totalWeight) {
		return getDeliveryFee(totalWeight, WeightUnit.g);
	}
	
	
	
	
	
	
	

}

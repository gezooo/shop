package com.zg.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.zg.entity.Product.WeightUnit;
import com.zg.util.SystemConfigUtil;

@Entity
public class Order extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192240900593393146L;
	
	public static final int DEFAULT_ORDER_LIST_PAGE_SIZE = 15;
	
	public enum OrderStatus {
		UNPROCESSED, PROCESSED, COMPLETED, INVALID
	}
	
	public enum PaymentStatus {
		UNPAID, PART_PAID, PAID, PART_REFUND, REFUNDED
	}
	
	public enum ShippingStatus {
		UNShIPPED, PART_SHIPPED, SHIPPED, PART_RESHIPED, RESHIPED 
	}
	
	private String orderSn;
	
	private OrderStatus orderStatus;
	
	private PaymentStatus paymentStatus;
	
	private ShippingStatus shippingStatus;
	
	private String deliveryTypeName;
	
	private String paymentConfigName;
	
	private BigDecimal productTotalPrice;
	
	private BigDecimal deliveryFee;
	
	private BigDecimal paymentFee;
	
	private BigDecimal totalAmount;
	
	private BigDecimal paidAmount;
	
	private Double productWeight;
	
	private WeightUnit productWeightUnit;
	
	private Integer productTotalQuantity;
	
	private String shipName;
	
	private String shipArea;
	
	private String shipAreaPath;
	
	private String shipAddress;
	
	private String shipZipcode;
	
	private String shipPhone;
	
	private String shipMobile;
	
	private String memo;
	
	private Member member;
	
	private DeliveryType deliveryType;
	
	private PaymentConfig paymentConfig;
	
	private Set<OrderItem> orderItemSet;
	
	private Set<OrderLog> orderLogSet;
	
	private Set<Payment> paymentSet;
	
	private Set<Refund> refundSet;
	
	private Set<Shipping> shippingSet;
	
	private Set<Reship> reshipSet;

	@Column(unique = true, updatable = false, nullable = false)
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	@Enumerated
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Enumerated
	@Column(nullable = false)
	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@Column(nullable = false)
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	@Column(nullable = false)
	public String getPaymentConfigName() {
		return paymentConfigName;
	}

	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = SystemConfigUtil.getOrderScaleBigDecimal(deliveryFee);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SystemConfigUtil.getOrderScaleBigDecimal(paymentFee);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = SystemConfigUtil.getOrderScaleBigDecimal(totalAmount);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = SystemConfigUtil.getOrderScaleBigDecimal(paidAmount);
	}

	@Column(nullable = false)
	public Double getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(Double productWeight) {
		this.productWeight = productWeight;
	}

	@Enumerated
	@Column(nullable = false)
	public WeightUnit getProductWeightUnit() {
		return productWeightUnit;
	}

	public void setProductWeightUnit(WeightUnit productWeightUnit) {
		this.productWeightUnit = productWeightUnit;
	}

	@Column(nullable = false)
	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

	@Column(nullable = false)
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(nullable = false)
	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	public String getShipAreaPath() {
		return shipAreaPath;
	}

	public void setShipAreaPath(String shipAreaPath) {
		this.shipAreaPath = shipAreaPath;
	}

	@Column(nullable = false)
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	@Column(nullable = false)
	public String getShipZipcode() {
		return shipZipcode;
	}

	public void setShipZipcode(String shipZipcode) {
		this.shipZipcode = shipZipcode;
	}

	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	@Column(length = 5000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate asc")
	public Set<OrderLog> getOrderLogSet() {
		return orderLogSet;
	}

	public void setOrderLogSet(Set<OrderLog> orderLogSet) {
		this.orderLogSet = orderLogSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Payment> getPaymentSet() {
		return paymentSet;
	}

	public void setPaymentSet(Set<Payment> paymentSet) {
		this.paymentSet = paymentSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Refund> getRefundSet() {
		return refundSet;
	}

	public void setRefundSet(Set<Refund> refundSet) {
		this.refundSet = refundSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Shipping> getShippingSet() {
		return shippingSet;
	}

	public void setShippingSet(Set<Shipping> shippingSet) {
		this.shippingSet = shippingSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY )
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Reship> getReshipSet() {
		return reshipSet;
	}

	public void setReshipSet(Set<Reship> reshipSet) {
		this.reshipSet = reshipSet;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

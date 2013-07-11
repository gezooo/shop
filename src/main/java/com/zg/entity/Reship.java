package com.zg.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.zg.common.util.SystemConfigUtils;

@Entity
public class Reship extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7670804114563700221L;
	
	private String reshipSn;
	
	private String deliveryTypeName;
	
	private String deliveryCorpName;
	
	private String deliverySn;
	
	private BigDecimal deliveryFee;
	
	private String shipName;
	
	private String shipArea;
	
	private String shipAreaPath;
	
	private String shipAddress;
	
	private String shipZipCode;
	
	private String shipPhone;
	
	private String shipMobile;
	
	private String memo;
	
	private Order order;
	
	private DeliveryType deliveryType;
	
	private Set<DeliveryItem> deliveryItemSet;

	@Column(updatable = false, nullable = false, unique = true)
	public String getReshipSn() {
		return reshipSn;
	}

	public void setReshipSn(String reshipSn) {
		this.reshipSn = reshipSn;
	}

	@Column(updatable = false, nullable = false)
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	@Column(updatable = false, nullable = false)
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	@Column(updatable = false)
	public String getDeliverySn() {
		return deliverySn;
	}

	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}

	@Column(updatable = false, nullable = false)
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = SystemConfigUtils.getPriceScaleBigDecimal(deliveryFee);
	}

	@Column(updatable = false, nullable = false)
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(updatable = false, nullable = false)
	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	@Column(updatable = false)
	public String getShipAreaPath() {
		return shipAreaPath;
	}

	public void setShipAreaPath(String shipAreaPath) {
		this.shipAreaPath = shipAreaPath;
	}

	@Column(updatable = false, nullable = false)
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	@Column(updatable = false, nullable = false)
	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	@Column(updatable = false)
	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	@Column(updatable = false)
	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToMany(mappedBy = "reship", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate asc")
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	
	
	
	

}

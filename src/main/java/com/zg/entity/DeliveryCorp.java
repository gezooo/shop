package com.zg.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/*
* @author gez
* @version 0.1
*/

@Entity
public class DeliveryCorp extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147346296140780852L;
	
	private String name;
	
	private String url;
	
	private Integer orderList;
	
	private Set<DeliveryType> deliveryTypeSet;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "defaultDeliveryCorp")
	public Set<DeliveryType> getDeliveryTypeSet() {
		return deliveryTypeSet;
	}

	public void setDeliveryTypeSet(Set<DeliveryType> deliveryTypeSet) {
		this.deliveryTypeSet = deliveryTypeSet;
	}
	
	

}

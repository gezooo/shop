package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
* @author gez
* @version 0.1
*/

@Entity
public class FriendLink extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4540555118562058145L;
	
	private String name;
	
	private String logo;
	
	private String url;
	
	private Integer orderList;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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
	
	

}

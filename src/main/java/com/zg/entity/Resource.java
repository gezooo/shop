package com.zg.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Resource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -925397554559444876L;
	
	public static final String SEPARATOR = ",";
	
	private String name;
	
	private String value;
	
	private Boolean isSystem;
	
	private String description;
	
	private Integer orderList;
	
	private Set<Role> roleSet;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@ManyToMany(mappedBy = "resourceSet", fetch = FetchType.LAZY)
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public static String getSeparator() {
		return SEPARATOR;
	}
	
	@Transient
	public String getRoleSetString() {
		StringBuffer stringBuffer = new StringBuffer();
		if(this.roleSet == null || this.roleSet.size() == 0) {
			return "";
		}
		for(Role role : this.roleSet) {
			stringBuffer.append(SEPARATOR + role.getValue());
		}
		if(stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(0);
		}
		return stringBuffer.toString();
	}
	
	

}

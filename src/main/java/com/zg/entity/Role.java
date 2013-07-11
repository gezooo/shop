package com.zg.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -289982990036558123L;
	
	private String name;
	
	private String value;
	
	private Boolean isSystem;
	
	private String description;
	
	private Set<Admin> adminSet;
	
	private Set<Resource> resourceSet;

	
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

	@Column(nullable = false, updatable = true)
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

	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Resource> getResourceSet() {
		return resourceSet;
	}

	public void setResourceSet(Set<Resource> resourceSet) {
		this.resourceSet = resourceSet;
	}
	
	
	
	

}

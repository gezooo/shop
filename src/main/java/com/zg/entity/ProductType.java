package com.zg.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

/*
* @author gez
* @version 0.1
*/

@Entity
public class ProductType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3249439028991987601L;
	
	private String name;
	
	private List<ProductAttribute> productAttributeList;
	
	private Set<Product> productSet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy(clause = "orderList asc")
	public List<ProductAttribute> getProductAttributeList() {
		return productAttributeList;
	}

	public void setProductAttributeList(List<ProductAttribute> productAttributeList) {
		this.productAttributeList = productAttributeList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	
	@Transient
	public List<ProductAttribute> getEnabledProductAttributeList() {
		if (productAttributeList == null) {
			return null;
		}
		List<ProductAttribute> enabledProductAttributeList = new ArrayList<ProductAttribute>();
		for (ProductAttribute productAttribute : productAttributeList) {
			if (productAttribute.getIsEnabled()) {
				enabledProductAttributeList.add(productAttribute);
			}
		}
		return enabledProductAttributeList;
	}
	
	

}

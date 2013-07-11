package com.zg.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

/*
* @author gez
* @version 0.1
*/

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "productType_id"})
	}
)
public class ProductAttribute extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3179735708144548584L;
	
	public enum AttributeType {
		TEXT, NUMBER, ALPHAINT, SELECT, CHECKBOX, DATE
	}
	
	private String name;
	
	private AttributeType attributeType;
	
	private Boolean isRequired;
	
	private Boolean isEnabled;
	
	private Integer orderList;
	
	private String attributeOptionStore;
	
	private ProductType productType;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}

	@Column(nullable = false)
	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	
	public String getAttributeOptionStore() {
		return attributeOptionStore;
	}

	public void setAttributeOptionStore(String attributeOptionStore) {
		this.attributeOptionStore = attributeOptionStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getAttributeOptionList() {
		if (StringUtils.isEmpty(attributeOptionStore)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(attributeOptionStore);
		return (List<String>) JSONSerializer.toJava(jsonArray);
	}
	

	@Transient
	public void setAttributeOptionList(List<String> attributeOptionList) {
		if (attributeOptionList == null || attributeOptionList.size() == 0) {
			attributeOptionStore = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(attributeOptionList);
		attributeOptionStore = jsonArray.toString();
	}
	
	
	

}

package com.zg.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;

import freemarker.template.utility.StringUtil;

@Entity
public class MemberAttribute extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4116371449556449245L;
	
	public enum AttributeType {
		TEXT, NUMBER, ALPHAINT, EMAIL, SELECT, CHECKBOX, NAME, GENDER, DATE, AREA, 
		ADDRESS, ZIPCODE, MOBILE, PHONE, QQ, MSN, WANGWANG, SKYPE
	}
	
	private String name;
	
	private AttributeType attributeType;
	
	private Boolean isRequired;
	
	private Boolean isEnabled;
	
	private Integer orderList;
	
	private String attributeOptionStore;

	@Column(nullable = false, unique = true)
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
	
	@Transient
	public List<String> getAttributeOptionList() {
		if(StringUtils.isEmpty(this.attributeOptionStore)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(this.attributeOptionStore);
		return (List<String>) JSONSerializer.toJava(jsonArray);
	}
	
	@Transient
	public void setAttributeOptionList(List<String> attributeOptionList) {
		if(attributeOptionList == null || attributeOptionList.size() == 0) {
			this.attributeOptionStore = null;
		}
		JSONArray jsonArray = JSONArray.fromObject(attributeOptionList);
		this.attributeOptionStore = jsonArray.toString();
	}
	

}

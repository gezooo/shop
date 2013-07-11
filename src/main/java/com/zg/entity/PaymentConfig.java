package com.zg.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.zg.beans.TenpayConfig;
import com.zg.common.util.JsonUtils;
import com.zg.common.util.SystemConfigUtils;

/*
* @author gez
* @version 0.1
*/

@Entity
public class PaymentConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3954017142870371119L;
	
	public enum PaymentConfigType {
		DEPOSIT, OFFLINE, TENPAY
	}
	
	public enum PaymentFeeType {
		SCALE, FIXED
	}
	
	private PaymentConfigType paymentConfigType;
	
	private String name;
	
	private PaymentFeeType paymentFeeType;
	
	private BigDecimal paymentFee;
	
	private String description;
	
	private Integer orderList;
	
	private String configObjectStore;
	
	private Set<Order> orderSet;
	
	private Set<Payment> paymentSet;
	
	private Set<Refund> refundSet;

	@Enumerated
	@Column(nullable = false)
	public PaymentConfigType getPaymentConfigType() {
		return paymentConfigType;
	}

	public void setPaymentConfigType(PaymentConfigType paymentConfigType) {
		this.paymentConfigType = paymentConfigType;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentFeeType getPaymentFeeType() {
		return paymentFeeType;
	}

	public void setPaymentFeeType(PaymentFeeType paymentFeeType) {
		this.paymentFeeType = paymentFeeType;
	}

	@Column(precision = 15, scale = 5)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SystemConfigUtils.getPriceScaleBigDecimal(paymentFee);
	}

	@Column(length = 10000)
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

	public String getConfigObjectStore() {
		return this.configObjectStore;
	}

	public void setConfigObjectStore(String configObjectStore) {
		this.configObjectStore = configObjectStore;
	}

	@OneToMany(mappedBy = "paymentConfig", fetch = FetchType.LAZY)
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	@OneToMany(mappedBy = "paymentConfig", fetch = FetchType.LAZY)
	public Set<Payment> getPaymentSet() {
		return paymentSet;
	}

	public void setPaymentSet(Set<Payment> paymentSet) {
		this.paymentSet = paymentSet;
	}

	@OneToMany(mappedBy = "paymentConfig", fetch = FetchType.LAZY)
	public Set<Refund> getRefundSet() {
		return refundSet;
	}

	public void setRefundSet(Set<Refund> refundSet) {
		this.refundSet = refundSet;
	}
	
	@Transient
	public Object getConfigObject() {
		if(StringUtils.isEmpty(this.configObjectStore)) {
			return null;
		}
		
		if(this.paymentConfigType == PaymentConfigType.DEPOSIT) {
			return null;
		} else if (this.paymentConfigType == PaymentConfigType.OFFLINE) {
			return null;
		} else if  (this.paymentConfigType == PaymentConfigType.TENPAY) {
			/*
			JsonConfig jsonConfig = new JsonConfig();
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(this.configObjectStore);
			jsonConfig.setRootClass(TenpayConfig.class);
			return (TenpayConfig) JSONSerializer.toJava(jsonObject, jsonConfig);
			*/
			return JsonUtils.json2Java(this.configObjectStore, TenpayConfig.class);
			
		}
		return null;
	}
	
	@Transient
	public void setConfigObject(Object object) {
		if(object == null) {
			this.configObjectStore = null;
			return;
		}
		//JSONObject jsonObject = JSONObject.fromObject(object);
		if(this.paymentConfigType == PaymentConfigType.DEPOSIT) {
			this.configObjectStore = null;
		} else if (this.paymentConfigType == PaymentConfigType.OFFLINE) {
			this.configObjectStore = null;
		} else if  (this.paymentConfigType == PaymentConfigType.TENPAY) {
			this.configObjectStore = JsonUtils.java2Json(object);
		}
	}
	
	@Transient
	public BigDecimal getPaymentFee(BigDecimal totalAmount) {
		BigDecimal paymentFee = new BigDecimal("0");
		if(paymentFeeType == PaymentFeeType.SCALE) {
			//here the paymentFee is "0", now I am not clear about the SCALE payment Fee Type.
			paymentFee = totalAmount.multiply(new BigDecimal(paymentFee.toString()).divide(new BigDecimal("100")));
		} else {
			paymentFee = this.paymentFee;
		}
		return paymentFee;
	}
	
	

}

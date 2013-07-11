package com.zg.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.zg.common.util.SystemConfigUtils;

@Entity
public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -471629221279239995L;
	
	public enum PaymentType {
		RECHARGE, DEPOSIT, ONLINE, OFFLINE
	}
	
	public enum PaymentStatus {
		READY, TIMEOUT, INVALID, SUCCESS, FAILURE
	}
	
	private String paymentSn;
	
	private PaymentType paymentType;
	
	private String paymentConfigName;
	
	private String bankName;
	
	private String bankAccount;
	
	private BigDecimal totalAmount;
	
	private BigDecimal paymentFee;
	
	private String payer;
	
	private String operator;
	
	private String memo;
	
	private PaymentStatus paymentStatus;
	
	private PaymentConfig paymentConfig;
	
	private Deposit deposit;
	
	private Order order;

	@Column(nullable = false, updatable = false, unique = true)
	public String getPaymentSn() {
		return paymentSn;
	}

	public void setPaymentSn(String paymentSn) {
		this.paymentSn = paymentSn;
	}

	@Enumerated
	@Column(nullable = false, updatable = false)
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Column(nullable = false, updatable = false)
	public String getPaymentConfigName() {
		return paymentConfigName;
	}

	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}

	@Column(updatable = false)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(updatable = false)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(nullable = false, updatable = false, precision = 15, scale = 5)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = SystemConfigUtils.getPriceScaleBigDecimal(totalAmount);
	}

	@Column(nullable = false, updatable = false, precision = 15, scale = 5)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SystemConfigUtils.getPriceScaleBigDecimal(paymentFee);;
	}

	@Column(nullable = false, updatable = false)
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(nullable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(updatable = false, length = 5000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	

}

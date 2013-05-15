package com.zg.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.zg.util.SystemConfigUtil;

@Entity
public class Deposit extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5097690227409087483L;
	
	public static final int DEFAULT_DEPOSIT_LIST_PAGE_SIZE = 15;
	
	public enum DepositType {
		MEMBER_RECHARGE, 
		MEMBER_PAYMENT,
		ADMIN_RECHARGE,
		ADMIN_CHARGE_BACK,
		ADMIN_PAYMENT,
		ADMIN_REFUND
	}
	
	private DepositType depositType;
	
	private BigDecimal credit;
	
	private BigDecimal debit;
	
	private BigDecimal balance;
	
	private Member member;
	
	private Payment payment;
	
	private Refund refund;

	@Enumerated
	@Column(nullable = false, updatable = false)
	public DepositType getDepositType() {
		return depositType;
	}

	public void setDepositType(DepositType depositType) {
		this.depositType = depositType;
	}

	@Column(nullable = false, updatable = false)
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = SystemConfigUtil.getOrderScaleBigDecimal(credit);
	}

	@Column(nullable = false, updatable = false)
	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = SystemConfigUtil.getOrderScaleBigDecimal(debit);
	}

	@Column(nullable = false, updatable = false)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = SystemConfigUtil.getOrderScaleBigDecimal(balance);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deposit")
	@Cascade(value = {CascadeType.DELETE})
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deposit")
	@Cascade(value = {CascadeType.DELETE})
	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}
	
	
	
	

}

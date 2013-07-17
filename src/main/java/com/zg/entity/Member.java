package com.zg.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import com.zg.beans.JsonJavaTransformerFactory;
import com.zg.common.util.SystemConfigUtils;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Member extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1060802466551903239L;
	
	public static final String LOGIN_MEMBER_ID_SESSION_NAME = "loginMemberId";
	
	public static final String LOGIN_MEMBER_USERNAME_COOKIE_NAME = "loginMemberUsername";
	
	public static final String LOGIN_REDIRECTION_URL_SESSION_NAME = "redirectionUrl";
	
	public static final String PASSWORD_RECOVER_KEY_SEPARATOR = "_";
	
	public static final int PASSWORD_RECOVERY_KEY_PERIOD = 10080; //unit Minutes
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String safeQuestion;
	
	private String safeAnswer;
	
	private Integer point;
	
	private BigDecimal deposit;
	
	private Boolean isAccountEnabled;
	
	private Boolean isAccountLocked;
	
	private Integer loginFailureCount;
	
	private Date lockedDate;
	
	private String registerIp;
	
	private String lastLoginIp;
	
	private Date lastLoginDate;
	
	private String passwordRecoverKey;
	
	
	private MemberRank memberRank;
	
	private Map<MemberAttribute, String> memberAttributeMapStore;
	
	private Set<Receiver> receiverSet;
	
	private Set<Product> favoriteProductSet;
	
	private Set<CartItem> cartItemSet;
	
	private Set<Message> inboxMessageSet;
	
	private Set<Message> outboxMessageSet;
	
	private Set<Order> orderSet;
	
	private Set<Deposit> depositSet;
	
	@Column(updatable = false, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSafeQuestion() {
		return safeQuestion;
	}

	public void setSafeQuestion(String safeQuestion) {
		this.safeQuestion = safeQuestion;
	}

	public String getSafeAnswer() {
		return safeAnswer;
	}

	public void setSafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
	}

	@Column(nullable = false)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = SystemConfigUtils.getOrderScaleBigDecimal(deposit);
	}

	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@Column(nullable = false, updatable = false)
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

	@ElementCollection
	@MapKeyJoinColumn
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value = {CascadeType.DELETE})
	public Map<MemberAttribute, String> getMemberAttributeMapStore() {
		return memberAttributeMapStore;
	}

	public void setMemberAttributeMapStore(
			Map<MemberAttribute, String> memberAttributeMapStore) {
		this.memberAttributeMapStore = memberAttributeMapStore;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Receiver> getReceiverSet() {
		return receiverSet;
	}

	public void setReceiverSet(Set<Receiver> receiverSet) {
		this.receiverSet = receiverSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("name desc")
	public Set<Product> getFavoriteProductSet() {
		return favoriteProductSet;
	}

	public void setFavoriteProductSet(Set<Product> favoriteProductSet) {
		this.favoriteProductSet = favoriteProductSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "toMember")
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Message> getInboxMessageSet() {
		return inboxMessageSet;
	}

	public void setInboxMessageSet(Set<Message> inboxMessageSet) {
		this.inboxMessageSet = inboxMessageSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromMember")
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Message> getOutboxMessageSet() {
		return outboxMessageSet;
	}

	public void setOutboxMessageSet(Set<Message> outboxMessageSet) {
		this.outboxMessageSet = outboxMessageSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@OrderBy("createDate desc")
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = {CascadeType.DELETE})
	@OrderBy("createDate desc")
	public Set<Deposit> getDepositSet() {
		return depositSet;
	}

	public void setDepositSet(Set<Deposit> depositSet) {
		this.depositSet = depositSet;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}
	
	@Transient
	public Map<MemberAttribute, List<String>> getMemberAttributeMap() {
		if(this.memberAttributeMapStore == null || this.memberAttributeMapStore.size() == 0) {
			return null;
		}
		Map<MemberAttribute, List<String>> memberAttributeMap = new HashMap<MemberAttribute, List<String>>();
		for(MemberAttribute memberAttribute : this.memberAttributeMapStore.keySet()) {
			String memberAttributeValueStore = this.memberAttributeMapStore.get(memberAttribute);
			if(StringUtils.isEmpty(memberAttributeValueStore)) {
				memberAttributeMap.put(memberAttribute, null);
			} else {
				/*
				JSONArray jsonArray = JSONArray.fromObject(memberAttributeValueStore);
				memberAttributeMap.put(memberAttribute, (List<String>)JSONSerializer.toJava(jsonArray));
				*/
				List<String> memberAttributeValueStoreVar = JsonJavaTransformerFactory.getJsonJavaTransformer().json2JavaList(memberAttributeValueStore, String.class);
				memberAttributeMap.put(memberAttribute, memberAttributeValueStoreVar);
			}
		}
		return memberAttributeMap;
	}
	
	@Transient
	public void setMemberAttributeMap(Map<MemberAttribute, List<String>> memberAttributeMap) {
		if(memberAttributeMap == null || memberAttributeMap.size() == 0) {
			this.memberAttributeMapStore = null;
			return;
		}
		Map<MemberAttribute, String> memberAttributeMapStore = new HashMap<MemberAttribute, String>();
		for(MemberAttribute memberAttribute : memberAttributeMap.keySet()) {
			List<String> memberAttributeValueList = memberAttributeMap.get(memberAttribute);
			if(memberAttributeValueList != null && memberAttributeValueList.size() > 0) {
				String memberAttributeMapStoreJsonVar = JsonJavaTransformerFactory.getJsonJavaTransformer().javaList2json(memberAttributeValueList);
				//JSONArray jsonArray = JSONArray.fromObject(memberAttributeValueList);
				memberAttributeMapStore.put(memberAttribute, memberAttributeMapStoreJsonVar);
			} else {
				memberAttributeMapStore.put(memberAttribute, null);
			}
			
		}
		this.memberAttributeMapStore = memberAttributeMapStore;
	}
	

}

package com.zg.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Admin extends BaseEntity implements UserDetails {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3608074269374885843L;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String name;
	
	private String department;
	
	private boolean isAccountEnabled;
	
	private Boolean isAccountLocked;
	
	private Boolean isAccountExpired;
	
	private Boolean isCredentialsExpired;
	
	private Integer loginFailureCount;
	
	private Date lockedDate;
	
	private Date loginDate;
	
	private String loginIp;
	
	private Set<Role> roleSet;
	
	private Set<GrantedAuthority> authorities;
	
	@Override
	@Transient
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	@Override
	@Column(updatable = false, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return this.isAccountEnabled;
	}

	
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(nullable = false)
	public boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(nullable = false)
	public boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
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

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@Transient
	public void unLock() {
		this.setLoginFailureCount(0);
		this.setIsAccountLocked(false);
		this.setLockedDate(null);
	}
	

}

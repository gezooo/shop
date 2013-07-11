package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Log extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503088349988170888L;
	
	private String operationName;
	
	private String operator;
	
	private String actionClassName;
	
	private String actionMethodName;
	
	private String ip;
	
	private String info;

	@Column(nullable = false)
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@Column(nullable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(nullable = false)
	public String getActionClassName() {
		return actionClassName;
	}

	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}

	@Column(nullable = false)
	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	@Column(nullable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(length = 5000)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	

}

package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The aggrement
 * @author zhangge
 *
 */
@Entity
public class Agreement extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5241497883730490982L;
	
	public static final String AGREEMENT_ID = "1";
	
	private String content;

	@Column(length = 1000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}

package com.zg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
* @author gez
* @version 0.1
*/

@Entity
public class Footer extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3397237281548056882L;
	
	public static final String FOOTER_ID = "1";
	
	private String content;

	@Column(length = 10000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}

package com.zg.beans;

import java.io.Serializable;


/*
* @author gez
* @version 0.1
*/

public class MailConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3202956125751133393L;
	public static final String SMTP_TEST = "smtpTest";// SMTP邮箱配置测试
	public static final String PASSWORD_RECOVER = "passwordRecover";// 密码找回
	
	private String name;// 配置名称
	private String description;// 描述
	private String subject;// 主题
	private String templateFilePath;// Freemarker模板文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
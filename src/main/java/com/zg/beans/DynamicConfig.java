package com.zg.beans;

import java.io.Serializable;

/*
* @author gez
* @version 0.1
*/

public class DynamicConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1811827836477425032L;
	
	private String name;// 配置名称
	private String description;// 描述
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

}

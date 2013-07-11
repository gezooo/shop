package com.zg.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.zg.beans.HtmlConfig;
import com.zg.common.util.TemplateConfigUtils;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 静态模板
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class TemplateHtmlAction extends BaseAdminAction {

	private static final long serialVersionUID = -4868275299665932737L;
	
	private HtmlConfig htmlConfig;
	private String templateFileContent;
	
	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		htmlConfig = TemplateConfigUtils.getHtmlConfig(htmlConfig.getName());
		templateFileContent = TemplateConfigUtils.readTemplateFileContent(htmlConfig);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		htmlConfig = TemplateConfigUtils.getHtmlConfig(htmlConfig.getName());
		TemplateConfigUtils.writeTemplateFileContent(htmlConfig, templateFileContent);
		ServletContext servletContext = ServletActionContext.getServletContext();
		freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		redirectionUrl = "template_html!list.action";
		return SUCCESS;
	}

	// 获取生成静态模板配置集合
	public List<HtmlConfig> getHtmlConfigList() {
		return TemplateConfigUtils.getHtmlConfigList();
	}

	public HtmlConfig getHtmlConfig() {
		return htmlConfig;
	}

	public void setHtmlConfig(HtmlConfig htmlConfig) {
		this.htmlConfig = htmlConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}
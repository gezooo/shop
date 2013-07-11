package com.zg.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.zg.beans.DynamicConfig;
import com.zg.common.util.TemplateConfigUtils;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 动态模板
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class TemplateDynamicAction extends BaseAdminAction {

	private static final long serialVersionUID = -5743098999960376400L;
	
	private DynamicConfig dynamicConfig;
	private String templateFileContent;
	
	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		dynamicConfig = TemplateConfigUtils.getDynamicConfig(dynamicConfig.getName());
		templateFileContent = TemplateConfigUtils.readTemplateFileContent(dynamicConfig);
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
		dynamicConfig = TemplateConfigUtils.getDynamicConfig(dynamicConfig.getName());
		TemplateConfigUtils.writeTemplateFileContent(dynamicConfig, templateFileContent);
		ServletContext servletContext = ServletActionContext.getServletContext();
		freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		redirectionUrl = "template_dynamic!list.action";
		return SUCCESS;
	}
	
	// 获取动态模板配置集合
	public List<DynamicConfig> getDynamicConfigList() {
		return TemplateConfigUtils.getDynamicConfigList();
	}

	public DynamicConfig getDynamicConfig() {
		return dynamicConfig;
	}

	public void setDynamicConfig(DynamicConfig dynamicConfig) {
		this.dynamicConfig = dynamicConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}
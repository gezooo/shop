package com.zg.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.zg.beans.DynamicConfig;
import com.zg.util.TemplateConfigUtil;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 动态模板
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3B93C55116C40C5218E1862CE13377DF
 * ============================================================================
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
		dynamicConfig = TemplateConfigUtil.getDynamicConfig(dynamicConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(dynamicConfig);
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
		dynamicConfig = TemplateConfigUtil.getDynamicConfig(dynamicConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(dynamicConfig, templateFileContent);
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		redirectionUrl = "template_dynamic!list.action";
		return SUCCESS;
	}
	
	// 获取动态模板配置集合
	public List<DynamicConfig> getDynamicConfigList() {
		return TemplateConfigUtil.getDynamicConfigList();
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
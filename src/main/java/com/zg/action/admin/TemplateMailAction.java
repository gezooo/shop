package com.zg.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.zg.beans.MailConfig;
import com.zg.common.util.TemplateConfigUtils;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 邮件模板
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3AE912EF052249487D08E69CADDD3FCD
 * ============================================================================
 */

@ParentPackage("admin")
public class TemplateMailAction extends BaseAdminAction {

	private static final long serialVersionUID = -3965561383196862741L;
	
	private MailConfig mailConfig;
	private String templateFileContent;
	
	@Resource
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		mailConfig = TemplateConfigUtils.getMailConfig(mailConfig.getName());
		templateFileContent = TemplateConfigUtils.readTemplateFileContent(mailConfig);
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
		mailConfig = TemplateConfigUtils.getMailConfig(mailConfig.getName());
		TemplateConfigUtils.writeTemplateFileContent(mailConfig, templateFileContent);
		ServletContext servletContext = ServletActionContext.getServletContext();
		freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		redirectionUrl = "template_dynamic!list.action";
		return SUCCESS;
	}
	
	// 获取邮件模板配置集合
	public List<MailConfig> getMailConfigList() {
		return TemplateConfigUtils.getMailConfigList();
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}
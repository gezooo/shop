package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.entity.Footer;
import com.zg.service.FooterService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 页面底部信息
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class FooterAction extends BaseAdminAction {

	private static final long serialVersionUID = -2689864301265300268L;

	private Footer footer;

	@Resource
	private FooterService footerService;

	// 编辑
	public String edit() {
		footer = footerService.getFooter();
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "footer.content", message = "内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Footer persistent = footerService.getFooter();
		BeanUtils.copyProperties(footer, persistent, new String[]{"id", "createDate", "modifyDate"});
		footerService.update(persistent);
		redirectionUrl = "footer!edit.action";
		return SUCCESS;
	}

	public Footer getFooter() {
		return footer;
	}

	public void setFooter(Footer footer) {
		this.footer = footer;
	}

}
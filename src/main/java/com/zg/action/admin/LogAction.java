package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.Log;
import com.zg.service.LogService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 日志
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX12370A283DAE7C30B22944B7DAC51220
 * ============================================================================
 */

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

	private static final long serialVersionUID = 8784555891643520648L;

	private Log log;

	@Resource
	private LogService logService;
	
	protected Pager<Log> pager;
	
	public Pager<Log> getPager() {
		return pager;
	}

	public void setPager(Pager<Log> pager) {
		this.pager = pager;
	}

	// 列表
	public String list() {
		pager = logService.findByPager(pager);
		return LIST;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}
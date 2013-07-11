package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.Log;
import com.zg.service.LogService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 日志
 * @author gez
 * @version 0.1
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
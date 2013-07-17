package com.zg.action.admin;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.zg.common.util.EncacheCacheConfigUtils;



/**
 * 后台Action类 - 缓存
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {

	private static final long serialVersionUID = 3290111140770511789L;
	
	@Resource
	private FreemarkerManager freemarkerManager;
	
	// 刷新所有缓存
	public String flush() {
		//cacheManager.flushAll();
		flushCache();
		ServletContext servletContext = ServletActionContext.getServletContext();
		freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		return SUCCESS;
	}
	
	// 更新页面缓存
	private void flushCache() {
		/*
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
		*/
		EncacheCacheConfigUtils.flushAll();
	}

}
package com.zg.action.admin;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.zg.util.EncacheCacheConfigUtil;


import freemarker.template.TemplateException;

/**
 * 后台Action类 - 缓存
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0FAAA132C15B2FB2C919342F341B91C6
 * ============================================================================
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
		EncacheCacheConfigUtil.flushAll();
	}

}
package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Refund;
import com.zg.entity.Reship;
import com.zg.service.ReshipService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 退货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8EAAC961EFD4ACBAA255A8B63695E97B
 * ============================================================================
 */

@ParentPackage("admin")
public class ReshipAction extends BaseAdminAction {

	private static final long serialVersionUID = -571777768643219736L;

	private Reship reship;

	@Resource
	private ReshipService reshipService;
	
	protected Pager<Reship> pager;
	
	public Pager<Reship> getPager() {
		return pager;
	}

	public void setPager(Pager<Reship> pager) {
		this.pager = pager;
	}

	// 查看
	public String view() {
		reship = reshipService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = reshipService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			reshipService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，会员数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}

}
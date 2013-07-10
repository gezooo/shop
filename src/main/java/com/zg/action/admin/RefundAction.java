package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Refund;
import com.zg.service.RefundService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 退款
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX25BB55410A0F8C44CDC7AD2649E42DCA
 * ============================================================================
 */

@ParentPackage("admin")
public class RefundAction extends BaseAdminAction {

	private static final long serialVersionUID = 229015918586548826L;

	private Refund refund;

	@Resource
	private RefundService refundService;
	
	protected Pager<Refund> pager;
	
	public Pager<Refund> getPager() {
		return pager;
	}

	public void setPager(Pager<Refund> pager) {
		this.pager = pager;
	}

	// 查看
	public String view() {
		refund = refundService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = refundService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			refundService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，会员数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

}
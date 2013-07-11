package com.zg.action.admin;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Payment;
import com.zg.service.PaymentService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 支付
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class PaymentAction extends BaseAdminAction {

	private static final long serialVersionUID = -4276446217262552805L;

	private Payment payment;

	@Resource
	private PaymentService paymentService;
	
	protected Pager<Payment> pager;
	
	public Pager<Payment> getPager() {
		return pager;
	}

	public void setPager(Pager<Payment> pager) {
		this.pager = pager;
	}

	// 查看
	public String view() {
		payment = paymentService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = paymentService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		paymentService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
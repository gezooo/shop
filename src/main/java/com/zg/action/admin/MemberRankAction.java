package com.zg.action.admin;

import java.util.Set;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Member;
import com.zg.entity.MemberRank;
import com.zg.service.MemberRankService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员分类
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class MemberRankAction extends BaseAdminAction {

	private static final long serialVersionUID = -5451875129461788865L;

	private MemberRank memberRank;

	@Resource
	private MemberRankService memberRankService;
	
	private Pager<MemberRank> pager;
	
	public Pager<MemberRank> getPager() {
		return pager;
	}

	public void setPager(Pager<MemberRank> pager) {
		this.pager = pager;
	}

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = memberRank.getName();
		if (memberRankService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 列表
	public String list() {
		pager = memberRankService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		for (String id : ids) {
			MemberRank memberRank = memberRankService.load(id);
			Set<Member> memberSet = memberRank.getMemberSet();
			if (memberSet != null && memberSet.size() > 0) {
				return ajaxJsonErrorMessage("会员等级[" + memberRank.getName() + "]下存在会员,删除失败!");
			}
		}
		long totalCount = memberRankService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajaxJsonErrorMessage("删除失败!必须至少保留一个会员等级");
		}
		memberRankService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		memberRank = memberRankService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberRank.name", message = "等级名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberRank.preferentialScale", message = "优惠百分比不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.point", message = "所需积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.isDefault", message = "是否为默认等级不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberRank.point", min = "0", message = "所需积分只允许为正整数或零!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (memberRank.getPreferentialScale() < 0) {
			addActionError("优惠百分比必须大于或等于零!");
			return ERROR;
		}
		if (memberRankService.getMemberRankByPoint(memberRank.getPoint()) != null) {
			addActionError("已存在相同积分的会员等级!");
			return ERROR;
		}
		memberRankService.save(memberRank);
		redirectionUrl = "member_rank!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberRank.name", message = "等级名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberRank.preferentialScale", message = "优惠百分比不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.point", message = "所需积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.isDefault", message = "是否为默认等级不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberRank.point", min = "0", message = "所需积分只允许为正整数或零!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (memberRank.getPreferentialScale() < 0) {
			addActionError("优惠百分比必须大于或等于零!");
			return ERROR;
		}
		MemberRank persistent = memberRankService.load(id);
		MemberRank equalPointMemberRank = memberRankService.getMemberRankByPoint(memberRank.getPoint());
		if (equalPointMemberRank != null && equalPointMemberRank != persistent) {
			addActionError("已存在相同积分的会员等级!");
			return ERROR;
		}
		BeanUtils.copyProperties(memberRank, persistent, new String[] {"id", "createDate", "modifyDate", "memberSet"});
		memberRankService.update(persistent);
		redirectionUrl = "member_rank!list.action";
		return SUCCESS;
	}

	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

}
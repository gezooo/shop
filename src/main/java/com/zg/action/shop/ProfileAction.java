package com.zg.action.shop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.zg.entity.Member;
import com.zg.entity.MemberAttribute;
import com.zg.entity.MemberAttribute.AttributeType;
import com.zg.service.AreaService;
import com.zg.service.MemberAttributeService;
import com.zg.service.MemberService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 个人信息
 * @author gez
 * @version 0.1
 */

@ParentPackage("member")
public class ProfileAction extends BaseShopAction {

	private static final long serialVersionUID = -7704084885878684413L;
	
	private Member member;
	
	@Resource
	private MemberService memberService;
	@Resource
	private MemberAttributeService memberAttributeService;
	@Resource
	private AreaService areaService;
	
	// 编辑
	public String edit() {
		member = getLoginMember();
		return INPUT;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Map<MemberAttribute, List<String>> memberAttributeMap = new HashMap<MemberAttribute, List<String>>();
		List<MemberAttribute> enabledMemberAttributeList = memberAttributeService.getEnabledMemberAttributeList();
		for (MemberAttribute memberAttribute : enabledMemberAttributeList) {
			String[] parameterValues = getParameterValues(memberAttribute.getId());
			if (memberAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
				addActionError(memberAttribute.getName() + "不允许为空!");
				return ERROR;
			}
			if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
				if (memberAttribute.getAttributeType() == AttributeType.NUMBER) {
					Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "只允许输入数字!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.ALPHAINT) {
					Pattern pattern = Pattern.compile("[a-zA-Z]+");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "只允许输入字母!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.EMAIL) {
					Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "E-mail格式错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.DATE) {
					Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "日期格式错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.AREA) {
					if (!areaService.isAreaPath(parameterValues[0])) {
						addActionError(memberAttribute.getName() + "地区错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.SELECT || memberAttribute.getAttributeType() == AttributeType.CHECKBOX) {
					List<String> attributeOptionList = memberAttribute.getAttributeOptionList();
					for (String parameterValue : parameterValues) {
						if (!attributeOptionList.contains(parameterValue)) {
							addActionError("参数错误!");
							return ERROR;
						}
					}
				}
				memberAttributeMap.put(memberAttribute, Arrays.asList(parameterValues));
			}
		}
		member.setMemberAttributeMap(memberAttributeMap);
		Member persistent = getLoginMember();
		if (StringUtils.isNotEmpty(member.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(member.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(member, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "safeQuestion", "safeAnswer", "point", "deposit", "isAccountEnabled", "isAccountLocked", "loginFailureCount", "lockedDate", "registerIp", "loginIp", "loginDate", "passwordRecoverKey", "memberRank", "receiverSet", "favoriteProductSet", "cartItemSet", "inboxMessageSet", "outboxMessageSet", "orderSet", "depositSet" });
		memberService.update(persistent);
		redirectionUrl = "profile!edit.action";
		return SUCCESS;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	// 获取已启用的会员注册项
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		return memberAttributeService.getEnabledMemberAttributeList();
	}

}
package com.zg.action.admin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.entity.Article;
import com.zg.entity.Deposit;
import com.zg.entity.Member;
import com.zg.entity.MemberAttribute;
import com.zg.entity.MemberRank;
import com.zg.entity.Deposit.DepositType;
import com.zg.entity.MemberAttribute.AttributeType;
import com.zg.service.DepositService;
import com.zg.service.MemberAttributeService;
import com.zg.service.MemberRankService;
import com.zg.service.MemberService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class MemberAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Member member;

	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private MemberAttributeService memberAttributeService;
	@Resource
	private com.zg.service.AreaService areaService;
	@Resource
	private DepositService depositService;
	
	protected Pager<Member> pager;
	
	public Pager<Member> getPager() {
		return pager;
	}

	public void setPager(Pager<Member> pager) {
		this.pager = pager;
	}

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = member.getUsername();
		if (memberService.isExistByUsername(username)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	// 查看
	public String view() {
		member = memberService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = memberService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		for (String id : ids) {
			Member member = memberService.load(id);
			if (member.getDeposit().compareTo(new BigDecimal("0")) > 0) {
				return ajaxJsonErrorMessage("会员[" + member.getUsername() + "]预付款余额不为零，删除失败！");
			}
		}
		memberService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		member = memberService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "member.point", message = "积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.isAccountEnabled", message = "是否启用不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "member.point", min = "0", message = "积分必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (member.getDeposit().compareTo(new BigDecimal("0")) < 0) {
			addActionError("预存款不允许小于0");
			return ERROR;
		}
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
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setSafeQuestion(null);
		member.setSafeAnswer(null);
		member.setIsAccountLocked(false);
		member.setLoginFailureCount(0);
		member.setPasswordRecoverKey(null);
		member.setLockedDate(null);
		member.setLastLoginDate(new Date());
		member.setRegisterIp(getRequest().getRemoteAddr());
		member.setLastLoginIp(null);
		member.setMemberAttributeMap(memberAttributeMap);
		member.setReceiverSet(null);
		member.setFavoriteProductSet(null);
		memberService.save(member);
		
		// 预存款记录
		if (member.getDeposit().compareTo(new BigDecimal("0")) > 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.ADMIN_RECHARGE);
			deposit.setCredit(member.getDeposit());
			deposit.setDebit(new BigDecimal("0"));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			deposit.setPayment(null);
			depositService.save(deposit);
		}
		redirectionUrl = "member!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "member.point", message = "积分不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!"),
			@RequiredFieldValidator(fieldName = "member.isAccountEnabled", message = "是否启用不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "member.point", min = "0", message = "积分必须为零或正整数!")
		},
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (member.getDeposit().compareTo(new BigDecimal("0")) < 0) {
			addActionError("预存款不允许小于0");
			return ERROR;
		}
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
		Member persistent = memberService.load(id);
		BigDecimal previousDeposit = persistent.getDeposit();
		BigDecimal currentDeposit = member.getDeposit();
		if (StringUtils.isEmpty(member.getPassword())) {
			member.setPassword(persistent.getPassword());
		} else {
			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		}
		BeanUtils.copyProperties(member, persistent, new String[] {"id", "createDate", "modifyDate", "username", "safeQuestion", "safeAnswer", "isAccountLocked", "loginFailureCount", "lockedDate", "registerIp", "loginIp", "loginDate", "passwordRecoverKey", "receiverSet", "favoriteProductSet", "orderSet", "inboxMessageSet", "outboxMessageSet", "orderSet", "depositSet" });
		memberService.update(persistent);
		
		// 预存款记录
		if (currentDeposit.compareTo(previousDeposit) > 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.ADMIN_RECHARGE);
			deposit.setCredit(currentDeposit.subtract(previousDeposit));
			deposit.setDebit(new BigDecimal("0"));
			deposit.setBalance(currentDeposit);
			deposit.setMember(persistent);
			deposit.setPayment(null);
			depositService.save(deposit);
		} else if (member.getDeposit().compareTo(previousDeposit) < 0) {
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.ADMIN_CHARGE_BACK);
			deposit.setCredit(new BigDecimal("0"));
			deposit.setDebit(previousDeposit.subtract(currentDeposit));
			deposit.setBalance(currentDeposit);
			deposit.setMember(persistent);
			deposit.setPayment(null);
			depositService.save(deposit);
		}
		redirectionUrl = "member!list.action";
		return SUCCESS;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	// 获取所有会员等级
	public List<MemberRank> getAllMemberRank() {
		return memberRankService.getAll();
	}

	// 获取已启用的会员注册项
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		return memberAttributeService.getEnabledMemberAttributeList();
	}

}
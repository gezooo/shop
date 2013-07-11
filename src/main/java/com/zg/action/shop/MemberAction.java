package com.zg.action.shop;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import com.zg.beans.CartItemCookie;
import com.zg.beans.SystemConfig;
import com.zg.common.JCaptchaEngine;
import com.zg.entity.CartItem;
import com.zg.entity.Member;
import com.zg.entity.Product;
import com.zg.service.AgreementService;
import com.zg.service.CartItemService;
import com.zg.service.MailService;
import com.zg.service.MemberRankService;
import com.zg.service.MemberService;
import com.zg.service.ProductService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.octo.captcha.service.CaptchaService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 会员
  * @author gez
 * @version 0.1
 */

@ParentPackage("shop")
@Results({ 
	@Result(name = "memberCenterIndexAction", location = "member_center!index.action", type = "redirect"),
	@Result(name = "index", location = "/", type = "redirect")
})
public class MemberAction extends BaseShopAction {

	private static final long serialVersionUID = 1115660086350733102L;

	private Member member;
	private Boolean isAgreeAgreement;
	private String passwordRecoverKey;

	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private AgreementService agreementService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private MailService mailService;
	@Resource
	private ProductService productService;
	@Resource
	private CartItemService cartItemService;
	
	// 会员登录验证
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String login() throws Exception {
		String captchaID = getRequest().getSession().getId();
		String challengeResponse = StringUtils.upperCase(getRequest().getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
			addActionError("验证码输入错误!");
			return ERROR;
		}
		SystemConfig systemConfig = getSystemConfig();
		Member loginMember = memberService.getMemberByUsername(member.getUsername());
		if (loginMember != null) {
			// 解除会员账户锁定
			if (loginMember.getIsAccountLocked()) {
				if (systemConfig.getIsLoginFailureLock()) {
					int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
					if (loginFailureLockTime != 0) {
						Date lockedDate = loginMember.getLockedDate();
						Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
						Date now = new Date();
						if (now.after(nonLockedTime)) {
							loginMember.setLoginFailureCount(0);
							loginMember.setIsAccountLocked(false);
							loginMember.setLockedDate(null);
							memberService.update(loginMember);
						}
					}
				} else {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked(false);
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if (!loginMember.getIsAccountEnabled()) {
				addActionError("您的账号已被禁用,无法登录!");
				return ERROR;
			}
			if (loginMember.getIsAccountLocked()) {
				addActionError("您的账号已被锁定,无法登录!");
				return ERROR;
			}
			if (!memberService.verifyMember(member.getUsername(), member.getPassword())) {
				if (systemConfig.getIsLoginFailureLock()) {
					int loginFailureLockCount = getSystemConfig().getLoginFailureLockCount();
					int loginFailureCount = loginMember.getLoginFailureCount() + 1;
					if (loginFailureCount >= systemConfig.getLoginFailureLockCount()) {
						loginMember.setIsAccountLocked(true);
						loginMember.setLockedDate(new Date());
					}
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					if (getSystemConfig().getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						addActionError("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
					} else {
						addActionError("您的用户名或密码错误!");
					}
				} else {
					addActionError("用户名或密码错误!");
				}
				return ERROR;
			}
		} else {
			addActionError("用户名或密码错误!");
			return ERROR;
		}
		loginMember.setLastLoginIp(getRequest().getRemoteAddr());
		loginMember.setLastLoginDate(new Date());
		memberService.update(loginMember);
		
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		
		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(member.getUsername().toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);
		
		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}
		
		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);
		
		String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		getRequest().getSession().removeAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		if (StringUtils.isEmpty(redirectionUrl)) {
			return "memberCenterIndexAction";
		} else {
			getResponse().sendRedirect(redirectionUrl);
			return null;
		}
	}
	
	// Ajax会员登录验证
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxLogin() throws Exception {
		String captchaID = getRequest().getSession().getId();
		String challengeResponse = StringUtils.upperCase(getRequest().getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
			return ajaxJsonErrorMessage("验证码输入错误！");
		}
		SystemConfig systemConfig = getSystemConfig();
		Member loginMember = memberService.getMemberByUsername(member.getUsername());
		if (loginMember != null) {
			// 解除会员账户锁定
			if (loginMember.getIsAccountLocked()) {
				if (systemConfig.getIsLoginFailureLock()) {
					int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
					if (loginFailureLockTime != 0) {
						Date lockedDate = loginMember.getLockedDate();
						Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
						Date now = new Date();
						if (now.after(nonLockedTime)) {
							loginMember.setLoginFailureCount(0);
							loginMember.setIsAccountLocked(false);
							loginMember.setLockedDate(null);
							memberService.update(loginMember);
						}
					}
				} else {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked(false);
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if (!loginMember.getIsAccountEnabled()) {
				return ajaxJsonErrorMessage("您的账号已被禁用,无法登录！");
			}
			if (loginMember.getIsAccountLocked()) {
				return ajaxJsonErrorMessage("您的账号已被锁定,无法登录！");
			}
			if (!memberService.verifyMember(member.getUsername(), member.getPassword())) {
				if (systemConfig.getIsLoginFailureLock()) {
					int loginFailureLockCount = getSystemConfig().getLoginFailureLockCount();
					int loginFailureCount = loginMember.getLoginFailureCount() + 1;
					if (loginFailureCount >= systemConfig.getLoginFailureLockCount()) {
						loginMember.setIsAccountLocked(true);
						loginMember.setLockedDate(new Date());
					}
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					if (loginFailureLockCount - loginFailureCount <= 3) {
						return ajaxJsonErrorMessage("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定！");
					} else {
						return ajaxJsonErrorMessage("您的用户名或密码错误！");
					}
				} else {
					return ajaxJsonErrorMessage("您的用户名或密码错误！");
				}
			}
		} else {
			return ajaxJsonErrorMessage("您的用户名或密码错误！");
		}
		loginMember.setLastLoginIp(getRequest().getRemoteAddr());
		loginMember.setLastLoginDate(new Date());
		memberService.update(loginMember);
		
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		
		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(member.getUsername().toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}
		
		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);
		
		return ajaxJsonSuccessMessage("会员登录成功！");
	}
	
	// 会员注销
	public String logout() {
		getRequest().getSession().removeAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Cookie cookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, null);
		cookie.setPath(getRequest().getContextPath() + "/");
		cookie.setMaxAge(0);
		getResponse().addCookie(cookie);
		return "index";
	}
	
	// 获取注册协议内容
	public String getAgreement() {
		return ajaxText(agreementService.getAgreement().getContent());
	}
	
	// 检查用户名是否存在
	public String checkUsername() {
		String username = member.getUsername();
		if (memberService.isExistByUsername(username)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	// Ajax会员注册保存
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxRegister() throws Exception {
		if (isAgreeAgreement == null || isAgreeAgreement == false) {
			return ajaxJsonErrorMessage("必须同意注册协议才可进行注册操作！");
		}
		if (!getSystemConfig().getIsRegister()) {
			return ajaxJsonErrorMessage("本站注册功能现已关闭！");
		}
		String captchaID = getRequest().getSession().getId();
		String challengeResponse = StringUtils.upperCase(getRequest().getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
			return ajaxJsonErrorMessage("验证码输入错误！");
		}
		member.setUsername(member.getUsername().toLowerCase());
		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setSafeQuestion(null);
		member.setSafeAnswer(null);
		member.setMemberRank(memberRankService.getDefaultMemberRank());
		member.setPoint(0);
		member.setDeposit(new BigDecimal("0"));
		member.setIsAccountEnabled(true);
		member.setIsAccountLocked(false);
		member.setLoginFailureCount(0);
		member.setPasswordRecoverKey(null);
		member.setLockedDate(null);
		member.setLastLoginDate(new Date());
		member.setRegisterIp(getRequest().getRemoteAddr());
		member.setLastLoginIp(getRequest().getRemoteAddr());
		member.setMemberAttributeMap(null);
		member.setReceiverSet(null);
		member.setFavoriteProductSet(null);
		memberService.save(member);
		
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());
		
		// 写入会员登录Cookie
		Cookie loginMemberCookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(member.getUsername().toLowerCase(), "UTF-8"));
		loginMemberCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberCookie);
		
		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(member);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}
		
		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);
		
		return ajaxJsonSuccessMessage("会员注册成功！");
	}
	
	// 密码找回
	public String passwordRecover() throws Exception {
		return "password_recover";
	}
	
	// 发送密码找回邮件
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
		}, 
		emails = { 
			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
		}
	)
	@InputConfig(resultName = "error")
	public String sendPasswordRecoverMail() throws Exception {
		Member persistent = memberService.getMemberByUsername(member.getUsername());
		if (persistent == null || StringUtils.equalsIgnoreCase(persistent.getEmail(), member.getEmail()) == false) {
			return ajaxJsonErrorMessage("用户名或E-mail输入错误！");
		}
		if (StringUtils.isNotEmpty(persistent.getSafeQuestion()) && StringUtils.isNotEmpty(persistent.getSafeAnswer())) {
			if (StringUtils.isEmpty(member.getSafeAnswer())) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("safeQuestion", persistent.getSafeQuestion());
				jsonMap.put(STATUS, WARN);
				jsonMap.put(MESSAGE, "请填写密码保护问题答案！");
				return ajaxJson(jsonMap);
			}
			if (StringUtils.equalsIgnoreCase(persistent.getSafeAnswer(), member.getSafeAnswer()) == false) {
				return ajaxJsonErrorMessage("密码保护答案错误！");
			}
		}
		if (!mailService.isMailConfigComplete()) {
			return ajaxJsonErrorMessage("系统邮件发送功能尚未配置，请联系管理员！");
		}
		persistent.setPasswordRecoverKey(memberService.buildPasswordRecoverKey());
		memberService.update(persistent);
		mailService.sendPasswordRecoverMail(persistent);
		return ajaxJsonSuccessMessage("系统已发送邮件到您的E-mail，请根据邮件提示操作！");
	}
	
	// 密码修改
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String passwordModify() throws Exception {
		Member persistent = memberService.get(id);
		if (persistent == null || StringUtils.equalsIgnoreCase(persistent.getPasswordRecoverKey(), passwordRecoverKey) == false) {
			addActionError("对不起，此密码找回链接已失效！");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = memberService.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils.addMinutes(passwordRecoverKeyBuildDate, Member.PASSWORD_RECOVERY_KEY_PERIOD);
		Date now = new Date();
		if (now.after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起，此密码找回链接已过期！");
			return ERROR;
		}
		return "password_modify";
	}
	
	// 密码更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
		}
	)
	@InputConfig(resultName = "error")
	public String passwordUpdate() throws Exception {
		Member persistent = memberService.get(id);
		if (persistent == null || StringUtils.equalsIgnoreCase(persistent.getPasswordRecoverKey(), passwordRecoverKey) == false) {
			addActionError("对不起，此密码找回链接已失效！");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = memberService.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils.addMinutes(passwordRecoverKeyBuildDate, Member.PASSWORD_RECOVERY_KEY_PERIOD);
		Date now = new Date();
		if (now.after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起，此密码找回链接已过期！");
			return ERROR;
		}
		persistent.setPassword(DigestUtils.md5Hex(member.getPassword()));
		persistent.setPasswordRecoverKey(null);
		memberService.update(persistent);
		redirectionUrl = getRequest().getContextPath() + "/";
		addActionMessage("密码修改成功！");
		return SUCCESS;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Boolean getIsAgreeAgreement() {
		return isAgreeAgreement;
	}

	public void setIsAgreeAgreement(Boolean isAgreeAgreement) {
		this.isAgreeAgreement = isAgreeAgreement;
	}

	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}

}
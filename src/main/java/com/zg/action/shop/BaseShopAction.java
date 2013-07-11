package com.zg.action.shop;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import com.zg.beans.Pager;
import com.zg.beans.SystemConfig;
import com.zg.common.util.SystemConfigUtils;
import com.zg.entity.Footer;
import com.zg.entity.FriendLink;
import com.zg.entity.Member;
import com.zg.entity.Navigation;
import com.zg.service.FooterService;
import com.zg.service.FriendLinkService;
import com.zg.service.MemberService;
import com.zg.service.NavigationService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台Action类 - 前台基类
 * @author gez
 * @version 0.1
 */

public class BaseShopAction extends ActionSupport {

	private static final long serialVersionUID = 6718838811223344556L;
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	
	protected String id;
	protected String[] ids;
	protected Pager pager;
	protected String logInfo;// 日志记录信息
	protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	
	@Resource
	protected MemberService memberService;
	
	@Resource
	protected NavigationService navigationService;
	@Resource
	protected FriendLinkService friendLinkService;
	@Resource
	protected FooterService footerService;
	
	public String input() {
		return null;
	}

	// 获取系统配置信息
	public SystemConfig getSystemConfig() {
		return SystemConfigUtils.getSystemConfig();
	}
	
	// 获取商品价格货币格式
	public String getPriceCurrencyFormat() {
		return SystemConfigUtils.getPriceCurrencyFormat();
	}
	
	// 获取商品价格货币格式（包含货币单位）
	public String getPriceUnitCurrencyFormat() {
		return SystemConfigUtils.getPriceUnitCurrencyFormat();
	}
	
	// 获取订单货币格式
	public String getOrderCurrencyFormat() {
		return SystemConfigUtils.getOrderCurrencyFormat();
	}
	
	// 获取订单货币格式（包含货币单位）
	public String getOrderUnitCurrencyFormat() {
		return SystemConfigUtils.getOrderUnitCurrencyFormat();
	}

	// 获取当前登录会员，若未登录则返回null
	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}

	// 获取Attribute
	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	// 获取Session
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 获取Session
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	// 设置Session
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取Application
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 设置页面不缓存
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}
	
	public List<Navigation> getTopNavigationList() {
		return navigationService.getTopNavigationList();
	}

	public List<Navigation> getMiddleNavigationList() {
		return navigationService.getMiddleNavigationList();
	}

	public List<Navigation> getBottomNavigationList() {
		return navigationService.getBottomNavigationList();
	}

	public List<FriendLink> getFriendLinkList() {
		return friendLinkService.getAll();
	}

	public List<FriendLink> getPictureFriendLinkList() {
		return friendLinkService.getPictureFriendLinkList();
	}

	public List<FriendLink> getTextFriendLinkList() {
		return friendLinkService.getTextFriendLinkList();
	}

	public Footer getFooter() {
		return footerService.getFooter();
	}

}
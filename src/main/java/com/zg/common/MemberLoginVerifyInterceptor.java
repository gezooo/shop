package com.zg.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zg.common.util.CommonUtils;
import com.zg.entity.Member;

/*
* @author gez
* @version 0.1
*/

public class MemberLoginVerifyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 4386362054894184770L;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberLoginVerifyInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug(CommonUtils.displayMessage(" called", null));
		}
		String loginMemberId = (String) invocation.getInvocationContext().getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (loginMemberId == null) {
			HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
			Cookie cookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, null);
			cookie.setPath(request.getContextPath() + "/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);	
			if(logger.isDebugEnabled()){
				logger.debug("set cookie loginMemberUsername to null");
				logger.debug(cookie.getName() + ": " + cookie.getValue());
				
			}
			String redirectionUrl = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				redirectionUrl += "?" + queryString;
			}
			request.getSession().setAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, redirectionUrl);
			return "login";
		}

		return invocation.invoke();
	}

}

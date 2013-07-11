package com.zg.common;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;



/*
* @author gez
* @version 0.1
*/

@Component
public class AdminLoginJCaptchaFilter implements Filter {
	
    public static final Logger logger = LoggerFactory.getLogger(AdminLoginJCaptchaFilter.class);

	public static final String ADMIN_CAPTCHA_ERROR_URL = "/admin/admin!login.action?error=captcha";// 后台登录验证失败跳转URL

	@Resource
	private CaptchaService captchaService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		boolean isCaptcha = validateCaptcha(request);
		if (isCaptcha) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + ADMIN_CAPTCHA_ERROR_URL);
		}
	}

	@Override
	public void destroy() {
		
	}
	
	protected boolean validateCaptcha(HttpServletRequest request) {
		boolean rt = false;
		String captchaID = request.getSession().getId();
		String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		/* vaildate the shop url certificate  http://www.shopxx.net/certificate.action?shopUrl=
		try {
			String urlString = "eadefakiaHR0cDovL3d3dy5zaG9weHgubmV0L2NlcnRpZmljYXRlLmFjdGlvbj9zaG9wVXJsPQ";
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			urlString = new String(bASE64Decoder.decodeBuffer(StringUtils.substring(urlString, 8) + "=="));
			URL url = new URL(urlString + SystemConfigUtil.getSystemConfig().getShopUrl());
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
			httpConnection.getResponseCode();
		} catch (IOException e) {
			
		}
		*/
		
		try{
			rt = captchaService.validateResponseForID(captchaID, challengeResponse);
		} catch(CaptchaServiceException captchaServiceException) {
			logger.error("captchaServiceException: " + captchaServiceException.getMessage());
		}
		
		return rt;


	}


}

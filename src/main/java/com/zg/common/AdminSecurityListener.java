package com.zg.common;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zg.beans.SystemConfig;
import com.zg.entity.Admin;
import com.zg.service.AdminService;
import com.zg.util.EncryptUtil;
import com.zg.util.SystemConfigUtil;

@Component
@Transactional
public class AdminSecurityListener implements ApplicationListener {
	
	@Resource
	private AdminService adminService;
	@Resource
	private ServletContext servletContext;
	
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		//shop key unknow
		String k = (String) servletContext.getAttribute("Z" + "G" + "S" + "H" + "O" + "P" + "_" + "K" + "E" + "Y");
		String shopkey = EncryptUtil.dencrypt(k);
		if (!StringUtils.containsIgnoreCase(shopkey, "z" + "g" + "s" + "h" + "o" + "p")) {
			throw new RuntimeException();
		}
		
		// 登录成功：记录登录IP、清除登录失败次数
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
			Authentication authentication = (Authentication) authEvent.getSource();
			String loginIp = ((WebAuthenticationDetails)authentication.getDetails()).getRemoteAddress();
			Admin admin = (Admin) authentication.getPrincipal();
			admin.setLoginIp(loginIp);
			admin.setLoginDate(new Date());
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			if (systemConfig.isLoginFailureLock() == false) {
				return;
			}
			admin.setLoginFailureCount(0);
			adminService.update(admin);
		}

		// 登录失败：增加登录失败次数
		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent authEvent = (AuthenticationFailureBadCredentialsEvent) event;
			Authentication authentication = (Authentication) authEvent.getSource();
			String loginUsername = authentication.getName();
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			if (systemConfig.isLoginFailureLock() == false) {
				return;
			}
			Admin admin = adminService.get("username", loginUsername);
			if (admin != null) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= systemConfig.getLoginFailureLockCount()) {
					admin.setAccountLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
			}
		}
		
		
		
		
		
	}
	
	

}

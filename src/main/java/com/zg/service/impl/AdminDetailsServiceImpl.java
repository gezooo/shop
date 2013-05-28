package com.zg.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zg.beans.SystemConfig;
import com.zg.dao.AdminDao;
import com.zg.entity.Admin;
import com.zg.entity.Role;
import com.zg.util.SystemConfigUtil;

@Service
@Transactional
public class AdminDetailsServiceImpl implements UserDetailsService {
	
	@Resource
	private AdminDao adminDao;

	@Override
	public Admin loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		Admin admin = adminDao.getAdminByUsername(userName);
		if(admin == null) {
			throw new UsernameNotFoundException("Admin ["+ userName +"] not exist");
		}
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		if(admin.isAccountLocked()) {
			if(systemConfig.isLoginFailureLock()) {
				int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
				if(loginFailureLockTime != 0) {
					Date lockedDate = admin.getLockedDate();
					Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					Date now = new Date();
					if(now.after(nonLockedTime)) {
						admin.unLock();
						adminDao.update(admin);
					}
					
				}
			} else {
				admin.unLock();
				adminDao.update(admin);				
			}
		}
		admin.setAuthorities(getGrantedAuthorities(admin));
		return admin;
	}
	
	public Set<GrantedAuthority> getGrantedAuthorities(Admin admin) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for(Role role : admin.getRoleSet()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getValue()));
		}
		return grantedAuthorities;
	}

}

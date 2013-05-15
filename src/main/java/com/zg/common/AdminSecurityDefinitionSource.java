package com.zg.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;



import org.springframework.stereotype.Component;

import com.zg.entity.Resource;
import com.zg.entity.Role;
import com.zg.service.ResourceService;


@Component
public class AdminSecurityDefinitionSource implements FactoryBean {
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	public boolean isSingleton() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return FilterInvocationSecurityMetadataSource.class;
	}

	@Override
	public Object getObject() throws Exception {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = buildRequestMap();       
		//UrlMatcher matcher = getUrlMatcher();
		DefaultFilterInvocationSecurityMetadataSource definitionSource = 
				new DefaultFilterInvocationSecurityMetadataSource(requestMap);
		return definitionSource;
	}

	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		Map<String, Collection<String>> resourceMap = this.getResourceMap();
		for (Map.Entry<String, Collection<String>> entry : resourceMap.entrySet()) {
			RequestMatcher requestMatcher = new AntPathRequestMatcher(entry.getKey());
			Collection<ConfigAttribute> configAttributeCollection = new ArrayList<ConfigAttribute>();
			for(String role : entry.getValue()) {
				configAttributeCollection.add(new SecurityConfig(role));
			}
			requestMap.put(requestMatcher, configAttributeCollection);
		}
		return requestMap;
	}
	
	protected Map<String, Collection<String>> getResourceMap() {
		Map<String, Collection<String>> resourceMap = new LinkedHashMap<String, Collection<String>>();
		for (Resource resource : resourceService.getAll()) {
			String resourceValue = resource.getValue();
			Collection<String> roleCollection = null;
			for(Role role : resource.getRoleSet()) {
				roleCollection = new ArrayList<String>();
				roleCollection.add(role.getName());
			}
			resourceMap.put(resourceValue, roleCollection);
		}
		return resourceMap;
	}

}

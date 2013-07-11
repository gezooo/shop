package com.zg.common;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/*
* @author gez
* @version 0.1
*/

public class TrimInterceptor extends AbstractInterceptor {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5359498020839464805L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		for (String key : parameters.keySet()) {
			Object value = parameters.get(key);
			if (value instanceof String[]) {
				String[] valueArray = (String[]) value;
				for (int i = 0; i < valueArray.length; i++) {
					valueArray[i] = valueArray[i].trim();
				}
				parameters.put(key, valueArray);
			}
		}
		return invocation.invoke();
	}

}

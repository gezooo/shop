package com.zg.common;

import com.zg.beans.Pager;

/*
* @author gez
* @version 0.1
*/

public class PagerSupport<T> {
	
	protected Pager<T> pager;
	
	public Pager<T> getPager() {
		return pager;
	}

	public void setPager(Pager<T> pager) {
		this.pager = pager;
	}

}

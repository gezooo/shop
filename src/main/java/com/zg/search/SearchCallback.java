package com.zg.search;

import java.util.List;

/*
* @author gez
* @version 0.1
*/

public interface SearchCallback<T> {
	
	public void onFinishSearch(List<T> results, int totalResultSize);

}

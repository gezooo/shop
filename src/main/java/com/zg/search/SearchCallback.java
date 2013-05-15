package com.zg.search;

import java.util.List;

public interface SearchCallback<T> {
	
	public void onFinishSearch(List<T> results, int totalResultSize);

}

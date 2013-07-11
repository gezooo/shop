package com.zg.beans;

import java.util.List;

/*
* @author gez
* @version 0.1
*/

public class Pager<T> {
	
	public enum OrderType {
		ASC, DESC
	}
	
	public static final Integer MAX_PAGE_SIZE = 500;
	
	private Integer pageNumber = 1;
	
	private Integer pageSize = 20;
	
	private Integer totalCount = 0;
	
	private Integer pageCount = 0;
	
	private String property;
	
	private String keywords;
	
	private String orderBy = "createDate";
	
	private OrderType orderType = OrderType.DESC;
	
	private List<T> list;
	
	public Integer getPageNumber() {
		return this.pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber) {
		if(pageNumber < 1) {
			this.pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}
	
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> dataList) {
		this.list = dataList;
	}
	
	

}

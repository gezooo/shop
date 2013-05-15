package com.zg.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zg.beans.Pager;

public interface BaseDao<T, PK extends Serializable>{
	
	public T get(PK id);

	public T load(PK id);

	public List<T> get(PK[] ids);

	public T get(String propertyName, Object value);

	public List<T> getList(String propertyName, Object value);

	public List<T> getAll();

	public Long getTotalCount();

	public boolean isUnique(String propertyName, Object oldValue, Object newValue);

	public boolean isExist(String propertyName, Object value);

	public PK save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void delete(PK id);

	public void delete(PK[] ids);

	public void flush();

	public void clear();

	public void evict(Object object);

	public Pager<T> findByPager(Pager<T> pager);
	
	public Pager<T> findByPager(Pager<T> pager, DetachedCriteria detachedCriteria);



}

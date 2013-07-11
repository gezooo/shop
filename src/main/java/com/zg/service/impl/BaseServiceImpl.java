package com.zg.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zg.beans.Pager;
import com.zg.dao.BaseDao;
import com.zg.service.BaseService;

/*
* @author gez
* @version 0.1
*/

public class BaseServiceImpl<T, PK extends Serializable > implements BaseService<T, PK>{
	
	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(PK id) {
		return baseDao.get(id);
	}

	@Override
	public T load(PK id) {
		return baseDao.load(id);
	}

	@Override
	public List<T> get(PK[] ids) {
		return baseDao.get(ids);
	}

	@Override
	public T get(String propertyName, Object value) {
		return baseDao.get(propertyName, value);
	}

	@Override
	public List<T> getList(String propertyName, Object value) {
		return baseDao.getList(propertyName, value);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public Long getTotalCount() {
		return baseDao.getTotalCount();
	}

	@Override
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		return baseDao.isUnique(propertyName, oldValue, newValue);
	}

	@Override
	public boolean isExist(String propertyName, Object value) {
		return baseDao.isExist(propertyName, value);
	}

	@Override
	public PK save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Override
	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	@Override
	public void flush() {
		baseDao.flush();
	}

	@Override
	public void clear() {
		baseDao.clear();
	}

	@Override
	public void evict(Object object) {
		baseDao.evict(object);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager) {
		return baseDao.findByPager(pager);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager,
			DetachedCriteria detachedCriteria) {
		return baseDao.findByPager(pager, detachedCriteria);
	}

}

package com.zg.dao.impl;

import java.util.List;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.ArticleCategoryDao;
import com.zg.entity.ArticleCategory;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/*
* @author gez
* @version 0.1
*/

@Repository
public class ArticleCategoryDaoImpl extends BaseDaoImpl<ArticleCategory, String> implements ArticleCategoryDao {
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getRootArticleCategoryList() {
		String hql = "from ArticleCategory articleCategory where articleCategory.parent is null order by articleCategory.orderList asc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory) {
		String hql = "from ArticleCategory articleCategory where articleCategory != ? and articleCategory.id in(:ids) order by articleCategory.orderList asc";
		String[] ids = articleCategory.getPath().split(ArticleCategory.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, articleCategory).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory) {
		String hql = "from ArticleCategory articleCategory where articleCategory != ? and articleCategory.path like ? order by articleCategory.orderList asc";
		return getSession().createQuery(hql).setParameter(0, articleCategory).setParameter(1, articleCategory.getPath() + "%").list();
	}
	
	// 设置path值
	@Override
	public String save(ArticleCategory articleCategory) {
		String id = super.save(articleCategory);
		ArticleCategory parent = articleCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			articleCategory.setPath(parentPath + ArticleCategory.PATH_SEPARATOR + id);
		} else {
			articleCategory.setPath(id);
		}
		super.update(articleCategory);
		return id;
	}
	
	// 设置path值
	@Override
	public void update(ArticleCategory articleCategory) {
		ArticleCategory parent = articleCategory.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			articleCategory.setPath(parentPath + ArticleCategory.PATH_SEPARATOR + articleCategory.getId());
		} else {
			articleCategory.setPath(articleCategory.getId());
		}
		super.update(articleCategory);
	}

	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleCategory> getAll() {
		String hql = "from ArticleCategory articleCategory order by articleCategory.orderList asc, articleCategory.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleCategory> getList(String propertyName, Object value) {
		String hql = "from ArticleCategory articleCategory where articleCategory." + propertyName + "=? order by articleCategory.orderList asc, articleCategory.createDate desc";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderBy("orderList");
			pager.setOrderType(OrderType.ASC);
		}
		return super.findByPager(pager, detachedCriteria);
	}

	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleCategory.class);
		return this.findByPager(pager, detachedCriteria);
	}

}
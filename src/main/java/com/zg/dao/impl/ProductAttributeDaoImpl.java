package com.zg.dao.impl;

import java.util.List;
import java.util.Map;

import com.zg.beans.Pager;
import com.zg.beans.Pager.OrderType;
import com.zg.dao.ProductAttributeDao;
import com.zg.entity.Product;
import com.zg.entity.ProductAttribute;
import com.zg.entity.ProductType;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 商品属性
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9F75E1059319B1C5FEFD644E19AE7400
 * ============================================================================
 */

@Repository
public class ProductAttributeDaoImpl extends BaseDaoImpl<ProductAttribute, String> implements ProductAttributeDao {
	
	@SuppressWarnings("unchecked")
	public List<ProductAttribute> getEnabledProductAttributeList() {
		String hql = "from ProductAttribute productAttribute where productAttribute.isEnabled = ? order by productAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductAttribute> getEnabledProductAttributeList(ProductType productType) {
		String hql = "from ProductAttribute productAttribute where productAttribute.isEnabled = ? and productAttribute.productType = ? order by productAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productType).list();
	}
	
	public ProductAttribute getProductAttribute(ProductType productType, String name) {
		String hql = "from ProductAttribute productAttribute where productAttribute.productType = ? and productAttribute.name = ?";
		return (ProductAttribute) getSession().createQuery(hql).setParameter(0, productType).setParameter(1, name).uniqueResult();
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductAttribute> getAll() {
		String hql = "from ProductAttribute productAttribute order by productAttribute.orderList asc productAttribute.createDate desc";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductAttribute> getList(String propertyName, Object value) {
		String hql = "from ProductAttribute productAttribute where productAttribute." + propertyName + "=? order by productAttribute.orderList asc productAttribute.createDate desc";
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductAttribute.class);
		return this.findByPager(pager, detachedCriteria);
	}
	
	// 重写方法，删除的同时清除关联
	@SuppressWarnings("unchecked")
	@Override
	public void delete(ProductAttribute productAttribute) {
		String hql = "from Product as product join product.productAttributeMapStore productAttributeMapStore where index(productAttributeMapStore) = :key";
		List<Product> productList = getSession().createQuery(hql).setParameter("key", productAttribute).list();
		for (Product product : productList) {
			Map<ProductAttribute, String> productAttributeMapStore = product.getProductAttributeMapStore();
			productAttributeMapStore.remove(productAttribute);
		}
		super.delete(productAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String id) {
		ProductAttribute productAttribute = load(id);
		this.delete(productAttribute);
	}

	// 重写方法，删除的同时清除关联
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}
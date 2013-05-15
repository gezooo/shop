package com.zg.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.zg.beans.ProductImage;
import com.zg.util.SystemConfigUtil;

@Entity
@Indexed
public class Product extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6396762385901632855L;
	
	public enum WeightUnit {
		g, kg, t
	}
	
	public static final int MAX_BEST_PRODUCT_LIST_COUNT = 20;
	
	public static final int MAX_NEW_PRODUCT_LIST_COUNT = 20;
	
	public static final int MAX_HOT_PRODUCT_LIST_COUNT = 20;
	
	public static final int DEFAULT_PRODUCT_LIST_PAGE_SIZE = 20;
	
	private String productSn;
	
	private String name;
	
	private BigDecimal price;
	
	private BigDecimal marketPrice;
	
	private Double weight;
	
	private WeightUnit weightUnit;
	
	private Integer store;
	
	private Integer freezeStore;
	
	private Integer point;
	
	private Boolean isMarketable;
	
	private Boolean isBest;
	
	private Boolean isNew;
	
	private Boolean isHot;
	
	private String description;
	
	private String metaKeywords;
	
	private String metaDescription;
	
	private String htmlFilePath;
	
	private String productImageListStore;
	
	private ProductCategory productCategory;
	
	private Brand brand;
	
	private ProductType productType;
	
	private Map<ProductAttribute, String> productAttributeMapStore;
	
	private Set<Member> favoriteMemberSet;
	
	private Set<CartItem> cartItemSet;
	
	private Set<OrderItem> orderItemSet;
	
	private Set<DeliveryItem> deliveryItemSet;

	@Column(nullable = false, unique = true)
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = SystemConfigUtil.getPriceScaleBigDecimal(price);
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = SystemConfigUtil.getPriceScaleBigDecimal(marketPrice);
	}

	@Column(nullable = false)
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Enumerated
	@Column(nullable = false)
	public WeightUnit getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(WeightUnit weightUnit) {
		this.weightUnit = weightUnit;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public Integer getFreezeStore() {
		return freezeStore;
	}

	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}

	@Column(nullable = false)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		if(point == null || point < 0)
			point = 0;
		this.point = point;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
	@Column(nullable = false)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Column(length = 10000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 5000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 5000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(nullable = false, updatable = false)
	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(length = 10000)
	public String getProductImageListStore() {
		return productImageListStore;
	}

	public void setProductImageListStore(String productImageListStore) {
		this.productImageListStore = productImageListStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@ElementCollection
	@MapKeyJoinColumn
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value = { CascadeType.DELETE})
	public Map<ProductAttribute, String> getProductAttributeMapStore() {
		return productAttributeMapStore;
	}

	public void setProductAttributeMapStore(
			Map<ProductAttribute, String> productAttributeMapStore) {
		this.productAttributeMapStore = productAttributeMapStore;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteProductSet")
	public Set<Member> getFavoriteMemberSet() {
		return favoriteMemberSet;
	}

	public void setFavoriteMemberSet(Set<Member> favoriteMemberSet) {
		this.favoriteMemberSet = favoriteMemberSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@Cascade(value = { CascadeType.DELETE })
	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	
	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public List<ProductImage> getProductImageList() {
		if(StringUtils.isEmpty(this.productImageListStore)) {
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(ProductImage.class);
		JSONArray jsonArray = JSONArray.fromObject(productImageListStore);
		return (List<ProductImage>) JSONSerializer.toJava(jsonArray);
		
	}
	
	@Transient
	public void setProductImageList(List<ProductImage> productImageList) {
		if(productImageList == null || productImageList.size() == 0 ) {
			this.productImageListStore = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(productImageList);
		this.productImageListStore = jsonArray.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public Map<ProductAttribute, List<String>> getProductAttributeMap() {
		if(this.productAttributeMapStore == null || this.productAttributeMapStore.size() == 0) {
			return null;
		}
		Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
		for(Entry<ProductAttribute, String> entry : productAttributeMapStore.entrySet()) {
			ProductAttribute productAttribute = entry.getKey();
			String productAttributeValueStore = entry.getValue();
			if(StringUtils.isNotEmpty(productAttributeValueStore)) {
				JSONArray jsonArray = JSONArray.fromObject(productAttributeValueStore);
				productAttributeMap.put(productAttribute, (List<String>) JSONSerializer.toJava(jsonArray));
			} else {
				productAttributeMap.put(productAttribute, null);
			}
			
		}
		return productAttributeMap;
	}
	
	@Transient
	public void setProductAttributeMap(Map<ProductAttribute, List<String>> productAttributreMap) {
		if(productAttributreMap == null || productAttributreMap.size() == 0) {
			this.productAttributeMapStore = null;
			return;
		}
		Map<ProductAttribute, String> productAttributeMapStore = new HashMap<ProductAttribute, String>();
		for(Entry<ProductAttribute, List<String>> entry : productAttributreMap.entrySet()) {
			ProductAttribute productAttribute = entry.getKey();
			List<String> productAttributeValueList = entry.getValue();
			if(productAttributeValueList != null && productAttributeValueList.size() > 0) {
				JSONArray jsonArray = JSONArray.fromObject(productAttributeValueList);
				productAttributeMapStore.put(productAttribute, jsonArray.toString());
			} else {
				productAttributeMapStore.put(productAttribute, null);
			}
		}
		this.productAttributeMapStore = productAttributeMapStore;
	}
	
	@Transient
	public ProductImage getProductImage(String productImageId) {
		List<ProductImage> productImageList = getProductImageList();
		for(ProductImage productImage : productImageList) {
			if(StringUtils.equals(productImageId, productImage.getId())) {
				return productImage;
			}
		}
		return null;
	}
	
	@Transient
	public boolean getIsOutOfStock() {
		if(store != null && freezeStore >= store) {
			return true;
		} else {
			return false;
		}
	}
	
	public BigDecimal getPreferentialPrice(Member member) {
		if(member != null) {
			BigDecimal preferentialPrice = this.price.multiply(new BigDecimal(member.getMemberRank().getPreferentialScale().toString()).divide(new BigDecimal("100")));
			return SystemConfigUtil.getPriceScaleBigDecimal(preferentialPrice);
		} else {
			return this.price;
		}
	}
	

}

package com.zg.action.admin;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
//import javax.servlet.jsp.PageContext;

import com.zg.beans.Pager;
import com.zg.beans.ProductImage;
import com.zg.beans.SystemConfig;
import com.zg.beans.SystemConfig.PointType;
import com.zg.common.util.SerialNumberUtils;
import com.zg.entity.Brand;
import com.zg.entity.OrderItem;
import com.zg.entity.Product;
import com.zg.entity.ProductAttribute;
import com.zg.entity.ProductCategory;
import com.zg.entity.ProductType;
import com.zg.entity.Order.OrderStatus;
import com.zg.entity.Product.WeightUnit;
import com.zg.entity.ProductAttribute.AttributeType;
import com.zg.service.BrandService;
import com.zg.service.ProductAttributeService;
import com.zg.service.ProductCategoryService;
import com.zg.service.ProductImageService;
import com.zg.service.ProductService;
import com.zg.service.ProductTypeService;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

//import com.opensymphony.oscache.base.Cache;
//import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class ProductAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192334L;

	private File[] productImages;
	private String[] productImagesFileName;
	private String[] productImageParameterTypes;
	private String[] productImageIds;
	
	private Product product;
	
	protected Pager<Product> pager;

	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private BrandService brandService;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductAttributeService productAttributeService;
	@Resource
	private ProductImageService productImageService;
	@Resource
	private CacheManager cacheManager;

	public Pager<Product> getPager() {
		return pager;
	}

	public void setPager(Pager<Product> pager) {
		this.pager = pager;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		product = productService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = productService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		for (String id : ids) {
			Product product = productService.load(id);
			Set<OrderItem> orderItemSet = product.getOrderItemSet();
			for (OrderItem orderItem : orderItemSet) {
				if (orderItem.getOrder().getOrderStatus() != OrderStatus.COMPLETED && orderItem.getOrder().getOrderStatus() != OrderStatus.INVALID) {
					return ajaxJsonErrorMessage("商品[" + product.getName() + "]订单处理未完成，删除失败！");
				}
			}
		}
		productService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.name", message = "商品名称不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "product.price", message = "销售价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.marketPrice", message = "市场价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weight", message = "商品重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weightUnit", message = "商品重量单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isBest", message = "是否精品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isNew", message = "是否新品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isHot", message = "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.productCategory.id", message = "所属分类不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "product.point", min = "0", message = "积分必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "product.store", min = "0", message = "库存必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (product.getPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("销售价不允许小于0");
			return ERROR;
		}
		if (product.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("市场价不允许小于0");
			return ERROR;
		}
		if (product.getWeight() < 0) {
			addActionError("商品重量只允许为正数或零!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(product.getProductSn())) {
			if (productService.isExist("productSn", product.getProductSn())) {
				addActionError("货号重复,请重新输入!");
				return ERROR;
			}
		} else {
			String productSn = SerialNumberUtils.buildProductSn();
			product.setProductSn(productSn);
		}
		ProductType productType = product.getProductType();
		if (productType != null && StringUtils.isNotEmpty(productType.getId())) {
			productType = productTypeService.load(productType.getId());
		} else {
			productType = null;
		}
		
		if (productType != null) {
			Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
			List<ProductAttribute> enabledProductAttributeList = productAttributeService.getEnabledProductAttributeList(productType);
			for (ProductAttribute productAttribute : enabledProductAttributeList) {
				String[] parameterValues = getParameterValues(productAttribute.getId());
				if (productAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productAttribute.getAttributeType() == AttributeType.NUMBER) {
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.ALPHAINT) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.DATE) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.SELECT || productAttribute.getAttributeType() == AttributeType.CHECKBOX) {
						List<String> attributeOptionList = productAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productAttributeMap.put(productAttribute, Arrays.asList(parameterValues));
				}
			}
			product.setProductAttributeMap(productAttributeMap);
		} else {
			product.setProductAttributeMap(null);
		}
		product.setProductType(productType);

		if (productImages != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)) {
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			for(int i = 0; i < productImages.length; i ++) {
				String productImageExtension =  StringUtils.substringAfterLast(productImagesFileName[i], ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
				if (getSystemConfig().getUploadLimit() != 0 && productImages[i].length() > getSystemConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return ERROR;
				}
			}
		}
		
		if (StringUtils.isEmpty(product.getBrand().getId())) {
			product.setBrand(null);
		}
		
		if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
			if (product.getPoint() == null) {
				addActionError("积分不允许为空!");
				return ERROR;
			}
		} else {
			product.setPoint(0);
		}
		
		List<ProductImage> productImageList = new ArrayList<ProductImage>();
		if (productImages != null && productImages.length > 0) {
			for(int i = 0; i < productImages.length; i ++) {
				ProductImage productImage = productImageService.buildProductImage(productImages[i]);
				productImageList.add(productImage);
			}
		}
		product.setProductImageList(productImageList);
		product.setFreezeStore(0);
		product.setFavoriteMemberSet(null);
		productService.save(product);
		flushCache();
		redirectionUrl = "product!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.name", message = "商品名称不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "product.price", message = "销售价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.marketPrice", message = "市场价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weight", message = "商品重量不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weightUnit", message = "商品重量单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isBest", message = "是否精品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isNew", message = "是否新品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isHot", message = "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.productCategory.id", message = "所属分类不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "product.point", min = "0", message = "积分必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "product.store", min = "0", message = "库存必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if (product.getPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("销售价不允许小于0");
			return ERROR;
		}
		if (product.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("市场价不允许小于0");
			return ERROR;
		}
		if (product.getWeight() < 0) {
			addActionError("商品重量只允许为正数或零!");
			return ERROR;
		}
		Product persistent = productService.load(id);
		if (StringUtils.isNotEmpty(product.getProductSn())) {
			if (!productService.isUnique("productSn", persistent.getProductSn(), product.getProductSn())) {
				addActionError("货号重复,请重新输入!");
				return ERROR;
			}
		} else {
			String productSn = SerialNumberUtils.buildProductSn();
			product.setProductSn(productSn);
		}
		
		ProductType productType = product.getProductType();
		if (productType != null && StringUtils.isNotEmpty(productType.getId())) {
			productType = productTypeService.load(productType.getId());
		} else {
			productType = null;
		}
		
		if (productType != null) {
			Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
			List<ProductAttribute> enabledProductAttributeList = productAttributeService.getEnabledProductAttributeList(productType);
			for (ProductAttribute productAttribute : enabledProductAttributeList) {
				String[] parameterValues = getParameterValues(productAttribute.getId());
				if (productAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productAttribute.getAttributeType() == AttributeType.NUMBER) {
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.ALPHAINT) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.DATE) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.SELECT || productAttribute.getAttributeType() == AttributeType.CHECKBOX) {
						List<String> attributeOptionList = productAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productAttributeMap.put(productAttribute, Arrays.asList(parameterValues));
				}
			}
			product.setProductAttributeMap(productAttributeMap);
		} else {
			product.setProductAttributeMap(null);
		}
		product.setProductType(productType);
		if (productImages != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)) {
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			for(int i = 0; i < productImages.length; i ++) {
				String productImageExtension =  StringUtils.substringAfterLast(productImagesFileName[i], ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
				if (getSystemConfig().getUploadLimit() != 0 && productImages[i].length() > getSystemConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return ERROR;
				}
			}
		}
		
		List<ProductImage> productImageList = new ArrayList<ProductImage>();
		if (productImageParameterTypes != null) {
			int index = 0;
			int productImageFileIndex = 0;
			int productImageIdIndex = 0;
			for (String parameterType : productImageParameterTypes) {
				if (StringUtils.equalsIgnoreCase(parameterType, "productImageFile")) {
					ProductImage destProductImage = productImageService.buildProductImage(productImages[productImageFileIndex]);
					productImageList.add(destProductImage);
					productImageFileIndex ++;
					index ++;
				} else if (StringUtils.equalsIgnoreCase(parameterType, "productImageId")) {
					ProductImage destProductImage = persistent.getProductImage(productImageIds[productImageIdIndex]);
					productImageList.add(destProductImage);
					productImageIdIndex ++;
					index ++;
				}
			}
		}
		
		if (StringUtils.isEmpty(product.getBrand().getId())) {
			product.setBrand(null);
		}
		if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
			if (product.getPoint() == null) {
				addActionError("积分不允许为空!");
				return ERROR;
			}
		} else {
			product.setPoint(0);
		}
		if (product.getStore() == null) {
			product.setFreezeStore(0);
		} else {
			product.setFreezeStore(persistent.getFreezeStore());
		}
		
		List<ProductImage> persistentProductImageList = persistent.getProductImageList();
		if (persistentProductImageList != null) {
			for (ProductImage persistentProductImage : persistentProductImageList) {
				if (!productImageList.contains(persistentProductImage)) {
					productImageService.deleteFile(persistentProductImage);
				}
			}
		}
		product.setProductImageList(productImageList);
		BeanUtils.copyProperties(product, persistent, new String[] {"id", "createDate", "modifyDate", "htmlFilePath", "favoriteMemberSet", "cartItemSet", "orderItemSet", "deliveryItemSet"});
		productService.update(persistent);
		flushCache();
		redirectionUrl = "product!list.action";
		return SUCCESS;
	}
	
	// 获取所有品牌
	public List<Brand> getAllBrand() {
		return brandService.getAll();
	}

	// 获取所有商品类型
	public List<ProductType> getAllProductType() {
		return productTypeService.getAll();
	}

	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	// 更新页面缓存
	private void flushCache() {
		//Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		//cache.flushAll(new Date());
		Cache cache = cacheManager.getCache("SimplePageCachingFilter");
		cache.clear();
		
	}
	
	// 获取商品分类树
	public List<ProductCategory> getProductCategoryTreeList() {
		return productCategoryService.getProductCategoryTreeList();
	}

	public File[] getProductImages() {
		return productImages;
	}

	public void setProductImages(File[] productImages) {
		this.productImages = productImages;
	}

	public String[] getProductImagesFileName() {
		return productImagesFileName;
	}

	public void setProductImagesFileName(String[] productImagesFileName) {
		this.productImagesFileName = productImagesFileName;
	}

	public String[] getProductImageParameterTypes() {
		return productImageParameterTypes;
	}

	public void setProductImageParameterTypes(String[] productImageParameterTypes) {
		this.productImageParameterTypes = productImageParameterTypes;
	}
	
	public String[] getProductImageIds() {
		return productImageIds;
	}

	public void setProductImageIds(String[] productImageIds) {
		this.productImageIds = productImageIds;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
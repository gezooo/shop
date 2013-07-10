package com.zg.action.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import com.zg.beans.CartItemCookie;
import com.zg.beans.SystemConfig.PointType;
import com.zg.entity.CartItem;
import com.zg.entity.Member;
import com.zg.entity.Product;
import com.zg.service.CartItemService;
import com.zg.service.ProductService;
import com.zg.util.SystemConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 购物车项
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX25805FB3BE09EA71895EAB4E18BEB912
 * ============================================================================
 */

@ParentPackage("shop")
public class CartItemAction extends BaseShopAction {

	private static final long serialVersionUID = 4404250263935012169L;
	
	private Integer quantity;// 商品数量
	private Integer totalQuantity;// 商品总数
	private Integer totalPoint;// 总积分
	private BigDecimal totalPrice;// 总计商品价格
	private List<CartItem> cartItemList = new ArrayList<CartItem>();

	@Resource
	private ProductService productService;
	@Resource
	private CartItemService cartItemService;

	// 添加购物车项
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String ajaxAdd() {
		Product product = productService.load(id);
		if (!product.getIsMarketable()) {
			return ajaxJsonErrorMessage("此商品已下架!");
		}
		if (quantity == null || quantity < 1) {
			quantity = 1;
		}
		Integer totalQuantity = 0;// 总计商品数量
		BigDecimal totalPrice = new BigDecimal("0");// 总计商品价格
		Member loginMember = getLoginMember();
		if (loginMember == null) {
			List<CartItemCookie> cartItemCookieList = new ArrayList<CartItemCookie>();
			boolean isExist = false;
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
							List<CartItemCookie> previousCartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
							for (CartItemCookie previousCartItemCookie : previousCartItemCookieList) {
								Product cartItemCookieProduct = productService.load(previousCartItemCookie.getI());
								if (StringUtils.equals(previousCartItemCookie.getI(), id)) {
									isExist = true;
									previousCartItemCookie.setQ(previousCartItemCookie.getQ() + quantity);
									if (product.getStore() != null && (product.getFreezeStore() + previousCartItemCookie.getQ()) > product.getStore()) {
										return ajaxJsonErrorMessage("添加购物车失败，商品库存不足!");
									}
								}
								cartItemCookieList.add(previousCartItemCookie);
								totalQuantity += previousCartItemCookie.getQ();
								totalPrice =  cartItemCookieProduct.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(previousCartItemCookie.getQ().toString())).add(totalPrice);
							}
						}
					}
				}
			}
			if (!isExist) {
				CartItemCookie cartItemCookie = new CartItemCookie();
				cartItemCookie.setI(id);
				cartItemCookie.setQ(quantity);
				cartItemCookieList.add(cartItemCookie);
				totalQuantity += quantity;
				totalPrice =  product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(quantity.toString())).add(totalPrice);
				if (product.getStore() != null && (product.getFreezeStore() + cartItemCookie.getQ()) > product.getStore()) {
					return ajaxJsonErrorMessage("添加购物车失败，商品库存不足!");
				}
			}
			for (CartItemCookie cartItemCookie : cartItemCookieList) {
				if (StringUtils.equals(cartItemCookie.getI(), id)) {
					Product cartItemCookieProduct = productService.load(cartItemCookie.getI());
					if (product.getStore() != null && (cartItemCookieProduct.getFreezeStore() + cartItemCookie.getQ()) > cartItemCookieProduct.getStore()) {
						return ajaxJsonErrorMessage("添加购物车失败，商品库存不足!");
					}
				}
			}
			JSONArray jsonArray = JSONArray.fromObject(cartItemCookieList);
			Cookie cookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, jsonArray.toString());
			cookie.setPath(getRequest().getContextPath() + "/");
			cookie.setMaxAge(CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
			getResponse().addCookie(cookie);
		} else {
			boolean isExist = false;
			Set<CartItem> previousCartItemList = loginMember.getCartItemSet();
			if (previousCartItemList != null) {
				for (CartItem previousCartItem : previousCartItemList) {
					if (StringUtils.equals(previousCartItem.getProduct().getId(), id)) {
						isExist = true;
						previousCartItem.setQuantity(previousCartItem.getQuantity() + quantity);
						if (product.getStore() != null && (product.getFreezeStore() + previousCartItem.getQuantity()) > product.getStore()) {
							return ajaxJsonErrorMessage("添加购物车失败，商品库存不足!");
						}
						cartItemService.update(previousCartItem);
					}
					totalQuantity += previousCartItem.getQuantity();
					totalPrice =  previousCartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(previousCartItem.getQuantity().toString())).add(totalPrice);
				}
			}
			if (!isExist) {
				CartItem cartItem = new CartItem();
				cartItem.setMember(loginMember);
				cartItem.setProduct(product);
				cartItem.setQuantity(quantity);
				if (product.getStore() != null && (product.getFreezeStore() + cartItem.getQuantity()) > product.getStore()) {
					return ajaxJsonErrorMessage("添加购物车失败，商品库存不足!");
				}
				cartItemService.save(cartItem);
				totalQuantity += quantity;
				totalPrice =  product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(quantity.toString())).add(totalPrice);
			}
		}
		totalPrice = SystemConfigUtil.getOrderScaleBigDecimal(totalPrice);
		DecimalFormat decimalFormat = new DecimalFormat(getOrderUnitCurrencyFormat());
		String totalPriceString = decimalFormat.format(totalPrice);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "添加至购物车成功！");
		jsonMap.put("totalQuantity", totalQuantity.toString());
		jsonMap.put("totalPrice", totalPriceString);
		return ajaxJson(jsonMap);
	}
	
	// 购物车项列表
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String ajaxList() {
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		Member loginMember = getLoginMember();
		totalQuantity = 0;
		totalPrice = new BigDecimal("0");
		if (loginMember == null) {
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService.load(cartItemCookie.getI());
								if (product != null) {
									totalQuantity += cartItemCookie.getQ();
									totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItemCookie.getQ().toString())).add(totalPrice);
									DecimalFormat decimalFormat = new DecimalFormat(getPriceCurrencyFormat());
									String priceString = decimalFormat.format(product.getPreferentialPrice(getLoginMember()));
									Map<String, String> jsonMap = new HashMap<String, String>();
									jsonMap.put("name", product.getName());
									jsonMap.put("price", priceString);
									jsonMap.put("quantity", cartItemCookie.getQ().toString());
									jsonMap.put("htmlFilePath", product.getHtmlFilePath());
									jsonList.add(jsonMap);
								}
							}
						}
					}
				}
			}
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					Product product = cartItem.getProduct();
					totalQuantity += cartItem.getQuantity();
					totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(totalPrice);
					DecimalFormat decimalFormat = new DecimalFormat(getPriceCurrencyFormat());
					String priceString = decimalFormat.format(cartItem.getProduct().getPreferentialPrice(getLoginMember()));
					Map<String, String> jsonMap = new HashMap<String, String>();
					jsonMap.put("name", product.getName());
					jsonMap.put("price", priceString);
					jsonMap.put("quantity", cartItem.getQuantity().toString());
					jsonMap.put("htmlFilePath", cartItem.getProduct().getHtmlFilePath());
					jsonList.add(jsonMap);
				}
			}
		}
		totalPrice = SystemConfigUtil.getOrderScaleBigDecimal(totalPrice);
		DecimalFormat decimalFormat = new DecimalFormat(getOrderUnitCurrencyFormat());
		String totalPriceString = decimalFormat.format(totalPrice);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("totalQuantity", totalQuantity.toString());
		jsonMap.put("totalPrice", totalPriceString);
		jsonList.add(0, jsonMap);
		JSONArray jsonArray = JSONArray.fromObject(jsonList);
		return ajaxJson(jsonArray.toString());
	}
	
	// 购物车项列表
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "error")
	public String list() {
		Member loginMember = getLoginMember();
		totalQuantity = 0;
		totalPoint = 0;
		totalPrice = new BigDecimal("0");
		if (loginMember == null) {
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService.load(cartItemCookie.getI());
								if (product != null) {
									totalQuantity += cartItemCookie.getQ();
									if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
										totalPoint = product.getPoint() * cartItemCookie.getQ() + totalPoint;
									}
									totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItemCookie.getQ().toString())).add(totalPrice);
									CartItem cartItem = new CartItem();
									cartItem.setProduct(product);
									cartItem.setQuantity(cartItemCookie.getQ());
									cartItemList.add(cartItem);
								}
							}
						}
					}
				}
			}
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				cartItemList = new ArrayList<CartItem>(cartItemSet);
				for (CartItem cartItem : cartItemSet) {
					totalQuantity += cartItem.getQuantity();
					if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
						totalPoint = cartItem.getProduct().getPoint() * cartItem.getQuantity() + totalPoint;
					}
					totalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(totalPrice);
				}
			}
		}
		totalPrice = SystemConfigUtil.getOrderScaleBigDecimal(totalPrice);
		if (getSystemConfig().getPointType() == PointType.ORDERAMOUNT) {
			totalPoint = totalPrice.multiply(new BigDecimal(getSystemConfig().getPointScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		setResponseNoCache();
		return "list";
	}
	
	// 修改购物车项
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String ajaxEdit() {
		if (quantity == null || quantity < 1) {
			quantity = 1;
		}
		Member loginMember = getLoginMember();
		totalQuantity = 0;
		totalPoint = 0;
		totalPrice = new BigDecimal("0");
		BigDecimal subtotalPrice = new BigDecimal("0");
		if (loginMember == null) {
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray previousJsonArray = JSONArray.fromObject(cookie.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(previousJsonArray, jsonConfig);
							Iterator<CartItemCookie> iterator = cartItemCookieList.iterator();
							while (iterator.hasNext()) {
								CartItemCookie cartItemCookie = iterator.next();
								Product product = productService.load(cartItemCookie.getI());
								if (StringUtils.equals(id, cartItemCookie.getI())) {
									cartItemCookie.setQ(quantity);
									subtotalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(quantity.toString()));
									if (product.getStore() != null && (product.getFreezeStore() + cartItemCookie.getQ()) > product.getStore()) {
										return ajaxJsonErrorMessage("商品库存不足！");
									}
								}
								totalQuantity += cartItemCookie.getQ();
								if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
									totalPoint = product.getPoint() * cartItemCookie.getQ() + totalPoint;
								}
								totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItemCookie.getQ().toString())).add(totalPrice);
							}
							JSONArray jsonArray = JSONArray.fromObject(cartItemCookieList);
							Cookie newCookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, jsonArray.toString());
							newCookie.setPath(getRequest().getContextPath() + "/");
							newCookie.setMaxAge(CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
							getResponse().addCookie(newCookie);
						}
					}
				}
			}
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					Product product = cartItem.getProduct();
					if (StringUtils.equals(id, cartItem.getProduct().getId())) {
						cartItem.setQuantity(quantity);
						if (product.getStore() != null && (product.getFreezeStore() + cartItem.getQuantity()) > product.getStore()) {
							return ajaxJsonErrorMessage("商品库存不足！");
						}
						cartItemService.update(cartItem);
						subtotalPrice = cartItem.getSubTotalPrice();
					}
					totalQuantity += cartItem.getQuantity();
					if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
						totalPoint = product.getPoint() * cartItem.getQuantity() + totalPoint;
					}
					totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(totalPrice);
				}
			}
		}
		DecimalFormat orderUnitCurrencyFormat = new DecimalFormat(getOrderUnitCurrencyFormat());
		DecimalFormat orderCurrencyFormat = new DecimalFormat(getOrderCurrencyFormat());
		totalPrice = SystemConfigUtil.getOrderScaleBigDecimal(totalPrice);
		if (getSystemConfig().getPointType() == PointType.ORDERAMOUNT) {
			totalPoint = totalPrice.multiply(new BigDecimal(getSystemConfig().getPointScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		String subtotalPriceString = orderCurrencyFormat.format(subtotalPrice);
		String totalPriceString = orderUnitCurrencyFormat.format(totalPrice);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("subtotalPrice", subtotalPriceString);
		jsonMap.put("totalQuantity", totalQuantity.toString());
		jsonMap.put("totalPoint", totalPoint.toString());
		jsonMap.put("totalPrice", totalPriceString);
		jsonMap.put(STATUS, SUCCESS);
		return ajaxJson(jsonMap);
	}
	
	// 删除购物车项
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String ajaxDelete() {
		Member loginMember = getLoginMember();
		totalQuantity = 0;
		totalPoint = 0;
		totalPrice = new BigDecimal("0");
		if (loginMember == null) {
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray previousJsonArray = JSONArray.fromObject(cookie.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer.toJava(previousJsonArray, jsonConfig);
							Iterator<CartItemCookie> iterator = cartItemCookieList.iterator();
							while (iterator.hasNext()) {
								CartItemCookie cartItemCookie = iterator.next();
								if (StringUtils.equals(cartItemCookie.getI(), id)) {
									iterator.remove();
								} else {
									Product product = productService.load(cartItemCookie.getI());
									totalQuantity += cartItemCookie.getQ();
									if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
										totalPoint = product.getPoint() * cartItemCookie.getQ() + totalPoint;
									}
									totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItemCookie.getQ().toString())).add(totalPrice);
								}
							}
							JSONArray jsonArray = JSONArray.fromObject(cartItemCookieList);
							Cookie newCookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, jsonArray.toString());
							newCookie.setPath(getRequest().getContextPath() + "/");
							newCookie.setMaxAge(CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
							getResponse().addCookie(newCookie);
						}
					}
				}
			}
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					if (StringUtils.equals(cartItem.getProduct().getId(), id)) {
						cartItemService.delete(cartItem);
					} else {
						Product product = cartItem.getProduct();
						totalQuantity += cartItem.getQuantity();
						if (getSystemConfig().getPointType() == PointType.PRODUCTSET) {
							totalPoint = product.getPoint() * cartItem.getQuantity() + totalPoint;
						}
						totalPrice = product.getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(totalPrice);
					}
				}
			}
		}
		totalPrice = SystemConfigUtil.getOrderScaleBigDecimal(totalPrice);
		if (getSystemConfig().getPointType() == PointType.ORDERAMOUNT) {
			totalPoint = totalPrice.multiply(new BigDecimal(getSystemConfig().getPointScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
		}
		DecimalFormat decimalFormat = new DecimalFormat(getOrderUnitCurrencyFormat());
		String totalPriceString = decimalFormat.format(totalPrice);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("totalQuantity", totalQuantity.toString());
		jsonMap.put("totalPoint", totalPoint.toString());
		jsonMap.put("totalPrice", totalPriceString);
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "商品删除成功！");
		return ajaxJson(jsonMap);
	}
	
	// 清空购物车项
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String ajaxClear() {
		Member loginMember = getLoginMember();
		if (loginMember == null) {
			Cookie cookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
			cookie.setPath(getRequest().getContextPath() + "/");
			cookie.setMaxAge(0);
			getResponse().addCookie(cookie);
		} else {
			Set<CartItem> cartItemSet = loginMember.getCartItemSet();
			if (cartItemSet != null) {
				for (CartItem cartItem : cartItemSet) {
					cartItemService.delete(cartItem);
				}
			}
		}
		return ajaxJsonSuccessMessage("购物车清空成功！");
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

}
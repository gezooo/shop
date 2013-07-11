package com.zg.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/*
* @author gez
* @version 0.1
*/

public class SystemConfig implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3880866661069963350L;


	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	}

	public enum RoundType {
		ROUND_HALF_UP, ROUND_UP, ROUND_DOWN 
	}
	
	public enum StoreFreezeTime {
		ORDER, PAYMENT, SHIP
	}
	
	public enum WatermarkPosition {
		NO, TOP_LEFT, TOP_RIGHT, CENTER, BOTTOM_LEFT, BOTTOM_RIGHT 
	}
	
	public enum PointType {
		DISABLE, ORDERAMOUNT, PRODUCTSET
	}
	
	public static final String HOT_SEARCH_SEPARATOR = ",";
	
	public static final String EXTENSION_SEPARATOR = ",";
	
	public static final String LOGO_UPLOAD_NAME = "logo";
	
	public static final String DEFAULT_BIG_PRODUCT_IMAGE_FILE_NAME = "default_big_product_image";
	
	public static final String DEFAULT_SMALL_PRODUCT_IMAGE_FILE_NAME = "default_small_product_image";
	
	public static final String DEFAULT_THUMBNAIL_PRODUCT_IMAGE_FILE_NAME = "default_thumbnail_product_image";
	
	public static final String WATERMARK_IMAGE_FILE_NAME = "watermark";
	
	public static final String UPLOAD_IMAGE_DIR = "/upload/image/";
	
	public static final String UPLOAD_MEDIA_DIR = "/upload/media/";
	
	public static final String UPLOAD_FILE_DIR = "/upload/file/";
	
	private String systemName;
	
	private String systemVersion;
	
	private String systemDescription;
	
	private Boolean isInstalled;
	
	private String shopName;
	
	private String shopUrl;
	
	private String shopLogo;
	
	private String hotSearch;
	
	private String metaKeywords;
	
	private String metaDescription;
	
	private String address;
	
	private String phone;
	
	private String zipCode;
	
	private String email;
	
	private CurrencyType currencyType;
	
	private String currencySign;
	
	private String currencyUnit;
	
	private Integer priceScale;
	
	private RoundType priceRoundType;
	
	private Integer orderScale;
	
	private RoundType orderRoundType;
	
	private String certtext;
	
	private Integer storeAlertCount;
	
	private StoreFreezeTime storeFreezeTime;
	
	private Integer uploadLimit;
			
	private boolean isLoginFailureLock;
	
	private Integer loginFailureLockCount;
	
	private Integer loginFailureLockTime;
	
	private Boolean isRegister;
	
	private String watermarkImagePath; 
	
	private WatermarkPosition watermarkPosition;
	
	private Integer watermarkAlpha;
	
	private Integer bigProductImageWidth;
	
	private Integer bigProductImageHeight;
	
	private Integer smallProductImageWidth;
	
	private Integer smallProductImageHeight;
	
	private Integer thumbnailProductImageWidth;
	
	private Integer thumbnailProductImageHeight;
	
	private String defaultBigProductImagePath;
	
	private String defaultSmallProductImagePath;
	
	private String defaultThumbnailProductImagePath;
	
	private String allowedUploadImageExtension;
	
	private String allowedUploadMediaExtension;
	
	private String allowedUploadFileExtension;
	
	private String smtpFromMail;
	
	private String smtpHost;
	
	private Integer smtpPort;
	
	private String smtpUsername;
	
	private String smtpPassword;
	
	private PointType pointType;
	
	private Double pointScale;
	
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}
	

	public int getPriceScale() {
		return this.priceScale;
	}

	public RoundType getPriceRoundType() {
		return this.priceRoundType;
	}

	public Integer getOrderScale() {
		return this.orderScale;
	}

	public RoundType getOrderRoundType() {
		return this.orderRoundType;
	}


	public String getSystemName() {
		return systemName;
	}


	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}


	public String getSystemVersion() {
		return systemVersion;
	}


	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}


	public String getSystemDescription() {
		return systemDescription;
	}


	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}


	public Boolean getIsInstalled() {
		return isInstalled;
	}


	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getShopUrl() {
		return shopUrl;
	}


	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}


	public String getShopLogo() {
		return shopLogo;
	}


	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}


	public String getHotSearch() {
		return hotSearch;
	}


	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}


	public String getMetaKeywords() {
		return metaKeywords;
	}


	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}


	public String getMetaDescription() {
		return metaDescription;
	}


	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public CurrencyType getCurrencyType() {
		return currencyType;
	}


	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}


	public String getCurrencySign() {
		return currencySign;
	}


	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}


	public String getCurrencyUnit() {
		return currencyUnit;
	}


	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}


	public String getCerttext() {
		return certtext;
	}


	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}


	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}


	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}


	public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}


	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}


	public Integer getUploadLimit() {
		return uploadLimit;
	}


	public void setUploadLimit(Integer uploadLimit) {
		this.uploadLimit = uploadLimit;
	}


	public boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}


	public void setIsLoginFailureLock(boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}


	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}


	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}


	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}


	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}


	public Boolean getIsRegister() {
		return isRegister;
	}


	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}


	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}


	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}


	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}


	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}


	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}


	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}


	public Integer getBigProductImageWidth() {
		return bigProductImageWidth;
	}


	public void setBigProductImageWidth(Integer bigProductImageWidth) {
		this.bigProductImageWidth = bigProductImageWidth;
	}


	public Integer getBigProductImageHeight() {
		return bigProductImageHeight;
	}


	public void setBigProductImageHeight(Integer bigProductImageHeight) {
		this.bigProductImageHeight = bigProductImageHeight;
	}


	public Integer getSmallProductImageWidth() {
		return smallProductImageWidth;
	}


	public void setSmallProductImageWidth(Integer smallProductImageWidth) {
		this.smallProductImageWidth = smallProductImageWidth;
	}


	public Integer getSmallProductImageHeight() {
		return smallProductImageHeight;
	}


	public void setSmallProductImageHeight(Integer smallProductImageHeight) {
		this.smallProductImageHeight = smallProductImageHeight;
	}


	public Integer getThumbnailProductImageWidth() {
		return thumbnailProductImageWidth;
	}


	public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
		this.thumbnailProductImageWidth = thumbnailProductImageWidth;
	}


	public Integer getThumbnailProductImageHeight() {
		return thumbnailProductImageHeight;
	}


	public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
		this.thumbnailProductImageHeight = thumbnailProductImageHeight;
	}


	public String getDefaultBigProductImagePath() {
		return defaultBigProductImagePath;
	}


	public void setDefaultBigProductImagePath(String defaultBigProductImagePath) {
		this.defaultBigProductImagePath = defaultBigProductImagePath;
	}


	public String getDefaultSmallProductImagePath() {
		return defaultSmallProductImagePath;
	}


	public void setDefaultSmallProductImagePath(String defaultSmallProductImagePath) {
		this.defaultSmallProductImagePath = defaultSmallProductImagePath;
	}


	public String getDefaultThumbnailProductImagePath() {
		return defaultThumbnailProductImagePath;
	}


	public void setDefaultThumbnailProductImagePath(
			String defaultThumbnailProductImagePath) {
		this.defaultThumbnailProductImagePath = defaultThumbnailProductImagePath;
	}


	public String getAllowedUploadImageExtension() {
		return allowedUploadImageExtension;
	}


	public void setAllowedUploadImageExtension(String allowedUploadImageExtension) {
		this.allowedUploadImageExtension = allowedUploadImageExtension;
	}


	public String getAllowedUploadMediaExtension() {
		return allowedUploadMediaExtension;
	}


	public void setAllowedUploadMediaExtension(String allowedUploadMediaExtension) {
		this.allowedUploadMediaExtension = allowedUploadMediaExtension;
	}


	public String getAllowedUploadFileExtension() {
		return allowedUploadFileExtension;
	}


	public void setAllowedUploadFileExtension(String allowedUploadFileExtension) {
		this.allowedUploadFileExtension = allowedUploadFileExtension;
	}


	public String getSmtpFromMail() {
		return smtpFromMail;
	}


	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}


	public String getSmtpHost() {
		return smtpHost;
	}


	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}


	public Integer getSmtpPort() {
		return smtpPort;
	}


	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}


	public String getSmtpUsername() {
		return smtpUsername;
	}


	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}


	public String getSmtpPassword() {
		return smtpPassword;
	}


	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}


	public PointType getPointType() {
		return pointType;
	}


	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}


	public Double getPointScale() {
		return pointScale;
	}


	public void setPointScale(Double pointScale) {
		this.pointScale = pointScale;
	}


	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}


	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}


	public void setOrderScale(Integer orderScale) {
		this.orderScale = orderScale;
	}


	public void setOrderRoundType(RoundType orderRoundType) {
		this.orderRoundType = orderRoundType;
	}
	
	
	

}

package com.zg.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zg.beans.SystemConfig;
import com.zg.beans.SystemConfig.CurrencyType;
import com.zg.beans.SystemConfig.PointType;
import com.zg.beans.SystemConfig.RoundType;
import com.zg.beans.SystemConfig.StoreFreezeTime;
import com.zg.beans.SystemConfig.WatermarkPosition;
import com.zg.util.SystemConfigUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 系统设置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCB0831367FDD7078CE4312A6DBF9737D
 * ============================================================================
 */

@ParentPackage("admin")
public class SystemConfigAction extends BaseAdminAction {

	private static final long serialVersionUID = -6200425957233641240L;

	private SystemConfig systemConfig;
	private File shopLogo;
	private String shopLogoFileName;
	private File defaultBigProductImage;
	private String defaultBigProductImageFileName;
	private File defaultSmallProductImage;
	private String defaultSmallProductImageFileName;
	private File defaultThumbnailProductImage;
	private String defaultThumbnailProductImageFileName;
	private File watermarkImage;
	private String watermarkImageFileName;

	// 编辑
	public String edit() {
		systemConfig = SystemConfigUtil.getSystemConfig();
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "systemConfig.shopName", message = "网店名称不允许为空!"),
			@RequiredStringValidator(fieldName = "systemConfig.shopUrl", message = "网店网址不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "systemConfig.currencyType", message = "货币种类不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.currencySign", message = "货币符号不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.currencyUnit", message = "货币单位不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.priceScale", message = "商品价格精确位数不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.priceRoundType", message = "商品价格精确方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.orderScale", message = "订单金额精确位数不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.orderRoundType", message = "订单金额精确方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.storeAlertCount", message = "商品库存报警数量不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.storeFreezeTime", message = "库存预占时间点不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.uploadLimit", message = "文件上传最大值不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.isRegister", message = "是否开放注册不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.isLoginFailureLock", message = "是否开启自动锁定账号功能不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.loginFailureLockCount", message = "连续登录失败最大次数不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.loginFailureLockTime", message = "自动解锁时间不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.bigProductImageWidth", message = "商品图片（大）宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.bigProductImageHeight", message = "商品图片（大）高不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.smallProductImageWidth", message = "商品图片（小）宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.smallProductImageHeight", message = "商品图片（小）高不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.thumbnailProductImageWidth", message = "商品缩略图宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.thumbnailProductImageHeight", message = "商品缩略图高不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.watermarkAlpha", message = "水印透明度不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.pointType", message = "积分获取方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "systemConfig.pointScale", message = "积分换算比率不允许为空!")
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "systemConfig.priceScale", min = "0", message = "商品价格精确位数必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.priceScale", max = "4", message = "商品价格精确位数位不能大于4!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.orderScale", min = "0", message = "订单金额精确位数必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.orderScale", max = "4", message = "订单金额精确位数位不能大于4!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.storeAlertCount", min = "0", message = "商品库存报警数量必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.uploadLimit", min = "0", message = "文件上传最大值必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.loginFailureLockCount", min = "1", message = "连续登录失败最大次数必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.loginFailureLockTime", min = "0", message = "自动解锁时间必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.bigProductImageWidth", min = "1", message = "商品图片（大）宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.bigProductImageHeight", min = "1", message = "商品图片（大）高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.smallProductImageWidth", min = "1", message = "商品图片（小）宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.smallProductImageHeight", min = "1", message = "商品图片（小）高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.thumbnailProductImageWidth", min = "1", message = "商品缩略图宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.thumbnailProductImageHeight", min = "1", message = "商品缩略图高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.smtpPort", min = "0", message = "SMTP端口必须为零正整数!"),
			@IntRangeFieldValidator(fieldName = "systemConfig.watermarkAlpha", min = "0", max="100", message = "水印透明度取值范围在${min}-${max}之间!")
		},
		urls = {
			@UrlValidator(fieldName = "systemConfig.url", message = "网店网址不允许为空!")
		},
		emails = {
			@EmailValidator(fieldName = "systemConfig.email", message = "E-mail格式错误!"),
			@EmailValidator(fieldName = "systemConfig.smtpFromMail", message = "发件人邮箱格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if (systemConfig.getPointType() == PointType.ORDERAMOUNT) {
			if (systemConfig.getPointScale() < 0) {
				addActionError("积分换算比率不允许小于0!");
				return ERROR;
			}
		} else {
			systemConfig.setPointScale(0D);
		}
		SystemConfig persistent = SystemConfigUtil.getSystemConfig();
		if (shopLogo != null || defaultBigProductImage != null || defaultSmallProductImage != null || defaultThumbnailProductImage != null || watermarkImage != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			if (shopLogo != null) {
				String shopLogoExtension =  StringUtils.substringAfterLast(shopLogoFileName, ".").toLowerCase();
				if (!ArrayUtils.contains(imageExtensionArray, shopLogoExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
			}
			if (defaultBigProductImage != null) {
				String defaultBigProductImageExtension =  StringUtils.substringAfterLast(defaultBigProductImageFileName, ".").toLowerCase();
				if (!ArrayUtils.contains(imageExtensionArray, defaultBigProductImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
			}
			if (defaultSmallProductImage != null) {
				String defaultSmallProductImageExtension =  StringUtils.substringAfterLast(defaultSmallProductImageFileName, ".").toLowerCase();
				if (!ArrayUtils.contains(imageExtensionArray, defaultSmallProductImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
			}
			if (defaultThumbnailProductImage != null) {
				String defaultThumbnailProductImageExtension =  StringUtils.substringAfterLast(defaultThumbnailProductImageFileName, ".").toLowerCase();
				if (!ArrayUtils.contains(imageExtensionArray, defaultThumbnailProductImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
			}
			if (watermarkImage != null) {
				String watermarkImageExtension =  StringUtils.substringAfterLast(watermarkImageFileName, ".").toLowerCase();
				if (!ArrayUtils.contains(imageExtensionArray, watermarkImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
			}
		}
		
		int uploadLimit = systemConfig.getUploadLimit() * 1024;
		if (uploadLimit != 0) {
			if (shopLogo != null && shopLogo.length() > uploadLimit) {
				addActionError("网店Logo文件大小超出限制!");
				return ERROR;
			}
			if (defaultBigProductImage != null && defaultBigProductImage.length() > uploadLimit) {
				addActionError("默认商品图片（大）文件大小超出限制!");
				return ERROR;
			}
			if (defaultSmallProductImage != null && defaultSmallProductImage.length() > uploadLimit) {
				addActionError("默认商品图片（小）文件大小超出限制!");
				return ERROR;
			}
			if (defaultThumbnailProductImage != null && defaultThumbnailProductImage.length() > uploadLimit) {
				addActionError("默认缩略图文件大小超出限制!");
				return ERROR;
			}
			if (watermarkImage != null && watermarkImage.length() > uploadLimit) {
				addActionError("水印图片文件大小超出限制!");
				return ERROR;
			}
		}
		if (StringUtils.isEmpty(systemConfig.getSmtpPassword())) {
			systemConfig.setSmtpPassword(persistent.getSmtpPassword());
		}
		if (systemConfig.getIsLoginFailureLock() == false) {
			systemConfig.setLoginFailureLockCount(3);
			systemConfig.setLoginFailureLockTime(10);
		}
		if (shopLogo != null) {
			File oldShopLogoFile = new File(ServletActionContext.getServletContext().getRealPath(persistent.getShopLogo()));
			if (oldShopLogoFile.isFile()) {
				oldShopLogoFile.delete();
			}
			String shopLogoFilePath = SystemConfig.UPLOAD_IMAGE_DIR + SystemConfig.LOGO_UPLOAD_NAME + "." +  StringUtils.substringAfterLast(shopLogoFileName, ".").toLowerCase();
			File shopLogoFile = new File(ServletActionContext.getServletContext().getRealPath(shopLogoFilePath));
			FileUtils.copyFile(shopLogo, shopLogoFile);
			persistent.setShopLogo(shopLogoFilePath);
		}
		// 处理默认商品图片（大）
		if (defaultBigProductImage != null) {
			File oldDefaultBigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(persistent.getDefaultBigProductImagePath()));
			if (oldDefaultBigProductImageFile.exists()) {
				oldDefaultBigProductImageFile.delete();
			}
			String defaultBigProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + SystemConfig.DEFAULT_BIG_PRODUCT_IMAGE_FILE_NAME + "." +  StringUtils.substringAfterLast(defaultBigProductImageFileName, ".").toLowerCase();
			File defaultBigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(defaultBigProductImagePath));
			FileUtils.copyFile(defaultBigProductImage, defaultBigProductImageFile);
			persistent.setDefaultBigProductImagePath(defaultBigProductImagePath);
		}
		// 处理默认商品图片（小）
		if (defaultSmallProductImage != null) {
			File oldDefaultSmallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(persistent.getDefaultSmallProductImagePath()));
			if (oldDefaultSmallProductImageFile.exists()) {
				oldDefaultSmallProductImageFile.delete();
			}
			String defaultSmallProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + SystemConfig.DEFAULT_SMALL_PRODUCT_IMAGE_FILE_NAME + "." +  StringUtils.substringAfterLast(defaultSmallProductImageFileName, ".").toLowerCase();
			File defaultSmallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(defaultSmallProductImagePath));
			FileUtils.copyFile(defaultSmallProductImage, defaultSmallProductImageFile);
			persistent.setDefaultSmallProductImagePath(defaultSmallProductImagePath);
		}
		// 处理默认商品缩略图
		if (defaultThumbnailProductImage != null) {
			File oldDefaultThumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(persistent.getDefaultThumbnailProductImagePath()));
			if (oldDefaultThumbnailProductImageFile.exists()) {
				oldDefaultThumbnailProductImageFile.delete();
			}
			String defaultThumbnailProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + SystemConfig.DEFAULT_THUMBNAIL_PRODUCT_IMAGE_FILE_NAME + "."
					+  StringUtils.substringAfterLast(defaultThumbnailProductImageFileName, ".").toLowerCase();
			File defaultThumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(defaultThumbnailProductImagePath));
			FileUtils.copyFile(defaultThumbnailProductImage, defaultThumbnailProductImageFile);
			persistent.setDefaultThumbnailProductImagePath(defaultThumbnailProductImagePath);
		}
		// 处理水印图片
		if (watermarkImage != null) {
			File oldWatermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(persistent.getWatermarkImagePath()));
			if (oldWatermarkImageFile.exists()) {
				oldWatermarkImageFile.delete();
			}
			String watermarkImagePath = SystemConfig.UPLOAD_IMAGE_DIR + SystemConfig.WATERMARK_IMAGE_FILE_NAME + "." +  StringUtils.substringAfterLast(watermarkImageFileName, ".").toLowerCase();
			File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(watermarkImagePath));
			FileUtils.copyFile(watermarkImage, watermarkImageFile);
			persistent.setWatermarkImagePath(watermarkImagePath);
		}
		BeanUtils.copyProperties(systemConfig, persistent, new String[] {"systemName", "systemVersion", "systemDescription", "isInstalled", "shopLogo", "defaultBigProductImagePath", "defaultSmallProductImagePath", "defaultThumbnailProductImagePath", "watermarkImagePath"});
		SystemConfigUtil.update(persistent);
		redirectionUrl = "system_config!edit.action";
		return SUCCESS;
	}
	
	// 获取所有货币种类
	public List<CurrencyType> getAllCurrencyType() {
		List<CurrencyType> allCurrencyType = new ArrayList<CurrencyType>();
		for (CurrencyType currencyType : CurrencyType.values()) {
			allCurrencyType.add(currencyType);
		}
		return allCurrencyType;
	}
	
	// 获取所有小数位精确方式
	public List<RoundType> getAllRoundType() {
		List<RoundType> allRoundType = new ArrayList<RoundType>();
		for (RoundType roundType : RoundType.values()) {
			allRoundType.add(roundType);
		}
		return allRoundType;
	}
	
	// 获取所有库存预占时间点
	public List<StoreFreezeTime> getAllStoreFreezeTime() {
		List<StoreFreezeTime> allStoreFreezeTime = new ArrayList<StoreFreezeTime>();
		for (StoreFreezeTime storeFreezeTime : StoreFreezeTime.values()) {
			allStoreFreezeTime.add(storeFreezeTime);
		}
		return allStoreFreezeTime;
	}

	// 获取所有WatermarkPosition值
	public List<WatermarkPosition> getAllWatermarkPosition() {
		List<WatermarkPosition> allWatermarkPosition = new ArrayList<WatermarkPosition>();
		for (WatermarkPosition watermarkPosition : WatermarkPosition.values()) {
			allWatermarkPosition.add(watermarkPosition);
		}
		return allWatermarkPosition;
	}
	
	// 获取所有积分获取方式
	public List<PointType> getAllPointType() {
		List<PointType> allPointType = new ArrayList<PointType>();
		for (PointType pointType : PointType.values()) {
			allPointType.add(pointType);
		}
		return allPointType;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public File getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(File shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getShopLogoFileName() {
		return shopLogoFileName;
	}

	public void setShopLogoFileName(String shopLogoFileName) {
		this.shopLogoFileName = shopLogoFileName;
	}

	public File getDefaultBigProductImage() {
		return defaultBigProductImage;
	}

	public void setDefaultBigProductImage(File defaultBigProductImage) {
		this.defaultBigProductImage = defaultBigProductImage;
	}

	public String getDefaultBigProductImageFileName() {
		return defaultBigProductImageFileName;
	}

	public void setDefaultBigProductImageFileName(String defaultBigProductImageFileName) {
		this.defaultBigProductImageFileName = defaultBigProductImageFileName;
	}

	public File getDefaultSmallProductImage() {
		return defaultSmallProductImage;
	}

	public void setDefaultSmallProductImage(File defaultSmallProductImage) {
		this.defaultSmallProductImage = defaultSmallProductImage;
	}

	public String getDefaultSmallProductImageFileName() {
		return defaultSmallProductImageFileName;
	}

	public void setDefaultSmallProductImageFileName(String defaultSmallProductImageFileName) {
		this.defaultSmallProductImageFileName = defaultSmallProductImageFileName;
	}

	public File getDefaultThumbnailProductImage() {
		return defaultThumbnailProductImage;
	}

	public void setDefaultThumbnailProductImage(File defaultThumbnailProductImage) {
		this.defaultThumbnailProductImage = defaultThumbnailProductImage;
	}

	public String getDefaultThumbnailProductImageFileName() {
		return defaultThumbnailProductImageFileName;
	}

	public void setDefaultThumbnailProductImageFileName(String defaultThumbnailProductImageFileName) {
		this.defaultThumbnailProductImageFileName = defaultThumbnailProductImageFileName;
	}

	public File getWatermarkImage() {
		return watermarkImage;
	}

	public void setWatermarkImage(File watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	public String getWatermarkImageFileName() {
		return watermarkImageFileName;
	}

	public void setWatermarkImageFileName(String watermarkImageFileName) {
		this.watermarkImageFileName = watermarkImageFileName;
	}

}
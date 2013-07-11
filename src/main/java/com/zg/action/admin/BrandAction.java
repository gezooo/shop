package com.zg.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.zg.beans.Pager;
import com.zg.beans.SystemConfig;
import com.zg.beans.Pager.OrderType;
import com.zg.common.util.CommonUtils;
import com.zg.entity.Brand;
import com.zg.entity.ProductAttribute;
import com.zg.service.BrandService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 品牌
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1DD6D6F9E9EE16D448DC6DB3F4425D5E
 * ============================================================================
 */

@ParentPackage("admin")
public class BrandAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	
	private Brand brand;
	private File logo;
	private String logoFileName;
	
	protected Pager<Brand> pager;


	@Resource
	private BrandService brandService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		brand = brandService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if(pager == null) {
			pager = new Pager<Brand>();
			pager.setOrderType(OrderType.ASC);
			pager.setOrderBy("orderList");
		}
		pager = brandService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		brandService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		urls = {
			@UrlValidator(fieldName = "brand.url", message = "网址格式错误!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "brand.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (logo != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			String logoExtension = StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			if (!ArrayUtils.contains(imageExtensionArray, logoExtension)) {
				addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
				return ERROR;
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0 && logo.length() > uploadLimit) {
				addActionError("Logo文件大小超出限制!");
				return ERROR;
			}
			File uploadImageDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_IMAGE_DIR));
			if (!uploadImageDir.exists()) {
				uploadImageDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + CommonUtils.getUUID() + "." + logoExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadImagePath));
			FileUtils.copyFile(logo, file);
			brand.setLogo(uploadImagePath);
		}
		brandService.save(brand);
		redirectionUrl = "brand!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "brand.name", message = "品牌名称不允许为空!")
		},
		urls = {
			@UrlValidator(fieldName = "brand.url", message = "网址格式错误!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "brand.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "brand.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Brand persistent = brandService.load(id);
		if (logo != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			String logoExtension = StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			if (!ArrayUtils.contains(imageExtensionArray, logoExtension)) {
				addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
				return ERROR;
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0 && logo.length() > uploadLimit) {
				addActionError("Logo文件大小超出限制!");
				return ERROR;
			}
			File uploadImageDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_IMAGE_DIR));
			if (!uploadImageDir.exists()) {
				uploadImageDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + CommonUtils.getUUID() + "." + logoExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadImagePath));
			FileUtils.copyFile(logo, file);
			persistent.setLogo(uploadImagePath);
		}
		BeanUtils.copyProperties(brand, persistent, new String[]{"id", "createDate", "modifyDate", "logo", "productSet"});
		brandService.update(persistent);
		redirectionUrl = "brand!list.action";
		return SUCCESS;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public Pager<Brand> getPager() {
		return pager;
	}

	public void setPager(Pager<Brand> pager) {
		this.pager = pager;
	}

}
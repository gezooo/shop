package com.zg.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zg.beans.ProductImage;
import com.zg.beans.SystemConfig;
import com.zg.common.util.CommonUtils;
import com.zg.common.util.ImageUtils;
import com.zg.common.util.SystemConfigUtils;
import com.zg.service.ProductImageService;

/*
* @author gez
* @version 0.1
*/

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Override
	public ProductImage buildProductImage(File uploadProductImageFile) {
		SystemConfig systemConfig = SystemConfigUtils.getSystemConfig();
		String sourceProductImageFormatName = ImageUtils.getImageFormatName(uploadProductImageFile);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtils.getUUID();
		
		String sourceProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourceProductImageFormatName;
		String bigProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.BIG_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String smallProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.SMALL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String thumbnailProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.THUMBNAIL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;

		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceProductImagePath));
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigProductImagePath));
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallProductImagePath));
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailProductImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));
		
		File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
		File bigProductImageParentFile = bigProductImageFile.getParentFile();
		File smallProductImageParentFile = smallProductImageFile.getParentFile();
		File thumbnailProductImageParentFile = thumbnailProductImageFile.getParentFile();
		
		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		if (!bigProductImageParentFile.exists()) {
			bigProductImageParentFile.mkdirs();
		}
		if (!smallProductImageParentFile.exists()) {
			smallProductImageParentFile.mkdirs();
		}
		if (!thumbnailProductImageParentFile.exists()) {
			thumbnailProductImageParentFile.mkdirs();
		}
		
		try {
			BufferedImage srcBufferedImage = ImageIO.read(uploadProductImageFile);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(uploadProductImageFile, sourceProductImageFile);
			// 商品图片（大）缩放、水印处理
			ImageUtils.zoomAndWatermark(srcBufferedImage, bigProductImageFile, systemConfig.getBigProductImageHeight(), systemConfig.getBigProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
			ImageUtils.zoomAndWatermark(srcBufferedImage, smallProductImageFile, systemConfig.getSmallProductImageHeight(), systemConfig.getSmallProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
			ImageUtils.zoom(srcBufferedImage, thumbnailProductImageFile, systemConfig.getThumbnailProductImageHeight(), systemConfig.getThumbnailProductImageWidth());

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProductImage productImage = new ProductImage();
		productImage.setId(uuid);
		productImage.setSourceProductImagePath(sourceProductImagePath);
		productImage.setBigProductImagePath(bigProductImagePath);
		productImage.setSmallProductImagePath(smallProductImagePath);
		productImage.setThumbnailProductImagePath(thumbnailProductImagePath);
		return productImage;
	}

	@Override
	public void deleteFile(ProductImage productImage) {
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSourceProductImagePath()));
		if (sourceProductImageFile.exists()) {
			sourceProductImageFile.delete();
		}
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getBigProductImagePath()));
		if (bigProductImageFile.exists()) {
			bigProductImageFile.delete();
		}
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSmallProductImagePath()));
		if (smallProductImageFile.exists()) {
			smallProductImageFile.delete();
		}
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getThumbnailProductImagePath()));
		if (thumbnailProductImageFile.exists()) {
			thumbnailProductImageFile.delete();
		}		
	}

}

package com.zg.service;

import java.io.File;

import com.zg.beans.ProductImage;

public interface ProductImageService {
	
	public ProductImage buildProductImage(File uploadProductImageFile);
	
	public void deleteFile(ProductImage productImage);



}

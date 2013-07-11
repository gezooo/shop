package com.zg.beans;

/*
* @author gez
* @version 0.1
*/

public class ProductImage {
	
	public static final String PRODUCT_IMAGE_FILE_EXTENSION = "jpg";// 商品图片文件名扩展名
	public static final String BIG_PRODUCT_IMAGE_FILE_NAME_SUFFIX = "_big";// 商品图片（大）文件名后缀
	public static final String SMALL_PRODUCT_IMAGE_FILE_NAME_SUFFIX = "_small";// 商品图片（小）文件名后缀
	public static final String THUMBNAIL_PRODUCT_IMAGE_FILE_NAME_SUFFIX = "_thumbnail";// 商品缩略图文件名后缀
	
	private String id;// ID
	private String sourceProductImagePath;// 商品图片（原）路径
	private String bigProductImagePath;// 商品图片（大）路径
	private String smallProductImagePath;// 商品图片（小）路径
	private String thumbnailProductImagePath;// 商品图片（缩略图）路径
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSourceProductImagePath() {
		return sourceProductImagePath;
	}
	
	public void setSourceProductImagePath(String sourceProductImagePath) {
		this.sourceProductImagePath = sourceProductImagePath;
	}

	public String getBigProductImagePath() {
		return bigProductImagePath;
	}
	
	public void setBigProductImagePath(String bigProductImagePath) {
		this.bigProductImagePath = bigProductImagePath;
	}
	
	public String getSmallProductImagePath() {
		return smallProductImagePath;
	}
	
	public void setSmallProductImagePath(String smallProductImagePath) {
		this.smallProductImagePath = smallProductImagePath;
	}
	
	public String getThumbnailProductImagePath() {
		return thumbnailProductImagePath;
	}
	
	public void setThumbnailProductImagePath(String thumbnailProductImagePath) {
		this.thumbnailProductImagePath = thumbnailProductImagePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProductImage other = (ProductImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

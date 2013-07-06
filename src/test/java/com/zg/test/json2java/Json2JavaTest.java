package com.zg.test.json2java;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.junit.Test;

import com.zg.beans.ProductImage;

public class Json2JavaTest {
	
	@Test
	public void test(){}
	
	public static void testJson2Java(){
		String productImageListStore = "[{\"bigProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_big.jpg\",\"id\":\"b955bd91c8b64844b4681b2eb44849d1\",\"smallProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_small.jpg\",\"sourceProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1.jpeg\",\"thumbnailProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_thumbnail.jpg\"}]";
		JSONArray jsonArray = JSONArray.fromObject(productImageListStore);
		List productImageList = (List)JSONSerializer.toJava(jsonArray);
		
		//JSONObject.toBean(p, ProductImage.class);
		for(Object o : productImageList){
			JSONObject jsonObject=JSONObject.fromObject(o);
            ProductImage obj=(ProductImage)JSONObject.toBean(jsonObject, ProductImage.class);
			System.out.println(obj.getBigProductImagePath());
		}

	}
	
	public static void testJson2Java2(){
		String productImageListStore = "[{\"bigProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_big.jpg\",\"id\":\"b955bd91c8b64844b4681b2eb44849d1\",\"smallProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_small.jpg\",\"sourceProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1.jpeg\",\"thumbnailProductImagePath\":\"/upload/image/201307/b955bd91c8b64844b4681b2eb44849d1_thumbnail.jpg\"}]";
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(ProductImage.class);
		JSONArray jsonArray = JSONArray.fromObject(productImageListStore);
		List<ProductImage> list =  (List<ProductImage>) JSONSerializer.toJava(jsonArray, jsonConfig);
		for(ProductImage p : list){
			System.out.println(p.getBigProductImagePath());
		}
	}
	
	public static void testList2Json(){
		String[] sl = {"a","b","c"};

		JSONArray jsonArray = JSONArray.fromObject(sl);
		System.out.println(jsonArray.toString());
		
	}
	
	
	
	public static void main(String[] args) {
		testList2Json();
	}

}

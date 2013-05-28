package com.zg.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zg.beans.HtmlConfig;
import com.zg.util.TemplateConfigUtil;

public class TestTemplateConfigUtil {
	
	@Test
	public void testConfig() {
		
		HtmlConfig config = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		assertEquals("/WEB-INF/template/shop/article_content.ftl", config.getTemplateFilePath());
		
		
	}

}

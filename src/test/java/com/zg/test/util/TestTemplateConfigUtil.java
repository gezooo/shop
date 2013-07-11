package com.zg.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zg.beans.HtmlConfig;
import com.zg.common.util.TemplateConfigUtils;

public class TestTemplateConfigUtil {
	
	@Test
	public void testConfig() {
		
		HtmlConfig config = TemplateConfigUtils.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		assertEquals("/WEB-INF/template/shop/article_content.ftl", config.getTemplateFilePath());
		
		
	}

}

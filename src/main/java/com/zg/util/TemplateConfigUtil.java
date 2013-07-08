package com.zg.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zg.beans.DynamicConfig;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zg.action.admin.AdminAction;
import com.zg.beans.HtmlConfig;
import com.zg.beans.MailConfig;
import com.zg.common.ClassLoadUtil;
import com.zg.common.ClassPathFileReaderRandomBackgroundGenerator;

@Component
public class TemplateConfigUtil {
	
    public static final Logger logger = LoggerFactory.getLogger(TemplateConfigUtil.class);
    
    public static final String CONFIG_FILE_NAME = "template.xml";// 模板配置文件名称
	public static final String DYNAMIC_CONFIG_LIST_CACHE_KEY = "dynamicConfigList";// 动态模板配置缓存Key
	public static final String HTML_CONFIG_LIST_CACHE_KEY = "htmlConfigList";// 生成静态模板配置缓存Key
	public static final String MAIL_CONFIG_LIST_CACHE_KEY = "mailConfigList";// 邮件模板配置缓存Key

	public static HtmlConfig getHtmlConfig(String name) {
		Document document = null;
		String configFilePath;
		try {
			/*
			configFilePath = new File(Thread.currentThread().getContextClassLoader()
					.getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			*/
	    	//InputStream is = TemplateConfigUtil.class.getClassLoader().getResourceAsStream("template/" + CONFIG_FILE_NAME);

			SAXReader saxReader = new SAXReader();
			document = saxReader.read(ClassLoadUtil.getResourceAsStream(CONFIG_FILE_NAME));
			HtmlConfig.class.getResourceAsStream("");
		} catch (DocumentException e) {
			logger.error("read template error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		Element element = (Element) document.selectSingleNode("/shopxx/htmlConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	String htmlFilePath = element.element("htmlFilePath").getTextTrim();
    	HtmlConfig htmlConfig = new HtmlConfig();
    	htmlConfig.setName(element.getName());
    	htmlConfig.setDescription(description);
    	htmlConfig.setTemplateFilePath(templateFilePath);
    	htmlConfig.setHtmlFilePath(htmlFilePath);
		return htmlConfig;
	}
	
	/**
	 * 根据邮件模板配置名称获取MailConfig对象
	 * 
	 * @return MailConfig对象
	 */
	public static MailConfig getMailConfig(String name) {
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode("/shopxx/mailConfig/" + name);
		String description = element.element("description").getTextTrim();
		String subject = element.element("subject").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
		MailConfig mailConfig = new MailConfig();
    	mailConfig.setName(element.getName());
    	mailConfig.setDescription(description);
    	mailConfig.setSubject(subject);
    	mailConfig.setTemplateFilePath(templateFilePath);
		return mailConfig;
	}
	
	/**
	 * 获取动态模板配置
	 * 
	 * @return DynamicConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<DynamicConfig> getDynamicConfigList() {
		List<DynamicConfig> dynamicConfigList = (List<DynamicConfig>) EncacheCacheConfigUtil.getFromCache(DYNAMIC_CONFIG_LIST_CACHE_KEY);
		if (dynamicConfigList != null) {
			return dynamicConfigList;
		}
		File configFile = null;
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/shopxx/dynamicConfig");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		dynamicConfigList = new ArrayList<DynamicConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templateFilePath = element.element("templateFilePath").getTextTrim();
	    	DynamicConfig dynamicConfig = new DynamicConfig();
	    	dynamicConfig.setName(element.getName());
	    	dynamicConfig.setDescription(description);
	    	dynamicConfig.setTemplateFilePath(templateFilePath);
	    	dynamicConfigList.add(dynamicConfig);
	    }
	    EncacheCacheConfigUtil.putInCache(DYNAMIC_CONFIG_LIST_CACHE_KEY, dynamicConfigList);
		return dynamicConfigList;
	}
	
	/**
	 * 根据动态模板配置名称获取DynamicConfig对象
	 * 
	 * @return DynamicConfig对象
	 */
	public static DynamicConfig getDynamicConfig(String name) {
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode("/shopxx/dynamicConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	DynamicConfig dynamicConfig = new DynamicConfig();
    	dynamicConfig.setName(element.getName());
    	dynamicConfig.setDescription(description);
    	dynamicConfig.setTemplateFilePath(templateFilePath);
		return dynamicConfig;
	}

	

}

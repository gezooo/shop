package com.zg.test.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;


import org.apache.log4j.BasicConfigurator;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zg.beans.SystemConfig;
import com.zg.common.util.CommonUtils;
import com.zg.entity.Article;
import com.zg.test.mock.util.MockSystemConfigUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-freemarker.xml"})
public class BuildHtmlTest {
	
	private static final Logger logger =  LoggerFactory.getLogger(BuildHtmlTest.class);

	
	//freemarkerManager is a framework internal bean, and should inject using Struts2 Inject annotation.
	private FreemarkerManager freemarkerManager;
	
	@Autowired
	private ServletContext servletContext;

	@Test
	public void testBuildHtml(){
	
		 
		String templateFilePath = "/template/article_content.ftl";
		String htmlFilePath = "/html/article_content/201307/6fbfd80482524b27b923af9a966128c7.html";
		Map<String, Object> commonData = new HashMap<String, Object>();
		SystemConfig systemConfig = MockSystemConfigUtil.getSystemConfig();
		commonData.put("systemConfig", systemConfig);
		Article article = new Article();
		commonData.put("article", article);
		commonData.put("base", "base");
		
		//buildHtml(templateFilePath, htmlFilePath, commonData);
	}
	
	public void buildHtml(String templateFilePath, String htmlFilePath,
			Map<String, Object> data) {
		logger.debug(CommonUtils.displayMessage("Called", null));

		//freemarker.cache.WebappTemplateLoader r;
		 Configuration freemarkerCfg = new Configuration();
		 freemarkerCfg.setServletContextForTemplateLoading(ServletActionContext  
	                .getServletContext(), "/");  
		try {
			Template template = freemarkerCfg.getTemplate(templateFilePath);
			logger.debug(CommonUtils.displayMessage("configuration.getTemplate", null));

			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			logger.debug(CommonUtils.displayMessage("File(servletContext", null));
			System.out.println("file path: " + htmlFile.getPath());


			File htmlDirectory = htmlFile.getParentFile();
			logger.debug(CommonUtils.displayMessage("htmlFile.getParentFile", null));

			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			logger.debug(CommonUtils.displayMessage("htmlDirectory.mkdirs", null));

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			logger.debug(CommonUtils.displayMessage("new BufferedWriter", null));

			template.process(data, out);
			logger.debug(CommonUtils.displayMessage("template.process", null));

			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("IOException" + e.getMessage());
		} catch (TemplateException e) {
			logger.error("TemplateException" + e.getMessage());
		}

		
	}
	
	@Before
	public void setUp() throws Exception {
        /*
        mgr = new FreemarkerManager();
        mgr.setEncoding("UTF-8");
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        response = new StrutsMockHttpServletResponse();
        response.setWriter(writer);
        request = new MockHttpServletRequest();
        servletContext = new StrutsMockServletContext();
        stack = ActionContext.getContext().getValueStack();
        context = new ActionContext(stack.getContext());
        context.put(StrutsStatics.HTTP_RESPONSE, response);
        context.put(StrutsStatics.HTTP_REQUEST, request);
        context.put(StrutsStatics.SERVLET_CONTEXT, servletContext);
        ServletActionContext.setServletContext(servletContext);
        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);
        servletContext.setAttribute(FreemarkerManager.CONFIG_SERVLET_CONTEXT_KEY, null);
        invocation = new MockActionInvocation();
        invocation.setStack(stack);
        invocation.setInvocationContext(context);
        servletContext.setRealPath(new File(FreeMarkerResultTest.class.getResource(
					"someFreeMarkerFile.ftl").toURI()).toURL().getFile());
					*/
		
		//DOMConfigurator.configure ( "log4j.xml" ); 
		BasicConfigurator.configure() ;
		//System.setProperty("log4j.configuration", "log4j.xml");
    }

	
}

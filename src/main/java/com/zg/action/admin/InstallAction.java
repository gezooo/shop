package com.zg.action.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zg.common.util.SystemConfigUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 后台Action类 - 安装
 * @author gez
 * @version 0.1
 */

@ParentPackage("admin")
public class InstallAction extends BaseAdminAction {

	private static final long serialVersionUID = -83169049612878557L;
	
	public static final String JDBC_CONFIG_FILE_NAME = "jdbc.properties";// JDBC配置文件
	public static final String JDBC_CONFIG_FILE_DESCRIPTION = "SHOP++ JDBC配置文件";// JDBC配置文件描述
	public static final String SQL_INSTALL_FILE_NAME = "shopxx.sql";// SQL安装文件
	
	public static final String BACKUP_WEB_CONFIG_FILE_NAME = "backup-web.xml";// Web备份配置文件名称
	public static final String BACKUP_APPLICATION_CONTEXT_CONFIG_FILE_NAME = "backup-applicationContext.xml";// Spring备份配置文件名称
	public static final String BACKUP_COMPASS_APPLICATION_CONTEXT_CONFIG_FILE_NAME = "backup-applicationContext-compass.xml";// Compass备份配置文件名称
	public static final String BACKUP_SECURITY_APPLICATION_CONTEXT_CONFIG_FILE_NAME = "backup-applicationContext-security.xml";// SpringSecurity备份配置文件名称
	
	public static final String WEB_CONFIG_FILE_NAME = "web.xml";// Web配置文件名称
	public static final String APPLICATION_CONTEXT_CONFIG_FILE_NAME = "applicationContext.xml";// Spring配置文件名称
	public static final String COMPASS_APPLICATION_CONTEXT_CONFIG_FILE_NAME = "applicationContext-compass.xml";// Compass配置文件名称
	public static final String SECURITY_APPLICATION_CONTEXT_CONFIG_FILE_NAME = "applicationContext-security.xml";// SpringSecurity配置文件名称
	
	private Boolean isAgreeAgreement;
	private String databaseHost;
	private String databasePort;
	private String databaseUsername;
	private String databasePassword;
	private String databaseName;
	private String adminUsername;
	private String adminPassword;
	private String installStatus;

	// 授权协议
	public String license() {
		if (isInstalled()) {
			addActionError("SHOP++已完成安装，请勿重复操作！");
			return ERROR;
		}
		return "license";
	}
	
	// 检查系统环境
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "isAgreeAgreement", message = "必须同意授权协议，才允许进行安装!")
		}
	)
	@InputConfig(resultName = "error")
	public String check() {
		if (isInstalled()) {
			addActionError("SHOP++已完成安装，请勿重复操作！");
			return ERROR;
		}
		if (!isAgreeAgreement) {
			addActionError("必须同意授权协议，才允许进行安装!");
		}
		return "check";
	}
	
	// 系统配置
	public String setting() {
		if (isInstalled()) {
			addActionError("SHOP++已完成安装，请勿重复操作！");
			return ERROR;
		}
		return "setting";
	}
	
	// 安装保存
	public String save() throws URISyntaxException, IOException, DocumentException {
		if (isInstalled()) {
			return ajaxJsonErrorMessage("SHOP++已完成安装，请勿重复操作！");
		}
		if (StringUtils.isEmpty(databaseHost)) {
			return ajaxJsonErrorMessage("请填写数据库主机!");
		}
		if (StringUtils.isEmpty(databasePort)) {
			return ajaxJsonErrorMessage("请填写数据库端口!");
		}
		if (StringUtils.isEmpty(databaseUsername)) {
			return ajaxJsonErrorMessage("请填写数据库用户名!");
		}
		if (StringUtils.isEmpty(databasePassword)) {
			return ajaxJsonErrorMessage("请填写数据库密码!");
		}
		if (StringUtils.isEmpty(databaseName)) {
			return ajaxJsonErrorMessage("请填写数据库名称!");
		}
		if (StringUtils.isEmpty(adminUsername)) {
			return ajaxJsonErrorMessage("请填写管理员用户名!");
		}
		if (StringUtils.isEmpty(adminPassword)) {
			return ajaxJsonErrorMessage("请填写管理员密码!");
		}
		if (StringUtils.isEmpty(installStatus)) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, "requiredCheckFinish");
			return ajaxJson(jsonMap);
		}
		
		String jdbcUrl = "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
        	// 检测数据库连接
            connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			String[] types = {"TABLE"};
			resultSet = databaseMetaData.getTables(null, databaseName, "%", types);
			if (StringUtils.equalsIgnoreCase(installStatus, "databaseCheck")) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put(STATUS, "databaseCheckFinish");
				return ajaxJson(jsonMap);
			}
			
			// 导入数据库
			if (StringUtils.equalsIgnoreCase(installStatus, "databaseCreate")) {
				StringBuffer stringBuffer = new StringBuffer(); 
				BufferedReader bufferedReader = null;
				String sqlFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SQL_INSTALL_FILE_NAME;
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFilePath), "UTF-8"));
				String line = "";
				while (null != line) {
					line = bufferedReader.readLine();
					stringBuffer.append(line);
					if (null != line && line.endsWith(";")) {
						System.out.println("[SHOP++安装程序]SQL: " + line);
						preparedStatement = connection.prepareStatement(stringBuffer.toString());
						preparedStatement.executeUpdate();
						stringBuffer = new StringBuffer();
					}
				}
				String insertAdminSql = "INSERT INTO `admin` VALUES ('402881862bec2a21012bec2bd8de0003','2010-10-10 0:0:0','2010-10-10 0:0:0','技术部','admin@shopxx.net',b'1',b'0',b'0',b'0',NULL,NULL,0,NULL,'管理员','" + DigestUtils.md5Hex(adminPassword) + "','" + adminUsername + "');";
				String insertAdminRoleSql = "INSERT INTO `admin_role` VALUES ('402881862bec2a21012bec2bd8de0003','402881862bec2a21012bec2b70510002');";
				preparedStatement = connection.prepareStatement(insertAdminSql);
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement(insertAdminRoleSql);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("数据库连接失败，请检查数据库设置信息!");
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
					resultSet = null;
				}
				if(preparedStatement != null) {
	        		preparedStatement.close();
	        		preparedStatement = null;
				}
				if(connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 处理数据库配置文件
		String configFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + JDBC_CONFIG_FILE_NAME;
		Properties properties = new Properties();
		properties.put("jdbc.driver", "com.mysql.jdbc.Driver");
		properties.put("jdbc.url", jdbcUrl);
		properties.put("jdbc.username", databaseUsername);
		properties.put("jdbc.password", databasePassword);
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "false");
        OutputStream outputStream = new FileOutputStream(configFilePath);
        properties.store(outputStream, JDBC_CONFIG_FILE_DESCRIPTION);
        outputStream.close();
        
        // 处理框架配置文件
		String backupWebConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + BACKUP_WEB_CONFIG_FILE_NAME;
		String backupApplicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + BACKUP_APPLICATION_CONTEXT_CONFIG_FILE_NAME;
		String backupCompassApplicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + BACKUP_COMPASS_APPLICATION_CONTEXT_CONFIG_FILE_NAME;
		String backupSecurityApplicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + BACKUP_SECURITY_APPLICATION_CONTEXT_CONFIG_FILE_NAME;

		String webConfigFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/" + WEB_CONFIG_FILE_NAME;
		String applicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + APPLICATION_CONTEXT_CONFIG_FILE_NAME;
		String compassApplicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + COMPASS_APPLICATION_CONTEXT_CONFIG_FILE_NAME;
		String securityApplicationContextConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SECURITY_APPLICATION_CONTEXT_CONFIG_FILE_NAME;

		FileUtils.copyFile(new File(backupWebConfigFilePath), new File(webConfigFilePath));
		FileUtils.copyFile(new File(backupApplicationContextConfigFilePath), new File(applicationContextConfigFilePath));
		FileUtils.copyFile(new File(backupCompassApplicationContextConfigFilePath), new File(compassApplicationContextConfigFilePath));
		FileUtils.copyFile(new File(backupSecurityApplicationContextConfigFilePath), new File(securityApplicationContextConfigFilePath));
		
		// 处理系统配置文件
		String systemConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SystemConfigUtils.CONFIG_FILE_NAME;
		File systemConfigFile = new File(systemConfigFilePath);
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(systemConfigFile);
		Element rootElement = document.getRootElement();
		Element systemConfigElement = rootElement.element("systemConfig");
		Node isInstalledNode = document.selectSingleNode("/shopxx/systemConfig/isInstalled");
		if(isInstalledNode == null){
			isInstalledNode = systemConfigElement.addElement("isInstalled");
		}
		isInstalledNode.setText("true");
		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			outputFormat.setIndent(true);// 设置是否缩进
			outputFormat.setIndent("	");// 以TAB方式实现缩进
			outputFormat.setNewlines(true);// 设置是否换行
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(systemConfigFile), outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxJsonSuccessMessage("SHOP++安装成功，请重新启动服务器！");
	}
	
	// 检测是否已安装
	private boolean isInstalled() {
		try {
			String systemConfigFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + SystemConfigUtils.CONFIG_FILE_NAME;
			File systemConfigFile = new File(systemConfigFilePath);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(systemConfigFile);
			Node isInstalledNode = document.selectSingleNode("/shopxx/systemConfig/isInstalled");
			if(isInstalledNode != null && StringUtils.equalsIgnoreCase(isInstalledNode.getText(), "false")){
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	// freemarker静态方法调用
	public TemplateHashModel getStatics() {
		return BeansWrapper.getDefaultInstance().getStaticModels();
	}

	public Boolean getIsAgreeAgreement() {
		return isAgreeAgreement;
	}

	public void setIsAgreeAgreement(Boolean isAgreeAgreement) {
		this.isAgreeAgreement = isAgreeAgreement;
	}

	public String getDatabaseHost() {
		return databaseHost;
	}

	public void setDatabaseHost(String databaseHost) {
		this.databaseHost = databaseHost;
	}

	public String getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getInstallStatus() {
		return installStatus;
	}

	public void setInstallStatus(String installStatus) {
		this.installStatus = installStatus;
	}

}
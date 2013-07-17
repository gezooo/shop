package com.zg.test.mock.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;



import com.zg.beans.SystemConfig;
import com.zg.common.ConfigurationManager;
import com.zg.common.util.SystemConfigUtils;


public class MockSystemConfigUtil extends SystemConfigUtils{
	
	public static File getConfigFile() throws URISyntaxException {
		File file = new File("temp");
		OutputStream os = null;
		InputStream ins = null;
		try {
			os = new FileOutputStream(file);
		
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			ins = new FileInputStream(new File(ConfigurationManager.getConfigProperties(SystemConfigUtils.CONFIG_FILE_PATH_NAME)));
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return file;
	}
	
	public static SystemConfig getSystemConfig() {
		return loadSystemConfigFromConfigFile();
	}

}

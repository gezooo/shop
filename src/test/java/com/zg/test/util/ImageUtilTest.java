package com.zg.test.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.zg.beans.SystemConfig;
import com.zg.test.mock.util.MockSystemConfigUtil;
import com.zg.util.ImageUtil;

public class ImageUtilTest {
	
	public static final String PATH = "C:\\Users\\zhangge\\learningShopPlus\\shop\\src\\test\\java\\com\\zg\\test\\util\\";

	@Test
	public void testZoomAndWatermark() {
		SystemConfig systemConfig = MockSystemConfigUtil.getSystemConfig();
		
		File uploadProductImageFile = new File(PATH + "source.jpg");
		File watermarkImageFile = new File(PATH + "watermark.jpg");
		File bigProductImageFile = new File(PATH + "bigProduct.jpg");

		
		BufferedImage srcBufferedImage;
		try {
			srcBufferedImage = ImageIO.read(uploadProductImageFile);
			ImageUtil.zoomAndWatermark(srcBufferedImage, bigProductImageFile, systemConfig.getBigProductImageHeight(), systemConfig.getBigProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

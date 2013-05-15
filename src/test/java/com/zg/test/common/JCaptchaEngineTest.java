package com.zg.test.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.zg.common.ClassPathFileReaderRandomBackgroundGenerator;

public class JCaptchaEngineTest {
	
	@Test
	public void testGetImage() {
		ClassPathFileReaderRandomBackgroundGenerator gen = new ClassPathFileReaderRandomBackgroundGenerator(80, 28, "captcha/");
		BufferedImage image = gen.getBackground();
		try {
			ImageIO.write(image, "jpg", new FileOutputStream(new File("C:\\Users\\zhangge\\Desktop\\temp\\testImage.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

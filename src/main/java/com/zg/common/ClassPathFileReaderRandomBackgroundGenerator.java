/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.zg.common;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * <p>File reader background generator that return a random image (JPEG ONLY) from the ones found in the classpath </p>
 * <p/>
 * TODO : add some gif, bmp,... reader facilities.
 *
 * @author zhangge
 * @version 1.0
 */
public class ClassPathFileReaderRandomBackgroundGenerator implements BackgroundGenerator {
	
	private int height;
    private int width;

    Random myRandom = new SecureRandom();

    /**
     * @return the generated image height
     */
    public int getImageHeight() {
        return height;
    }

    /**
     * @return teh generated image width
     */
    public int getImageWidth() {
        return width;
    }

    private List images = new ArrayList();
    private String rootPath;

    public ClassPathFileReaderRandomBackgroundGenerator(Integer width,
                                               Integer height, String rootPath) {
    	this.width = width != null ? width.intValue() : this.width;
        this.height = height != null ? height.intValue() : this.height;
        //this.images=images;
        if (rootPath != null)
            this.rootPath = rootPath;
        
        try {
        	if(!localCache.containsKey("images")) {	
        		Properties properties = getCaptchaProperties(rootPath);
    			String files = (String) properties.get("bgfiles");
    	        StringTokenizer token = new StringTokenizer(files, ",");
    	        while (token.hasMoreElements()) {
                    String file = token.nextToken();
                    BufferedImage out = getImage(rootPath + file);
                    if (out != null) {
                        images.add(images.size(), out);
                    }
                }
    	        
    	        if (images.size() != 0) {
                    for (int i = 0; i < images.size(); i++) {
                        BufferedImage bufferedImage = (BufferedImage) images.get(i);
                        images.set(i, tile(bufferedImage));
                    }
                    localCache.put("images", images);
                } else {
                    throw new CaptchaException("Root path directory is valid but " +
                            "does not contains any image (jpg) files");
                }
        	}
			

		} catch (IOException e) {
			throw new CaptchaException("Root path directory is valid but " +
                    "does not contains any image (jpg) files");
		}
        
        
    }

    /**
     *
     */
    protected static Map localCache = new HashMap();
    
    protected Properties getCaptchaProperties(String rootPath) throws IOException{
    	if(localCache.containsKey("properties")) {
    		return (Properties) localCache.get("properties");
    	}
    	InputStream is = ClassLoadUtil.getResourceAsStream(rootPath + "captcha_bg_files.properties");
    	Properties properties = new Properties();
    	properties.load(is);
    	localCache.put("properties", properties);
    	is.close();
    	return properties;	
    }


    private BufferedImage tile(BufferedImage tileImage) {
        BufferedImage image = new BufferedImage(getImageWidth(),
                getImageHeight(), tileImage.getType());
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int NumberX = (getImageWidth() / tileImage.getWidth());
        int NumberY = (getImageHeight() / tileImage.getHeight());
        for (int k = 0; k <= NumberY; k++) {
            for (int l = 0; l <= NumberX; l++) {
                g2.drawImage(tileImage, l * tileImage.getWidth(), k *
                        tileImage.getHeight(),
                        Math.min(tileImage.getWidth(), getImageWidth()),
                        Math.min(tileImage.getHeight(), getImageHeight()),
                        null);
            }
        }
        g2.dispose();
        return image;
    }

    private static BufferedImage getImage(String file) {
        BufferedImage out = null;
        try {

            //            ImageInfo info = new ImageInfo();
            //            Image image = ToolkitFactory.getToolkit().createImage(o.toString());
            //            info.setInput(new FileInputStream(o));
            //            out = new BufferedImage(info.getWidth(), info.getHeight(),BufferedImage.TYPE_INT_RGB );
            //            out.getGraphics().drawImage(image,out.getWidth(),out.getHeight(),null);
            //            out.getGraphics().dispose();
            //
        	InputStream is = ClassPathFileReaderRandomBackgroundGenerator.class.getClassLoader().getResourceAsStream(file);
        	out = ImageIO.read(is);
            is.close();

            // Return the format name
            return out;
        } catch (IOException e) {
            throw new CaptchaException("Unknown error during file reading ", e);
        }
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        return (BufferedImage) images.get(myRandom.nextInt(images.size()));
    }

}

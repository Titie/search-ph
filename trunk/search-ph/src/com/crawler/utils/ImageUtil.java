package com.crawler.utils;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;

public class ImageUtil {
	
	public static int[] getImageSize(byte[] imagedata) throws Exception {
		Image image = new ImageIcon(imagedata).getImage();
		int[] imageSize = new int[]{image.getWidth(null), image.getHeight(null)};
	    return imageSize;
	}
	
	public static byte[] resizeImage(byte[] imagedata, int newWidth, int newHeight) {
		Image image = new ImageIcon(imagedata).getImage();
		int orgWidth = image.getWidth(null);
		int orgHeight = image.getHeight(null);
		if (orgWidth <= newWidth && orgHeight <= newHeight) {
			return imagedata;
		}
		int newSize[] = calculateNewSize(orgWidth, orgHeight, newWidth, newHeight);
		
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			
			Image img = image.getScaledInstance(newSize[0], newSize[1], Image.SCALE_SMOOTH);
			JimiWriter jimiWriter = Jimi.createJimiWriter("image/jpeg", out);
			JPGOptions options = new JPGOptions();
			options.setQuality(25);
			jimiWriter.setOptions(options);
			//JimiRasterImage raster = Jimi.createRasterImage(img.getSource());
			jimiWriter.setSource(img);
			jimiWriter.putImage(out);
			out.flush();
			return out.toByteArray();
		} catch (Throwable t) {
			t.printStackTrace();
			return imagedata;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {}
			}
		}
	}
	
	public static int[] calculateNewSize(int orgWidth, int orgHeight, int screenWidth, int screenHeight) {
		if (orgWidth <= screenWidth && orgHeight <= screenHeight) {
			return new int[] {orgWidth, orgHeight};
		}
	    double newRatio = (double)screenWidth / (double)screenHeight;
	    double imageRatio = (double)orgWidth / (double)orgHeight;
	    if (newRatio < imageRatio) {
	      screenHeight = (int)(screenWidth / imageRatio);
	    } else {
	      screenWidth = (int)(screenHeight * imageRatio);
	    }
	    int[] newSize = new int[2];
	    newSize[0] = screenWidth;
	    newSize[1] = screenHeight;
	    return newSize;
	}	
	
	public static void main(String[] args) {
		//System.out.println(new String(Base64.encodeBase64("http://vnexpress.net/Files/Subject/3B/A1/BB/55/thi1.jpg".getBytes())));
		try {
			byte[] data = FileUtils.readFileToByteArray(new File("c:\\temp\\dien.jpg"));
			byte[] resizedData = resizeImage(data, 200, 250);
			FileUtils.writeByteArrayToFile(new File("c:\\temp\\dien1.jpg"), resizedData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

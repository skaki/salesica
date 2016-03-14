package com.salesica.extractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.xmlbeans.XmlException;

public class ppt {

	public static final String fromFile(String filename) {
		XSLFPowerPointExtractor poiExtractor = null;
		try {
			File file = new File(filename);
			FileInputStream fs = new FileInputStream(file);
			OPCPackage pptx = OPCPackage.open(fs);
			poiExtractor = new XSLFPowerPointExtractor(pptx);
			String content = poiExtractor.getText(true, true);
			return replaceConsecutiveSpaces(content, " ");
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} finally {
			try {
				if (poiExtractor != null)
					poiExtractor.close();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		return null;
	}
	
	public static final String fromUrl(String Url) {
		java.net.URL url;
		try {
			url = new java.net.URL(Url);
			File file = File.createTempFile("tmp_", ".pdf", new File("/tmp"));
			FileUtils.copyURLToFile(url, file);
			String text = fromFile(file.getAbsolutePath());
			file.delete();
			return text;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static final String replaceConsecutiveSpaces(String source, String replace) { 
		  StringBuilder target = new StringBuilder(); 
		  int l = source.length(); 
		  boolean consecutiveSpace = false; 
		  for (int i = 0; i < l; i++) { 
		   char c = source.charAt(i); 
		   if (Character.isWhitespace(c)) { 
		    if (!consecutiveSpace) { 
		     if (replace != null) 
		      target.append(replace); 
		     consecutiveSpace = true; 
		    } 
		   } else { 
		    target.append(c); 
		    if (consecutiveSpace) 
		     consecutiveSpace = false; 
		   } 
		  } 
		  return target.toString(); 
	}
}

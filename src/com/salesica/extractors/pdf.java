package com.salesica.extractors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class pdf {

	public static final String fromFile(String fileName) {
		File file = new File(fileName);
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(PDDocument.load(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}
	
	public static final String fromUrl(String Url) {
		java.net.URL url;
		try {
			url = new java.net.URL(Url);
			File file = File.createTempFile("tmp_", ".pdf", new File("/tmp"));
			FileUtils.copyURLToFile(url, file);
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(PDDocument.load(file));
			file.delete();
			return text;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

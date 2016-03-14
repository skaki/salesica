package com.salesica.extractors.tests;

import org.junit.Test;

import com.salesica.extractors.ppt;

public class TestPPTExtractor {

	String pptFile = "/Users/skaki/Documents/workspace/salesica/src/com/salesica/textanalytics/tests/data/salesicaPitchv7.ppt";
	String pptUrl = "http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf";

	// NOT WORKING, 3/8/2016.
	@Test
	public void test_file() {		
		System.out.println(ppt.fromFile(pptFile));
	}
	
	/*
	@Test
	public void test_url() {		
		//System.out.println(pdf.fromUrl(pdfUrl));
	}
	*/

}

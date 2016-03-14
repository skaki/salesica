package com.salesica.extractors.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

import com.salesica.extractors.pdf;
import com.salesica.textanalytics.Concepts;

public class TestPDFExtractor {

	String pdfFile = "/Users/skaki/Documents/workspace/salesica/src/com/salesica/textanalytics/tests/data/executivesummary.pdf";
	String pdfUrl = "http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf";

	@Test
	public void test_file() {		
		System.out.println(pdf.fromFile(pdfFile));
	}
	
	@Test
	public void test_url() {		
		System.out.println(pdf.fromUrl(pdfUrl));
	}

}

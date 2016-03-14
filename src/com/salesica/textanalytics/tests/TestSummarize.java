package com.salesica.textanalytics.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.salesica.extractors.pdf;
import com.salesica.textanalytics.Concepts;

public class TestSummarize {

	String blogURL = "https://blog.percolate.com/2016/03/zero-based-budget-for-marketing/";
	String pdfFile = "/Users/skaki/Documents/workspace/salesica/src/com/salesica/textanalytics/tests/data/executivesummary.pdf";
	String pdfUrl = "http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf";
	
	@Test
	public void test() {
		List<String> cons = Concepts.SummarizeUrl(blogURL);
		for(String s : cons) {
			System.out.println(s);
		}
	}

	@Test
	public void test_pdf_file() {
		List<String> cons = Concepts.SummarizeDoc("PDF Document", pdf.fromFile(pdfFile));
		for(String s : cons) {
			System.out.println(s);
		}
	}

	@Test
	public void test_pdf_url() {
		List<String> cons = Concepts.SummarizeDoc("PDF Document", pdf.fromUrl(pdfUrl));
		for(String s : cons) {
			System.out.println(s);
		}
	}

}

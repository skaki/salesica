package com.salesica.textanalytics.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.salesica.textanalytics.Concepts;

public class TestConcepts {
	
	static final String text =  "Apple was founded by Steve Jobs, Steve Wozniak and Ronald Wayne.";
	static final String blogURL = "https://blog.percolate.com/2016/03/zero-based-budget-for-marketing/";
	
	@Test
	public void test_plain_text() {
		List<String> cons = Concepts.findConceptsInText(text);		
		System.out.print(cons);
	}
	
	@Test
	public void test_url() {
		List<String> cons = Concepts.findConceptsInUrl(blogURL);
		System.out.println(cons);
	}	
	
}

package com.salesica.rest.tests;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import org.springframework.web.client.RestTemplate;

import com.salesica.models.NlsModel;
import com.salesica.models.SlackPost;

public class nlp {

	public static final String REST_SERVICE_URI = "http://localhost:8080/v1/nlp";
	public static final RestTemplate restTemplate = new RestTemplate();
	static final String blogURL = "https://blog.percolate.com/2016/03/zero-based-budget-for-marketing/";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_concepts() {
		try {			
			
			JSONObject j = new JSONObject();
			
			j.put("type", "url.html");
			j.put("subject", blogURL);
	
			String s = restTemplate.getForObject(REST_SERVICE_URI + "/concepts?query=" +  j.toJSONString(), String.class);	
			System.out.println(s);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_summarize() {
		try {			
			
			JSONObject j = new JSONObject();
			
			j.put("type", "url.html");
			j.put("subject", blogURL);
	
			String s = restTemplate.getForObject(REST_SERVICE_URI + "/summarize?query=" + j.toJSONString(), String.class);	
			System.out.println(s);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
	}

}

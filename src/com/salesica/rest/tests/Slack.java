package com.salesica.rest.tests;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import org.springframework.web.client.RestTemplate;

import com.salesica.models.SlackPost;

public class Slack {

	public static final String REST_SERVICE_URI = "http://localhost:8080/slack";
	public static final RestTemplate restTemplate = new RestTemplate();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void gettest() {
		try {			
			
			SlackPost a = new SlackPost();
			a.setChannel("selling");
			a.setMessage("hello");
			
			URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/", a, SlackPost.class);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
	}

}

package com.salesica.rest.tests;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import org.springframework.web.client.RestTemplate;

import com.salesica.elastic.CrawlEntity;

public class Crawls {

	public static final String REST_SERVICE_URI = "http://localhost:8080/v1/crawls";
	public static final String CRAWL_URI = "http://localhost:8080";
	public static final RestTemplate restTemplate = new RestTemplate();
	
	@Before
	public void setUp() throws Exception {
		try {			
			
			CrawlEntity a = new CrawlEntity();
			a.setId("1001");
			a.setUri(CRAWL_URI);
			
			Collection<String> tags = new ArrayList<String>();
			
			tags.add("John Smith");
			tags.add("Engineer");
			a.setTags(tags);
			URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/", a, CrawlEntity.class);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gettest() {
		try {			
			CrawlEntity retVal = restTemplate.getForObject(REST_SERVICE_URI+"/1001", CrawlEntity.class);		
			assertEquals(retVal.getId(), "1001");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		restTemplate.delete(REST_SERVICE_URI+"/1001");		
	}

}

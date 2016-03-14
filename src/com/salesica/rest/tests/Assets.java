package com.salesica.rest.tests;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.salesica.elastic.AssetEntity;

import org.springframework.web.client.RestTemplate;

public class Assets {

	public static final String REST_SERVICE_URI = "http://localhost:8080/v1/assets";
	public static final RestTemplate restTemplate = new RestTemplate();
	
	@Before
	public void setUp() throws Exception {
		try {			
			
			AssetEntity a = new AssetEntity();
			a.setId("1001");
			a.setDescription("oh la la");
			a.setTitle("Piggy");
			
			Collection<String> tags = new ArrayList<String>();
			
			tags.add("High Tech");
			tags.add("Pharma");
			a.setTags(tags);
			URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/", a, AssetEntity.class);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gettest() {
		try {			
			AssetEntity retVal = restTemplate.getForObject(REST_SERVICE_URI+"/1001", AssetEntity.class);		
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

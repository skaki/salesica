package com.salesica.integrationtests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.Test;

import com.salesica.rest.BoxIntegration;

public class BoxIntegrationTest {

	@Test
	public void test() {
		BoxIntegration b = new BoxIntegration();
		try {
			System.out.println(b.QueryBox().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}

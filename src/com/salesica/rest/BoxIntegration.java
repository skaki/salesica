package com.salesica.rest;


import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestController
public class BoxIntegration {
    private static final String DEVELOPER_TOKEN = "MDthM4pfbWcylmiZPnIs3asStcpznASg";
    private static final int MAX_DEPTH = 1;

    private static final String USER_AGENT = "Mozilla/5.0";
    
    private static final String GET_URL = "http://localhost:9090/SpringMVCExample";
 
    private static final String POST_URL = "https://hooks.slack.com/services/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";
 
    @RequestMapping(value = "/getfilelist", method = RequestMethod.GET)
    public String getfilelist() throws JSONException, IOException {
    	
        final Logger log = Logger.getLogger(BoxIntegration.class.getName());

    	/*
    	JSONObject a = QueryBox();
    	*/
    	
    	JSONObject a = new JSONObject();
    	
    	a.put("FirstName", "John");
    	a.put("LastName", "Smith");
    	
        log.debug("Added REST Mapping");

    	return a.toString();
    }

    public JSONObject QueryBox() throws JSONException {
    	
    	final Logger log = Logger.getLogger("com.box.sdk");

        BoxAPIConnection api = new BoxAPIConnection(DEVELOPER_TOKEN);

        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();

        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
        JSONObject a = listFolder(rootFolder, 0);
        return a;
    }
        
    private JSONObject listFolder(BoxFolder folder, int depth) throws JSONException {
    	
    	JSONObject retVal = new JSONObject() ;

    	JSONArray list = new JSONArray() ;
    	
        for (BoxItem.Info itemInfo : folder) {
            String indent = "";
            for (int i = 0; i < depth; i++) {
                indent += "    ";
            }

            // System.out.println(indent + itemInfo.getName());
            list.put(itemInfo.getName());
            
            if (itemInfo instanceof BoxFolder.Info) {
                BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
                if (depth < MAX_DEPTH) {
                    list.put(listFolder(childFolder, depth + 1));
                }
            }
        }
        
        retVal.put("data", list);
        
        return retVal;
    }
    
    private static void sendPOST() throws IOException, JSONException {
    	 
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(POST_URL);
        httpPost.addHeader("User-Agent", USER_AGENT);
 
        JSONObject p = new JSONObject();
        
        p.put("channel", "#genereal");
        p.put("username", "webhookbot");
        p.put("text", "This is posted to #general and comes from a bot named webhookbot.");
        p.put("icon_emoji", ":ghost:");
        
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        // urlParameters.add("payload", p);
 
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);
 
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
 
        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));
 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
 
        // print result
        System.out.println(response.toString());
        httpClient.close();
 
    }
}
package com.salesica.rest;


import com.salesica.models.SlackPost;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;

@RestController
@RequestMapping(value = "/slack")
public class Slack {
 
	final Logger log = Logger.getLogger(Slack.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createasset(@RequestBody SlackPost slackpost, UriComponentsBuilder ucBuilder) throws JSONException, IOException {

    	SlackSession session = SlackSessionFactory.createWebSocketSlackSession("xoxb-25662817779-3SxckHJMMDyjG7AhDbXybuHp");
        session.connect();
        SlackChannel channel = session.findChannelByName(slackpost.getChannel());
        session.sendMessage(channel, slackpost.getMessage());
        session.disconnect();
         
    	HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}

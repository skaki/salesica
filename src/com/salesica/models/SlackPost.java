package com.salesica.models;

public class SlackPost {
	 private String channel;
	 private String message;
	 
	 public SlackPost() {}

	 public String getChannel() {
	        return channel;
	 }

     public void setChannel(String channel) {
        this.channel = channel;
     }
     
     public String getMessage() {
	        return message;
	 }

     public void setMessage(String message) {
    	 this.message = message;
     }
}

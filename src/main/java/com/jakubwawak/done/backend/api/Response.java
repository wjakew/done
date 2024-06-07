/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.api.health_endpoint.Health;
import com.jakubwawak.done.backend.api.token_endpoint.Token;
import com.jakubwawak.done.backend.entity.ApiToken;

import java.time.LocalDateTime;

/**
 * Object for storing API response
 */
public class Response {

    // objects stored as payload
    public ApiToken token;
    public Health health;

    // fields for storing request data
    public String endpoint_name;
    public String response_time;
    public String server_name;
    public String user_email;
    public String response_description;
    public String response_code;
    public String response_usedTokenKey;    // Token code used for the request

    /**
     * Default Constructor
     */
    public Response(String endpoint){
        token = null;
        health = null;
        this.endpoint_name = endpoint;
        this.response_time = LocalDateTime.now().toString();
        this.server_name = DoneApplication.SERVER_NAME;
        this.user_email = null;
        response_code = "response_created";
        response_description = "Response object created";
        response_usedTokenKey = null;
    }

    /**
     * Function for summarizing response after the object is created and filled with data
     */
    public void summarizeResponse(){
        //TODO
    }

}

/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.ApiToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Object for storing API response
 */
public class Response {

    public ApiToken token;

    // fields for storing request data
    public String endpoint_name;
    public String endpoint_method;
    public String response_time;
    public String server_name;
    public String user_email;
    public String response_description;
    public String response_code;
    public String response_usedTokenKey;    // Token code used for the request
    public Map<String,Object> body;

    /**
     * Default Constructor
     */
    public Response(String endpoint,String endpoint_method){
        token = null;
        this.endpoint_name = endpoint;
        this.endpoint_method = endpoint_method;
        this.response_time = LocalDateTime.now().toString();
        this.server_name = DoneApplication.SERVER_NAME;
        this.user_email = null;
        response_code = "response_created";
        response_description = "Response object created";
        response_usedTokenKey = null;
        body = new HashMap<>();
    }

    /**
     * Function for summarizing response after the object is created and filled with data
     */
    public void summarizeResponse(){
        //TODO
    }

}

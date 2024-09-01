/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.ApiToken;
import org.bson.Document;

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
     * Function for preparing document to save
     * @return Document
     */
    public Document prepareDocument(){
        Document document = new Document();
        document.append("token",token.prepareDocument());
        document.append("endpoint_name",endpoint_name);
        document.append("endpoint_method",endpoint_method);
        document.append("response_time",response_time);
        document.append("server_name",server_name);
        document.append("user_email",user_email);
        document.append("response_description",response_description);
        document.append("response_code",response_code);
        document.append("response_usedTokenKey",response_usedTokenKey);
        document.append("body",body);
        return document;
    }

    /**
     * Function for summarizing response after the object is created and filled with data
     */
    public void summarizeResponse(){
        if ( DoneApplication.enableAPILogging == 1 )
            DoneApplication.database.logRequestResponse(this);
    }

}

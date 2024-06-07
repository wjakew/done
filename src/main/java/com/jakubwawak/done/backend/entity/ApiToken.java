/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * Object for storing entity information about an API token
 */
public class ApiToken {
    public ObjectId token_id;      // Unique identifier for the token
    private ObjectId token_owner;   // Owner of the token (user ID)
    public String token_code;       // The actual token value
    public LocalDateTime token_time; // Timestamp when the token was created
    public String token_desc;       // Description of the token



    public int token_active;        // Flag for token activity

    /**
     * Default constructor
     */
    public ApiToken(DoneUser user) {
        token_id = null;     // New tokens don't have an ID yet
        token_owner = user.user_id;
        RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
        token_code = rwge.generateRandomString(30,true,false);
        token_time = LocalDateTime.now();
        token_desc = "Token for user " + user.user_email;
        token_active = 1;
    }

    /**
     * Constructor with database support
     * @param document
     */
    public ApiToken(Document document) {
        token_id = document.getObjectId("_id");
        token_owner = document.getObjectId("token_owner");
        token_code = document.getString("token_code");
        token_time = document.getDate("token_time").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        token_desc = document.getString("token_desc");
        token_active = document.getInteger("token_active");
    }

    /**
     * Function for preparing a document representation of the object
     * @return Document representing the API token
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("token_owner", token_owner);
        document.append("token_code", token_code);
        document.append("token_time", token_time);
        document.append("token_desc", token_desc);
        document.append("token_active", token_active);
        return document;
    }


    // Getters
    private String getTokenCode() { return token_code; }
    // Add more getters as needed
    public ObjectId getTokenOwner() { return token_owner; }
}

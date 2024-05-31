/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import com.jakubwawak.done.DoneApplication;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * Object for storing entity information about an API key
 */
public class ApiKey {
    public ObjectId api_key_id;  // Unique identifier for the API key
    public ObjectId user_id;
    public String api_key;
    public String api_code;
    public String api_description;
    public String api_created;
    public int readOnlyFlag;

    /**
     * Default constructor
     */
    public ApiKey(DoneUser user) {
        api_key_id = null;   // New API keys don't have an ID yet
        user_id = user.user_id;
        api_key = "";
        api_code = "";
        api_description = "";
        api_created = LocalDateTime.now().toString();
        readOnlyFlag = 0;
    }

    /**
     * Constructor with database support
     * @param document
     */
    public ApiKey(Document document){
        api_key_id = document.getObjectId("_id");
        user_id = document.getObjectId("user_id");
        api_key = document.getString("api_key");
        api_code = document.getString("api_code");
        api_description = document.getString("api_description");
        api_created = document.getString("api_created");
        readOnlyFlag = document.getInteger("readOnlyFlag");
    }

    // ... (Constructor from Document, similar to DoneTask) ...

    /**
     * Function for preparing a document representation of the object
     * @return Document representing the API key
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("user_id", user_id);
        document.append("api_key", api_key);
        document.append("api_code", api_code);
        document.append("api_description", api_description);
        document.append("api_created", api_created);
        document.append("readOnlyFlag", readOnlyFlag);
        return document;
    }

    // ... (compare method, similar to DoneTask) ...

    // Getters
    public String getApiKey() { return api_key; }
}

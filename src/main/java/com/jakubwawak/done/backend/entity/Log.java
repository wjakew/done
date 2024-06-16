/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Object for storing entity information about a log
 */
public class Log {
    public ObjectId log_id;  // Unique identifier for the log
    public String log_category; // Category of the log
    public String log_content; // Content of the log
    public Date log_time; // Time of the log
    public Date log_created; // Creation time of the log
    public String log_additionalinfo; // Additional info related to the log

    /**
     * Default constructor
     */
    public Log() {
        log_id = null;   // New logs don't have an ID yet
        log_category = "";
        log_content = "";
        log_time = new Date();
        log_created = new Date();
        log_additionalinfo = "";
    }

    /**
     * Constructor with database support
     * @param document
     */
    public Log(Document document){
        log_id = document.getObjectId("_id");
        log_category = document.getString("log_category");
        log_content = document.getString("log_content");
        log_time = document.getDate("log_time");
        log_created = document.getDate("log_created");
        log_additionalinfo = document.getString("log_additionalinfo");
    }

    /**
     * Function for preparing a document representation of the object
     * @return Document representing the log
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("log_category", log_category);
        document.append("log_content", log_content);
        document.append("log_time", log_time);
        document.append("log_created", log_created);
        document.append("log_additionalinfo", log_additionalinfo);
        return document;
    }
}
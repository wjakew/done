/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import com.jakubwawak.done.DoneApplication;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Object for storing entity information about a memory
 */
public class DoneMemory {
    public ObjectId memory_id;  // Unique identifier for the memory
    public Date memory_date;    // Date of the memory
    public String memory_quote; // Quote related to the memory
    public int memory_happystate; // State of happiness related to the memory
    public int memory_stresstate; // State of stress related to the memory
    public ObjectId user_id; // User ID related to the memory
    public String memory_note; // Note related to the memory
    public List<ObjectId> memory_connectednotes; // List of connected notes to the memory

    /**
     * Default constructor
     */
    public DoneMemory() {
        memory_id = null;   // New memories don't have an ID yet
        memory_date = new Date();
        memory_quote = "";
        memory_happystate = 0;
        memory_stresstate = 0;
        user_id = DoneApplication.loggedUser.user_id;
        memory_note = "";
        memory_connectednotes = new ArrayList<>();
    }

    /**
     * Constructor with database support
     * @param document
     */
    public DoneMemory(Document document){
        memory_id = document.getObjectId("_id");
        memory_date = document.getDate("memory_date");
        memory_quote = document.getString("memory_quote");
        memory_happystate = document.getInteger("memory_happystate");
        memory_stresstate = document.getInteger("memory_stresstate");
        user_id = document.getObjectId("user_id");
        memory_note = document.getString("memory_note");
        memory_connectednotes = (List<ObjectId>) document.get("memory_connectednotes");
    }

    /**
     * Function for preparing a document representation of the object
     * @return Document representing the memory
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("memory_date", memory_date);
        document.append("memory_quote", memory_quote);
        document.append("memory_happystate", memory_happystate);
        document.append("memory_stresstate", memory_stresstate);
        document.append("user_id", user_id);
        document.append("memory_note", memory_note);
        document.append("memory_connectednotes", memory_connectednotes);
        return document;
    }
}
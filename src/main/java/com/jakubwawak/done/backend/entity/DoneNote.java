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
 * Object for storing entity information about a note
 */
public class DoneNote {
    public ObjectId note_id;  // Unique identifier for the note
    public String note_title; // Title of the note
    public String note_raw; // Raw content of the note
    public Date note_creationtime; // Creation time of the note
    public ObjectId user_id; // User ID related to the note
    public List<ObjectId> note_connectedObjects; // List of connected objects to the note

    private boolean newFlag;

    /**
     * Default constructor
     */
    public DoneNote() {
        note_id = null;   // New notes don't have an ID yet
        note_title = "";
        note_raw = "";
        note_creationtime = new Date();
        user_id = DoneApplication.loggedUser.user_id;
        note_connectedObjects = new ArrayList<>();
        newFlag = true;
    }

    /**
     * Constructor with database support
     * @param document
     */
    public DoneNote(Document document){
        note_id = document.getObjectId("_id");
        note_title = document.getString("note_title");
        note_raw = document.getString("note_raw");
        note_creationtime = document.getDate("note_creationtime");
        user_id = document.getObjectId("user_id");
        note_connectedObjects = document.getList("note_connectedObjects",ObjectId.class);
        newFlag = false;
    }

    /**
     * Function for preparing a document representation of the object
     * @return Document representing the note
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("note_title", note_title);
        document.append("note_raw", note_raw);
        document.append("note_creationtime", note_creationtime);
        document.append("user_id", user_id);
        document.append("note_connectedObjects", note_connectedObjects);
        return document;
    }

    public boolean getNewFlag(){
        return newFlag;
    }

    public void setNewFlag(boolean newFlag){
        this.newFlag = newFlag;
    }
}

package com.jakubwawak.done.backend.entity;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Object for storing entity information about a journal task
 */
public class DoneTask {
    public ObjectId task_id;
    public ObjectId user_id;
    public String task_name;
    public String task_status;
    public String task_timestamp;
    public ArrayList<String> task_comments;

    /**
     * Default constructor
     */
    public DoneTask() {
        task_id = null;
        user_id = null;
        task_name = "";
        task_status = "";
        task_timestamp = "";
    }

    /**
     * Constructor with database support
     *
     * @param document Document containing task information
     */
    public DoneTask(Document document) {
        task_id = document.getObjectId("_id");
        user_id = document.getObjectId("user_id");
        task_name = document.getString("task_name");
        task_status = document.getString("task_status");
        task_timestamp = document.getString("task_timestamp");
    }

    /**
     * Function for preparing a document representation of the object
     *
     * @return Document representing the task
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("_id", task_id); // Use "_id" for MongoDB's default identifier field
        document.append("user_id", user_id);
        document.append("task_name", task_name);
        document.append("task_status", task_status);
        document.append("task_timestamp", task_timestamp);
        return document;
    }
}
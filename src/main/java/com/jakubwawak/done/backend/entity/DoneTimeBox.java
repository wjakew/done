/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.entity;

import com.jakubwawak.done.backend.database.DatabaseTask;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Entity object for storing time box information
 */
public class DoneTimeBox {

    public String timebox_name;
    public String timebox_description;
    public ObjectId user_id;
    public String timebox_created;
    public String timebox_dateSelected;

    public ArrayList<ObjectId> currentTaskObjectId;

    public ArrayList<DoneTask> currentTask;

    /**
     * Constructor
     */
    public DoneTimeBox(){
        timebox_name = "none";
        timebox_description = "none";
        user_id = new ObjectId();
        timebox_created = "none";
        timebox_dateSelected = "none";
        currentTaskObjectId = new ArrayList<>();
        currentTask = new ArrayList<>();
        loadTasks();
    }

    /**
     * Constructor with database support
     * @param document
     */
    public DoneTimeBox(Document document){
        timebox_name = document.getString("timebox_name");
        timebox_description = document.getString("timebox_description");
        user_id = document.getObjectId("user_id");
        timebox_created = document.getString("timebox_created");
        timebox_dateSelected = document.getString("timebox_dateSelected");
        currentTaskObjectId = (ArrayList<ObjectId>) document.get("currentTaskObjectId");
        currentTask = new ArrayList<>();
        loadTasks();
    }

    /**
     * Function for preparing document
     * @return Document
     */
    public Document prepareDocument(){
        Document doc = new Document("timebox_name",timebox_name)
                .append("timebox_description",timebox_description)
                .append("user_id",user_id)
                .append("timebox_created",timebox_created)
                .append("timebox_dateSelected",timebox_dateSelected)
                .append("currentTaskObjectId",currentTaskObjectId);
        return doc;
    }

    /**
     * Function for loading object tasks
     */
    void loadTasks(){
        currentTask.clear();
        DatabaseTask databaseTask = new DatabaseTask();
        for(ObjectId object : currentTaskObjectId){
            currentTask.add(databaseTask.getTaskById(object));
        }
    }

    /**
     * Function for adding tasks to the timebox
     * @param objectId
     */
    void addTask(ObjectId objectId){
        currentTaskObjectId.add(objectId);
        loadTasks();
    }

    /**
     * Function for removing tasks from the timebox
     * @param objectId
     */
    void removeTask(ObjectId objectId){
        currentTaskObjectId.remove(objectId);
        loadTasks();
    }
}

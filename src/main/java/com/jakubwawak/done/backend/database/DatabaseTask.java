/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Object for maintaining tasks on database
 */
public class DatabaseTask {

    Database database;

    /**
     * Constructor
     */
    public DatabaseTask(){
        this.database = DoneApplication.database;
    }

    /**
     * Function for adding task
     * @param doneTask
     * @return Integer
     */
    public int insertTask(DoneTask doneTask){
        try{
            MongoCollection<Document> task_collection = database.get_data_collection("done_task");
            if ( doneTask != null ){
                InsertOneResult result = task_collection.insertOne(doneTask.prepareDocument());
                if ( result.wasAcknowledged() ){
                    database.log("DB-TASK-INSERT","Created new task ("+result.getInsertedId().asObjectId().toString()+")");
                    return 1;
                }
                database.log("DB-TASK-INSERT-NULL","Tried to insert but database responded with result null!");
                return 0;
            }
            return -2;
        }catch(Exception ex){
            database.log("DB-TASK-INSERT-FAILED","Failed to insert task to database ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for updating a task in the database
     * @param task The task to be updated
     * @return boolean Returns true if the operation was successful, false otherwise
     */
    public boolean updateTask(DoneTask task) {
        try {
            // Get the collection
            MongoCollection<Document> collection = database.get_data_collection("done_task");

            // Prepare the document
            Document doc = task.prepareDocument();

            // Create the update operation
            Bson updateOperation = new Document("$set", doc);

            // Update the document
            UpdateResult result = collection.updateOne(eq("_id", task.task_id), updateOperation);

            // If we reach this point, the operation was successful
            if (result.wasAcknowledged()){
                database.log("DB-TASK-UPDATE","Updated task in database ("+task.task_id.toString()+")");
                return true;
            }
            else{
                database.log("DB-TASK-UPDATE-NULL","Tried to update but database responded with result null!");
                return false;
            }
        } catch (Exception e) {
            // Log the error
            database.log("DB-TASK-UPDATE-FAILED", "Failed to update task in database (" + e.toString() + ")");
            // The operation was not successful
            return false;
        }
    }

    /**
     * Function for loading a collection of DoneTask objects from the database
     * @return List<DoneTask> Returns a list of DoneTask objects
     */
    public List<DoneTask> loadTasks() {
        List<DoneTask> tasks = new ArrayList<>();
        try {
            // Get the collection
            MongoCollection<Document> collection = database.get_data_collection("done_task");
            // Find the tasks
            MongoCursor<Document> cursor = collection.find(Filters.eq("user_id", DoneApplication.loggedUser.user_id)).iterator();
            // Convert each document to a DoneTask object and add it to the list
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                DoneTask task = new DoneTask(doc);
                tasks.add(task);
            }
            database.log("DB-TASK-COLLECTION-LOAD","Loaded tasks from database ("+tasks.size()
                    +" tasks for user ("+DoneApplication.loggedUser.user_id.toString()+")");
        } catch (Exception e) {
            // Log the error
            database.log("DB-TASK-LOAD-FAILED", "Failed to load tasks from database (" + e.toString() + ")");
        }
        return tasks;
    }
}

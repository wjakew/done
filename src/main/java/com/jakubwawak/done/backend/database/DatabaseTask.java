/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.backend.entity.DoneTask;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

/**
 * Object for maintaining tasks on database
 */
public class DatabaseTask {

    Database database;

    /**
     * Constructor
     * @param database
     */
    public DatabaseTask(Database database){
        this.database = database;
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
                    database.log("DB-TASK-INSERT","Created new task ("+result.getInsertedId().toString()+")");
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
}

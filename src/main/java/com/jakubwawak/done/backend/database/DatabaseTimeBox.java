/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

/**
 * Object for managing time boxes objects in database
 */
public class DatabaseTimeBox {

    Database database;

    /**
     * Constructor
     */
    public DatabaseTimeBox(){
        this.database = DoneApplication.database;
    }

    /**
     * Function for inserting time box to database
     * @param timeBox
     * @return Integer
     */
    public int createTimeBox(DoneTimeBox timeBox){
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            InsertOneResult result = timebox_collection.insertOne(timeBox.prepareDocument());
            if ( result.wasAcknowledged() ){
                database.log("DB-TIMEBOX-INSERT","Time box inserted ("+result.getInsertedId().asObjectId().toString()+")");
                DoneApplication.databaseHistory.addHistoryEntry("task","Created new time box "+timeBox.timebox_name,"CREATE",result.getInsertedId().asObjectId().getValue(),timeBox.user_id);
                return 1;
            }
            else{
                database.log("DB-TIMEBOX-INSERT-FAILED","Failed to insert time box to database");
                return -1;
            }
        }catch(Exception e){
            database.log("DB-TIMEBOX-INSERT-FAILED","Failed to insert time box to database ("+e.toString()+")");
            return -1;
        }
    }

    /**
     * Function for updating time box object on database
     * @param timeBox
     * @return Integer
     */
    public int updateTimeBox(DoneTimeBox timeBox){
        try{
            MongoCollection<Document> collection = database.get_data_collection("done_timebox");
            Document doc = timeBox.prepareDocument();
            Bson updateOperation = new Document("$set",doc);
            UpdateResult result = collection.updateOne(eq("_id",timeBox.timebox_id),updateOperation);

            if ( result.wasAcknowledged() ){
                database.log("DB-TIMEBOX-UPDATE","Updated timebox in database ("+timeBox.timebox_id.toString()+")");
                DoneApplication.databaseHistory.addHistoryEntry("task","Updated time box "+timeBox.timebox_name,"UPDATE",timeBox.timebox_id,timeBox.user_id);
                return 1;
            }
            else{
                database.log("DB-TIMEBOX-UPDATE-NULL","Tried to update but database responded with result null!");
                return 0;
            }
        }catch(Exception ex){
            // Log the error
            database.log("DB-TIMEBOX-UPDATE-FAILED", "Failed to update time box in database (" + ex.toString() + ")");
            // The operation was not successful
            return -1;
        }
    }

    /**
     * Function for getting time box object from database
     * @param timebox_id
     * @return DoneTimeBox
     */
    public DoneTimeBox getTimeBox(ObjectId timebox_id){
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            Document doc = timebox_collection.find(eq("_id",timebox_id)).first();
            if ( doc != null ){
                return new DoneTimeBox(doc);
            }
            else{
                database.log("DB-TIMEBOX-GET-NULL","Time box not found ("+timebox_id.toString()+")");
                return null;
            }
        }catch(Exception e){
            database.log("DB-TIMEBOX-GET-FAILED","Failed to get time box from database ("+e.toString()+")");
            return null;
        }
    }

    /**
     * Function for removing time box from database
     * @param timebox_id
     * @return Integer
     */
    public int removeTimeBox(ObjectId timebox_id){
        DoneTimeBox timeBox = getTimeBox(timebox_id);
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            Document query = new Document("timebox_id",timebox_id);
            timebox_collection.deleteOne(query);
            return 1;
        }catch(Exception e){
            database.log("DB-TIMEBOX-REMOVE-FAILED","Failed to remove time box from database ("+e.toString()+")");
            DoneApplication.databaseHistory.addHistoryEntry("task","Removed time box ("+timebox_id.toString()+")","REMOVE",timebox_id,timeBox.timebox_id);
            return -1;
        }
    }

    /**
     * Function for removing task from all timebox objects
     * @param task_id
     * @return Integer
     */
    public int removeTaskFromAllTimeBoxes(ObjectId task_id){
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            Bson filter = Filters.exists("currentTaskObjectId"); // filter documents that have a 'tasks' field
            Bson update = new Document("$pull", new Document("currentTaskObjectId", task_id)); // pull task_id from 'tasks' array
            UpdateResult result = timebox_collection.updateMany(filter, update);
            if (result.wasAcknowledged()) {
                database.log("DB-TIMEBOX-REMOVE-TASK", "Removed task from all time boxes ("+task_id.toString()+"), amount "+result.getModifiedCount());
                return 1;
            } else {
                database.log("DB-TIMEBOX-REMOVE-TASK-NULL", "Tried to remove task but database responded with result null!");
                return 0;
            }
        } catch(Exception ex){
            database.log("DB-TIMEBOX-REMOVE-TASK-FAILED", "Failed to remove task from all time boxes (" + ex.toString() + ")");
            return -1;
        }
    }

    /**
     * Function for getting logged user time box collection
     * @return ArrayList
     */
    public ArrayList<DoneTimeBox> getLoggedUserTimeBoxList(){
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            ArrayList<DoneTimeBox> timeboxes = new ArrayList<>();
            for (Document doc : timebox_collection.find()){
                if ( doc.getObjectId("user_id").equals(DoneApplication.loggedUser.user_id)){
                    timeboxes.add(new DoneTimeBox(doc));
                }
            }
            database.log("DB-TIMEBOX-GET","Time boxes fetched ("+timeboxes.size()+")");
            return timeboxes;
        }catch(Exception e){
            database.log("DB-TIMEBOX-GET-FAILED","Failed to get time boxes from database ("+e.toString()+")");
            return null;
        }
    }

    /**
     * Function for getting time box counter for logged user
     * @return
     */
    public int getUserTimeBoxCount(){
        return getLoggedUserTimeBoxList().size();
    }
}

/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneTimeBox;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

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
     * Function for removing time box from database
     * @param timebox_id
     * @return Integer
     */
    public int removeTimeBox(ObjectId timebox_id){
        try{
            MongoCollection<Document> timebox_collection = database.get_data_collection("done_timebox");
            Document query = new Document("timebox_id",timebox_id);
            timebox_collection.deleteOne(query);
            return 1;
        }catch(Exception e){
            database.log("DB-TIMEBOX-REMOVE-FAILED","Failed to remove time box from database ("+e.toString()+")");
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
            return timeboxes;
        }catch(Exception e){
            database.log("DB-TIMEBOX-GET-FAILED","Failed to get time boxes from database ("+e.toString()+")");
            return null;
        }
    }
}

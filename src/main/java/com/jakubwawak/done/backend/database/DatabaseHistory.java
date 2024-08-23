/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Object for maintaining database history
 */
public class DatabaseHistory {

    Database database;

    /**
     * Constructor
     */
    public DatabaseHistory(){
        database = DoneApplication.database;
    }

    /**
     * Function for adding history entry
     * @param source
     * @param message
     * @param category
     * @param objectId
     * @return int
     */
    public int addHistoryEntry(String source, String message, String category, ObjectId objectId, ObjectId user_id){
        Document document = new Document();
        document.append("source", source);
        document.append("message", message);
        document.append("category", category);
        document.append("object_id", objectId);
        document.append("user_id", user_id);
        document.append("timestamp", System.currentTimeMillis());

        try{
            MongoCollection<Document> historyCollection = database.get_data_collection("done_history");
            InsertOneResult result = historyCollection.insertOne(document);
            if ( result.wasAcknowledged() ){
                DoneApplication.database.log("DONE-HISTORY-ADD","History entry added: " + document.toString());
                return 1;
            }
            else{
                DoneApplication.database.log("DONE-HISTORY-ADD-NOTACC","Failed to add history entry: " + document.toString());
                return 0;
            }
        }catch(Exception ex){
            DoneApplication.database.log("DONE-HISTORY-ADD-FAILED","Error: " + ex.toString());
            return -1;
        }
    }

    /**
     * Function for getting logged user history
     * @return ArrayList
     */
    public ArrayList<Document> getLoggedUserHistory() {
        try {
            MongoCollection<Document> historyCollection = database.get_data_collection("done_history");

            // Get the current date
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long startOfDay = calendar.getTimeInMillis();

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            long endOfDay = calendar.getTimeInMillis();

            // Query for today's entries
            ArrayList<Document> result = historyCollection.find(
                    Filters.and(
                            Filters.eq("user_id", DoneApplication.loggedUser.user_id),
                            Filters.gte("timestamp", startOfDay),
                            Filters.lt("timestamp", endOfDay)
                    )
            ).sort(Sorts.descending("timestamp")).into(new ArrayList<>());

            return result;
        } catch (Exception e) {
            DoneApplication.database.log("DONE-HISTORY-GET-FAILED", "Error: " + e.toString());
            return null;
        }
    }
}

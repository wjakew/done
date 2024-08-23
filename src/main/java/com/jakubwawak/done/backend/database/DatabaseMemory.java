/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.DoneMemory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DatabaseMemory {

    Database database;

    /**
     * Constructor
     */
    public DatabaseMemory (){
        this.database = DoneApplication.database;
    }

    /**
     * Function for creating memory
     * @param memory
     * @return
     */
    public int createMemory(DoneMemory memory){
        try{
            MongoCollection<Document> memoryCollection = database.get_data_collection("done_memory");
            if (memory != null){
                InsertOneResult result = memoryCollection.insertOne(memory.prepareDocument());
                if (result.wasAcknowledged()){
                    DoneApplication.database.log("DB-MEMORY-CREATE","Created new memory for user ("+memory.user_id.toString()+")");
                    DoneApplication.databaseHistory.addHistoryEntry("memory","Created new memory "+memory.memory_date.toString(),"CREATE",result.getInsertedId().asObjectId().getValue(),memory.user_id);
                    return 1;
                }
                else{
                    DoneApplication.database.log("DB-MEMORY-CREATE-EMPTY","Failed to create new memory for user ("+memory.user_id.toString()+")");
                    return 0;
                }
            }
            else{
                DoneApplication.database.log("DB-MEMORY-CREATE-NULL","Failed to create new memory for user (NULL)");
                return 0;
            }
        }catch (Exception e){
            DoneApplication.database.log("DB-MEMORY-CREATE-EXCEPTION",""+e.toString());
            return -1;
        }
    }

    /**
     * Function for updating memory
     * @param memory
     * @return Integer
     */
    public int updateMemory(DoneMemory memory){
        try{
            MongoCollection<Document> memoryCollection = database.get_data_collection("done_memory");
            if (memory != null){
                memoryCollection.replaceOne(Filters.eq("_id", memory.memory_id), memory.prepareDocument());
                DoneApplication.database.log("DB-MEMORY-UPDATE","Updated memory for user ("+memory.user_id.toString()+")");
                DoneApplication.databaseHistory.addHistoryEntry("memory","Updated memory "+memory.memory_date.toString(),"UPDATE",memory.memory_id,memory.user_id);
                return 1;
            }
            else{
                DoneApplication.database.log("DB-MEMORY-UPDATE-NULL","Failed to update memory for user (NULL)");
                return 0;
            }
        }catch (Exception e){
            DoneApplication.database.log("DB-MEMORY-UPDATE-EXCEPTION","Failed to update memory: "+e.toString());
            return -1;
        }
    }

    /**
     * Function for getting today's memory for logged user
     * @param userId
     * @return DoneMemory
     */
    public DoneMemory getTodaysMemoryForLoggedUser(ObjectId userId){
        MongoCollection<Document> memoryCollection = database.get_data_collection("done_memory");

        FindIterable<Document> documents = memoryCollection.find(Filters.eq("user_id", userId));

        for (Document document : documents) {
            Date memoryDate = document.getDate("memory_date");
            LocalDate localMemoryDate = memoryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (localMemoryDate.equals(LocalDate.now())) {
                DoneApplication.database.log("DB-MEMORY-TODAY-GET","Got today's memory for user ("+userId.toString()+")");
                return new DoneMemory(document);
            }
        }
        DoneApplication.database.log("DB-MEMORY-TODAY-GET-NULL","Memory from today is not in the database for user ("+userId.toString()+")");
        return null;
    }

    /**
     * Function for getting collection of memories for logged user
     * @return ArrayList
     */
    public ArrayList<DoneMemory> getCollectionOfMemoriesLoggedUser(){
        MongoCollection<Document> memoryCollection = database.get_data_collection("done_memory");
        ArrayList<DoneMemory> mems = new ArrayList<>();
        FindIterable<Document> documents = memoryCollection.find(Filters.eq("user_id", DoneApplication.loggedUser.user_id));
        for (Document document : documents) {
            mems.add(new DoneMemory(document));
        }
        Collections.reverse(mems);
        return mems;
    }

    /**
     * Function for getting memory by id
     * @return ArrayList
     */
    public ArrayList<Double> calculateUserMood(){
        ArrayList<Double> data = new ArrayList<>();
        ArrayList<DoneMemory> memories = getCollectionOfMemoriesLoggedUser();
        double happy_Mood = 0;
        double stress_Mood = 0;
        double count = 0;
        for (DoneMemory memory : memories){
            happy_Mood += memory.memory_happystate;
            stress_Mood += memory.memory_stresstate;
            count++;
        }
        if ( count != 0 ){
            happy_Mood = happy_Mood/count;
        }
        data.add(round(happy_Mood/count,2));
        data.add(round(stress_Mood/count,2));
        return data;
    }

    double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

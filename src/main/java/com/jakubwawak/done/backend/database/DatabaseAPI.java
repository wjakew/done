/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.ApiKey;
import com.jakubwawak.done.backend.entity.DoneUser;
import com.jakubwawak.done.backend.maintanance.RandomWordGeneratorEngine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class DatabaseAPI {

    Database database;

    /**
     * Constructor
     */
    public DatabaseAPI(){
        database = DoneApplication.database;
    }

    /**
     * Function for creating API key for user
     * @return Integer
     */
    public int createAPIKeyForLoggedUser(){
        ApiKey apiKey = new ApiKey(DoneApplication.loggedUser);
        RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
        apiKey.api_key = rwge.generateRandomString(30,true,false);
        apiKey.api_code = rwge.generateRandomString(45,true,false);
        apiKey.api_description = "User API key ("+DoneApplication.loggedUser.user_email+")";
        apiKey.api_created = LocalDateTime.now().toString();
        // inserting to database
        try{
            MongoCollection<Document> apiKey_collection = database.get_data_collection("done_api");
            InsertOneResult result = apiKey_collection.insertOne(apiKey.prepareDocument());
            if ( result.wasAcknowledged()){
                database.log("DB-API-INSERT","API key inserted ("+apiKey.api_key+")");
                return 1;
            }
            database.log("DB-API-INSERT-FAILED","Failed to insert API key");
            return -1;
        }catch(Exception ex){
            database.log("DB-API-INSERT-FAILED","Failed to insert api to database ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for retrieving API key for given user
     * @param user_id
     * @return ApiKey
     */
    public ApiKey getUserApiKey(ObjectId user_id){
        try{
            MongoCollection<Document> apiKey_collection = database.get_data_collection("done_api");
            Document query = new Document("user_id",user_id);
            Document result = apiKey_collection.find(query).first();
            if ( result != null ){
                return new ApiKey(result);
            }
            database.log("DB-API-GET-NOAPI","Cannot find api key for user: "+user_id);
            return null;
        }catch(Exception ex){
            database.log("DB-API-GET-FAILED","Failed to get api key for user: "+user_id+" ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for validating api key pair and returning user_id
     * @param apiKey
     * @param apiCode
     * @return Integer
     */
    public ObjectId validateAPIKeyPair(String apiKey, String apiCode){
        try{
            MongoCollection<Document> apiKey_collection = database.get_data_collection("done_api");
            Document query = new Document("api_key",apiKey);
            Document result = apiKey_collection.find(query).first();
            if ( result != null ){
                if ( result.getString("api_code").equals(apiCode)){
                    database.log("DB-API-VALIDATE","API KEY VALIDATION");
                    return result.getObjectId("user_id");
                }
                database.log("DB-API-VALIDATE-FAILED","API Codes PAIR not matching database records! ("+apiKey+"/"+apiCode+")");
                return null;
            }
            database.log("DB-API-GET-NOAPI","Cannot find api key for pair ("+apiKey+"/"+apiCode+")");
            return null;
        }catch(Exception ex){
            database.log("DB-API-GET-FAILED","Failed to get api key for pair ("+apiKey+"/"+apiCode+") ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for creating api key for given user
     * @param user_email
     * @return Integer
     */
    public int createApiKey(String user_email){
        DatabaseUser du = new DatabaseUser();
        DoneUser user = du.getUserByEmail(user_email);
        if ( user != null ){
            ApiKey apiKey = new ApiKey(user);
            RandomWordGeneratorEngine rwge = new RandomWordGeneratorEngine();
            apiKey.api_key = rwge.generateRandomString(30,true,false);
            apiKey.api_key = apiKey.api_key.toUpperCase();
            apiKey.api_code = rwge.generateRandomString(45,true,false);
            apiKey.api_code = apiKey.api_code.toUpperCase();
            apiKey.api_description = "User API key ("+user.user_email+")";
            apiKey.user_id = user.user_id;
            apiKey.api_created = LocalDateTime.now().toString();
            // inserting to database
            try{
                MongoCollection<Document> apiKey_collection = database.get_data_collection("done_api");
                InsertOneResult result = apiKey_collection.insertOne(apiKey.prepareDocument());
                if ( result.wasAcknowledged()){
                    database.log("DB-API-INSERT","API key inserted ("+apiKey.api_key+"), code: "+apiKey.api_code + " for user: "+user.user_email);
                    return 1;
                }
                database.log("DB-API-INSERT-FAILED","Failed to insert API key");
                return -1;
            }catch(Exception ex){
                database.log("DB-API-INSERT-FAILED","Failed to insert api to database ("+ex.toString()+")");
                return -1;
            }
        }
        database.log("DB-API-INSERT-NOUSER","Cannot find user to insert api key for");
        return -2;
    }

    /**
     * Function for removing api key for user
     * @param user_email
     * @return
     */
    public int removeApiKey(String user_email){
        DatabaseUser du = new DatabaseUser();
        DoneUser user = du.getUserByEmail(user_email);
        if ( user != null ){
            try{
                MongoCollection<Document> apiKey_collection = database.get_data_collection("done_api");
                Document query = new Document("user_id",user.user_id);
                apiKey_collection.deleteOne(query);
                database.log("DB-API-DELETE","API key deleted for user: "+user.user_email);
                return 1;
            }catch(Exception ex){
                database.log("DB-API-DELETE-FAILED","Failed to delete api key for user: "+user.user_email+" ("+ex.toString()+")");
                return -1;
            }
        }
        database.log("DB-API-DELETE-NOUSER","Cannot find user to delete api key for");
        return -2;
    }
}

/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.database;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.entity.ApiToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.model.Updates;

/**
 * Object for creating token database
 */
public class DatabaseToken {

    Database database;

    /**
     * Constructor
     */
    public DatabaseToken(){
        database = DoneApplication.database;
    }

    /**
     * Function for creating token
     * @param apiKey
     * @param apiCode
     * @return int
     */
    public ApiToken createToken(String apiKey, String apiCode){
        DatabaseAPI databaseAPI = new DatabaseAPI();
        DatabaseUser databaseUser = new DatabaseUser();
        ObjectId user_id = databaseAPI.validateAPIKeyPair(apiKey,apiCode);
        if ( user_id != null ){
            // user found, create token
            ApiToken apiToken = new ApiToken(databaseUser.getUserByID(user_id));
            MongoCollection collection = database.get_data_collection("done_token");
            InsertOneResult result = collection.insertOne(apiToken.prepareDocument());
            if ( result.wasAcknowledged() ){
                database.log("DB-TOKEN-INSERT","Token created for user ("+user_id.toString()+") ID: "+result.getInsertedId().asObjectId().toString());
                apiToken.token_id = result.getInsertedId().asObjectId().getValue();
                return apiToken;
            }
            else{
                database.log("DB-TOKEN-INSERT-FAILED","Failed to insert token for user ("+user_id.toString()+")");
                return null;
            }
        }
        else{
            database.log("DB-TOKEN-INSERT-FAILED","Failed to create token for user");
            return null;
        }
    }

    /**
     * Function for deactivating all tokens for a given user
     * @param user_id
     * @return boolean
     */
    public boolean deactivateAllUserTokens(ObjectId user_id) {
        MongoCollection<Document> collection = database.get_data_collection("done_token");

        // Create a filter to select all documents with the given user_id
        Bson filter = Filters.eq("token_owner", user_id);

        // Create an update operation to set token_active to 0
        Bson update = Updates.set("token_active", 0);

        // Execute the update operation
        UpdateResult result = collection.updateMany(filter, update);

        if (result.wasAcknowledged()) {
            database.log("DB-TOKEN-DEACTIVATE", "Deactivated all tokens for user (" + user_id.toString() + ")");
            return true;
        } else {
            database.log("DB-TOKEN-DEACTIVATE-FAILED", "Failed to deactivate tokens for user (" + user_id.toString() + ")");
            return false;
        }
    }

}


package com.jakubwawak.done.datamanager;

import com.jakubwawak.done.backend.database.DatabaseAPI;
import com.jakubwawak.done.backend.entity.ApiKey;
import org.bson.types.ObjectId;

/**
 * Object for managing API data
 */
public class ApiManager {

    private DatabaseAPI databaseAPI;

    /**
     * Constructor
     */
    public ApiManager() {
        this.databaseAPI = new DatabaseAPI();
    }

    /**
     * Function for creating API key for logged user
     * @return Integer
     */
    public int createAPIKeyForLoggedUser() {
        return databaseAPI.createAPIKeyForLoggedUser();
    }

    /**
     * Function for retrieving API key for given user
     * @param userId
     * @return ApiKey
     */
    public ApiKey getUserApiKey(ObjectId userId) {
        return databaseAPI.getUserApiKey(userId);
    }

    /**
     * Function for validating API key pair
     * @param apiKey
     * @param apiCode
     * @return ObjectId
     */
    public ObjectId validateAPIKeyPair(String apiKey, String apiCode) {
        return databaseAPI.validateAPIKeyPair(apiKey, apiCode);
    }

    /**
     * Function for creating API key
     * @param userEmail
     * @return Integer
     */
    public int createApiKey(String userEmail) {
        return databaseAPI.createApiKey(userEmail);
    }

    /**
     * Function for removing API key
     * @param userEmail
     * @return Integer
     */
    public int removeApiKey(String userEmail) {
        return databaseAPI.removeApiKey(userEmail);
    }
}
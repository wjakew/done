/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.DatabaseAPI;
import com.jakubwawak.done.backend.database.DatabaseToken;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.ApiToken;
import com.jakubwawak.done.backend.entity.DoneUser;
import org.bson.types.ObjectId;

/**
 * Function for managing token data
 */
public class TokenManager {

    /**
     * Function for rolling token for request
     * @param apiKey
     * @param apiCode
     * @return
     */
    public ApiToken rollTokenForRequest(String apiKey, String apiCode){
        DatabaseAPI databaseAPI = new DatabaseAPI();
        DatabaseToken databaseToken = new DatabaseToken();

        // checking if api pair is valid, checking user
        ObjectId user_id = databaseAPI.validateAPIKeyPair(apiKey,apiCode);

        if ( user_id != null ){
            // user is valid
            databaseToken.deactivateAllUserTokens(user_id);
            ApiToken apiToken = databaseToken.createToken(apiKey,apiCode);
            if ( apiToken != null ){
                DoneApplication.database.log("TOKEN-MANAGER-ROLL","Token rolled for user ("+user_id.toString()+")");
                return apiToken;
            }
            else{
                DoneApplication.database.log("TOKEN-MANAGER-ROLL-FAILED","Failed to roll token for user ("+user_id.toString()+")");
                return null;
            }
        }
        DoneApplication.database.log("TOKEN-MANAGER-ROLL","Cannot roll token, invalid api pair ("+apiKey+" "+apiCode+")");
        return null;
    }

    /**
     * Function for rolling token for user
     * @param user_email
     * @return
     */
    public ApiToken rollTokenForUser(String user_email){
        DatabaseToken databaseToken = new DatabaseToken();
        DatabaseUser databaseUser = new DatabaseUser();
        DoneUser user = databaseUser.getUserByEmail(user_email);
        if ( user != null ){
            databaseToken.deactivateAllUserTokens(user.user_id);
            ApiToken apiToken = databaseToken.createToken(user.user_id);
            if ( apiToken != null ){
                DoneApplication.database.log("TOKEN-MANAGER-ROLL","Token rolled for user ("+user.user_id.toString()+")");
                DoneApplication.database.log("TOKEN-MANAGER-INFO","Token ("+apiToken.token_code+") rolled for user ("+user.user_id.toString()+")");
                return apiToken;
            }
            DoneApplication.database.log("TOKEN-MANAGER-ROLL-FAILED","Failed to roll token for user ("+user.user_id.toString()+")");
            return null;
        }
        else{
            DoneApplication.database.log("TOKEN-MANAGER-ROLL-FAILED","Failed to roll token for user ("+user_email+")");
            return null;
        }
    }

    /**
     * Function for validating token
     * @param token
     * @return String
     */
    public ApiToken validateToken(String token){
        DatabaseToken databaseToken = new DatabaseToken();
        ApiToken apiToken = databaseToken.getToken(token);
        if ( apiToken != null ){
            DoneApplication.database.log("TOKEN-MANAGER-VALIDATE","Token validated for user ("+apiToken.getTokenOwner().toString()+")");
            return apiToken;
        }
        DoneApplication.database.log("TOKEN-MANAGER-VALIDATE","Token validation failed for token ("+token+")");
        return null;
    }

}

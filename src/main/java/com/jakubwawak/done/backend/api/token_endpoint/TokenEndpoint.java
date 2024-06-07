/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.token_endpoint;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.api.Response;
import com.jakubwawak.done.backend.database.DatabaseUser;
import com.jakubwawak.done.backend.entity.ApiToken;
import com.jakubwawak.done.datamanager.TokenManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Object for creating token endpoint
 */
@RestController
public class TokenEndpoint {

    @GetMapping("/api/token/create/{api_key}/{api_code}")
    public Response createToken(@PathVariable String api_key, @PathVariable String api_code){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.rollTokenForRequest(api_key,api_code);
        Response response = new Response("/api/token/create/{api_key}/{api_code}");
        if ( userToken != null ){
            response.token = userToken;
            response.response_code = "token_created";
            response.response_description = "Created token for API call";
            DatabaseUser databaseUser = new DatabaseUser();
            response.user_email = databaseUser.getUserByID(userToken.getTokenOwner()).user_email;
        }
        else{
            response.response_code = "token_not_created";
            response.response_description = "Wrong api pair";
        }
        return response;
    }
}

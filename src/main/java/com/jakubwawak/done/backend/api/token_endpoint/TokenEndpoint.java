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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Object for creating token endpoint
 */
@RestController
public class TokenEndpoint {

    /**
     * Function for creating token for given api key and api code
     * @param body
     * @return Response
     * required body:
     * {
     *     "api_key":"api_key",
     *     "api_code":"api_code"
     * }
     * response_codes:
     * token_created - token created
     * token_not_created - wrong api pair
     */
    @GetMapping("/api/v1/token/create")
    public Response createToken(@RequestBody Map<String,String> body){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.rollTokenForRequest(body.get("api_key"),body.get("api_code"));
        Response response = new Response("/api/v1/token/create","GET");
        try{
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
        } catch (Exception e){
            response.response_code = "token_error";
            response.response_description = "Error ("+e.toString()+")";
            response.user_email = "none";
        }
        return response;
    }

    /**
     * Function for validating token
     * @param token
     * @return Response
     * required request header:
     * token - token to validate
     * response_codes:
     * token_valid - token is valid
     * token_notactive - token is not active
     * token_invalid - token is invalid
     * token_error - API error
     */
    @GetMapping("/api/v1/token/validate")
    public Response validateToken(@RequestHeader String token){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/v1/token/validate","GET");
        try{
            if ( userToken != null ){
                if ( userToken.token_active == 1 ){
                    response.token = userToken;
                    response.response_code = "token_valid";
                    response.response_description = "Token is valid";
                    DatabaseUser databaseUser = new DatabaseUser();
                    response.user_email = databaseUser.getUserByID(userToken.getTokenOwner()).user_email;
                }
                else{
                    response.token = null;
                    response.response_code = "token_notactive";
                    response.response_description = "token is not active";
                    response.user_email = "none";
                }
            }
            else{
                response.token = null;
                response.response_code = "token_invalid";
                response.response_description = "Token is invalid";
                response.user_email = "none";
            }
        }catch (Exception e){
            response.response_code = "token_error";
            response.response_description = "Error ("+e.toString()+")";
            response.user_email = "none";
        }
        return response;
    }
}

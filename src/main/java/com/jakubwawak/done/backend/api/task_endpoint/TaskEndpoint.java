/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.task_endpoint;

import com.jakubwawak.done.backend.api.Response;
import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.ApiToken;
import com.jakubwawak.done.backend.entity.DoneTask;
import com.jakubwawak.done.datamanager.TokenManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Endpoint for task maintaining
 */
@RestController
public class TaskEndpoint {

    /**
     * Function for creating task
     * @param token
     * @param body
     * @return Response
     * required request header:
     * token - token to validate
     * required body:
     * {
     *    "task_name":"task_name"
     * }
     * response_codes:
     * task_created - task created
     * task_not_created - failed to create task
     * token_notactive - token is not active
     * token_invalid - token is invalid
     * task_error - API error
     */
    @PostMapping("/api/v1/task/create")
    public Response createTask(@RequestHeader String token, @RequestBody Map<String,String> body){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/task/create","POST");
        try{
            if ( userToken != null ){
                if ( userToken.token_active == 1 ){
                    DatabaseTask databaseTask = new DatabaseTask();
                    DoneTask task = new DoneTask(userToken.getTokenOwner());
                    task.task_name = body.get("task_name");
                    int result = databaseTask.insertTask(task);
                    if ( result == 1 ){
                        response.response_code = "task_created";
                        response.response_description = "Task created";
                    }
                    else{
                        response.response_code = "task_not_created";
                        response.response_description = "Failed to create task";
                    }
                }
                else{
                    response.response_code = "token_notactive";
                    response.response_description = "Token is not active";
                }
            }
            else{
                response.response_code = "token_invalid";
                response.response_description = "Token is invalid";
            }
        } catch (Exception e){
            response.response_code = "task_error";
            response.response_description = "Error ("+e.toString()+")";
        }
        return response;
    }

    /**
     * Function for listing tasks
     * @param token
     * @return Response
     * required request header
     */
    @GetMapping("/api/v1/task/list")
    public Response listTasks(@RequestHeader String token){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/v1/task/list","GET");
        try{
            if ( userToken != null ){
                if ( userToken.token_active == 1 ){
                    DatabaseTask databaseTask = new DatabaseTask();
                    for(DoneTask task : databaseTask.loadUserTasks(userToken.getTokenOwner())){
                        response.body.put(task.task_id.toString(),task.prepareDocument());
                    }
                    response.response_code = "task_list_created";
                    response.response_description = "Task collection created";
                }
                else{
                    response.response_code = "token_notactive";
                    response.response_description = "Token is not active";
                }
            }
            else{
                response.response_code = "token_invalid";
                response.response_description = "Token is invalid";
            }
        } catch (Exception e){
            response.response_code = "task_error";
            response.response_description = "Error ("+e.toString()+")";
        }
        return response;
    }

}

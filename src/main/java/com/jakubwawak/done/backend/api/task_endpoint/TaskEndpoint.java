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
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Endpoint for task maintaining
 */
@RestController
public class TaskEndpoint {

    /**
     * Endpoint for creating task
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
        response.summarizeResponse();
        return response;
    }

    /**
     * Endpoint for updating task
     * @param token
     * @param body
     * @return Response
     * required request header
     * required body with whole task data
     */
    @PostMapping("/api/v1/task/update")
    public Response updateTask(@RequestHeader String token, @RequestBody Map<String,Object> body){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/v1/task/update","POST");
        try {
            if (userToken != null) {
                if (userToken.token_active == 1) {
                    DatabaseTask databaseTask = new DatabaseTask();
                    DoneTask task = new DoneTask(body);
                    boolean result = databaseTask.updateTask(task);
                    if (result) {
                        response.response_code = "task_updated";
                        response.response_description = "Task updated";
                    } else {
                        response.response_code = "task_not_updated";
                        response.response_description = "Failed to update task";
                    }
                } else {
                    response.response_code = "token_notactive";
                    response.response_description = "Token is not active";
                }
            } else {
                response.response_code = "token_invalid";
                response.response_description = "Token is invalid";
            }
        }catch(Exception e){
            response.response_code = "task_error";
            response.response_description = "Error ("+e.toString()+")";
        }
        response.summarizeResponse();
        return response;
    }


    /**
     * Endpoint for changing task status
     * @param token
     * @param body
     * @return Response
     * required request header
     * required body:
     * {
     *     "task_id": ObjectID,
     *     "task_status", String
     * }
     */
    @PostMapping("/api/v1/task/change-status")
    public Response changeTaskStatus(@RequestHeader String token, @RequestBody Map<String,Object> body){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/v1/task/change-status","POST");
        try {
            if (userToken != null) {
                if (userToken.token_active == 1) {
                    DatabaseTask databaseTask = new DatabaseTask();
                    ObjectId task_id = (ObjectId) body.get("task_id");
                    String task_status = (String) body.get("task_status");
                    String[] statusList = {"NEW","IN PROGRESS","DONE"};
                    List<String> statusCollection = Arrays.asList(statusList);

                    if ( !statusCollection.contains(task_status) ){
                        response.response_code = "task_status_invalid";
                        response.response_description = "Task status is invalid";
                        return response;
                    }

                    boolean result = databaseTask.updateTaskStatus(task_id,task_status);
                    if (result) {
                        response.response_code = "task_status_changed";
                        response.response_description = "Task status changed";
                    } else {
                        response.response_code = "task_status_not_changed";
                        response.response_description = "Failed to change task status";
                    }
                } else {
                    response.response_code = "token_notactive";
                    response.response_description = "Token is not active";
                }
            } else {
                response.response_code = "token_invalid";
                response.response_description = "Token is invalid";
            }
        }catch(Exception e){
            response.response_code = "task_error";
            response.response_description = "Error ("+e.toString()+")";
        }
        response.summarizeResponse();
        return response;
    }

    /**
     * Endpoint for listing tasks
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
        response.summarizeResponse();
        return response;
    }
}

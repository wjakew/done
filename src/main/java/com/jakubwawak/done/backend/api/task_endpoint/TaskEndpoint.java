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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for task maintaining
 */
@RestController
public class TaskEndpoint {

    @GetMapping("/api/task/create/{token}/{task_title}")
    public Response createTask(@PathVariable String token, @PathVariable String task_title){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/task/create/{token}/{task_title}");
        if ( userToken != null ){
            if ( userToken.token_active == 1 ){
                DatabaseTask databaseTask = new DatabaseTask();
                DoneTask task = new DoneTask(userToken.getTokenOwner());
                task.task_name = task_title;
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
        return response;
    }

    @GetMapping("/api/task/list/{token}")
    public Response listTasks(@PathVariable String token){
        TokenManager tokenManager = new TokenManager();
        ApiToken userToken = tokenManager.validateToken(token);
        Response response = new Response("/api/task/create/{token}/{task_title}");
        if ( userToken != null ){
            if ( userToken.token_active == 1 ){
                DatabaseTask databaseTask = new DatabaseTask();
                response.taskList = databaseTask.loadUserTasks(userToken.getTokenOwner());
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
        return response;
    }

}

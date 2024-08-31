/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.health_endpoint;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.api.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Function for handling health endpoint
 */
@RestController
public class HealthEndpoint {

    /**
     * Function for getting health status
     * no body required
     * no request header required
     * @return Response
     */
    @GetMapping("/api/health")
    public Response getHealth() {
        Response response = new Response("/api/health", "GET");
        response.response_code = "health_ok";
        response.response_description = "Health endpoint working";
        response.body.put("database_connected", DoneApplication.database.connected);
        response.body.put("server_name", DoneApplication.SERVER_NAME);
        return response;
    }
}

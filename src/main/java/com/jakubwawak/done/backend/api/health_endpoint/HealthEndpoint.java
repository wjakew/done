/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.health_endpoint;

import com.jakubwawak.done.backend.api.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Function for handling health endpoint
 */
@RestController
public class HealthEndpoint {

    @GetMapping("/api/health")
    public Response getHealth() {
        Response response = new Response("/api/health");
        response.health = new Health();
        response.response_description = "health endpoint response created";
        return response;
    }
}

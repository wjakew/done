/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Function for handling health endpoint
 */
@RestController
public class HealthEndpoint {

    @GetMapping("/api/health")
    public Health getHealth() {
        return new Health();
    }
}

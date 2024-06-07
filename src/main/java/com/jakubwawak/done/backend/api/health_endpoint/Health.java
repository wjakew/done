/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.backend.api.health_endpoint;

import com.jakubwawak.done.DoneApplication;

/**
 * Object for storing health information
 */
public class Health {

    public String applicationStatus;
    public String runtimeStatus;
    public String databaseStatus;
    public String mongoDbURLStatus;

    /**
     * Constructor
     */
    public Health(){
        applicationStatus = "ready_run";
        runtimeStatus = DoneApplication.runTime;
        databaseStatus = Boolean.toString(DoneApplication.database.connected);
        mongoDbURLStatus = DoneApplication.database.database_url;
    }
}

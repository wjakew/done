/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.Database;
import com.jakubwawak.done.backend.database.DatabaseTask;

/**
 * Function for managing task data on database
 */
public class TaskDataManager {

    DatabaseTask databaseTask;

    /**
     * Constructor
     * @param database
     */
    public TaskDataManager(Database database){
        this.databaseTask = new DatabaseTask(DoneApplication.database);
    }
}

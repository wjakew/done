/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all right reserved
 */
package com.jakubwawak.done.datamanager;

import com.jakubwawak.done.DoneApplication;
import com.jakubwawak.done.backend.database.Database;
import com.jakubwawak.done.backend.database.DatabaseTask;
import com.jakubwawak.done.backend.entity.DoneTask;

/**
 * Function for managing task data on database
 */
public class TaskDataManager {

    DatabaseTask databaseTask;

    /**
     * Constructor
     * @param database
     */
    public TaskDataManager(){
        this.databaseTask = new DatabaseTask(DoneApplication.database);
    }

    /**
     * Function for inserting task to database
     * @param task_name
     * @return Integer
     */
    public int insertTask(String task_name){
        DoneTask doneTask = new DoneTask(task_name);
        if ( doneTask != null ){
            int ans = databaseTask.insertTask(doneTask);
            if (ans == 1){
                DoneApplication.notificationService("Added new task: "+task_name,1);
                // TODO update the task list in UI
                return 1;
            }
            else{
                DoneApplication.notificationService("Something went wrong, check database log!",2);
            }
        }
        DoneApplication.notificationService("Task returned null, nothing to add",1);
        return -1;
    }
}
